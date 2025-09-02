package com.company.test.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidationExceptionTest {
    @Test
    void shouldStoreMessage() {
        String msg = "Erro de validação";
        ValidationException ex = new ValidationException(msg);
        assertEquals(msg, ex.getMessage());
    }

    @Test
    void shouldBeRuntimeException() {
        ValidationException ex = new ValidationException("msg");
        assertTrue(ex instanceof RuntimeException);
    }
}
