package enzosdev.bjjtrack.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateEmailRequest {
    private Long id;

    @Email(message = "This Email is in wrong format")
    private String newEmail;
    @NotBlank(message = "password is required")
    private String password;
}
