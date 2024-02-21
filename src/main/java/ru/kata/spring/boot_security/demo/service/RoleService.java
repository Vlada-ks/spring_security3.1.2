package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.repository.RoleRepository;


@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


}
