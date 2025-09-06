package myproject.traineeship_management_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

import myproject.traineeship_management_app.domainmodel.Professor;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.services.ProfessorService;
import myproject.traineeship_management_app.services.TraineeshipPositionService;
import myproject.traineeship_management_app.services.UserService;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired private ProfessorService professorService;
    @Autowired private UserService userService;
    @Autowired private TraineeshipPositionService positionService; 

    /** US13: show/create/edit professor profile **/
    @GetMapping("/profile")
    public String showForm(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Professor existing = professorService.getByUser(user);
        model.addAttribute("professor", existing != null ? existing : new Professor());
        return "professor/profile";
    }

    @PostMapping("/profile")
    public String saveProfile(@ModelAttribute Professor professor, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Professor existing = professorService.getByUser(user);
        if (existing != null) {
            professor.setId(existing.getId());
        }
        professor.setUser(user);
        professorService.saveProfessor(professor);
        return "redirect:/professor/dashboard";
    }
    
    @GetMapping("/positions")
    public String listSupervisedPositions(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Professor prof = professorService.getByUser(user);
        List<TraineeshipPosition> positions = positionService.getSupervisedByProfessor(prof);
        model.addAttribute("positions", positions);
        return "professor/supervised_positions";
    }
    
    @GetMapping("/positions/{id}/evaluate")
    public String showEvaluation(@PathVariable Long id, Model model,
                                 @AuthenticationPrincipal UserDetails user) {

        TraineeshipPosition pos = positionService.getById(id);

        Professor me = professorService.getByUser(userService.findByUsername(user.getUsername()));
        if (!pos.getSupervisor().equals(me)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        model.addAttribute("position", pos);
        return "professor/evaluate_position";
    }

    @PostMapping("/positions/{id}/evaluate")
    public String submitEvaluation(@PathVariable Long id,
                                   @RequestParam int motivationRating,
                                   @RequestParam int effectivenessRating,
                                   @RequestParam int efficiencyRating,
                                   @RequestParam int facilitiesRating,
                                   @RequestParam int guidanceRating,
                                   @AuthenticationPrincipal UserDetails user,
                                   RedirectAttributes ra) {

        positionService.evaluateByProfessor(id,
            motivationRating, effectivenessRating, efficiencyRating,
            facilitiesRating, guidanceRating,
            user.getUsername());

        ra.addFlashAttribute("message", "Evaluation saved!");
        return "redirect:/professor/positions";
    }
    @GetMapping("/positions/pending")
    public String showPendingEvaluations(Model model, Principal principal) {

        String username = principal.getName();
        Professor me = professorService.getByUser(userService.findByUsername(username));

        List<TraineeshipPosition> pending =
            positionService.getPendingEvaluations(me);

        model.addAttribute("positions", pending);
        return "professor/pending_positions";  
    }
    

    
}
