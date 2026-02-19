package enzosdev.bjjtrack.repository;
import enzosdev.bjjtrack.entity.Academy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AcademyRepository extends JpaRepository<Academy, Long> {
        boolean existsBySlug(String slug);
        boolean existsByName(String name);
        Optional<Academy> findAcademyBySlugIgnoreCase(String slug);
}
