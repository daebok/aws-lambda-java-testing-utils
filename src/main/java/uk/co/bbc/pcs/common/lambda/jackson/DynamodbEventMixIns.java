package uk.co.bbc.pcs.common.lambda.jackson;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class DynamodbEventMixIns {

    public static void addMixIns(ObjectMapper objectMapper) {
        objectMapper.addMixIn(DynamodbEvent.class, LambdaEventMixIns.LambdaEventMixIn.class);
        objectMapper.addMixIn(AttributeValue.class, LambdaEventMixIns.AttributeValueMixIn.class);
        objectMapper.addMixIn(Record.class, RecordMixIn.class);
        objectMapper.addMixIn(StreamRecord.class, StreamRecordMixIn.class);
    }

    public static abstract class RecordMixIn {
        @JsonIgnore public static final String EVENT_VERSION = "eventVersion";
        @JsonIgnore public static final String EVENT_SOURCE = "eventSource";
        @JsonIgnore public static final String AWS_REGION = "awsRegion";
        @JsonIgnore public static final String DYNAMODB = "dynamodb";
        @JsonIgnore public static final String EVENT_ID = "eventID";
        @JsonIgnore public static final String EVENT_NAME = "eventName";

        @JsonProperty(EVENT_VERSION) public abstract String getEventVersion();
        @JsonProperty(EVENT_VERSION) public abstract void setEventVersion(String eventVersion);
        @JsonProperty(EVENT_SOURCE) public abstract String getEventSource();
        @JsonProperty(EVENT_SOURCE) public abstract void setEventSource(String eventSource);
        @JsonProperty(AWS_REGION) public abstract String getAwsRegion();
        @JsonProperty(AWS_REGION) public abstract void setAwsRegion(String awsRegion);
        @JsonProperty(DYNAMODB) public abstract StreamRecord getDynamodb();
        @JsonProperty(DYNAMODB) public abstract void setDynamodb(StreamRecord dynamodb);
        @JsonProperty(EVENT_ID) public abstract String getEventID();
        @JsonProperty(EVENT_ID) public abstract void setEventID(String eventID);
        @JsonProperty(EVENT_NAME) public abstract String getEventName();
        @JsonProperty(EVENT_NAME) public abstract void setEventName(String eventName);
    }

    public static abstract class StreamRecordMixIn {
        @JsonIgnore public static final String SIZE_BYTES = "SizeBytes";
        @JsonIgnore public static final String SEQUENCE_NUMBER = "SequenceNumber";
        @JsonIgnore public static final String STREAM_VIEW_TYPE = "StreamViewType";
        @JsonIgnore public static final String KEYS = "Keys";
        @JsonIgnore public static final String NEW_IMAGE = "NewImage";
        @JsonIgnore public static final String OLD_IMAGE = "OldImage";

        @JsonProperty(SIZE_BYTES) public abstract Long getSizeBytes();
        @JsonProperty(SIZE_BYTES) public abstract void setSizeBytes(Long sizeBytes);
        @JsonProperty(SEQUENCE_NUMBER) public abstract String getSequenceNumber();
        @JsonProperty(SEQUENCE_NUMBER) public abstract void setSequenceNumber(String sequenceNumber);
        @JsonProperty(STREAM_VIEW_TYPE) public abstract StreamViewType getStreamViewTypeEnum ();
        @JsonProperty(STREAM_VIEW_TYPE) public abstract void setStreamViewType(StreamViewType streamViewType);
        @JsonProperty(KEYS) public abstract Map<String, AttributeValue> getKeys();
        @JsonProperty(KEYS) public abstract void setKeys(Map<String, AttributeValue> keys);
        @JsonProperty(NEW_IMAGE) public abstract Map<String, AttributeValue> getNewImage();
        @JsonProperty(NEW_IMAGE) public abstract void setNewImage(Map<String, AttributeValue> newImage);
        @JsonProperty(OLD_IMAGE) public abstract Map<String, AttributeValue> getOldImage();
        @JsonProperty(OLD_IMAGE) public abstract void setOldImage(Map<String, AttributeValue> oldImage);
    }


}
