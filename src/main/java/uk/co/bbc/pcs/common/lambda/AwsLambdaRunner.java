package uk.co.bbc.pcs.common.lambda;

import uk.co.bbc.pcs.common.lambda.forwarder.RequestForwarder;
import uk.co.bbc.pcs.common.lambda.forwarder.RequestHandlerForwarder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.bbc.pcs.common.lambda.server.SparkConfig;
import uk.co.bbc.pcs.common.lambda.server.SparkRouter;

public class AwsLambdaRunner {

    private static Logger logger = LoggerFactory.getLogger(AwsLambdaRunner.class);
    private static SparkRouter router;

    public static void main (String[] args) {
        RequestForwarder requestForwarder = getRequestForwarder(args);
        startServer(requestForwarder);
    }

    public static void stopServer() {
        if (router != null) {
            router.stop();
        }
    }

    private static void startServer(RequestForwarder requestForwarder) {
        SparkConfig config = new SparkConfig();
        router = new SparkRouter(config, requestForwarder);
        router.start();
    }

    private static RequestForwarder getRequestForwarder(String[] args) {
        if (args.length == 0 ) {
            String message = "RequestHandler class name must be passed in as an argument";
            logger.error(message);
            throw new RuntimeException(message);
        }

        String className = args[0];
        return new RequestHandlerForwarder(className);
    }


}
