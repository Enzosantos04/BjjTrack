package enzosdev.bjjtrack.dto.request;


import enzosdev.bjjtrack.entity.Academy;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {

    @NotBlank
    @NotBlank(message = "name is required")
    private String name;
    private Academy academy;
    @NotNull(message = "academyId is required")
    private Long academyId;
}
