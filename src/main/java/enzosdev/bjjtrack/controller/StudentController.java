package enzosdev.bjjtrack.controller;

import enzosdev.bjjtrack.dto.request.StudentPromotionRequest;
import enzosdev.bjjtrack.dto.request.StudentRequest;
import enzosdev.bjjtrack.dto.response.StudentPromotionResponse;
import enzosdev.bjjtrack.dto.response.StudentResponse;
import enzosdev.bjjtrack.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest studentRequest){
        StudentResponse studentResponse = studentService.createStudent(studentRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentResponse);
    }

    @PatchMapping("/{id}/stripe")
    ResponseEntity<StudentPromotionResponse> promoteStudentStripe(@PathVariable Long id, @RequestBody StudentPromotionRequest promotionRequest){
        StudentPromotionResponse promotionResponse = studentService.promoteStripe(id, promotionRequest);
        return ResponseEntity.status(HttpStatus.OK).body(promotionResponse);
    }
}
