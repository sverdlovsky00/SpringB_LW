package ru.arhipov.url_db_rest_max.controller;
import ru.arhipov.url_db_rest_max.entity.User;
import ru.arhipov.url_db_rest_max.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    public AdminController(UserService userService){
        this.userService=userService;}

    @GetMapping("/users")
    public String listUsers(Model model){
        List<User>users=userService.findAll();
        model.addAttribute("users",users);
        return "admin/users";}

    @PostMapping("/users/{userId}/roles")
    public String addRoleToUser(@PathVariable Long userId,@RequestParam String role){
        userService.addRoleToUser(userId,role);
        return "redirect:/admin/users";}

    @PostMapping("/users/{userId}/roles/remove")
    public String removeRoleFromUser(@PathVariable Long userId,
                                     @RequestParam String role){
        userService.removeRoleFromUser(userId,role);
        return "redirect:/admin/users";}
}
