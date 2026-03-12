package enzosdev.bjjtrack.controller;

import enzosdev.bjjtrack.dto.StudentRequest;
import enzosdev.bjjtrack.dto.StudentResponse;
import enzosdev.bjjtrack.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
