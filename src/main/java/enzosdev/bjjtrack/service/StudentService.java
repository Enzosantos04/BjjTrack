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
import enzosdev.bjjtrack.enums.ScopeName;
import enzosdev.bjjtrack.exceptions.*;
import enzosdev.bjjtrack.mapper.StudentMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import enzosdev.bjjtrack.repository.StudentRepository;
import enzosdev.bjjtrack.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
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

    private void validateSameAcademy(Long resourceAcademyId, Long academyIdLogged, boolean isPlatformAdmin){
        if (isPlatformAdmin) {
            return;
        }
        if (!resourceAcademyId.equals(academyIdLogged)){
            throw new UnauthorizedAccessException("Access denied");
        }
    }

    public StudentResponse createStudent(StudentRequest studentRequest, Long academyIdLogged, boolean isPlatformAdmin){
        validateSameAcademy(studentRequest.getAcademyId(), academyIdLogged, isPlatformAdmin);

        User user = userRepository.findById(studentRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Academy academy = academyRepository.findById(studentRequest.getAcademyId())
                .orElseThrow(() -> new AcademyNotFoundException("Academy not found"));

        if (!user.getAcademy().getId().equals(academy.getId())){
            throw new UnauthorizedAccessException("Access denied");
        }

        if (studentRepository.existsStudentByUserId(studentRequest.getUserId())){
            throw new StudentAlreadyExistsException("Student already exists");

        }

        Student student = studentMapper.toEntity(studentRequest, academy, user);
        student = studentRepository.save(student);
        return studentMapper.toResponse(student);

    }

    public StudentPromotionResponse promoteStripe(Long id, Long academyIdLogged, boolean isPlatformAdmin, StudentPromotionRequest promotionRequest){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        validateSameAcademy(student.getAcademy().getId(), academyIdLogged, isPlatformAdmin);

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

    public StudentPromotionResponse promoteBelt(Long id, Long academyIdLogged, boolean isPlatformAdmin, StudentPromotionRequest promotionRequest){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        validateSameAcademy(student.getAcademy().getId(), academyIdLogged, isPlatformAdmin);

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


    public Page<StudentResponse> findAllStudents(Long academyIdLogged, boolean isPlatformAdmin, Pageable pageable){
        if (isPlatformAdmin){
            return studentRepository.findAllStudentsByUserActiveTrue(pageable)
                    .map(studentMapper::toResponse);
        }

        return studentRepository.findAllStudentsByUserActiveTrueAndAcademyId(academyIdLogged, pageable)
                .map(studentMapper::toResponse);
    }

    public Page<StudentResponse> findStudentsByAcademyId(Long id, Long academyIdLogged, boolean isPlatformAdmin, Pageable pageable){
        validateSameAcademy(id, academyIdLogged, isPlatformAdmin);

        if (!academyRepository.existsById(id)){
            throw new AcademyNotFoundException("Academy not found.");
        }

        return studentRepository.findStudentsByAcademyId(id, pageable)
                .map(studentMapper::toResponse);
    }

    public void deleteStudentById(Long id, Long academyIdLogged, boolean isPlatformAdmin){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        validateSameAcademy(student.getAcademy().getId(), academyIdLogged, isPlatformAdmin);

        studentRepository.deleteById(id);
    }

    @Cacheable(value = "students", key = "#id + '-' + #academyIdLogged")
    public StudentResponse findStudentById(Long id, Long academyIdLogged, boolean isPlatformAdmin){
        Optional<Student> student = studentRepository.findById(id);

        Student foundStudent = student
                .orElseThrow(() -> new StudentNotFoundException("Student not Found"));

        validateSameAcademy(foundStudent.getAcademy().getId(), academyIdLogged, isPlatformAdmin);

        return studentMapper.toResponse(foundStudent);
    }

    public StudentResponse findStudentByEmail(String email, Long academyIdLogged, boolean isPlatformAdmin){
        Optional<Student> student = studentRepository.findStudentByUserEmail(email);

        Student foundStudent = student
                .orElseThrow(() -> new UserNotFoundException("User not Found"));

        validateSameAcademy(foundStudent.getAcademy().getId(), academyIdLogged, isPlatformAdmin);

        return studentMapper.toResponse(foundStudent);
    }

    public StudentProfileUpdateResponse updateOwnProfileById(Long id, Long userIdLogged,  StudentProfileUpdateRequest request){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        if (!student.getUser().getId().equals(userIdLogged)) {
            throw new UnauthorizedAccessException("Access denied");
        }

        if(request.getBirthDate() == null){
            throw new EmptyFieldException("Birth date is required");
        }

        student.setBirthDate(request.getBirthDate());

        student = studentRepository.save(student);

        return studentMapper.toProfileUpdateResponse(student);
    }


    public StudentAdminUpdateResponse updateStudentAdminById(Long id, Long academyIdLogged, boolean isPlatformAdmin, StudentAdminUpdateRequest request){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

        validateSameAcademy(student.getAcademy().getId(), academyIdLogged, isPlatformAdmin);

        if(request.getBelt() == null){
            throw new EmptyFieldException("Belt is required");
        }

        if(request.getStripe() > 4 || request.getStripe() < 0){
            throw new InvalidStripesException("Stripe must be between 0 and 4");
        }

        if (request.getBirthDate() == null){
            throw new EmptyFieldException("Birth date is required");
        }

        student.setBelt(request.getBelt());
        student.setStripes(request.getStripe());
        student.setBirthDate(request.getBirthDate());
        student = studentRepository.save(student);
        return studentMapper.toAdminUpdateResponse(student);
    }
}
