package ProxyAssertions.org.junit.jupiter.api;

import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.opentest4j.MultipleFailuresError;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static spiderauxiliary.ProbeRecord.isAssertionError;
import static spiderauxiliary.TestExtension.*;

public class Assertions {

    public static <V> V fail(String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.fail();
        runOldAssertion(runnable, location);
        return null;
    }

    public static <V> V fail(String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.fail(message);
        runOldAssertion(runnable, location);
        return null;
    }

    public static <V> V fail(String message, Throwable cause, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.fail(message, cause);
        runOldAssertion(runnable, location);
        return null;
    }

    public static <V> V fail(Throwable cause, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.fail(cause);
        runOldAssertion(runnable, location);
        return null;
    }

    public static <V> V fail(Supplier<String> messageSupplier,String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.fail(messageSupplier);
        runOldAssertion(runnable, location);
        return null;
    }


    public static void assertTrue(boolean condition, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertTrue(condition);
        runOldAssertion(runnable, location);
    }


    public static void assertTrue(boolean condition, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertTrue(condition, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertTrue(BooleanSupplier booleanSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertTrue(booleanSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertTrue(BooleanSupplier booleanSupplier, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertTrue(booleanSupplier, message);
        runOldAssertion(runnable,location);
    }

    public static void assertTrue(boolean condition, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertTrue(condition, message);
        runOldAssertion(runnable,location);
    }

    public static void assertTrue(BooleanSupplier booleanSupplier, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertTrue(booleanSupplier, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertFalse(boolean condition, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertFalse(condition);
        runOldAssertion(runnable,location);
    }

    public static void assertFalse(boolean condition, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertFalse(condition, message);
        runOldAssertion(runnable,location);
    }

    public static void assertFalse(boolean condition, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertFalse(condition, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertFalse(BooleanSupplier booleanSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertFalse(booleanSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertFalse(BooleanSupplier booleanSupplier, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertFalse(booleanSupplier, message);
        runOldAssertion(runnable,location);
    }

    public static void assertFalse(BooleanSupplier booleanSupplier, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertFalse(booleanSupplier, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertNull(Object actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNull(actual);
        runOldAssertion(runnable,location);
    }

    public static void assertNull(Object actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNull(actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertNull(Object actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNull(actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertNotNull(Object actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotNull(actual);
        runOldAssertion(runnable,location);
    }

    public static void assertNotNull(Object actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotNull(actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertNotNull(Object actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotNull(actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(short expected, short actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(short expected, Short actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Short expected, short actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Short expected, Short actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(short expected, short actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(short expected, Short actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Short expected, short actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Short expected, Short actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(short expected, short actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(short expected, Short actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Short expected, short actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Short expected, Short actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(byte expected, byte actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(byte expected, Byte actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Byte expected, byte actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Byte expected, Byte actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(byte expected, byte actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(byte expected, Byte actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Byte expected, byte actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }


    public static void assertEquals(Byte expected, Byte actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(byte expected, byte actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(byte expected, Byte actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Byte expected, byte actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }


    public static void assertEquals(Byte expected, Byte actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(int expected, int actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(int expected, Integer actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Integer expected, int actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Integer expected, Integer actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(int expected, int actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(int expected, Integer actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Integer expected, int actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Integer expected, Integer actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(int expected, int actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(int expected, Integer actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Integer expected, int actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Integer expected, Integer actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(long expected, long actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(long expected, Long actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Long expected, long actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Long expected, Long actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(long expected, long actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(long expected, Long actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Long expected, long actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }


    public static void assertEquals(Long expected, Long actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(long expected, long actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(long expected, Long actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Long expected, long actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Long expected, Long actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(float expected, float actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(float expected, Float actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Float expected, float actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Float expected, Float actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(float expected, float actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(float expected, Float actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Float expected, float actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Float expected, Float actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(float expected, float actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(float expected, Float actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Float expected, float actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Float expected, Float actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(float expected, float actual, float delta, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, delta);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(float expected, float actual, float delta, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, delta, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(float expected, float actual, float delta, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, delta, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(double expected, double actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(double expected, Double actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Double expected, double actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Double expected, Double actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(double expected, double actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(double expected, Double actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Double expected, double actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Double expected, Double actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(double expected, double actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(double expected, Double actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Double expected, double actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Double expected, Double actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(double expected, double actual, double delta, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, delta);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(double expected, double actual, double delta, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, delta, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(double expected, double actual, double delta, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, delta, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(char expected, char actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(char expected, Character actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Character expected, char actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Character expected, Character actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(char expected, char actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(char expected, Character actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Character expected, char actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Character expected, Character actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(char expected, char actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(char expected, Character actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Character expected, char actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Character expected, Character actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Object expected, Object actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Object expected, Object actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertEquals(Object expected, Object actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(boolean[] expected, boolean[] actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(boolean[] expected, boolean[] actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(boolean[] expected, boolean[] actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(char[] expected, char[] actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(char[] expected, char[] actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(char[] expected, char[] actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(byte[] expected, byte[] actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(byte[] expected, byte[] actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(byte[] expected, byte[] actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(short[] expected, short[] actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(short[] expected, short[] actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(short[] expected, short[] actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(int[] expected, int[] actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(int[] expected, int[] actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(int[] expected, int[] actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(long[] expected, long[] actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(long[] expected, long[] actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(long[] expected, long[] actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, float delta, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, delta);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, float delta, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, delta, message);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(float[] expected, float[] actual, float delta, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, delta, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, message);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, double delta, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, delta);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, double delta, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, delta, message);
        runOldAssertion(runnable,location);
    }

    public static void assertArrayEquals(double[] expected, double[] actual, double delta, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, delta, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(Object[] expected, Object[] actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(Object[] expected, Object[] actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertArrayEquals(Object[] expected, Object[] actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertArrayEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertIterableEquals(Iterable<?> expected, Iterable<?> actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertIterableEquals(expected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertIterableEquals(Iterable<?> expected, Iterable<?> actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertIterableEquals(expected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertIterableEquals(Iterable<?> expected, Iterable<?> actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertIterableEquals(expected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertLinesMatch(List<String> expectedLines, List<String> actualLines, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertLinesMatch(expectedLines, actualLines);
        runOldAssertion(runnable, location);
    }

    public static void assertLinesMatch(List<String> expectedLines, List<String> actualLines, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertLinesMatch(expectedLines, actualLines, message);
        runOldAssertion(runnable, location);
    }

    public static void assertLinesMatch(List<String> expectedLines, List<String> actualLines, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertLinesMatch(expectedLines, actualLines, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertLinesMatch(Stream<String> expectedLines, Stream<String> actualLines, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertLinesMatch(expectedLines, actualLines);
        runOldAssertion(runnable, location);
    }

    public static void assertLinesMatch(Stream<String> expectedLines, Stream<String> actualLines, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertLinesMatch(expectedLines, actualLines, message);
        runOldAssertion(runnable, location);
    }

    public static void assertLinesMatch(Stream<String> expectedLines, Stream<String> actualLines, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertLinesMatch(expectedLines, actualLines, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(byte unexpected, byte actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(byte unexpected, Byte actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Byte unexpected, byte actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Byte unexpected, Byte actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(byte unexpected, byte actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(byte unexpected, Byte actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Byte unexpected, byte actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Byte unexpected, Byte actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(byte unexpected, byte actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(byte unexpected, Byte actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Byte unexpected, byte actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Byte unexpected, Byte actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(short unexpected, short actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(short unexpected, Short actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Short unexpected, short actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Short unexpected, Short actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(short unexpected, short actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(short unexpected, Short actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Short unexpected, short actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Short unexpected, Short actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(short unexpected, short actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(short unexpected, Short actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Short unexpected, short actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Short unexpected, Short actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(int unexpected, int actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(int unexpected, Integer actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Integer unexpected, int actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }


    public static void assertNotEquals(Integer unexpected, Integer actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(int unexpected, int actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(int unexpected, Integer actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Integer unexpected, int actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Integer unexpected, Integer actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(int unexpected, int actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(int unexpected, Integer actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Integer unexpected, int actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Integer unexpected, Integer actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(long unexpected, long actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(long unexpected, Long actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Long unexpected, long actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Long unexpected, Long actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(long unexpected, long actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(long unexpected, Long actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Long unexpected, long actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Long unexpected, Long actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(long unexpected, long actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(long unexpected, Long actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Long unexpected, long actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Long unexpected, Long actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(float unexpected, float actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(float unexpected, Float actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Float unexpected, float actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Float unexpected, Float actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(float unexpected, float actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(float unexpected, Float actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Float unexpected, float actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Float unexpected, Float actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(float unexpected, float actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(float unexpected, Float actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Float unexpected, float actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Float unexpected, Float actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(float unexpected, float actual, float delta, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(float unexpected, float actual, float delta, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, delta, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(float unexpected, float actual, float delta, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, delta, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(double unexpected, double actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(double unexpected, Double actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Double unexpected, double actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Double unexpected, Double actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(double unexpected, double actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(double unexpected, Double actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Double unexpected, double actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Double unexpected, Double actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(double unexpected, double actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(double unexpected, Double actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Double unexpected, double actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Double unexpected, Double actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(double unexpected, double actual, double delta, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, delta);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(double unexpected, double actual, double delta, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, delta, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(double unexpected, double actual, double delta, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, delta, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(char unexpected, char actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(char unexpected, Character actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Character unexpected, char actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Character unexpected, Character actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(char unexpected, char actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(char unexpected, Character actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Character unexpected, char actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Character unexpected, Character actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(char unexpected, char actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(char unexpected, Character actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Character unexpected, char actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Character unexpected, Character actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Object unexpected, Object actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Object unexpected, Object actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotEquals(Object unexpected, Object actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotEquals(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertSame(Object expected, Object actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertSame(expected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertSame(Object expected, Object actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertSame(expected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertSame(Object expected, Object actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertSame(expected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertNotSame(Object unexpected, Object actual, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotSame(unexpected, actual);
        runOldAssertion(runnable, location);
    }

    public static void assertNotSame(Object unexpected, Object actual, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotSame(unexpected, actual, message);
        runOldAssertion(runnable, location);
    }

    public static void assertNotSame(Object unexpected, Object actual, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertNotSame(unexpected, actual, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static void assertAll(Collection<Executable> executables, String location) throws MultipleFailuresError {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertAll(executables);
        runOldAssertion(runnable, location);
    }

    public static void assertAll(String heading, Collection<Executable> executables, String location) throws MultipleFailuresError {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertAll(heading, executables);
        runOldAssertion(runnable, location);
    }

    public static void assertAll(Stream<Executable> executables, String location) throws MultipleFailuresError {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertAll(executables);
        runOldAssertion(runnable, location);
    }

    public static void assertAll(String heading, Stream<Executable> executables, String location) throws MultipleFailuresError {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertAll(heading, executables);
        runOldAssertion(runnable, location);
    }

    /**
     * If the executable throws an assertion error, it will be muted and the test will continue.
     * If the executable throws other exceptions, it will return null
     * It is expected that, the return value will not be used for further analysis
     * @param expectedType
     * @param executable
     * @return
     * @param <T>
     */
    public static <T extends Throwable> T assertThrowsExactly(Class<T> expectedType, Executable executable, String location) {
        Callable callable = () -> org.junit.jupiter.api.Assertions.assertThrowsExactly(expectedType, executable);
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
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

    public static <T extends Throwable> T assertThrowsExactly(Class<T> expectedType, Executable executable, String message, String location) {
        Callable callable = () -> org.junit.jupiter.api.Assertions.assertThrowsExactly(expectedType, executable, message);
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
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

    public static <T extends Throwable> T assertThrowsExactly(Class<T> expectedType, Executable executable, Supplier<String> messageSupplier, String location) {
        Callable callable = () -> org.junit.jupiter.api.Assertions.assertThrowsExactly(expectedType, executable, messageSupplier);
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
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

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, String location) {
        Callable callable = () -> org.junit.jupiter.api.Assertions.assertThrows(expectedType, executable);
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
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

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, String message, String location) {
        Callable callable = () -> org.junit.jupiter.api.Assertions.assertThrows(expectedType, executable, message);
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
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

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, Supplier<String> messageSupplier, String location) {
        Callable callable = () -> org.junit.jupiter.api.Assertions.assertThrows(expectedType, executable, messageSupplier);
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
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

    public static void assertDoesNotThrow(Executable executable, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertDoesNotThrow(executable);
        runOldAssertion(runnable, location);
    }

    public static void assertDoesNotThrow(Executable executable, String message, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertDoesNotThrow(executable, message);
        runOldAssertion(runnable, location);
    }

    public static void assertDoesNotThrow(Executable executable, Supplier<String> messageSupplier, String location) {
        Runnable runnable = () -> org.junit.jupiter.api.Assertions.assertDoesNotThrow(executable, messageSupplier);
        runOldAssertion(runnable, location);
    }

    public static <T> T assertDoesNotThrow(ThrowingSupplier<T> supplier, String location) {

        Callable callable = () -> org.junit.jupiter.api.Assertions.assertDoesNotThrow(supplier);
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
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

    public static <T> T assertDoesNotThrow(ThrowingSupplier<T> supplier, String message, String location) {
        Callable callable = () -> org.junit.jupiter.api.Assertions.assertDoesNotThrow(supplier, message);
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
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

    public static <T> T assertDoesNotThrow(ThrowingSupplier<T> supplier, Supplier<String> messageSupplier, String location) {
        Callable callable = () -> org.junit.jupiter.api.Assertions.assertDoesNotThrow(supplier, messageSupplier);
        try {
            recordExecutedOracle(location);
            T t = (T) callable.call();
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
