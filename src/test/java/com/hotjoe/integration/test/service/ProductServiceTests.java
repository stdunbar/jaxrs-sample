package com.hotjoe.integration.test.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.jboss.shrinkwrap.resolver.api.maven.strategy.TransitiveStrategy;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.Gson;
import com.hotjoe.model.Product;


/**
* This is the primary test harness for the service example.  It sets up the war file to be tested
* and runs a few tests.
*
* For speed in testing, there should be a single test class.  The reason is that each new test
* class requires spinning up a new instance of Wildfly and this is expensive.  You can certainly
* do it if you want but be aware of the overhead.
*
*/
@RunWith(Arquillian.class)
public class ProductServiceTests {
    // private static final Logger logger = LoggerFactory.getLogger(SampleUnitTests.class);

    /**
    * The @Deployment annotation is required with Arquillian tests.  This method creates a temporary
    * war file that will be tested.  For this example this war contains the dependencies specified
    * in the <pre>pom.xml</pre> file and anything in the <pre>com.hotjoe</pre> package.
    */
    @Deployment(testable = false)
    @SuppressWarnings("unused")
    public static WebArchive createDeployment() {

        PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");
        ScopeType[] scopes = {ScopeType.COMPILE, ScopeType.IMPORT, ScopeType.TEST}; // no SYSTEM and no PROVIDED
        File[] libs = pom.importDependencies(scopes).resolve().using(TransitiveStrategy.INSTANCE).asFile();

        return ShrinkWrap.create(WebArchive.class, "service-test.war")
                .addAsLibraries(libs)
                .addPackages(true, "com.hotjoe")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }



    /**
    * Tests the heartbeat service.  The <pre>@RunAsClient</pre> means that this test will run as
    * a true client of the service.
    */
    @Test
    @RunAsClient
    public void testHeartbeat(@ArquillianResource URL baseURL) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpGet httpGet = new HttpGet(baseURL.toExternalForm() + "rest/v1/heartbeat");

            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_OK);

                assertNotNull(response.getEntity());

                // get the entity from the response and convert it into a string
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                IOUtils.copy(response.getEntity().getContent(), byteArrayOutputStream);
                assertTrue(byteArrayOutputStream.toString(StandardCharsets.UTF_8).equalsIgnoreCase("ok"));
            }
        }
    }

    @Test
    @RunAsClient
    public void testNewProductHappyPath(@ArquillianResource URL baseURL) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(baseURL.toExternalForm() + "rest/v1/product");

            Random random = new Random();
            Integer productId = random.nextInt(100000);

            Product product = new Product();
            product.setProductId(productId);
            product.setDescription("This is the product description");
            OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            product.setCreateDate(formatter.format(now));

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(new Gson().toJson(product)));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_CREATED);

                assertNotNull(response.getEntity());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                IOUtils.copy(response.getEntity().getContent(), byteArrayOutputStream);
                String body = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

                Gson gson = new Gson();
                Product productResponse = gson.fromJson(body, Product.class);

                assertEquals(productResponse.getProductId(), productId);
                assertNotNull(productResponse.getDescription());
            }
        }
    }

    @Test
    @RunAsClient
    public void testNoProduct(@ArquillianResource URL baseURL) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(baseURL.toExternalForm() + "rest/v1/product");

            httpPost.addHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_BAD_REQUEST);
            }
        }
    }

    @Test
    @RunAsClient
    public void testNoProductId(@ArquillianResource URL baseURL) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(baseURL.toExternalForm() + "rest/v1/product");

            Product product = new Product();
            product.setDescription("This is the product description");
            OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            product.setCreateDate(formatter.format(now));

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(new Gson().toJson(product)));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_CREATED);

                assertNotNull(response.getEntity());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                IOUtils.copy(response.getEntity().getContent(), byteArrayOutputStream);
                String body = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

                Gson gson = new Gson();
                Product productResponse = gson.fromJson(body, Product.class);

                assertNotNull(productResponse.getProductId());
                assertNotNull(productResponse.getDescription());
            }
        }
    }

    @Test
    @RunAsClient
    public void testNewProductDuplicateId(@ArquillianResource URL baseURL) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(baseURL.toExternalForm() + "rest/v1/product");

            Random random = new Random();
            Integer productId = random.nextInt(100000);

            Product product = new Product();
            product.setProductId(productId);
            product.setDescription("This is the product description");
            OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            product.setCreateDate(formatter.format(now));

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(new Gson().toJson(product)));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_CREATED);

                assertNotNull(response.getEntity());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                IOUtils.copy(response.getEntity().getContent(), byteArrayOutputStream);
                String body = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

                Gson gson = new Gson();
                Product productResponse = gson.fromJson(body, Product.class);

                assertEquals(productResponse.getProductId(), productId);
                assertNotNull(productResponse.getDescription());
            }

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_CONFLICT);
            }
        }
    }

    @Test
    @RunAsClient
    public void testNewProductBadDescription(@ArquillianResource URL baseURL) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(baseURL.toExternalForm() + "rest/v1/product");

            Random random = new Random();
            Integer productId = random.nextInt(100000);

            Product product = new Product();
            product.setProductId(productId);
            product.setDescription("This is the product-description");
            OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            product.setCreateDate(formatter.format(now));

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(new Gson().toJson(product)));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_BAD_REQUEST);
            }
        }
    }

    @Test
    @RunAsClient
    public void testGetProduct(@ArquillianResource URL baseURL) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpPost httpPost = new HttpPost(baseURL.toExternalForm() + "rest/v1/product");

            Random random = new Random();
            Integer productId = random.nextInt(100000);

            Product product = new Product();
            product.setProductId(productId);
            product.setDescription("This is the product description");
            OffsetDateTime now = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS);
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            product.setCreateDate(formatter.format(now));

            httpPost.addHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(new Gson().toJson(product)));

            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_CREATED);

                assertNotNull(response.getEntity());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                IOUtils.copy(response.getEntity().getContent(), byteArrayOutputStream);
                String body = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

                Gson gson = new Gson();
                Product productResponse = gson.fromJson(body, Product.class);

                assertEquals(productResponse.getProductId(), productId);
                assertNotNull(productResponse.getDescription());
            }

            final HttpGet httpGet = new HttpGet(baseURL.toExternalForm() + "rest/v1/product/" + productId);

            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_OK);

                assertNotNull(response.getEntity());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                IOUtils.copy(response.getEntity().getContent(), byteArrayOutputStream);
                String body = byteArrayOutputStream.toString(StandardCharsets.UTF_8);

                Gson gson = new Gson();
                Product productResponse = gson.fromJson(body, Product.class);

                assertEquals(productId, productResponse.getProductId());
            }



            try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_CONFLICT);
            }
        }
    }

    @Test
    @RunAsClient
    public void testGetBadProductId(@ArquillianResource URL baseURL) throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            final HttpGet httpGet = new HttpGet(baseURL.toExternalForm() + "rest/v1/product/" + 1234);

            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                final int responseCode = response.getStatusLine().getStatusCode();
                assertEquals(responseCode, HttpURLConnection.HTTP_NOT_FOUND);
            }
        }
    }
}
