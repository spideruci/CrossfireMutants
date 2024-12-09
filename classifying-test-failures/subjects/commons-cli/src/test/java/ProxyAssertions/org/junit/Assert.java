package ProxyAssertions.org.junit;

import org.junit.function.ThrowingRunnable;
import org.junit.internal.ArrayComparisonFailure;

import java.util.concurrent.Callable;

import static spiderauxiliary.ProbeRecord.isAssertionError;
import static spiderauxiliary.TestExtension.*;

/**
 * Proxy Method From junit 4.13
 * AssertThat is not proxied
 */
public class Assert {
    public static <T extends Throwable> T assertThrows(Class<T> expectedThrowable, ThrowingRunnable runnable, String location) {

        Callable callable = () -> org.junit.Assert.assertThrows(expectedThrowable, runnable);
        boolean firstExecuted = false;
        if (executedAssertions.size() == 0 ) {
            firstExecuted = true;
        }
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
            if (firstExecuted) {
            }
            return t;
        } catch (Throwable t) {
            if (isAssertionError(t)) {
                // mute the exception and keep the record;
                recordFailedThrowable(location);
                return null;
            } else {
                return null;
            }
        }

    }

    public static <T extends Throwable> T assertThrows(String message, Class<T> expectedThrowable, ThrowingRunnable runnable,String location) {

        Callable callable = () -> org.junit.Assert.assertThrows(message, expectedThrowable, runnable);
        boolean firstExecuted = false;
        if (executedAssertions.size() == 0 ) {
            firstExecuted = true;
        }
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
            if (firstExecuted) {
            }
            return t;
        } catch (Throwable t) {
            if (isAssertionError(t)) {
                // mute the exception and keep the record;
                recordFailedThrowable(location);
                return null;
            } else {
                return null;
            }
        }
    }

    public static void assertTrue(String message, boolean condition, String location) {
        Runnable runnable = () -> org.junit.Assert.assertTrue(message, condition);
        runOldAssertion(runnable, location);
    }

    public static void assertTrue(boolean condition, String location) {
        Runnable runnable = () -> org.junit.Assert.assertTrue(condition);
        runOldAssertion(runnable, location);
    }

    public static void assertFalse(String message, boolean condition, String location) {
        Runnable runnable = () -> org.junit.Assert.assertFalse(message, condition);
        runOldAssertion(runnable, location);
    }

    public static void assertFalse(boolean condition, String location) {
        Runnable runnable = () -> org.junit.Assert.assertFalse(condition);
        runOldAssertion(runnable, location);
    }

    public static void fail(String message, String location) {
        Runnable runnable = () -> org.junit.Assert.fail(message);
        runOldAssertion(runnable, location);
    }

    public static void fail(String location) {
        Runnable runnable = () -> org.junit.Assert.fail();
        runOldAssertion(runnable, location);
    }

    public static void assertEquals(String message, Object expected, Object actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(message, expected, actual);
        runOldAssertion(runnable, location);
    }



    public static void assertEquals(Object expected, Object actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(expected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(String message, Object unexpected, Object actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotEquals(message, unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Object unexpected, Object actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }


    public static void assertNotEquals(String message, long unexpected, long actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotEquals(message, unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(long unexpected, long actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(String message, double unexpected, double actual, double delta, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotEquals(message, unexpected, actual, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(double unexpected, double actual, double delta, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotEquals(unexpected, actual, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(float unexpected, float actual, float delta, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotEquals(unexpected, actual, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(String message, Object[] expecteds, Object[] actuals, String location) throws ArrayComparisonFailure {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(Object[] expecteds, Object[] actuals, String location) {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(String message, boolean[] expecteds, boolean[] actuals, String location) throws ArrayComparisonFailure {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals, String location) {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(String message, byte[] expecteds, byte[] actuals, String location) throws ArrayComparisonFailure {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(byte[] expecteds, byte[] actuals, String location) {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(String message, char[] expecteds, char[] actuals, String location) throws ArrayComparisonFailure {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(char[] expecteds, char[] actuals, String location) {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(String message, short[] expecteds, short[] actuals, String location) throws ArrayComparisonFailure {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(short[] expecteds, short[] actuals, String location) {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(String message, int[] expecteds, int[] actuals, String location) throws ArrayComparisonFailure {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(int[] expecteds, int[] actuals, String location) {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(String message, long[] expecteds, long[] actuals, String location) throws ArrayComparisonFailure {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(message, expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(long[] expecteds, long[] actuals, String location) {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(String message, double[] expecteds, double[] actuals, double delta, String location) throws ArrayComparisonFailure {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(message, expecteds, actuals, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(double[] expecteds, double[] actuals, double delta, String location) {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(expecteds, actuals, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(String message, float[] expecteds, float[] actuals, float delta, String location) throws ArrayComparisonFailure {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(message, expecteds, actuals, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(float[] expecteds, float[] actuals, float delta, String location) {
        Runnable runnable = () -> org.junit.Assert.assertArrayEquals(expecteds, actuals, delta);
        runOldAssertion(runnable, location);
    }


    public static void assertEquals(String message, double expected, double actual, double delta, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(message, expected, actual, delta);
        runOldAssertion(runnable, location);
    }


    public static void assertEquals(String message, float expected, float actual, float delta, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(message, expected, actual, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(String message, float unexpected, float actual, float delta, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotEquals(message, unexpected, actual, delta);
        runOldAssertion(runnable, location);
    }


    public static void assertEquals(long expected, long actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(expected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertEquals(String message, long expected, long actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(message, expected, actual);
        runOldAssertion(runnable, location);
    }

    /** @deprecated */
    @Deprecated
    public static void assertEquals(double expected, double actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(expected, actual);
        runOldAssertion(runnable, location);
    }

    /** @deprecated */
    @Deprecated
    public static void assertEquals(String message, double expected, double actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(message, expected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertEquals(double expected, double actual, double delta, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(expected, actual, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertEquals(float expected, float actual, float delta, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(expected, actual, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertNotNull(String message, Object object, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotNull(message, object);
        runOldAssertion(runnable, location);
    }

    public static void assertNotNull(Object object, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotNull(object);
        runOldAssertion(runnable, location);
    }

    public static void assertNull(String message, Object object, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNull(message, object);
        runOldAssertion(runnable, location);
    }

    public static void assertNull(Object object, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNull(object);
        runOldAssertion(runnable, location);
    }


    public static void assertSame(String message, Object expected, Object actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertSame(message, expected, actual);
        runOldAssertion(runnable, location);

    }

    public static void assertSame(Object expected, Object actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertSame(expected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotSame(String message, Object unexpected, Object actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotSame(message, unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotSame(Object unexpected, Object actual, String location) {
        Runnable runnable = () -> org.junit.Assert.assertNotSame(unexpected, actual);
        runOldAssertion(runnable, location);
    }


    /** @deprecated */
    @Deprecated
    public static void assertEquals(String message, Object[] expecteds, Object[] actuals, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(message, expecteds, actuals);
        runOldAssertion(runnable, location);
    }


    /** @deprecated */
    @Deprecated
    public static void assertEquals(Object[] expecteds, Object[] actuals, String location) {
        Runnable runnable = () -> org.junit.Assert.assertEquals(expecteds, actuals);
        runOldAssertion(runnable, location);
    }

    public static void runOldAssertion(Runnable runnable,String location) {
        boolean firstExecuted = false;
        if (executedAssertions.size() == 0 ) {
            firstExecuted = true;
        }
        try {
            recordExecutedOracle(location);
            runnable.run();
        } catch (Throwable t) {
            if (isAssertionError(t)) {
                recordFailedThrowable(location);
            } else {
                throw t;
            }
        }
    }
}
