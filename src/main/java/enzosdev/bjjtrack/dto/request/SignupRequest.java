package enzosdev.bjjtrack.dto.request;

import jakarta.validation.Valid;
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
