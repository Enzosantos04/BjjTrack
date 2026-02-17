package enzosdev.bjjtrack.repository;

import enzosdev.bjjtrack.entity.Academy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademyRepository extends JpaRepository<Academy, Long> {
        boolean existsBySlug(String slug);
        boolean existsByName(String name);
}
