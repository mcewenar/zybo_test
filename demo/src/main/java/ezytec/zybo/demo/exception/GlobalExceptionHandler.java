package ezytec.zybo.demo.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomExceptions.NotFoundException.class)
    public ResponseEntity<ApiError> notFound(CustomExceptions.NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError(Instant.now().toString(), 404, ex.getMessage()));
    }

    @ExceptionHandler(CustomExceptions.ConflictException.class)
    public ResponseEntity<ApiError> conflict(CustomExceptions.ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError(Instant.now().toString(), 409, ex.getMessage()));
    }


    //Atrapa a todas las excepciones y las agrupa en una traza de errores por si ocurren diferentes tipos de errores
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.badRequest()
                .body(new ApiError(Instant.now().toString(), 400, msg));
    }
}

