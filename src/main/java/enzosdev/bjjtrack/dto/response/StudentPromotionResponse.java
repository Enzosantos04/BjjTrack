package enzosdev.bjjtrack.dto.response;


import enzosdev.bjjtrack.enums.Belt;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentPromotionResponse {
    private Long studentId;
    private Integer stripes;
    private Belt belt;
    private LocalDate lastPromotion;

}
