package app.service.controllers.admin.users.roles;

import app.domain.entites.users.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<Role> findAll();

    Optional<Role> findById(Integer id);

    Role findByRole(String role);

    boolean existsByRole(String role);

    void save(Role role);

    boolean existsById(Integer id);

    void deleteById(Integer id);
}
