package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showAllUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "usersRead";
    }

    @GetMapping("/new")
    public String addNewUser(Model model) {
        model.addAttribute("newUser", new User());
        return "userAdd";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "userAdd";
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("newUser", userService.getUserById(id));
        return "userAdd";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
} 
