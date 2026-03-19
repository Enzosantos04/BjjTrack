package enzosdev.bjjtrack.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        errorResponse.put("Status: ", "400");
        errorResponse.put("error: ", "Empty fields are not allowed, try again");
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(AcademyNameAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAcademyNameAlreadyExistsException(AcademyNameAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status: ", "400");
        response.put("error: ", "Academy name already exists, try again!");
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(AcademySlugAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleAcademySlugAlreadyExistsException(AcademySlugAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status: ", "400");
        response.put("error: ", "Academy slug already exists, try again!");
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(UserEmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserEmailAlreadyExistsException(UserEmailAlreadyExistsException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status: ", "400");
        response.put("error: ", "User email already exists, try again!");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<Map<String, String>> handleEmptyFieldException(EmptyFieldException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status: ", "400");
        response.put("error: ", "Empty Field are not allowed, try again!");
        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(AcademyNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleAcademyNotFoundException(AcademyNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status: ", "404");
        response.put("error: ", "Academy Not Found");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String >> handleUserNotFoundException(UserNotFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status ", "404");
        response.put("error: ", "User Not Found");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserAlreadyActivatedException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyActivatedException(UserAlreadyActivatedException ex){
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status: ", "400");
        response.put("error: ", "User already activated.");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(UserAlreadyDeactivatedException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyDeactivatedException(UserAlreadyDeactivatedException ex){
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status: ", "400");
        response.put("error: ", "User already deactivated.");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleInvalidEnum(HttpMessageNotReadableException ex) {
       Map<String, String > response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
       response.put("Status: ", "400");
       response.put("Error: ", "Invalid belt value. Allowed values: WHITE, BLUE, PURPLE, BROWN, BLACK");
       return  ResponseEntity.badRequest().body(response);

    }

    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleStudentAlreadyExistsException(StudentAlreadyExistsException ex) {
        Map<String, String > response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status: ", "400");
        response.put("Error: ", "Student already exists");
        return  ResponseEntity.badRequest().body(response);

    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, String >> handleStudentNotFoundException(StudentNotFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status ", "404");
        response.put("error: ", "Student Not Found");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(StudentAlreadyHasMaxStripesException.class)
    public ResponseEntity<Map<String, String >> handleStudentAlreadyHasMaxStripesException(StudentAlreadyHasMaxStripesException ex){
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status ", "400");
        response.put("error: ", "Student already has maximum stripes");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InvalidStripesException.class)
    public ResponseEntity<Map<String, String >> handleInvalidStripesException(InvalidStripesException ex){
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status ", "400");
        response.put("error: ", "Stripes must be between 0 and 4");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(InsufficientStripesForPromotionException.class)
    public ResponseEntity<Map<String, String >> handleInsufficientStripesForPromotion(InsufficientStripesForPromotionException ex){
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status ", "400");
        response.put("error: ", "Student has insufficient stripes for promotion");
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(SameBeltPromotionNotAllowedException.class)
    public ResponseEntity<Map<String, String >> handleSameBeltPromotionNotAllowedException(SameBeltPromotionNotAllowedException ex){
        Map<String, String> response = new HashMap<>();
        response.put("Message: ", ex.getMessage());
        response.put("Status ", "400");
        response.put("error: ", "Promoting to the same belt is not allowed");
        return ResponseEntity.badRequest().body(response);
    }



}
