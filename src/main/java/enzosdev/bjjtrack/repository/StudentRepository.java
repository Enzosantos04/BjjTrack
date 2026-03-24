package enzosdev.bjjtrack.repository;

import enzosdev.bjjtrack.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsStudentByUserId(Long userId);
    Page<Student> findStudentsByAcademyId(Long academyId, Pageable pageable);
    Page<Student> findAllStudentsByUserActiveTrue(Pageable pageable);
    Optional<Student> findStudentByUserEmail(String string);
}
