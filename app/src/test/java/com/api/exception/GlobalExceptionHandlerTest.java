package com.api.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    void handleValidationException_WithMethodArgumentNotValidException_ReturnsBadRequest() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = mock(FieldError.class);

        when(bindingResult.getFieldError()).thenReturn(fieldError);
        when(fieldError.getDefaultMessage()).thenReturn("Validation error message");

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(null, bindingResult);

        // Act
        ResponseEntity<String> response = handler.handleValidationException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Validation error message", response.getBody());
    }

    @Test
    void handleNullPointerException_WithNullPointerException_ReturnsBadRequest() {
        // Arrange
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        NullPointerException exception = new NullPointerException();

        // Act
        ResponseEntity<String> response = handler.handleNullPointerException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("A required input parameter is missing.", response.getBody());
    }
}
