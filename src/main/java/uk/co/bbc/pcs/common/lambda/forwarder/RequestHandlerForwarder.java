package uk.co.bbc.pcs.common.lambda.forwarder;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.util.json.JSONException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.bbc.pcs.common.lambda.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.bbc.pcs.common.lambda.jackson.AwsLambdaEventsObjectMapper;
import uk.co.bbc.pcs.common.lambda.mock.MockContextBuilder;

import java.io.IOException;
import java.util.Optional;

/**
 * Forwards requests on to the given RequestHandler.
 */
public class RequestHandlerForwarder implements RequestForwarder {

    private static Logger logger = LoggerFactory.getLogger(RequestHandlerForwarder.class);

    private static final String HANDLE_REQUEST_METHOD_NAME = "handleRequest";

    private final RequestHandler requestHandler;
    private final Class<?> requestType;
    private final ObjectMapper objectMapper;

    public RequestHandlerForwarder(String className) {
        this.requestHandler = ReflectionUtils.instantiateClassImplementing(className, RequestHandler.class);
        this.requestType = ReflectionUtils.getMethodParameterType(requestHandler.getClass(), HANDLE_REQUEST_METHOD_NAME, 0);

        objectMapper = new AwsLambdaEventsObjectMapper();
    }

    @Override
    public Optional<String> forwardRequest(String requestBody) {
        try {
            Object request = parseRequestBody(requestBody);
            String response = forwardToRequestHandler(request);
            return Optional.of(response);
        } catch (Exception e) {
            logger.error("Exception thrown when handling request", e);
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    private String forwardToRequestHandler(Object request) throws JsonProcessingException {
        Context context = new MockContextBuilder(requestHandler.getClass().getName()).createMockContext();
        Object response = requestHandler.handleRequest(request, context);
        return objectMapper.writeValueAsString(response);
    }

    private Object parseRequestBody(String rawRequestBody) throws JSONException, IOException {
        return objectMapper.readValue(rawRequestBody, requestType);
    }

}
