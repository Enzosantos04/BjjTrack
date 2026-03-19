package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.request.StudentPromotionRequest;
import enzosdev.bjjtrack.dto.request.StudentRequest;
import enzosdev.bjjtrack.dto.response.StudentPromotionResponse;
import enzosdev.bjjtrack.dto.response.StudentResponse;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.entity.Student;
import enzosdev.bjjtrack.entity.User;
import enzosdev.bjjtrack.enums.Belt;
import enzosdev.bjjtrack.exceptions.*;
import enzosdev.bjjtrack.mapper.StudentMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import enzosdev.bjjtrack.repository.StudentRepository;
import enzosdev.bjjtrack.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {


    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final AcademyRepository academyRepository;
    private final UserRepository userRepository;

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

        if (studentRepository.existsStudentByUserId(studentRequest.getUserId())){
            throw new StudentAlreadyExistsException("Student already exists");

        }


        Student student = studentMapper.toEntity(studentRequest, academy, user);
        student = studentRepository.save(student);
        return studentMapper.toResponse(student);

    }

    public StudentPromotionResponse promoteStripe(Long id, StudentPromotionRequest promotionRequest){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));
        student.setLastPromotion(promotionRequest.getLastPromotion());
        student.setBelt(student.getBelt());

        int stripes = student.getStripes();
        if (stripes < 0 || stripes > 4) {
            throw new InvalidStripesException("Stripes must be between 0 and 4");

        }

        if (stripes >= 4){
            throw new StudentAlreadyHasMaxStripesException("Student already has maximum stripes");
        }

        student.setStripes(stripes + 1);

        student = studentRepository.save(student);
        return studentMapper.toPromotionResponse(student);

    }

    public StudentPromotionResponse promoteBelt(Long id, StudentPromotionRequest promotionRequest){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        int stripes = student.getStripes();
        Enum<Belt> studentCurrentBelt = student.getBelt();

        if(stripes < 3){
            throw new InsufficientStripesForPromotionException("Student dont have minimum stripes quantity to be promoted");
        }

        student.setBelt(promotionRequest.getBelt());

        if (studentCurrentBelt == promotionRequest.getBelt()){
            throw new SameBeltPromotionNotAllowedException("Promoting to the same belt is not allowed");

        }

        student.setStripes(0);

        student = studentRepository.save(student);
        return studentMapper.toPromotionResponse(student);

    }
}
