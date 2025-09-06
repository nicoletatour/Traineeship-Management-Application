package myproject.traineeship_management_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import myproject.traineeship_management_app.domainmodel.Role;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.services.UserService;


@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(){
        return "auth/login";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("roles", Role.values()); // pass enum values to the template
        return "auth/register";
    }


    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model){
    	System.out.println(">>> registerUser got: " + user);
        if(userService.isUserPresent(user)){
        	System.out.println(">>> already present: " + user.getUsername());
            model.addAttribute("successMessage", "User already registered!");
            return "auth/login";
        }

        userService.saveUser(user);
        System.out.println(">>> after saveUser");
        model.addAttribute("successMessage", "User registered successfully!");

        return "auth/login";
    }
}
