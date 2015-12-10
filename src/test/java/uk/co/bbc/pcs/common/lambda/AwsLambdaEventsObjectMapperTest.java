package uk.co.bbc.pcs.common.lambda;

import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Test;
import uk.co.bbc.pcs.common.lambda.jackson.AwsLambdaEventsObjectMapper;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class AwsLambdaEventsObjectMapperTest {

    private final AwsLambdaEventsObjectMapper underTest = new AwsLambdaEventsObjectMapper();

    @Test
    public void shouldReadDynamodbEvent() throws IOException {
        String event = readFile("/dynamodb-event.json");

        DynamodbEvent result = underTest.readValue(event, DynamodbEvent.class);

        assertThat(result).isNotNull();
        assertThat(result.getRecords()).isNotNull();
        assertThat(result.getRecords()).hasSize(3);
        assertThat(result.getRecords().get(0)).isNotNull();
        assertThat(result.getRecords().get(1)).isNotNull();
        assertThat(result.getRecords().get(2)).isNotNull();
    }

    @Test
    public void shouldReadSnsEvent() throws IOException {
        String event = readFile("/sns-event.json");

        SNSEvent result = underTest.readValue(event, SNSEvent.class);

        assertThat(result).isNotNull();
        assertThat(result.getRecords()).isNotNull();
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getRecords().get(0)).isNotNull();
    }

    @Test
    public void shouldReadS3Event() throws IOException {
        String event = readFile("/s3-event.json");

        S3Event result = underTest.readValue(event, S3Event.class);

        assertThat(result).isNotNull();
        assertThat(result.getRecords()).isNotNull();
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getRecords().get(0)).isNotNull();
    }

    private String readFile(String filename) throws IOException {
        URL url = getClass().getResource(filename);
        return Resources.toString(url, Charsets.UTF_8);
    }

}
