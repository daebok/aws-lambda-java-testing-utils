package uk.co.bbc.pcs.common.lambda.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ObjectMapper which correctly handles events in com.amazonaws.services.lambda.runtime.events
 */
public class AwsLambdaEventsObjectMapper extends ObjectMapper {


    public AwsLambdaEventsObjectMapper() {
        super();

        // Don't serialize things that are null
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        DynamodbEventMixIns.addMixIns(this);
    }
}
