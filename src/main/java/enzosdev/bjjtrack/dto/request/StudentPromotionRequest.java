package enzosdev.bjjtrack.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import enzosdev.bjjtrack.enums.Belt;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentPromotionRequest {

    @NotNull(message = "Last promotion date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate lastPromotion;
    @Enumerated(EnumType.STRING)
    private Belt belt;
}
