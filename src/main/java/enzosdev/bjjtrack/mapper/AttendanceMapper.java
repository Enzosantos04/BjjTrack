package enzosdev.bjjtrack.mapper;

import enzosdev.bjjtrack.dto.request.AttendanceRequest;
import enzosdev.bjjtrack.dto.response.AttendanceResponse;
import enzosdev.bjjtrack.entity.Attendance;
import enzosdev.bjjtrack.entity.Student;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {

    public Attendance toEntity(AttendanceRequest request, Student student) {
        if (request == null) {
            return null;
        }

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setAttendanceDate(request.getAttendanceDate());
        return attendance;
    }

    public AttendanceResponse toResponse(Attendance attendance) {
        if (attendance == null) {
            return null;
        }

        AttendanceResponse response = new AttendanceResponse();
        response.setId(attendance.getId());
        response.setAttendanceDate(attendance.getAttendanceDate());

        if (attendance.getStudent() != null) {
            response.setStudentId(attendance.getStudent().getId());
            if (attendance.getStudent().getUser() != null) {
                response.setStudentName(attendance.getStudent().getUser().getName());
            }
        }

        return response;
    }
}
