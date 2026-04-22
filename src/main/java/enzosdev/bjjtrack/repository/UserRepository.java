package enzosdev.bjjtrack.repository;


import enzosdev.bjjtrack.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAcademyIdAndEmailIgnoreCase(Long id, String email);
    boolean existsByEmail(String email);
   Page<User> findByAcademyId(Long id, Pageable pageable);
    Optional<User> findByEmailIgnoreCaseAndAcademyId(String email, Long academyId);

}
