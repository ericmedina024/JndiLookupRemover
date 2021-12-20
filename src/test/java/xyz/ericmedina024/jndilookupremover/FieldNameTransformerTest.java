package xyz.ericmedina024.jndilookupremover;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldNameTransformerTest {

    @Test
    public void testLookupReplacement() {
        assertDoesNotThrow(() -> new FieldNameTransformer().lookup(), "FieldNameTransformer#lookup body was not replaced!");
        assertEquals(Constants.LOOKUP_RETURN_VALUE, new FieldNameTransformer().lookup(), "FieldNameTransformer#lookup return was not replaced!");
    }

    @Test
    public void testConvertJndiNameReplacement() {
        assertDoesNotThrow(() -> new FieldNameTransformer().convertJndiName(), "FieldNameTransformer#convertJndiName body was not replaced!");
        assertEquals("", new FieldNameTransformer().convertJndiName(), "FieldNameTransformer#convertJndiName return was not replaced!");
    }

}