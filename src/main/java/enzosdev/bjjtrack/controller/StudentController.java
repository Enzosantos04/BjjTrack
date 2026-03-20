package enzosdev.bjjtrack.controller;

import enzosdev.bjjtrack.dto.request.StudentPromotionRequest;
import enzosdev.bjjtrack.dto.request.StudentRequest;
import enzosdev.bjjtrack.dto.response.StudentPromotionResponse;
import enzosdev.bjjtrack.dto.response.StudentResponse;
import enzosdev.bjjtrack.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
   public ResponseEntity<StudentPromotionResponse> promoteStudentStripe(@PathVariable Long id, @RequestBody StudentPromotionRequest promotionRequest){
        StudentPromotionResponse promotionResponse = studentService.promoteStripe(id, promotionRequest);
        return ResponseEntity.status(HttpStatus.OK).body(promotionResponse);
    }

    @PatchMapping("/{id}/promote-belt")
    public ResponseEntity<StudentPromotionResponse> promoteStudentBelt(@PathVariable Long id, @RequestBody StudentPromotionRequest promotionRequest){
        StudentPromotionResponse studentResponse = studentService.promoteBelt(id, promotionRequest);
        return ResponseEntity.ok(studentResponse);

    }


    @GetMapping
    public ResponseEntity<Page<StudentResponse>> findAllStudents(Pageable pageable){
       Page<StudentResponse> studentResponse = studentService.findAllStudents(pageable);
       return ResponseEntity.ok(studentResponse);
    }

    @GetMapping("/academy/{id}")
    public ResponseEntity<Page<StudentResponse>> findStudentsByAcademyId(@PathVariable Long id, Pageable pageable){
        Page<StudentResponse> studentResponses = studentService.findStudentsByAcademyId(id, pageable);
        return ResponseEntity.ok(studentResponses);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Long id){
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }



}
