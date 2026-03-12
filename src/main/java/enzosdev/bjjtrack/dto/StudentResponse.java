package enzosdev.bjjtrack.dto;

import enzosdev.bjjtrack.enums.Belt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentResponse {
    private Long id;
    private Long userId;
    private Long academyId;
    private LocalDate birthDate;
    private Belt belt;
}
