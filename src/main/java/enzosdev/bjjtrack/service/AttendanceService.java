package enzosdev.bjjtrack.service;


import enzosdev.bjjtrack.dto.request.AttendanceRequest;
import enzosdev.bjjtrack.dto.response.AttendanceResponse;
import enzosdev.bjjtrack.entity.Attendance;
import enzosdev.bjjtrack.entity.Student;
import enzosdev.bjjtrack.exceptions.EmptyFieldException;
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
                .orElseThrow(() -> new StudentNotFoundException("Student not found"));

      if (attendanceRepository.existsByStudentIdAndAttendanceDate(attendanceRequest.getStudentId(),  attendanceRequest.getAttendanceDate())) {
          throw  new RuntimeException("Attendance already exists");
      }
        Attendance attendance = attendanceMapper.toEntity(attendanceRequest, student);
        attendanceRepository.save(attendance);
        return attendanceMapper.toResponse(attendance);

    }

    public void deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new RuntimeException("Attendance not found");
        }
        attendanceRepository.deleteById(id);
    }

}
