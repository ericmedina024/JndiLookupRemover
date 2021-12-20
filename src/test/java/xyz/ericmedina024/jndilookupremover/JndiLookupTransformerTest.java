package xyz.ericmedina024.jndilookupremover;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JndiLookupTransformerTest {

    @Test
    public void testLookupReplacement() {
        assertDoesNotThrow(() -> new JndiLookup().lookup(), "JndiLookup#lookup body was not replaced!");
        assertEquals(Constants.LOOKUP_RETURN_VALUE, new JndiLookup().lookup(), "JndiLookup#lookup return was not replaced!");
    }

    @Test
    public void testConvertJndiNameReplacement() {
        assertDoesNotThrow(() -> new JndiLookup().convertJndiName(), "JndiLookup#convertJndiName body was not replaced!");
        assertEquals("", new JndiLookup().convertJndiName(), "JndiLookup#convertJndiName return was not replaced!");
    }

}