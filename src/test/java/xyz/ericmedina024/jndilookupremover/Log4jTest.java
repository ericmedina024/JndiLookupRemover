package xyz.ericmedina024.jndilookupremover;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.lookup.JndiLookup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class Log4jTest {

    private static final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private static final PrintStream origOut = System.out;

    @BeforeAll
    public static void replaceStdOut() {
        System.setOut(new PrintStream(outStream));
    }

    @Test
    public void testLogOutput() {
        Logger logger = (Logger) LogManager.getLogger(Log4jTest.class);
        logger.setLevel(Level.INFO);

        // Invalid domain that will never be registered per RFC 2606
        logger.info("${jndi:ldap://test.invalid}");
        assertTrue(outStream.toString().contains(Constants.LOOKUP_RETURN_VALUE), "Expected output stream (\"" + outStream + "\") to contain replacement string!");
    }

    @Test
    public void testDirectCalls() {
        // JndiLookup in this method refers to the class within the org.apache package, NOT the class in this testing package
        assertEquals(Constants.LOOKUP_RETURN_VALUE, new JndiLookup().lookup(null, "test"), "org.apache...JndiLookup#lookup return was not replaced!");
        try {
            Method method = JndiLookup.class.getDeclaredMethod("convertJndiName", String.class);
            method.setAccessible(true);
            assertEquals("", method.invoke(new JndiLookup(), "badString"), "org.apache...JndiLookup#lookup return was not replaced!");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
            // we'll test this if we can, but ignore exceptions since newer JVMs don't let you easily access private members
        }
    }

    @AfterAll
    public static void revertStdOut() {
        System.setOut(origOut);
    }

}
