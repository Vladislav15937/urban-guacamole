package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositoryes.RoleRepositry;
import ru.kata.spring.boot_security.demo.repositoryes.UserRepository;
import ru.kata.spring.boot_security.demo.service.AdminServces;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminControllers {

    private final AdminServces adminServces;
    private final UserService userService;
    private final RoleRepositry roleRepositry;
    private final UserRepository userRepository;

    @Autowired
    public AdminControllers(AdminServces adminServces, UserService userService, RoleRepositry roleRepositry, UserRepository userRepository) {
        this.adminServces = adminServces;
        this.userService = userService;
        this.roleRepositry = roleRepositry;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String allUsers(Model model) {
        model.addAttribute("person", userRepository.findAll());
        return "users";
    }

    @GetMapping("/add")
    public String addUser(@ModelAttribute("person") @Validated User user, Model model) {

        List<Role> roles = roleRepositry.findAll();
        model.addAttribute("roles", roles);
        return "addUser";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("person") User user) {

        adminServces.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/view")
    public String show(@RequestParam("id") Long id, Model model) {
        User user = adminServces.getUserById(id).get();
        model.addAttribute("views", user);
        return "viewForAdmin";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        adminServces.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String updateUser(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", adminServces.getUserById(id).get());
        List<Role> roles = roleRepositry.findAll();
        model.addAttribute("roles", roles);
        return "updateUser";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") User user) {
        adminServces.updateUserById(user);
        return "redirect:/admin";
    }
}
