package uk.co.bbc.pcs.common.lambda.jackson;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.dynamodbv2.model.StreamViewType;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * MixIns for mapping com.amazonaws.services.lambda.runtime.events.DynamodbEvent
 * See http://wiki.fasterxml.com/JacksonMixInAnnotations for details about MixIns.
 */
public class DynamodbEventMixIns {

    public static final String L = "L";
    public static final String M = "M";
    public static final String BS = "BS";
    public static final String NS = "NS";
    public static final String SS = "SS";
    public static final String BOOL = "BOOL";
    public static final String NULL = "NULL";
    public static final String B = "B";
    public static final String N = "N";
    public static final String S = "S";
    public static final String OLD_IMAGE = "OldImage";
    public static final String NEW_IMAGE = "NewImage";
    public static final String STREAM_VIEW_TYPE = "StreamViewType";
    public static final String SEQUENCE_NUMBER = "SequenceNumber";
    public static final String SIZE_BYTES = "SizeBytes";
    public static final String KEYS = "Keys";
    public static final String AWS_REGION = "awsRegion";
    public static final String DYNAMODB = "dynamodb";
    public static final String EVENT_ID = "eventID";
    public static final String EVENT_NAME = "eventName";
    public static final String EVENT_SOURCE = "eventSource";
    public static final String EVENT_VERSION = "eventVersion";
    public static final String RECORDS = "Records";

    public static void addMixIns(ObjectMapper objectMapper) {
        objectMapper.addMixIn(DynamodbEvent.class, DynamodbEventMixIn.class);
        objectMapper.addMixIn(AttributeValue.class, AttributeValueMixIn.class);
        objectMapper.addMixIn(Record.class, RecordMixIn.class);
        objectMapper.addMixIn(StreamRecord.class, StreamRecordMixIn.class);
    }

    public static abstract class DynamodbEventMixIn {
        @JsonProperty(RECORDS) public abstract String getRecords();
        @JsonProperty(RECORDS) public abstract String setRecords();
    }

    public static abstract class RecordMixIn{
        @JsonProperty(AWS_REGION) public abstract String getAwsRegion();
        @JsonProperty(AWS_REGION) public abstract void setAwsRegion(String awsRegion);
        @JsonProperty(DYNAMODB) public abstract StreamRecord getDynamodb();
        @JsonProperty(DYNAMODB) public abstract void setDynamodb(StreamRecord dynamodb);
        @JsonProperty(EVENT_ID) public abstract String getEventID();
        @JsonProperty(EVENT_ID) public abstract void setEventID(String eventID);
        @JsonProperty(EVENT_NAME) public abstract String getEventName();
        @JsonProperty(EVENT_NAME) public abstract void setEventName(String eventName);
        @JsonProperty(EVENT_SOURCE) public abstract String getEventSource();
        @JsonProperty(EVENT_SOURCE) public abstract void setEventSource(String eventSource);
        @JsonProperty(EVENT_VERSION) public abstract String getEventVersion();
        @JsonProperty(EVENT_VERSION) public abstract void setEventVersion(String eventVersion);
    }

    public static abstract class StreamRecordMixIn{
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

    public static abstract class AttributeValueMixIn {
        @JsonProperty(S) public abstract String getS();
        @JsonProperty(S) public abstract void setS(String s);
        @JsonProperty(N) public abstract String getN();
        @JsonProperty(N) public abstract void setN(String n);
        @JsonProperty(B) public abstract ByteBuffer getB();
        @JsonProperty(B) public abstract void setB(ByteBuffer b);
        @JsonProperty(NULL) public abstract Boolean isNULL();
        @JsonProperty(NULL) public abstract void setNULL(Boolean nU);
        @JsonProperty(BOOL) public abstract Boolean getBOOL();
        @JsonProperty(BOOL) public abstract void setBOOL(Boolean bO);
        @JsonProperty(SS) public abstract List<String> getSS();
        @JsonProperty(SS) public abstract void setSS(List<String> sS);
        @JsonProperty(NS) public abstract List<String> getNS();
        @JsonProperty(NS) public abstract void setNS(List<String> nS);
        @JsonProperty(BS) public abstract List<String> getBS();
        @JsonProperty(BS) public abstract void setBS(List<String> bS);
        @JsonProperty(M) public abstract Map<String, AttributeValue> getM();
        @JsonProperty(M) public abstract void setM(Map<String, AttributeValue> val);
        @JsonProperty(L) public abstract List<AttributeValue> getL();
        @JsonProperty(L) public abstract void setL(List<AttributeValue> val);
    }

}
