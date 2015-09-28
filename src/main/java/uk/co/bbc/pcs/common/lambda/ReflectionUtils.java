package uk.co.bbc.pcs.common.lambda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectionUtils {
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);

    /**
     * @param className The class to be instantiated
     * @param targetInterface Interface which the class should implement
     * @param <T> The type of the targetInterface
     *
     * @return
     * An instance of the class, if the resolved class can be instantiated
     * and it implements the given interface.
     * Throws a RuntimeException otherwise.
     */
    @SuppressWarnings("unchecked")
    public static <T> T instantiateClassImplementing(String className, Class<T> targetInterface) {
        try {
            Class<?> resolvedClass = Class.forName(className);
            if (!targetInterface.isAssignableFrom(resolvedClass)) {
                String message = String.format("%s is not assignable from %s", targetInterface.getName(), resolvedClass.getName());
                logger.error(message);
                throw new RuntimeException(message);
            }
            return ((Class<T>) resolvedClass).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            String message = String.format("Could not instantiate class: %s", className);
            logger.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public static Class<?> resolveClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            String message = String.format("Could not resolve class: %s", className);
            logger.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

}
