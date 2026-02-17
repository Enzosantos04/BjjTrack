package enzosdev.bjjtrack.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AcademyRequest {

    @NotBlank(message = "Academy name is required")
    private String name;
    @NotBlank(message = "Academy slug is required")
    private String slug;
    private String logoUrl;

}
