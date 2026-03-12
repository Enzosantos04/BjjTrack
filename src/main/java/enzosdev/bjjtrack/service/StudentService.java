package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.StudentRequest;
import enzosdev.bjjtrack.dto.StudentResponse;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.entity.Student;
import enzosdev.bjjtrack.entity.User;
import enzosdev.bjjtrack.exceptions.AcademyNotFoundException;
import enzosdev.bjjtrack.exceptions.UserNotFoundException;
import enzosdev.bjjtrack.mapper.StudentMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import enzosdev.bjjtrack.repository.StudentRepository;
import enzosdev.bjjtrack.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    private StudentRepository studentRepository;
    private StudentMapper studentMapper;
    private AcademyRepository academyRepository;
    private UserRepository userRepository;

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper, AcademyRepository academyRepository, UserRepository userRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.academyRepository = academyRepository;
        this.userRepository = userRepository;
    }

    public StudentResponse createStudent(StudentRequest studentRequest){
        User user = userRepository.findById(studentRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Academy academy = academyRepository.findById(studentRequest.getAcademyId())
                .orElseThrow(() -> new AcademyNotFoundException("Academy not found"));

        Student student = studentMapper.toEntity(studentRequest, academy, user);
        student = studentRepository.save(student);
        return studentMapper.toResponse(student);

    }
}
