package enzosdev.bjjtrack.mapper;

import enzosdev.bjjtrack.dto.AcademyCreateResponseDTO;
import enzosdev.bjjtrack.dto.AdminResponseDTO;
import enzosdev.bjjtrack.dto.SignupResponse;
import org.springframework.stereotype.Component;

@Component
public class SignupMapper {
    public SignupResponse  toResponse(AcademyCreateResponseDTO academy, AdminResponseDTO admin){
       SignupResponse response = new SignupResponse();
         response.setAcademy(academy);
         response.setAdmin(admin);
        return response;
    }
}
