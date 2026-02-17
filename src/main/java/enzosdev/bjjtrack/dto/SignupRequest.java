package enzosdev.bjjtrack.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest{

    @NotNull(message = "Academy information is required")
    @Valid
    private AcademyRequest academy;
    @NotNull(message = "Admin information is required")
    @Valid
    private AdminRequest admin;
}
