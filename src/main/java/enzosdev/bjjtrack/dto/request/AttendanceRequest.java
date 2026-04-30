package enzosdev.bjjtrack.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceRequest {

    @NotNull(message = "Student id is required")
    private Long studentId;

    @PastOrPresent(message = "Attendance date cannot be in the future")
    @NotNull(message = "Attendance date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate attendanceDate;
}
