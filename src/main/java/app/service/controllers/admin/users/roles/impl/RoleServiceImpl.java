package app.service.controllers.admin.users.roles.impl;

import app.domain.entites.users.Role;
import app.repository.RoleRepository;
import app.service.controllers.admin.users.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }

    @Override
    public boolean existsByRole(String role) {
        return roleRepository.existsByRole(role);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public boolean existsById(Integer id) {
        return roleRepository.existsById(id);
    }

    @Override
    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }
}
