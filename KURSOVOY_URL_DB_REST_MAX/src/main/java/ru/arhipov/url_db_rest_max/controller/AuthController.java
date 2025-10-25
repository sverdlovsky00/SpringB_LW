package ru.arhipov.url_db_rest_max.controller;
import ru.arhipov.url_db_rest_max.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {this.authService = authService;}

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль");
        }
        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы");
        }
        return "login";}

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";}

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        // Валидация
        if (username == null || username.trim().isEmpty()) {
            model.addAttribute("error", "Имя пользователя не может быть пустым");
            return "register";}
        if (password == null || password.length() < 3) {
            model.addAttribute("error", "Пароль должен содержать минимум 3 символа");
            return "register";}

        boolean success = authService.registerUser(username.trim(), password);
        if (success) {
            model.addAttribute("message", "Регистрация прошла успешно! Теперь вы можете войти.");
            return "login";
        } else {
            model.addAttribute("error", "Пользователь с таким именем уже существует!");
            model.addAttribute("username", username);
            return "register";}
    }
}