package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.request.StudentAdminUpdateRequest;
import enzosdev.bjjtrack.dto.request.StudentProfileUpdateRequest;
import enzosdev.bjjtrack.dto.request.StudentPromotionRequest;
import enzosdev.bjjtrack.dto.request.StudentRequest;
import enzosdev.bjjtrack.dto.response.StudentAdminUpdateResponse;
import enzosdev.bjjtrack.dto.response.StudentProfileUpdateResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        if(stripes < 4){
            throw new InsufficientStripesForPromotionException("Student dont have minimum stripes quantity to be promoted");
        }



        if (studentCurrentBelt == promotionRequest.getBelt()){
            throw new SameBeltPromotionNotAllowedException("Promoting to the same belt is not allowed");

        }

        student.setBelt(promotionRequest.getBelt());
        student.setStripes(0);

        student = studentRepository.save(student);
        return studentMapper.toPromotionResponse(student);

    }


    public Page<StudentResponse> findAllStudents(Pageable pageable){
        return studentRepository.findAllStudentsByUserActiveTrue(pageable)
                .map(studentMapper::toResponse);
    }

    public Page<StudentResponse> findStudentsByAcademyId(Long id, Pageable pageable){
        if (!academyRepository.existsById(id)){
            throw new AcademyNotFoundException("Academy not found.");
        }

        return studentRepository.findStudentsByAcademyId(id, pageable)
                .map(studentMapper::toResponse);
    }

    public void deleteStudentById(Long id){
        if(!studentRepository.existsById(id)) {
            throw new StudentNotFoundException("Student not found");
        }

        studentRepository.deleteById(id);
    }


    public StudentResponse findStudentById(Long id){
        Optional<Student> student = studentRepository.findById(id);
        return student.map(studentMapper::toResponse)
                .orElseThrow(() -> new StudentNotFoundException("Student not Found"));
    }

    public StudentResponse findStudentByEmail(String email){
        Optional<Student> student = studentRepository.findStudentByUserEmail(email);
        return student.map(studentMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException("User not Found"));
    }

    public StudentProfileUpdateResponse updateOwnProfileById(Long id, StudentProfileUpdateRequest request){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        if(request.getBirthDate() == null){
            throw new RuntimeException("Birth date is required");
        }

        student.setBrithDate(request.getBirthDate());

        student = studentRepository.save(student);

        return studentMapper.toProfileUpdateResponse(student);
    }


    public StudentAdminUpdateResponse updateStudentAdminById(Long id, StudentAdminUpdateRequest request){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        if(request.getBelt() == null){
            throw new RuntimeException("Belt is required");
        }

        if(request.getStripe() > 4 || request.getStripe() < 0){
            throw new InvalidStripesException("Stripe must be between 0 and 4");
        }

        student.setBelt(request.getBelt());
        student.setStripes(request.getStripe());
        student = studentRepository.save(student);
        return studentMapper.toAdminUpdateResponse(student);
    }
}
