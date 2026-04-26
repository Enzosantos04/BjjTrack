package enzosdev.bjjtrack.repository;

import enzosdev.bjjtrack.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    boolean existsByStudentIdAndAttendanceDate(Long studentId, LocalDate attendanceDate);
    
}
