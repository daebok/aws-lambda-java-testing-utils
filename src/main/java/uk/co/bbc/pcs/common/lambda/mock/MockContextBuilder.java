package uk.co.bbc.pcs.common.lambda.mock;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.util.UUID;

public class MockContextBuilder {
    private final String functionName;
    private final String awsRequestId;
    private final LambdaLogger logger;

    private String logGroupName;
    private String logStreamName;
    private CognitoIdentity identity;
    private ClientContext clientContext;
    private int remainingTimeInMillis = 0;
    private int memoryLimitInMB = 0;
    private boolean remainingTimeDoesCountdown;

    public MockContextBuilder(String functionName) {
        this.functionName = functionName;
        this.awsRequestId = UUID.randomUUID().toString();
        // By default logger just prints to System.out
        this.logger = System.out::println;
    }

    public MockContextBuilder(String functionName, LambdaLogger logger) {
        this.functionName = functionName;
        this.logger = logger;
        this.awsRequestId = UUID.randomUUID().toString();
    }

    public MockContextBuilder withLogGroupName(String logGroupName) {
        this.logGroupName = logGroupName;
        return this;
    }

    public MockContextBuilder withLogStreamName(String logStreamName) {
        this.logStreamName = logStreamName;
        return this;
    }

    public MockContextBuilder withIdentity(CognitoIdentity identity) {
        this.identity = identity;
        return this;
    }

    public MockContextBuilder withClientContext(ClientContext clientContext) {
        this.clientContext = clientContext;
        return this;
    }

    public MockContextBuilder withRemainingTimeInMillis(int remainingTimeInMillis) {
        this.remainingTimeInMillis = remainingTimeInMillis;
        return this;
    }

    public MockContextBuilder withMemoryLimitInMB(int memoryLimitInMB) {
        this.memoryLimitInMB = memoryLimitInMB;
        return this;
    }

    public MockContextBuilder withRemainingTimeCountdown(boolean remainingTimeDoesCountdown) {
        this.remainingTimeDoesCountdown = remainingTimeDoesCountdown;
        return this;
    }

    public MockContext createMockContext() {
        return new MockContext(awsRequestId, logGroupName, logStreamName, functionName, identity,
                clientContext, remainingTimeInMillis, memoryLimitInMB, logger, remainingTimeDoesCountdown);
    }
}
