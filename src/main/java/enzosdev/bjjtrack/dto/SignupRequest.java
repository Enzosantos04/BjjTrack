package enzosdev.bjjtrack.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest{

    @Valid
    private AcademyRequest academy;
    @Valid
    private AdminRequest admin;
}
