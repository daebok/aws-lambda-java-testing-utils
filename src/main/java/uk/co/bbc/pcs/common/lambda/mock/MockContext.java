package uk.co.bbc.pcs.common.lambda.mock;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.common.base.Preconditions;

import java.util.Timer;
import java.util.TimerTask;

public class MockContext implements Context {

    private final String awsRequestId;
    private final String logGroupName;
    private final String logStreamName;
    private final String functionName;
    private final CognitoIdentity identity;
    private final ClientContext clientContext;
    private final int memoryLimitInMB;
    private final LambdaLogger logger;

    private int remainingTimeInMillis;
    private boolean remainingTimeDoesCountdown = false;
    private Timer timer = null;

    MockContext(String awsRequestId,
                String logGroupName,
                String logStreamName,
                String functionName,
                CognitoIdentity identity,
                ClientContext clientContext,
                int remainingTimeInMillis,
                int memoryLimitInMB,
                LambdaLogger logger) {

        Preconditions.checkNotNull(awsRequestId);
        Preconditions.checkNotNull(functionName);
        Preconditions.checkNotNull(logger);

        this.awsRequestId = awsRequestId;
        this.logGroupName = logGroupName;
        this.logStreamName = logStreamName;
        this.functionName = functionName;
        this.identity = identity;
        this.clientContext = clientContext;
        this.remainingTimeInMillis = remainingTimeInMillis;
        this.memoryLimitInMB = memoryLimitInMB;
        this.logger = logger;
    }

    MockContext(String awsRequestId,
                String logGroupName,
                String logStreamName,
                String functionName,
                CognitoIdentity identity,
                ClientContext clientContext,
                int remainingTimeInMillis,
                int memoryLimitInMB,
                LambdaLogger logger,
                boolean remainingTimeDoesCountdown) {
        this(awsRequestId, logGroupName, logStreamName, functionName, identity, clientContext, remainingTimeInMillis, memoryLimitInMB, logger);
        this.remainingTimeDoesCountdown = remainingTimeDoesCountdown;

        if (this.remainingTimeDoesCountdown) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (MockContext.this.remainingTimeInMillis >= 1000) {
                        MockContext.this.remainingTimeInMillis -= 1000;
                    } else {
                        MockContext.this.remainingTimeInMillis = 0;
                        timer.cancel();
                    }
                }
            }, 0, 1000);
        }
    }

    @Override
    public String getAwsRequestId() {
        return awsRequestId;
    }

    @Override
    public String getLogGroupName() {
        return logGroupName;
    }

    @Override
    public String getLogStreamName() {
        return logStreamName;
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public CognitoIdentity getIdentity() {
        return identity;
    }

    @Override
    public ClientContext getClientContext() {
        return clientContext;
    }

    @Override
    public int getRemainingTimeInMillis() {
        return remainingTimeInMillis;
    }

    @Override
    public int getMemoryLimitInMB() {
        return memoryLimitInMB;
    }

    @Override
    public LambdaLogger getLogger() {
        return logger;
    }
}
