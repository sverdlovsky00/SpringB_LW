package ru.arhipov.url_db_rest_max.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActuatorController {
    @GetMapping("/actuator-info")
    @PreAuthorize("isAuthenticated()")
    public String actuatorInfo() {return "actuator-info";}
}
