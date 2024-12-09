package ProxyAssertions.junit.framework;


import static spiderauxiliary.ProbeRecord.isAssertionError;
import static spiderauxiliary.TestExtension.*;

public class TestCase {

    public static void assertTrue(String message, boolean condition, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertTrue(message, condition);
        runOldAssertion(runnable, loc);
    }

    public static void assertTrue(boolean condition, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertTrue(condition);
        runOldAssertion(runnable, loc);
    }

    public static void assertFalse(String message, boolean condition, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertFalse(message, condition);
        runOldAssertion(runnable, loc);
    }

    public static void assertFalse(boolean condition, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertFalse(condition);
        runOldAssertion(runnable, loc);
    }

    public static void fail(String message, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.fail(message);
        runOldAssertion(runnable, loc);
    }

    public static void fail(String loc) {
        Runnable runnable = () -> junit.framework.TestCase.fail();
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String message, Object expected, Object actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(Object expected, Object actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String message, String expected, String actual,String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String expected, String actual,String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String message, double expected, double actual, double delta,String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(message, expected, actual, delta);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(double expected, double actual, double delta, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(expected, actual, delta);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String message, float expected, float actual, float delta, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(message, expected, actual, delta);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(float expected, float actual, float delta,String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(expected, actual, delta);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String message, long expected, long actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(long expected, long actual,String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String message, boolean expected, boolean actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(boolean expected, boolean actual,String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String message, byte expected, byte actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(byte expected, byte actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String message, char expected, char actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(char expected, char actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String message, short expected, short actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(short expected, short actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(String message, int expected, int actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertEquals(int expected, int actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertEquals(expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertNotNull(Object object, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertNotNull(object);
        runOldAssertion(runnable, loc);
    }

    public static void assertNotNull(String message, Object object, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertNotNull(message, object);
        runOldAssertion(runnable, loc);
    }

    public static void assertNull(Object object, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertNull(object);
        runOldAssertion(runnable, loc);
    }

    public static void assertNull(String message, Object object, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertNull(message, object);
        runOldAssertion(runnable, loc);
    }

    public static void assertSame(String message, Object expected, Object actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertSame(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertSame(Object expected, Object actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertSame(expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertNotSame(String message, Object expected, Object actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertNotSame(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void assertNotSame(Object expected, Object actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.assertNotSame(expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void failSame(String message, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.failSame(message);
        runOldAssertion(runnable, loc);
    }

    public static void failNotSame(String message, Object expected, Object actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.failNotSame(message, expected, actual);
        runOldAssertion(runnable, loc);
    }

    public static void failNotEquals(String message, Object expected, Object actual, String loc) {
        Runnable runnable = () -> junit.framework.TestCase.failNotEquals(message, expected, actual);
        runOldAssertion(runnable, loc);
    }


    public static void runOldAssertion(Runnable runnable,String location) {
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
