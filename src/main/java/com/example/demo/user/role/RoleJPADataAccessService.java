package com.example.demo.user.role;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.stream.Collectors;

@Repository("role_jpa")
public class RoleJPADataAccessService implements RoleDAO {

    private final RoleRepository roleRepository;

    public RoleJPADataAccessService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Map<String, Role> findALlRoles() {
       return roleRepository.findAll()
                .stream()
                .collect(Collectors.toMap(role->role.getName().name().toLowerCase(),role->role));
    }
}
