/* 
 * Code generated by Speakeasy (https://speakeasyapi.dev). DO NOT EDIT.
 */

package com.styra.opa.models.shared;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.styra.opa.utils.Utils;
import java.io.InputStream;
import java.lang.Deprecated;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * Provenance - Provenance information can be requested on individual API calls and are returned inline with the API response. To obtain provenance information on an API call, specify the `provenance=true` query parameter when executing the API call.
 */

public class Provenance {

    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("version")
    private Optional<? extends String> version;

    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("build_commit")
    private Optional<? extends String> buildCommit;

    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("build_timestamp")
    private Optional<? extends OffsetDateTime> buildTimestamp;

    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("build_host")
    private Optional<? extends String> buildHost;

    @JsonInclude(Include.NON_ABSENT)
    @JsonProperty("bundles")
    private Optional<? extends java.util.Map<String, Revision>> bundles;

    public Provenance(
            @JsonProperty("version") Optional<? extends String> version,
            @JsonProperty("build_commit") Optional<? extends String> buildCommit,
            @JsonProperty("build_timestamp") Optional<? extends OffsetDateTime> buildTimestamp,
            @JsonProperty("build_host") Optional<? extends String> buildHost,
            @JsonProperty("bundles") Optional<? extends java.util.Map<String, Revision>> bundles) {
        Utils.checkNotNull(version, "version");
        Utils.checkNotNull(buildCommit, "buildCommit");
        Utils.checkNotNull(buildTimestamp, "buildTimestamp");
        Utils.checkNotNull(buildHost, "buildHost");
        Utils.checkNotNull(bundles, "bundles");
        this.version = version;
        this.buildCommit = buildCommit;
        this.buildTimestamp = buildTimestamp;
        this.buildHost = buildHost;
        this.bundles = bundles;
    }

    public Optional<? extends String> version() {
        return version;
    }

    public Optional<? extends String> buildCommit() {
        return buildCommit;
    }

    public Optional<? extends OffsetDateTime> buildTimestamp() {
        return buildTimestamp;
    }

    public Optional<? extends String> buildHost() {
        return buildHost;
    }

    public Optional<? extends java.util.Map<String, Revision>> bundles() {
        return bundles;
    }

    public final static Builder builder() {
        return new Builder();
    }

    public Provenance withVersion(String version) {
        Utils.checkNotNull(version, "version");
        this.version = Optional.ofNullable(version);
        return this;
    }

    public Provenance withVersion(Optional<? extends String> version) {
        Utils.checkNotNull(version, "version");
        this.version = version;
        return this;
    }

    public Provenance withBuildCommit(String buildCommit) {
        Utils.checkNotNull(buildCommit, "buildCommit");
        this.buildCommit = Optional.ofNullable(buildCommit);
        return this;
    }

    public Provenance withBuildCommit(Optional<? extends String> buildCommit) {
        Utils.checkNotNull(buildCommit, "buildCommit");
        this.buildCommit = buildCommit;
        return this;
    }

    public Provenance withBuildTimestamp(OffsetDateTime buildTimestamp) {
        Utils.checkNotNull(buildTimestamp, "buildTimestamp");
        this.buildTimestamp = Optional.ofNullable(buildTimestamp);
        return this;
    }

    public Provenance withBuildTimestamp(Optional<? extends OffsetDateTime> buildTimestamp) {
        Utils.checkNotNull(buildTimestamp, "buildTimestamp");
        this.buildTimestamp = buildTimestamp;
        return this;
    }

    public Provenance withBuildHost(String buildHost) {
        Utils.checkNotNull(buildHost, "buildHost");
        this.buildHost = Optional.ofNullable(buildHost);
        return this;
    }

    public Provenance withBuildHost(Optional<? extends String> buildHost) {
        Utils.checkNotNull(buildHost, "buildHost");
        this.buildHost = buildHost;
        return this;
    }

    public Provenance withBundles(java.util.Map<String, Revision> bundles) {
        Utils.checkNotNull(bundles, "bundles");
        this.bundles = Optional.ofNullable(bundles);
        return this;
    }

    public Provenance withBundles(Optional<? extends java.util.Map<String, Revision>> bundles) {
        Utils.checkNotNull(bundles, "bundles");
        this.bundles = bundles;
        return this;
    }
    
    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Provenance other = (Provenance) o;
        return 
            java.util.Objects.deepEquals(this.version, other.version) &&
            java.util.Objects.deepEquals(this.buildCommit, other.buildCommit) &&
            java.util.Objects.deepEquals(this.buildTimestamp, other.buildTimestamp) &&
            java.util.Objects.deepEquals(this.buildHost, other.buildHost) &&
            java.util.Objects.deepEquals(this.bundles, other.bundles);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(
            version,
            buildCommit,
            buildTimestamp,
            buildHost,
            bundles);
    }
    
    @Override
    public String toString() {
        return Utils.toString(Provenance.class,
                "version", version,
                "buildCommit", buildCommit,
                "buildTimestamp", buildTimestamp,
                "buildHost", buildHost,
                "bundles", bundles);
    }
    
    public final static class Builder {
 
        private Optional<? extends String> version = Optional.empty();
 
        private Optional<? extends String> buildCommit = Optional.empty();
 
        private Optional<? extends OffsetDateTime> buildTimestamp = Optional.empty();
 
        private Optional<? extends String> buildHost = Optional.empty();
 
        private Optional<? extends java.util.Map<String, Revision>> bundles = Optional.empty();  
        
        private Builder() {
          // force use of static builder() method
        }

        public Builder version(String version) {
            Utils.checkNotNull(version, "version");
            this.version = Optional.ofNullable(version);
            return this;
        }

        public Builder version(Optional<? extends String> version) {
            Utils.checkNotNull(version, "version");
            this.version = version;
            return this;
        }

        public Builder buildCommit(String buildCommit) {
            Utils.checkNotNull(buildCommit, "buildCommit");
            this.buildCommit = Optional.ofNullable(buildCommit);
            return this;
        }

        public Builder buildCommit(Optional<? extends String> buildCommit) {
            Utils.checkNotNull(buildCommit, "buildCommit");
            this.buildCommit = buildCommit;
            return this;
        }

        public Builder buildTimestamp(OffsetDateTime buildTimestamp) {
            Utils.checkNotNull(buildTimestamp, "buildTimestamp");
            this.buildTimestamp = Optional.ofNullable(buildTimestamp);
            return this;
        }

        public Builder buildTimestamp(Optional<? extends OffsetDateTime> buildTimestamp) {
            Utils.checkNotNull(buildTimestamp, "buildTimestamp");
            this.buildTimestamp = buildTimestamp;
            return this;
        }

        public Builder buildHost(String buildHost) {
            Utils.checkNotNull(buildHost, "buildHost");
            this.buildHost = Optional.ofNullable(buildHost);
            return this;
        }

        public Builder buildHost(Optional<? extends String> buildHost) {
            Utils.checkNotNull(buildHost, "buildHost");
            this.buildHost = buildHost;
            return this;
        }

        public Builder bundles(java.util.Map<String, Revision> bundles) {
            Utils.checkNotNull(bundles, "bundles");
            this.bundles = Optional.ofNullable(bundles);
            return this;
        }

        public Builder bundles(Optional<? extends java.util.Map<String, Revision>> bundles) {
            Utils.checkNotNull(bundles, "bundles");
            this.bundles = bundles;
            return this;
        }
        
        public Provenance build() {
            return new Provenance(
                version,
                buildCommit,
                buildTimestamp,
                buildHost,
                bundles);
        }
    }
}

