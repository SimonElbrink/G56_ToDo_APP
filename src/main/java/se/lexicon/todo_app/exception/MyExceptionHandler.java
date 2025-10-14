package se.lexicon.todo_app.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String[] constraints = ex.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toArray(String[]::new);

        return createErrorResponse(HttpStatus.BAD_REQUEST, constraints);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleValidationViolation(ConstraintViolationException ex) {
        System.out.println("HandleValidationViolation: " + ex.getMessage());
        String[] violations = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toArray(String[]::new);
        return createErrorResponse(HttpStatus.BAD_REQUEST, violations);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
        String errorMessage = "Resource not found: " + ex.getMessage();
        return createErrorResponse(HttpStatus.NOT_FOUND, errorMessage);
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        System.out.println("HandleMethodArgumentTypeMismatch: " + ex.getMessage()); // Log?

        String invalidMessage = "Parameter '%s' should be of type '%s'";
        String errorMessage = String.format(invalidMessage, ex.getName(), ex.getRequiredType().getSimpleName());

        return createErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        System.out.println("HandleRuntimeException: " + ex.getMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        System.out.println("HandleGlobalException: " + ex.getMessage());
        String uuid = java.util.UUID.randomUUID().toString().toUpperCase();
        System.err.println("Error ID: " + uuid + " - " + ex.getMessage());
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + uuid);
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus status, String... errors) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), errors);
        return new ResponseEntity<>(errorResponse, status);

    }


}
