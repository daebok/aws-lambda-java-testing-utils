package uk.co.bbc.pcs.common.lambda.jackson;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class S3EventMixins {

    public static abstract class S3EventMixin {
        public S3EventMixin(@JsonProperty(LambdaEventMixIns.LambdaEventMixIn.RECORDS) List<S3EventNotification.S3EventNotificationRecord> records) { }
    }

    public static abstract class S3ObjectEntityMixIn {
        @JsonIgnore public static final String KEY = "key";
        @JsonIgnore public static final String SIZE = "size";
        @JsonIgnore public static final String ETAG = "eTag";
        @JsonIgnore public static final String SEQUENCER = "sequencer";

        public S3ObjectEntityMixIn(@JsonProperty(value = KEY) String key,
                                   @JsonProperty(value = SIZE) Long size,
                                   @JsonProperty(value = ETAG) String eTag,
                                   @JsonProperty(value = SEQUENCER) String versionId) { }
    }

    public static abstract class S3BucketEntityMixIn {
        @JsonIgnore public static final String ARN = "arn";
        @JsonIgnore public static final String NAME = "name";
        @JsonIgnore public static final String OWNER = "ownerIdentity";

        public S3BucketEntityMixIn(@JsonProperty(value = ARN) String arn,
                                   @JsonProperty(value = NAME) String name){}
    }

}