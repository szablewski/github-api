package bartosz.szablewski.githubapi.exception;

import bartosz.szablewski.githubapi.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;

import static java.time.LocalDateTime.now;

@ControllerAdvice
@Slf4j
@RestController
public class RestExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(HttpClientErrorException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(ex.getStatusCode().value())
                .error(ex.getMessage())
                .message("Requested repository could not be found.")
                .timestamp(now())
                .build();

        log.error("Requested repository could not be found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(406)
                .error(ex.getMessage())
                .message("Request repository have not acceptable media type.")
                .timestamp(now())
                .build();

        log.error("Request repository have not acceptable media type.");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(405)
                .error(ex.getMessage())
                .message("Request repository have not method not supported.")
                .timestamp(now())
                .build();

        log.error("Request repository have not method not supported.");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(401)
                .error(ex.getMessage())
                .message("Unauthorized request repository.")
                .timestamp(now())
                .build();

        log.error("Unauthorized request repository.");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorResponse);
    }

    @ExceptionHandler(EmailDuplicatedException.class)
    public ResponseEntity<ErrorResponse> handleEmailDuplicatedException(EmailDuplicatedException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.CONFLICT.value())
                .error(ex.getMessage())
                .message(String.format("Given email: %s already exist", ex.getEmail()))
                .timestamp(now())
                .build();

        log.error("Duplicated email: {}", ex.getEmail());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Owner and repo cannot be null or empty.")
                .message(ex.getMessage())
                .timestamp(now())
                .build();

        log.error("Request params was empty or null");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
