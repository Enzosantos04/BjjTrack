package enzosdev.bjjtrack.service;

import enzosdev.bjjtrack.dto.AcademyRequest;
import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.dto.AcademyUpdateRequest;
import enzosdev.bjjtrack.dto.UserResponse;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.exceptions.AcademyNotFoundException;
import enzosdev.bjjtrack.exceptions.EmptyFieldException;
import enzosdev.bjjtrack.mapper.AcademyMapper;
import enzosdev.bjjtrack.mapper.UserMapper;
import enzosdev.bjjtrack.repository.AcademyRepository;
import enzosdev.bjjtrack.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

@Service
public class AcademyService {




    private final AcademyRepository academyRepository;
    private final UserRepository userRepository;
    private final AcademyMapper academyMapper;
    private final UserMapper userMapper;

    public AcademyService(AcademyRepository academyRepository, UserRepository userRepository, AcademyMapper academyMapper, UserMapper userMapper) {
        this.academyRepository = academyRepository;
        this.userRepository = userRepository;
        this.academyMapper = academyMapper;
        this.userMapper = userMapper;
    }

    public Page<AcademyResponse> findAllAcademies(Pageable pageable){
       return academyRepository.findAll(pageable)
               .map(academyMapper::toResponse);
    }

    public void deleteAcademyById(Long id){
        if (!academyRepository.existsById(id)){
          throw new AcademyNotFoundException("Academy doesnt exists");
        }
        academyRepository.deleteById(id);
    }

    public AcademyResponse updateAcademyById(Long id, AcademyUpdateRequest academyUpdateRequest){
        Academy academy = academyRepository.findById(id)
                .orElseThrow(()-> new AcademyNotFoundException("Academy not foud."));

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

    public Page<UserResponse> listUsersByAcademyId(Long id, Pageable pageable){
        if(!academyRepository.existsById(id)){
            throw  new AcademyNotFoundException("Academy not found.");
        }

        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);

    }

    public AcademyResponse findAcademyById(Long id){

        Optional<Academy> academyResponse = academyRepository.findById(id);
              return academyResponse.map(academyMapper::toResponse)
                .orElseThrow(()-> new AcademyNotFoundException("Academy Not found"));

    }

    public AcademyResponse findAcademyBySlug(String slug){
        Optional<Academy> academy  = academyRepository.findAcademyBySlugIgnoreCase(slug);
        return academy.map(academyMapper::toResponse)
                .orElseThrow(()-> new AcademyNotFoundException("Academy Not found"));

    }

}
