package enzosdev.bjjtrack.dto.request;


import enzosdev.bjjtrack.enums.ScopeName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "User's name is required")
    private String name;
    @Email(message = "This Email is in wrong format")
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    private Long academyId;

    private List<ScopeName> scopes = new ArrayList<>();

}
