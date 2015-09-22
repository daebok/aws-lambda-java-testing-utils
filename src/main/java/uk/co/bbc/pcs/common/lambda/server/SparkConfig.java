package uk.co.bbc.pcs.common.lambda.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SparkConfig {
    private static Logger logger = LoggerFactory.getLogger(SparkConfig.class);

    private static final int DEFAULT_PORT = 8080;

    private static final String PORT_PROPERTY = "port";

    public int getPort() {
        return getIntegerProperty(PORT_PROPERTY, DEFAULT_PORT);
    }

    private int getIntegerProperty(String propertyName, int defaultValue) {
        if (System.getProperties().containsKey(propertyName)) {
            String rawValue = System.getProperty(propertyName);
            try {
                int value = Integer.parseInt(rawValue);
                logger.info("{} property set to {}", propertyName, value);
                return value;
            } catch (Exception e) {
                logger.warn("{} property was not set to an integer. Using default {}", propertyName, defaultValue);
                return defaultValue;
            }
        } else {
            logger.info("{} property was not set. Using default {}", propertyName, defaultValue);
            return defaultValue;
        }
    }

}
