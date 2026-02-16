package enzosdev.bjjtrack.mapper;

import enzosdev.bjjtrack.dto.AcademyResponse;
import enzosdev.bjjtrack.dto.AdminResponse;
import enzosdev.bjjtrack.dto.SignupResponse;
import org.springframework.stereotype.Component;

@Component
public class SignupMapper {
    public SignupResponse  toResponse(AcademyResponse academy, AdminResponse admin){
       SignupResponse response = new SignupResponse();
         response.setAcademy(academy);
         response.setAdmin(admin);
        return response;
    }
}
