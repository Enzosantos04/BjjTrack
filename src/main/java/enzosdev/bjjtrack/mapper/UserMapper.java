package enzosdev.bjjtrack.mapper;

import enzosdev.bjjtrack.dto.AdminResponse;
import enzosdev.bjjtrack.dto.UserRequest;
import enzosdev.bjjtrack.dto.UserResponse;
import enzosdev.bjjtrack.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public User toUserEntity(UserRequest dto, String hashedPassword){
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(hashedPassword);
        if(user.getAcademy() != null){
            dto.setAcademyId(user.getAcademy().getId());

        }
        dto.setAcademy(user.getAcademy());
        user.setActive(true);
        return user;
    }

    public UserResponse toResponse(User user){
        if(user == null) {
            return null;
        }
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAcademyId(user.getAcademy().getId());

        return dto;

    }
}
