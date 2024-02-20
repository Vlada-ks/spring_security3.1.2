package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.repository.RoleRepository;


@Service
public class RoleService {
    private final RoleRepository roleDao;

    public RoleService(RoleRepository roleDao) {
        this.roleDao = roleDao;
    }


}
