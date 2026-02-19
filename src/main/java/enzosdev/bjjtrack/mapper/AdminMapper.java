package enzosdev.bjjtrack.mapper;

import enzosdev.bjjtrack.dto.AdminRequest;
import enzosdev.bjjtrack.dto.AdminResponse;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {



    public User toAdminEntity(AdminRequest dto, Academy academy, String hashedPassword){
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(hashedPassword);
        user.setAcademy(academy);
        user.setActive(true);
        return user;
    }


    public AdminResponse toResponse(User user){
        if(user == null) {
            return null;
        }
        AdminResponse dto = new AdminResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;

    }
}
