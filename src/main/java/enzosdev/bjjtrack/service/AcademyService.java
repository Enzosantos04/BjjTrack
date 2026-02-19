package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.AcademyRequest;
import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.dto.AcademyUpdateRequest;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.exceptions.EmptyFieldException;
import enzosdev.bjjtrack.mapper.AcademyMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

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

    public void deleteAcademyById(Long id){
        if (!academyRepository.existsById(id)){
          throw new RuntimeException("Academy doesnt exists");
        }
        academyRepository.deleteById(id);
    }

    public AcademyResponse updateAcademyById(Long id, AcademyUpdateRequest academyUpdateRequest){
        Academy academy = academyRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Academy doesnt exists"));

        if(academyUpdateRequest.getName() != null){
            if(academyUpdateRequest.getName().isBlank()){
                throw new EmptyFieldException("Academy name empty is not allowed!");
            }
            academy.setName(academyUpdateRequest.getName());
        }
        if(academyUpdateRequest.getSlug() !=  null){
            if(academyUpdateRequest.getSlug().isBlank()){
                throw new EmptyFieldException("Academy Slug empty is not allowed!");
            }
            academy.setSlug(academyUpdateRequest.getSlug());
        }


        if(academyUpdateRequest.getLogoUrl() != null){
            academy.setLogoUrl(academyUpdateRequest.getLogoUrl());

        }
        Academy updatedAcademy = academyRepository.save(academy);
        return academyMapper.toResponse(updatedAcademy);

    }
}
