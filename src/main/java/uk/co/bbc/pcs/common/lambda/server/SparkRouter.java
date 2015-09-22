package uk.co.bbc.pcs.common.lambda.server;

import uk.co.bbc.pcs.common.lambda.forwarder.RequestForwarder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Spark;

import java.util.Optional;

public class SparkRouter {
    private static Logger logger = LoggerFactory.getLogger(SparkRouter.class);

    private final SparkConfig config;
    private final RequestForwarder requestForwarder;

    public SparkRouter(SparkConfig config, RequestForwarder requestForwarder) {
        this.config = config;
        this.requestForwarder = requestForwarder;
    }

    public void start() {
        int port = config.getPort();
        Spark.port(port);
        Spark.get("/", (request, response) -> "AWS Lambda Runner Java");
        Spark.post("/", this::forwardRequest);
        logger.info("Started server at port {}", port);
    }

    public void stop() {
        Spark.stop();
    }

    private String forwardRequest(Request request, Response response) {
        String rawRequestBody = request.body();
        Optional<String> responseBody = requestForwarder.forwardRequest(rawRequestBody);
        if (responseBody.isPresent()) {
            response.status(200);
            return responseBody.get();
        } else {
            logger.error("Request was not handled correctly");
            response.status(400);
            return "Request not handled correctly";
        }
    }

}
