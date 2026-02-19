package enzosdev.bjjtrack.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Status", "400");
        errorResponse.put("error: ", "Empty fields are not allowed, try again");
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(AcademyNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAcademyNameAlreadyExistsException(AcademyNameAlreadyExistsException ex){
        Map<String , String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status", "400");
        response.put("error: ", "Academy name already exists, try again!");
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(AcademySlugAlreadyExistsException.class)
    public ResponseEntity<Map<String,String>> handleAcademySlugAlreadyExistsException(AcademySlugAlreadyExistsException ex){
        Map<String,String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status", "400");
        response.put("error: ", "Academy slug already exists, try again!");
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<Map<String,String >> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException ex){
        Map<String,String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status", "400");
        response.put("error: ", "User email already exists, try again!");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<Map<String,String >> handleEmptyFieldException(EmptyFieldException ex){
        Map<String,String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status", "400");
        response.put("error: ", "Empty Field are not allowed, try again!");
        return ResponseEntity.badRequest().body(response);
    }
}
