package enzosdev.bjjtrack.mapper;

import enzosdev.bjjtrack.dto.AdminCreateDTO;
import enzosdev.bjjtrack.dto.AdminResponseDTO;
import enzosdev.bjjtrack.entity.Academy;
import enzosdev.bjjtrack.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {



    public User toAdminEntity(AdminCreateDTO dto, Academy academy, String hashedPassword){
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


    public AdminResponseDTO toResponse(User user){
        if(user == null) {
            return null;
        }
        AdminResponseDTO dto = new AdminResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;

    }
}
