package enzosdev.bjjtrack.dto;


import enzosdev.bjjtrack.entity.Academy;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    private String name;
    private String email;
    private Academy academy;
    private Long academyId;
}
