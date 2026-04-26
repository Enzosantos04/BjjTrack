package enzosdev.bjjtrack.service;


import enzosdev.bjjtrack.dto.request.AttendanceRequest;
import enzosdev.bjjtrack.dto.response.AttendanceResponse;
import enzosdev.bjjtrack.entity.Attendance;
import enzosdev.bjjtrack.entity.Student;
import enzosdev.bjjtrack.exceptions.AttendanceAlreadyExistsException;
import enzosdev.bjjtrack.exceptions.AttendanceNotFoundException;
import enzosdev.bjjtrack.exceptions.StudentNotFoundException;
import enzosdev.bjjtrack.mapper.AttendanceMapper;
import enzosdev.bjjtrack.repository.AttendanceRepository;
import enzosdev.bjjtrack.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private final StudentRepository studentRepository;


    public AttendanceService(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper, StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.studentRepository = studentRepository;
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

}
