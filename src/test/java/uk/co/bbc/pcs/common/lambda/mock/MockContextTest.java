package uk.co.bbc.pcs.common.lambda.mock;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class MockContextTest {
    private static Logger logger = LoggerFactory.getLogger(MockContextTest.class);

    private MockContext underTest;
    private MockContextBuilder builder;

    @Before
    public void setUp() throws Exception {
        builder = new MockContextBuilder("function_name", logger::info);
    }

    @Test
    public void getRemainingTimeInMillisShouldDecrement() throws Exception {
        underTest = builder.withRemainingTimeInMillis(10000).withRemainingTimeCountdown(true).createMockContext();
        long first = underTest.getRemainingTimeInMillis();
        Thread.sleep(1001);
        long second = underTest.getRemainingTimeInMillis();
        assertTrue(first > second);
    }

    @Test
    public void getRemainingTimeInMillisShouldNotDecrementWhenDisabled() throws Exception {
        underTest = builder.withRemainingTimeInMillis(10000).withRemainingTimeCountdown(false).createMockContext();
        long first = underTest.getRemainingTimeInMillis();
        Thread.sleep(1001);
        long second = underTest.getRemainingTimeInMillis();
        assertTrue(first == second);
    }
}
