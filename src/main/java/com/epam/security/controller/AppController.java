package com.epam.security.controller;

import com.epam.security.service.LoginAttemptService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Controller
public class AppController {

    LoginAttemptService loginAttemptService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok("some info");
    }

    @GetMapping("/about")
    public ResponseEntity<String> about() {
        return ResponseEntity.ok("it's non-authenticated access");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("admin data");
    }

    @GetMapping("/login")
    public String login(ModelMap modelMap, @RequestParam(required = false) Optional<String> error) {
        error.ifPresent((e) -> modelMap.addAttribute("error", e));
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }

    @GetMapping("/logout-success")
    public ResponseEntity<String> logoutSuccess() {
        return ResponseEntity.ok("logout-success");
    }

    @GetMapping("/users/blocked")
    @ResponseBody
    public Set<String> getBlockedUsers() {
        return loginAttemptService.getBlockedUsers();
    }
}
