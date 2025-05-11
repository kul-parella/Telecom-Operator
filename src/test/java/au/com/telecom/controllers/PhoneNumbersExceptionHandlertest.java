package au.com.telecom.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.context.request.WebRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class PhoneNumbersExceptionHandlerTest {

    private final PhoneNumbersExceptionHandler exceptionHandler = new PhoneNumbersExceptionHandler();
    private final WebRequest mockRequest = mock(WebRequest.class);

    @Test
    void handleHttpMessageNotReadableException_shouldReturnBadRequest() {

        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("Invalid JSON");
        ResponseEntity<Object> response = exceptionHandler.handleHttpMessageNotReadableException(ex, mockRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof PhoneNumbersExceptionHandler.ErrorResponse);

        PhoneNumbersExceptionHandler.ErrorResponse errorResponse =
                (PhoneNumbersExceptionHandler.ErrorResponse) response.getBody();
        assertEquals("Json input could not be parsed", errorResponse.getMessage());
    }

    @Test
    void handleException_shouldReturnInternalServerError() {

        Exception ex = new Exception("Something went wrong");
        ResponseEntity<Object> response = exceptionHandler.handleException(ex, mockRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof PhoneNumbersExceptionHandler.ErrorResponse);

        PhoneNumbersExceptionHandler.ErrorResponse errorResponse =
                (PhoneNumbersExceptionHandler.ErrorResponse) response.getBody();
        assertEquals("Internal server error", errorResponse.getMessage());
    }

    @Test
    void errorResponse_shouldHaveCorrectStructure() {

        String testMessage = "Test error message";
        PhoneNumbersExceptionHandler.ErrorResponse errorResponse =
                new PhoneNumbersExceptionHandler.ErrorResponse(testMessage);

        assertEquals(testMessage, errorResponse.getMessage());
        String newMessage = "New message";
        errorResponse.setMessage(newMessage);
        assertEquals(newMessage, errorResponse.getMessage());
    }
}