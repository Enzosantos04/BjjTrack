package enzosdev.bjjtrack.dto;


import enzosdev.bjjtrack.entity.Academy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private Academy academy;
    private Long academyId;
}
