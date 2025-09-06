package myproject.traineeship_management_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

import myproject.traineeship_management_app.domainmodel.Company;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.services.CompanyService;
import myproject.traineeship_management_app.services.UserService;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;
    private final UserService userService;

    @Autowired
    public CompanyController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showForm(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Company existing = companyService.getByUser(user);
        if (existing == null) model.addAttribute("company", new Company());
        else model.addAttribute("company", existing);
        return "company/profile";
    }

    @PostMapping("/profile")
    public String saveProfile(@ModelAttribute Company company, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Company existing = companyService.getByUser(user);
        if (existing != null) {
            company.setId(existing.getId());
        }
        company.setUser(user);
        companyService.saveCompany(company);
        return "redirect:/company/dashboard";
    }
}
