package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Transactional(readOnly = true)

    public Set<Role> getAllRoles(List<String> roleNames) {

        return new HashSet<>(entityManager.createQuery("FROM Role role WHERE role.name in (:roleNames)")
                .setParameter("roleNames", roleNames)
                .getResultList());
    }


}
