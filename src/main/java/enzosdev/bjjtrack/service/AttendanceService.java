package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.request.AttendanceRequest;
import enzosdev.bjjtrack.dto.response.AttendanceResponse;
import enzosdev.bjjtrack.entity.Attendance;
import enzosdev.bjjtrack.entity.Student;
import enzosdev.bjjtrack.exceptions.AcademyNotFoundException;
import enzosdev.bjjtrack.exceptions.AttendanceAlreadyExistsException;
import enzosdev.bjjtrack.exceptions.AttendanceNotFoundException;
import enzosdev.bjjtrack.exceptions.StudentNotFoundException;
import enzosdev.bjjtrack.mapper.AttendanceMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import enzosdev.bjjtrack.repository.AttendanceRepository;
import enzosdev.bjjtrack.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public AttendanceResponse createAttendance(AttendanceRequest attendanceRequest) {
        Student student = studentRepository.findById(attendanceRequest.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + attendanceRequest.getStudentId() + " not found"));

        if (attendanceRepository.existsByStudentIdAndAttendanceDate(attendanceRequest.getStudentId(), attendanceRequest.getAttendanceDate())) {
            throw new AttendanceAlreadyExistsException("Attendance already registered for this date");
        }

        Attendance attendance = attendanceMapper.toEntity(attendanceRequest, student);
        attendanceRepository.save(attendance);
        return attendanceMapper.toResponse(attendance);
    }

    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new AttendanceNotFoundException("Attendance with id " + id + " not found");
        }
        attendanceRepository.deleteById(id);
    }

    public Page<AttendanceResponse> findAttendancesByAcademyId (Long academyId, Pageable pageable) {
        if(!academyRepository.existsById(academyId)){
            throw  new AcademyNotFoundException("Academy not found.");
        }
       return attendanceRepository.findByStudentAcademyId(academyId, pageable)
               .map(attendanceMapper::toResponse);
    }

    public AttendanceResponse updateAttendanceById(Long id, AttendanceRequest attendanceRequest) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AttendanceNotFoundException("Attendance with id " + id + " not found"));

        studentRepository.findById(attendanceRequest.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student with id " + attendanceRequest.getStudentId() + " not found"));

        if (attendanceRepository.existsByStudentIdAndAttendanceDate(attendanceRequest.getStudentId(), attendanceRequest.getAttendanceDate())) {
            throw new AttendanceAlreadyExistsException("Attendance already registered for this date");
        }

        attendance.setAttendanceDate(attendanceRequest.getAttendanceDate());
        attendanceRepository.save(attendance);
        return attendanceMapper.toResponse(attendance);

    }

    public Page<AttendanceResponse> findAllAttendances( Pageable pageable) {
        return attendanceRepository.findAll(pageable)
                .map(attendanceMapper::toResponse);
    }

    public AttendanceResponse findAttendanceById(Long id) {
        Optional<Attendance> attendance = attendanceRepository.findById(id);
       return attendance.map(attendanceMapper::toResponse)
               .orElseThrow(() -> new AttendanceNotFoundException("Attendance with id " + id + " not found"));
    }

    public Page<AttendanceResponse> findAttendancesByStudentId(Long studentId, Pageable pageable) {
        if(!studentRepository.existsById(studentId)){
            throw  new StudentNotFoundException("Student with id " + studentId + " not found");
        }
        return attendanceRepository.findByStudentId(studentId, pageable)
                .map(attendanceMapper::toResponse);
    }

}
