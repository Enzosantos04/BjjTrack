package enzosdev.bjjtrack.dto.response;

import enzosdev.bjjtrack.enums.Belt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentResponse implements Serializable {
    private Long id;
    private String name;
    private String email;
    private Long academyId;
    private LocalDate birthDate;
    private Belt belt;
}
