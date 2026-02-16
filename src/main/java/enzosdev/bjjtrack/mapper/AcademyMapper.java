package enzosdev.bjjtrack.mapper;

import enzosdev.bjjtrack.dto.AcademyCreateDTO;
import enzosdev.bjjtrack.dto.AcademyCreateResponseDTO;
import enzosdev.bjjtrack.entity.Academy;
import org.springframework.stereotype.Component;

@Component
public class AcademyMapper {


    public Academy toEntity(AcademyCreateDTO dto){
        if (dto == null) {
            return null;
        }
        Academy academy = new Academy();
        academy.setName(dto.getName());
        academy.setLogoUrl(dto.getLogoUrl());
        academy.setSlug(dto.getSlug());
        return academy;
    }


    public AcademyCreateResponseDTO toResponse(Academy academy){
        if (academy == null) {
            return null;
        }
        AcademyCreateResponseDTO dto = new AcademyCreateResponseDTO();
        dto.setId(academy.getId());
        dto.setName(academy.getName());
        dto.setLogoUrl(academy.getLogoUrl());
        dto.setSlug(academy.getSlug());
        return dto;
    }

}
