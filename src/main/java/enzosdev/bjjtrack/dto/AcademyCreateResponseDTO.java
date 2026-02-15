package enzosdev.bjjtrack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AcademyCreateResponseDTO {
    private Long id;
    private String name;
    private String slug;
    private String logoUrl;


}
