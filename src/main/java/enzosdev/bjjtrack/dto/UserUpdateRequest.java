package enzosdev.bjjtrack.dto;


import enzosdev.bjjtrack.entity.Academy;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String name;
    @Email(message = "This Email is in wrong format")
    private String email;
    private Academy academy;
    private Long academyId;
}
