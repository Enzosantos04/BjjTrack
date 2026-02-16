package enzosdev.bjjtrack.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Academy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "logo_url")
    private String logoUrl;
    private String slug;
    @Column(nullable = false)
    private boolean active;
}
