package uk.co.bbc.pcs.common.lambda.jackson;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;

import java.util.Map;

public class SNSEventMixIns {

    public static void addMixIns(ObjectMapper objectMapper) {
        objectMapper.addMixIn(SNSEvent.class, LambdaEventMixIns.LambdaEventMixIn.class);
        objectMapper.addMixIn(SNSEvent.SNS.class, SNSMixIn.class);
        objectMapper.addMixIn(SNSEvent.SNSRecord.class, SNSRecordMixIn.class);
        objectMapper.addMixIn(SNSEvent.MessageAttribute.class, MessageAttributeMixIn.class);
    }

    public static abstract class SNSRecordMixIn {
        @JsonIgnore public static final String EVENT_VERSION = "EventVersion";
        @JsonIgnore public static final String EVENT_SOURCE = "EventSource";
        @JsonIgnore public static final String SNS = "Sns";
        @JsonIgnore public static final String EVENT_SUBSCRIPTION_ARN = "EventSubscriptionArn";

        @JsonProperty(EVENT_VERSION) public abstract String getEventVersion();
        @JsonProperty(EVENT_VERSION) public abstract void setEventVersion(String eventVersion);
        @JsonProperty(EVENT_SOURCE) public abstract String getEventSource();
        @JsonProperty(EVENT_SOURCE) public abstract void setEventSource(String eventSource);
        @JsonProperty(SNS) public abstract SNSEvent.SNS getSNS();
        @JsonProperty(SNS) public abstract void setSNS(String sns);
        @JsonProperty(EVENT_SUBSCRIPTION_ARN) public abstract String getEventSubscriptionArn();
        @JsonProperty(EVENT_SUBSCRIPTION_ARN) public abstract void setEventSubscriptionArn(String eventSubscriptionArn);
    }

    public static abstract class SNSMixIn {
        @JsonIgnore public static final String MESSAGE_ATTRIBUTES = "MessageAttributes";
        @JsonIgnore public static final String SIGNING_CERT_URL = "SigningCertUrl";
        @JsonIgnore public static final String MESSAGE_ID = "MessageId";
        @JsonIgnore public static final String MESSAGE = "Message";
        @JsonIgnore public static final String SUBJECT = "Subject";
        @JsonIgnore public static final String UNSUBSCRIBE_URL = "UnsubscribeUrl";
        @JsonIgnore public static final String TYPE = "Type";
        @JsonIgnore public static final String SIGNATURE_VERSION = "SignatureVersion";
        @JsonIgnore public static final String SIGNATURE = "Signature";
        @JsonIgnore public static final String TIMESTAMP = "Timestamp";
        @JsonIgnore public static final String TOPIC_ARN = "TopicArn";

        @JsonProperty(MESSAGE_ATTRIBUTES) public abstract Map<String, SNSEvent.MessageAttribute> getMessageAttributes();
        @JsonProperty(MESSAGE_ATTRIBUTES) public abstract void setMessageAttributes(Map<String, SNSEvent.MessageAttribute> messageAttributes);
        @JsonProperty(SIGNING_CERT_URL) public abstract String getSigningCertUrl();
        @JsonProperty(SIGNING_CERT_URL) public abstract void setSigningCertUrl(String signingCertUrl);
        @JsonProperty(MESSAGE_ID) public abstract String getMessageId();
        @JsonProperty(MESSAGE_ID) public abstract void setMessageId(String messageId);
        @JsonProperty(MESSAGE) public abstract String getMessage();
        @JsonProperty(MESSAGE) public abstract void setMessage(String message);
        @JsonProperty(SUBJECT) public abstract String getSubject();
        @JsonProperty(SUBJECT) public abstract void setSubject(String subject);
        @JsonProperty(UNSUBSCRIBE_URL) public abstract String getUnsubscribeUrl();
        @JsonProperty(UNSUBSCRIBE_URL) public abstract void setUnsubscribeUrl(String unsubscribeUrl);
        @JsonProperty(TYPE) public abstract String getType();
        @JsonProperty(TYPE) public abstract void setType(String type);
        @JsonProperty(SIGNATURE_VERSION) public abstract String getSignatureVersion();
        @JsonProperty(SIGNATURE_VERSION) public abstract void setSignatureVersion(String signatureVersion);
        @JsonProperty(SIGNATURE) public abstract String getSignature();
        @JsonProperty(SIGNATURE) public abstract void setSignature(String signature);
        @JsonProperty(TIMESTAMP) public abstract DateTime getTimestamp();
        @JsonProperty(TIMESTAMP) public abstract void setTimestamp(DateTime timestamp);
        @JsonProperty(TOPIC_ARN) public abstract String getTopicArn();
        @JsonProperty(TOPIC_ARN) public abstract void setTopicArn(String topicArn);
    }

    public static abstract class MessageAttributeMixIn {
        @JsonIgnore public static final String TYPE = "Type";
        @JsonIgnore public static final String VALUE = "Value";

        @JsonProperty(TYPE) public abstract String getType();
        @JsonProperty(TYPE) public abstract void setType(String type);
        @JsonProperty(VALUE) public abstract String getValue();
        @JsonProperty(VALUE) public abstract void setValue(String value);
    }

}
