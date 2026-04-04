package enzosdev.bjjtrack.dto.response;

import enzosdev.bjjtrack.enums.Belt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentAdminUpdateResponse {

    private Belt belt;
    private Integer stripe;
}
