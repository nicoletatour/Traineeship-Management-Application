// src/main/java/myproject/traineeship_management_app/controller/CompanyPositionController.java
package myproject.traineeship_management_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import myproject.traineeship_management_app.domainmodel.Company;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.services.CompanyService;
import myproject.traineeship_management_app.services.TraineeshipPositionService;
import myproject.traineeship_management_app.services.UserService;

@Controller
@RequestMapping("/company/positions")
public class CompanyPositionController {

    @Autowired private UserService userService;
    @Autowired private CompanyService companyService;
    @Autowired private TraineeshipPositionService positionService;


    @GetMapping           
    public String listAvailablePositions(Model model, Principal principal) {

        User    user    = userService.findByUsername(principal.getName());
        Company company = companyService.getByUser(user);

        List<TraineeshipPosition> positions = positionService.getAvailableByCompany(company);

        model.addAttribute("positions", positions);
        return "company/positions";    
    }

    @GetMapping("/assigned")
    public String listAssigned(Model model, Principal principal) {
        Company c = companyService.getByUser(
                        userService.findByUsername(principal.getName()));
        model.addAttribute("positions", positionService.getAssignedByCompany(c));
        return "company/assigned_positions";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("position", new TraineeshipPosition());
        return "company/create_position";
    }

    @PostMapping("/create")
    public String createPosition(
            @ModelAttribute("position") TraineeshipPosition position,
            Principal principal) {
        User user    = userService.findByUsername(principal.getName());
        Company comp = companyService.getByUser(user);
        position.setCompany(comp);

        positionService.save(position);
        return "redirect:/company/positions";
    }
    

    @GetMapping("/delete/{posId}")
    public String deletePosition(@PathVariable Long posId,
                                 Principal principal,
                                 RedirectAttributes attrs) {
        User user = userService.findByUsername(principal.getName());
        Company company = companyService.getByUser(user);
        TraineeshipPosition pos = positionService.getById(posId);
        if (pos == null || !pos.getCompany().equals(company)) {
            attrs.addFlashAttribute("error", "Cannot delete position");
        } else {
            positionService.deleteById(posId);
            attrs.addFlashAttribute("message", "Position deleted successfully");
        }
        return "redirect:/company/positions";
    }
    
    @GetMapping("/{posId}/evaluate")
    public String showEvaluationForm(@PathVariable Long posId,
                                     Model model,
                                     Principal principal,
                                     RedirectAttributes attrs) {
      User user = userService.findByUsername(principal.getName());
      Company company = companyService.getByUser(user);
      TraineeshipPosition pos = positionService.getById(posId);

      if (pos == null || !pos.getCompany().equals(company) || pos.getAssignedStudent() == null) {
        attrs.addFlashAttribute("error", "Cannot evaluate this position");
        return "redirect:/company/positions/assigned";
      }

      model.addAttribute("position", pos);
      return "company/evaluate_position";
    }

    @PostMapping("/{posId}/evaluate")
    public String submitEvaluation(
        @PathVariable Long posId,
        @RequestParam int companyMotivationRating,
        @RequestParam int companyEffectivenessRating,
        @RequestParam int companyEfficiencyRating,
        Principal principal,
        RedirectAttributes attrs) {

      User user = userService.findByUsername(principal.getName());
      Company company = companyService.getByUser(user);
      TraineeshipPosition pos = positionService.getById(posId);

      if (pos != null && pos.getCompany().equals(company) && pos.getAssignedStudent() != null) {

        pos.setCompanyMotivationRating(companyMotivationRating);
        pos.setCompanyEffectivenessRating(companyEffectivenessRating);
        pos.setCompanyEfficiencyRating(companyEfficiencyRating);

        positionService.save(pos);
        attrs.addFlashAttribute("message", "Company evaluation saved successfully");
      } else {
        attrs.addFlashAttribute("error", "Cannot evaluate this position");
      }
      return "redirect:/company/positions/assigned";
    }
    
}
