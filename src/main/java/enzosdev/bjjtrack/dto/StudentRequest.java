package enzosdev.bjjtrack.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import enzosdev.bjjtrack.enums.Belt;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Academy id is required")
    private Long academyId;

    @NotNull(message = "Birth date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    private Belt belt;

    private

}
