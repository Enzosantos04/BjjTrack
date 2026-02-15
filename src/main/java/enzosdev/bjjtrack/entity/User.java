package enzosdev.bjjtrack.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean active;

    @JoinColumn(name = "academy_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Academy academy;
}
