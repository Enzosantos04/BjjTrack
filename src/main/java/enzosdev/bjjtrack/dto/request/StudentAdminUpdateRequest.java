package enzosdev.bjjtrack.dto.request;

import enzosdev.bjjtrack.enums.Belt;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentAdminUpdateRequest {
    @NotNull(message = "Belt is required")
    private Belt belt;
    private Integer stripe;




}
