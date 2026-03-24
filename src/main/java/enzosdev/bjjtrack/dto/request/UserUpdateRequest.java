package enzosdev.bjjtrack.dto.request;


import enzosdev.bjjtrack.entity.Academy;
import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String name;
    private Academy academy;
    private Long academyId;
}
