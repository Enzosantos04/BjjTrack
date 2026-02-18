package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.mapper.AcademyMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AcademyService {




    private final AcademyRepository academyRepository;
    private final AcademyMapper academyMapper;


    public AcademyService(AcademyRepository academyRepository, AcademyMapper academyMapper) {
        this.academyRepository = academyRepository;
        this.academyMapper = academyMapper;
    }

    public Page<AcademyResponse> findAllAcademies(Pageable pageable){
       return academyRepository.findAll(pageable)
               .map(academyMapper::toResponse);
    }
}
