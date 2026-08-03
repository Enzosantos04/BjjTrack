package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.request.AttendanceRequest;
import enzosdev.bjjtrack.dto.response.AttendanceResponse;
import enzosdev.bjjtrack.entity.Attendance;
import enzosdev.bjjtrack.entity.Student;
import enzosdev.bjjtrack.exceptions.AcademyNotFoundException;
import enzosdev.bjjtrack.exceptions.AttendanceAlreadyExistsException;
import enzosdev.bjjtrack.exceptions.AttendanceNotFoundException;
import enzosdev.bjjtrack.exceptions.StudentNotFoundException;
import enzosdev.bjjtrack.exceptions.UnauthorizedAccessException;
import enzosdev.bjjtrack.mapper.AttendanceMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import enzosdev.bjjtrack.repository.AttendanceRepository;
import enzosdev.bjjtrack.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final StudentRepository studentRepository;
    private final AcademyRepository academyRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper, StudentRepository studentRepository, AcademyRepository academyRepository) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.studentRepository = studentRepository;
        this.academyRepository = academyRepository;
    }

    private void validateSameAcademy(Long resourceAcademyId, Long academyIdLogged, boolean isPlatformAdmin){
        if (isPlatformAdmin) {
            return;
        }
        if (!resourceAcademyId.equals(academyIdLogged)){
            throw new UnauthorizedAccessException("Access denied");
        }
    }

    public AttendanceResponse createAttendance(AttendanceRequest attendanceRequest, Long academyIdLogged, boolean isPlatformAdmin) {
        Student student = studentRepository.findById(attendanceRequest.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + attendanceRequest.getStudentId() + " not found"));

        validateSameAcademy(student.getAcademy().getId(), academyIdLogged, isPlatformAdmin);

        if (attendanceRepository.existsByStudentIdAndAttendanceDate(attendanceRequest.getStudentId(), attendanceRequest.getAttendanceDate())) {
            throw new AttendanceAlreadyExistsException("Attendance already registered for this date");
        }

        Attendance attendance = attendanceMapper.toEntity(attendanceRequest, student);
        attendanceRepository.save(attendance);
        return attendanceMapper.toResponse(attendance);
    }

    public void deleteAttendance(Long id, Long academyIdLogged, boolean isPlatformAdmin) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance with id " + id + " not found"));

        validateSameAcademy(attendance.getStudent().getAcademy().getId(), academyIdLogged, isPlatformAdmin);

        attendanceRepository.deleteById(id);
    }

    public Page<AttendanceResponse> findAttendancesByAcademyId (Long academyId, Long academyIdLogged, boolean isPlatformAdmin, Pageable pageable) {
        validateSameAcademy(academyId, academyIdLogged, isPlatformAdmin);

        if(!academyRepository.existsById(academyId)){
            throw  new AcademyNotFoundException("Academy not found.");
        }
       return attendanceRepository.findByStudentAcademyId(academyId, pageable)
                .map(attendanceMapper::toResponse);
    }

    public AttendanceResponse updateAttendanceById(Long id, AttendanceRequest attendanceRequest, Long academyIdLogged, boolean isPlatformAdmin) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance with id " + id + " not found"));

        validateSameAcademy(attendance.getStudent().getAcademy().getId(), academyIdLogged, isPlatformAdmin);

        studentRepository.findById(attendanceRequest.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + attendanceRequest.getStudentId() + " not found"));

        if (attendanceRepository.existsByStudentIdAndAttendanceDate(attendanceRequest.getStudentId(), attendanceRequest.getAttendanceDate())) {
            throw new AttendanceAlreadyExistsException("Attendance already registered for this date");
        }

        attendance.setAttendanceDate(attendanceRequest.getAttendanceDate());
        attendanceRepository.save(attendance);
        return attendanceMapper.toResponse(attendance);

    }

    public Page<AttendanceResponse> findAllAttendances(Long academyIdLogged, boolean isPlatformAdmin, Pageable pageable) {
        if (isPlatformAdmin){
            return attendanceRepository.findAll(pageable)
                    .map(attendanceMapper::toResponse);
        }

        return attendanceRepository.findByStudentAcademyId(academyIdLogged, pageable)
                .map(attendanceMapper::toResponse);
    }

    public AttendanceResponse findAttendanceById(Long id, Long academyIdLogged, boolean isPlatformAdmin) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
        return attendance.map(foundAttendance -> {
            validateSameAcademy(foundAttendance.getStudent().getAcademy().getId(), academyIdLogged, isPlatformAdmin);
            return attendanceMapper.toResponse(foundAttendance);
        }).orElseThrow(() -> new AttendanceNotFoundException("Attendance with id " + id + " not found"));
    }

    public Page<AttendanceResponse> findAttendancesByStudentId(Long studentId, Long academyIdLogged, boolean isPlatformAdmin, Pageable pageable) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + studentId + " not found"));

        validateSameAcademy(student.getAcademy().getId(), academyIdLogged, isPlatformAdmin);

        return attendanceRepository.findByStudentId(studentId, pageable)
                .map(attendanceMapper::toResponse);
    }

}
