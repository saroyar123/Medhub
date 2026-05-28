package in.medhub.backend.exception;

import in.medhub.backend.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Catches all ResponseStatusExceptions thrown across the app
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatusException(ResponseStatusException ex) {
        
        ApiResponse<Void> errorResponse = new ApiResponse<>(
            false, 
            ex.getReason() // Extracts the custom message you wrote in the service
        );
        
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    // Fallback: Catches any unexpected code crashes (like NullPointerExceptions)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        
        ApiResponse<Void> errorResponse = new ApiResponse<>(
            false, 
            "An unexpected internal server error occurred."
        );
        
        return new ResponseEntity<>(errorResponse, org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }
}