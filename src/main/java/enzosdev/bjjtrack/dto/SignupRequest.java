package enzosdev.bjjtrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest{

    private AcademyCreateDTO academy;
    private AdminCreateDTO admin;
}
