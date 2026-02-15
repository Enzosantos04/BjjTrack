package enzosdev.bjjtrack.dto;

import enzosdev.bjjtrack.entity.Academy;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SignupDTO {

    private String name;
    private String slug;
    private String logoUrl;
    private String username;
    private String email;
    private String password;
    private Academy academy;

}
