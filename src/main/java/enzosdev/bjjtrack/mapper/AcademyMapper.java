package enzosdev.bjjtrack.mapper;

import enzosdev.bjjtrack.dto.AcademyRequest;
import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.entity.Academy;
import org.springframework.stereotype.Component;

@Component
public class AcademyMapper {


    public Academy toEntity(AcademyRequest dto){
        if (dto == null) {
            return null;
        }
        Academy academy = new Academy();
        academy.setName(dto.getName());
        academy.setLogoUrl(dto.getLogoUrl());
        academy.setSlug(dto.getSlug());
        return academy;
    }


    public AcademyResponse toResponse(Academy academy){
        if (academy == null) {
            return null;
        }
        AcademyResponse dto = new AcademyResponse();
        dto.setId(academy.getId());
        dto.setName(academy.getName());
        dto.setLogoUrl(academy.getLogoUrl());
        dto.setSlug(academy.getSlug());
        return dto;
    }

}
