package com.daromon.goodeventbackend.Role;

import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role addRole(String name) {
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

}
