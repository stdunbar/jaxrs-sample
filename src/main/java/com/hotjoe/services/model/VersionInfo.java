package com.hotjoe.services.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class VersionInfo {
    private String branch;
    private String buildHost;
    private String buildTime;
    private String buildUserEmail;
    private String buildUserName;
    private String buildVersion;
    private String commitId;
    private String commitIdAbbrev;
    private String commitIdDescribe;
    private String commitIdDescribeShort;
    private String commitMessageFull;
    private String commitMessageShort;
    private String commitTime;
    private String commitUserEmail;
    private String commitUserName;
    private Boolean dirty;
    private String remoteOriginUrl;
    private String tags;
    private Integer totalCommitCount;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBuildHost() {
        return buildHost;
    }

    public void setBuildHost(String buildHost) {
        this.buildHost = buildHost;
    }

    public String getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    public String getBuildUserEmail() {
        return buildUserEmail;
    }

    public void setBuildUserEmail(String buildUserEmail) {
        this.buildUserEmail = buildUserEmail;
    }

    public String getBuildUserName() {
        return buildUserName;
    }

    public void setBuildUserName(String buildUserName) {
        this.buildUserName = buildUserName;
    }

    public String getBuildVersion() {
        return buildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getCommitIdAbbrev() {
        return commitIdAbbrev;
    }

    public void setCommitIdAbbrev(String commitIdAbbrev) {
        this.commitIdAbbrev = commitIdAbbrev;
    }

    public String getCommitIdDescribe() {
        return commitIdDescribe;
    }

    public void setCommitIdDescribe(String commitIdDescribe) {
        this.commitIdDescribe = commitIdDescribe;
    }

    public String getCommitIdDescribeShort() {
        return commitIdDescribeShort;
    }

    public void setCommitIdDescribeShort(String commitIdDescribeShort) {
        this.commitIdDescribeShort = commitIdDescribeShort;
    }

    public String getCommitMessageFull() {
        return commitMessageFull;
    }

    public void setCommitMessageFull(String commitMessageFull) {
        this.commitMessageFull = commitMessageFull;
    }

    public String getCommitMessageShort() {
        return commitMessageShort;
    }

    public void setCommitMessageShort(String commitMessageShort) {
        this.commitMessageShort = commitMessageShort;
    }

    public String getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(String commitTime) {
        this.commitTime = commitTime;
    }

    public String getCommitUserEmail() {
        return commitUserEmail;
    }

    public void setCommitUserEmail(String commitUserEmail) {
        this.commitUserEmail = commitUserEmail;
    }

    public String getCommitUserName() {
        return commitUserName;
    }

    public void setCommitUserName(String commitUserName) {
        this.commitUserName = commitUserName;
    }

    public Boolean getDirty() {
        return dirty;
    }

    public void setDirty(Boolean dirty) {
        this.dirty = dirty;
    }

    public String getRemoteOriginUrl() {
        return remoteOriginUrl;
    }

    public void setRemoteOriginUrl(String remoteOriginUrl) {
        this.remoteOriginUrl = remoteOriginUrl;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getTotalCommitCount() {
        return totalCommitCount;
    }

    public void setTotalCommitCount(Integer totalCommitCount) {
        this.totalCommitCount = totalCommitCount;
    }

    public VersionInfo withBranch(String branch) {
        this.branch = branch;
        return this;
    }

    public VersionInfo withBuildHost(String buildHost) {
        this.buildHost = buildHost;
        return this;
    }

    public VersionInfo withBuildTime(String buildTime) {
        this.buildTime = buildTime;
        return this;
    }

    public VersionInfo withBuildUserEmail(String buildUserEmail) {
        this.buildUserEmail = buildUserEmail;
        return this;
    }

    public VersionInfo withBuildUserName(String buildUserName) {
        this.buildUserName = buildUserName;
        return this;
    }

    public VersionInfo withBuildVersion(String buildVersion) {
        this.buildVersion = buildVersion;
        return this;
    }

    public VersionInfo withCommitId(String commitId) {
        this.commitId = commitId;
        return this;
    }

    public VersionInfo withCommitIdAbbrev(String commitIdAbbrev) {
        this.commitIdAbbrev = commitIdAbbrev;
        return this;
    }

    public VersionInfo withCommitIdDescribe(String commitIdDescribe) {
        this.commitIdDescribe = commitIdDescribe;
        return this;
    }

    public VersionInfo withCommitIdDescribeShort(String commitIdDescribeShort) {
        this.commitIdDescribeShort = commitIdDescribeShort;
        return this;
    }

    public VersionInfo withCommitMessageFull(String commitMessageFull) {
        this.commitMessageFull = commitMessageFull;
        return this;
    }

    public VersionInfo withCommitMessageShort(String commitMessageShort) {
        this.commitMessageShort = commitMessageShort;
        return this;
    }

    public VersionInfo withCommitTime(String commitTime) {
        this.commitTime = commitTime;
        return this;
    }

    public VersionInfo withCommitUserEmail(String commitUserEmail) {
        this.commitUserEmail = commitUserEmail;
        return this;
    }

    public VersionInfo withCommitUserName(String commitUserName) {
        this.commitUserName = commitUserName;
        return this;
    }

    public VersionInfo withDirty(Boolean dirty) {
        this.dirty = dirty;
        return this;
    }

    public VersionInfo withRemoteOriginUrl(String remoteOriginUrl) {
        this.remoteOriginUrl = remoteOriginUrl;
        return this;
    }

    public VersionInfo withTags(String tags) {
        this.tags = tags;
        return this;
    }

    public VersionInfo withTotalCommitCount(Integer totalCommitCount) {
        this.totalCommitCount = totalCommitCount;
        return this;
    }
}