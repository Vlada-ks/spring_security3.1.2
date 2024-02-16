package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserService implements UserDetailsService {
   private final UserRepository userRepository;


    @PersistenceContext
    private EntityManager entityManager;

    public UserService(@Lazy UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    public User findByUsername(String username) {
       return userRepository.findByUsername(username);

    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = findByUsername(username);
       if (user == null){
           throw new UsernameNotFoundException(String.format("User '%s' not found", username ));
       }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getRoles());
    }
//     private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
//       return roles.stream().map(r-> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
////    mapRolesToAuthorities
//



    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }


    public void saveUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }


    @Transactional(readOnly = true)
    public User getUserById(Integer id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("User with this id not found");
        }
        return user;
    }

    @Transactional
    public void updateUser(Integer id, User user) {
        User userid = entityManager.find(User.class, user.getId());
        if (userid == null) {
            throw new EntityNotFoundException("User with this id not found");
        }
        entityManager.merge(user);
        entityManager.flush();
    }


    public void deleteUser(Integer id) {
        User user = this.getUserById(id);
        if (user == null) {
            throw new EntityNotFoundException("User with this id not found");
        }
        entityManager.remove(user);
    }
}

