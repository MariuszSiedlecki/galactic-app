package com.galacticapp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class RegisterController {

    private CustomUserService customUserService;


    public RegisterController(CustomUserService customUserService) {
        this.customUserService = customUserService;
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute LoginUser loginUser) {
//       Optional<UserApp> userApp = customUserService.findUser(loginUser.getUsername());
            customUserService.saveUserApp(loginUser);
            return "redirect:/login";
        }


    @GetMapping("/register")
    public String loginPage() {
        return "register";
    }
}
