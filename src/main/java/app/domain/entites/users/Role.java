package app.domain.entites.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "users", name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String role;

    public Role(String role) {
        this.role = role;
    }
}
//    SYSTEM_ADMIN, MERCH_MANAGER, CONCERT_MANAGER, ALBUMS_MANAGER
