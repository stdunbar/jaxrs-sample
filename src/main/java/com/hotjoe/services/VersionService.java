package com.hotjoe.services;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hotjoe.services.logging.Logged;
import com.hotjoe.services.model.VersionInfo;

import com.hotjoe.services.exception.NotFoundServiceException;

/**
 * This class is used for allow a caller to get Git build information for the currently running code.  It does
 * this with the help of the maven git commit id plugin (https://github.com/ktoso/maven-git-commit-id-plugin).  The
 * plugin generates a Java .properties file that is placed in the root of the WEB-INF/classes directory that can
 * be then accessed by this class using <pre>getResourceAsStream</pre>.
 *
 */
@Path("/v1/version")
public class VersionService {
    private static final Logger logger = LoggerFactory.getLogger(VersionService.class);

    @Logged
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/summary")
    public Response getTextStatus() {

        try {
            Properties properties = getGitProperties();

            return Response.ok("built \"" + properties.getProperty("git.build.time") +
                                "\" in branch \"" + properties.getProperty("git.branch") +
                                "\" with short commit id \"" + properties.getProperty("git.commit.id.describe-short") + "\"").build();
        }
        catch( IOException ioe ) {
            logger.error( "can't locate git.properties on the class path");
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Can't load properties").build();
    }

    @Logged
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getFullStatus() {

        try {
            Properties properties = getGitProperties();

            VersionInfo versionInfo = new VersionInfo();

            versionInfo.withBranch(properties.getProperty("git.branch"))
                    .withBuildHost(properties.getProperty("git.build.host"))
                    .withBuildTime(properties.getProperty("git.build.time"))
                    .withBuildUserEmail(properties.getProperty("git.build.user.email"))
                    .withBuildUserName(properties.getProperty("git.build.user.name"))
                    .withBuildVersion(properties.getProperty("git.build.version"))
                    .withCommitId(properties.getProperty("git.commit.id"))
                    .withCommitIdAbbrev(properties.getProperty("git.commit.id.abbrev"))
                    .withCommitIdDescribe(properties.getProperty("git.commit.id.describe"))
                    .withCommitIdDescribeShort(properties.getProperty("git.commit.id.describe-short"))
                    .withCommitMessageFull(properties.getProperty("git.commit.message.full"))
                    .withCommitMessageShort(properties.getProperty("git.commit.message.short"))
                    .withCommitTime(properties.getProperty("git.commit.time"))
                    .withCommitUserEmail(properties.getProperty("git.commit.user.email"))
                    .withCommitUserName(properties.getProperty("git.commit.user.name"))
                    .withRemoteOriginUrl(properties.getProperty("git.remote.origin.url"))
                    .withTags(properties.getProperty("git.tags"));

            //
            // handle these separately in case they're null - the valueOf() will throw an NPE otherwise
            //
            String gitDirty = properties.getProperty( "git.dirty" );
            if( gitDirty != null )
                versionInfo.withDirty(Boolean.valueOf(gitDirty));

            String gitTotalCommitCount = properties.getProperty("git.total.commit.count");
            if( gitTotalCommitCount != null )
                versionInfo.withTotalCommitCount(Integer.valueOf(properties.getProperty("git.total.commit.count")));




            return Response.ok().entity(versionInfo).build();
        }
        catch( IOException ioe ) {
            logger.error( "can't locate git.properties on the class path");
        }

        throw new NotFoundServiceException("Can't load properties");
    }


    private Properties getGitProperties() throws IOException {

        Properties properties = new Properties();

        try (InputStream inputStream = this.getClass().getResourceAsStream("/git.properties")) {
            if (inputStream == null)
                throw new IOException("Can't locate properties file to generate version info");

            properties.load(inputStream);

            return properties;
        }
    }

}
