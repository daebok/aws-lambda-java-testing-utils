package uk.co.bbc.pcs.common.lambda;

import com.amazonaws.services.lambda.runtime.RequestHandler;
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
        if (args.length < 2) {
            String message = "RequestHandler and event type class names must be passed in as an argument";
            logger.error(message);
            throw new RuntimeException(message);
        }

        String className = args[0];
        RequestHandler requestHandler = ReflectionUtils.instantiateClassImplementing(className, RequestHandler.class);

        String eventClassName = args[1];
        Class<?> eventClass = ReflectionUtils.resolveClass(eventClassName);

        if(args.length == 3) {
            startServer(requestHandler, eventClass, args[2]);
        } else {
            startServer(requestHandler, eventClass, null);
        }
    }

    public static void stopServer() {
        if (router != null) {
            router.stop();
        }
    }

    public static void startServer(RequestHandler requestHandler, Class<?> eventClass, String lambdaFunctionName) {
        RequestForwarder requestForwarder = new RequestHandlerForwarder(requestHandler, eventClass, lambdaFunctionName);
        startServer(requestForwarder);
    }

    private static void startServer(RequestForwarder requestForwarder) {
        SparkConfig config = new SparkConfig();
        router = new SparkRouter(config, requestForwarder);
        router.start();
    }

}
