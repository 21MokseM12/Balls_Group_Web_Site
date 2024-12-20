package app.repository.users;

import app.domain.entites.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    boolean existsByRole(String role);

    Role findByRole(String role);
}
