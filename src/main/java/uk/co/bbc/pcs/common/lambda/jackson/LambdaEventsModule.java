package uk.co.bbc.pcs.common.lambda.jackson;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Record;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.s3.event.S3EventNotification;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

public class LambdaEventsModule extends Module {
    @Override
    public String getModuleName() {
        return "LambdaEventsModule";
    }

    @Override
    public Version version() {
        return Version.unknownVersion();
    }

    @Override
    public void setupModule(SetupContext setupContext) {
        // Adds MixIns for Lambda event objects
        // See http://wiki.fasterxml.com/JacksonMixInAnnotations for details about MixIns.
        
        // DynamoDB
        setupContext.setMixInAnnotations(DynamodbEvent.class, LambdaEventMixIns.LambdaEventMixIn.class);
        setupContext.setMixInAnnotations(AttributeValue.class, LambdaEventMixIns.AttributeValueMixIn.class);
        setupContext.setMixInAnnotations(Record.class, DynamodbEventMixIns.RecordMixIn.class);
        setupContext.setMixInAnnotations(StreamRecord.class, DynamodbEventMixIns.StreamRecordMixIn.class);

        // S3
        setupContext.setMixInAnnotations(S3Event.class, S3EventMixins.S3EventMixin.class);
        setupContext.setMixInAnnotations(S3EventNotification.S3ObjectEntity.class, S3EventMixins.S3ObjectEntityMixIn.class);
        setupContext.setMixInAnnotations(S3EventNotification.S3BucketEntity.class, S3EventMixins.S3BucketEntityMixIn.class);

        // SNS
        setupContext.setMixInAnnotations(SNSEvent.class, LambdaEventMixIns.LambdaEventMixIn.class);
        setupContext.setMixInAnnotations(SNSEvent.SNS.class, SNSEventMixIns.SNSMixIn.class);
        setupContext.setMixInAnnotations(SNSEvent.SNSRecord.class, SNSEventMixIns.SNSRecordMixIn.class);
        setupContext.setMixInAnnotations(SNSEvent.MessageAttribute.class, SNSEventMixIns.MessageAttributeMixIn.class);
    }
}
