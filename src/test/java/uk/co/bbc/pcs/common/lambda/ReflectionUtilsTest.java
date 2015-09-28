package uk.co.bbc.pcs.common.lambda;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReflectionUtilsTest {

    @Test
    public void shouldGetGenericParameter() {
        List<Integer> instance = Arrays.<Integer>asList(1, 2, 3);
        Type result = ReflectionUtils.getMethodGenericParameterType(instance, "get", 0);
        assertEquals("int", result.getTypeName());
    }

}