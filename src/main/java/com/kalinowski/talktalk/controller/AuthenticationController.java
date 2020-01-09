package com.kalinowski.talktalk.controller;

import com.kalinowski.talktalk.model.User;
import com.kalinowski.talktalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }

    @PostMapping("/register")
    public String registerUser(Model model, @Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("successMessage", "Please correct the errors in form!");
            model.addAttribute("bindingResult", bindingResult);
        }
        else if(userService.isUserAlreadyPresent(user)){
            model.addAttribute("successMessage", "User already exists!");
        }
        else {
            userService.saveUser(user);
            model.addAttribute("successMessage", "You have been successfully registered!");
        }
        model.addAttribute("user", new User());
        return "register";
    }
}
