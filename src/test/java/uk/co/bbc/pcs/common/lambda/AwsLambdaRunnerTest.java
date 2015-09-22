package uk.co.bbc.pcs.common.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.util.IOUtils;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.List;

import static com.jayway.restassured.RestAssured.given;

public class AwsLambdaRunnerTest {

    private static final int PORT = 1234;

    @Before
    public void setUp() {
        System.setProperty("port", String.valueOf(PORT));
    }

    @After
    public void shutDown() {
        AwsLambdaRunner.stopServer();
    }

    @Test(expected=RuntimeException.class)
    public void mainShouldThrowExceptionIfNonRequestHandlerClassName() {
        String[] args = {
                "java.lang.System"
        };
        AwsLambdaRunner.main(args);
    }

    @Test(expected=RuntimeException.class)
    public void mainShouldThrowExceptionIfClassDoesNotExist() {
        String[] args = {
                "not.existing.class"
        };
        AwsLambdaRunner.main(args);
    }

    @Test(expected=RuntimeException.class)
    public void mainShouldThrowExceptionIfNoArgumentsPassedIn() {
        String[] args = {};
        AwsLambdaRunner.main(args);
    }

    @Test
    public void mainShouldStartServerIfRequestHandlerPassedIn() {
        String[] args = {
                MockHandler.class.getName()
        };
        AwsLambdaRunner.main(args);
        given()
                .port(PORT)
        .when()
                .get("/")
        .then()
                .statusCode(200);
    }

    @Test
    public void mainShouldForwardToDynamoDbEventRequestHandler() throws IOException {
        String[] args = {
                MockHandler.class.getName()
        };
        AwsLambdaRunner.main(args);

        given()
                .port(PORT)
                .body(readFile("/dynamodb-event.json"))
        .when()
                .post("/")
        .then()
                .statusCode(200);

    }

    public static class MockHandler implements RequestHandler<DynamodbEvent, String>{
        @Override
        public String handleRequest(DynamodbEvent event, Context context) {
            List<DynamodbEvent.DynamodbStreamRecord> records = event.getRecords();
            DynamodbEvent.DynamodbStreamRecord record = records.get(0);
            context.getLogger().log(record.toString());
            return "Received event";
        }
    }

    private String readFile(String filename) throws IOException {
        URL url = getClass().getResource(filename);
        return Resources.toString(url, Charsets.UTF_8);
    }

}
