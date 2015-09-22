package uk.co.bbc.pcs.common.lambda.forwarder;

import java.util.Optional;

@FunctionalInterface
public interface RequestForwarder {

    /**
     * @return
     *    Returns an Optional containing the response if the request forward was successful.
     *    Returns an empty Optional otherwise.
     */
    Optional<String> forwardRequest(String requestBody);

}
