package myproject.traineeship_management_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import myproject.traineeship_management_app.domainmodel.*;
import myproject.traineeship_management_app.search.PositionSearchFactory;
import myproject.traineeship_management_app.search.ProfessorSearchCriteria;
import myproject.traineeship_management_app.search.ProfessorSearchFactory;
import myproject.traineeship_management_app.search.SearchCriteria;
import myproject.traineeship_management_app.services.*;

@Controller
@RequestMapping("/committee")
public class CommitteeController {

    @Autowired private CommitteeService committeeService;
    @Autowired private UserService userService;
    @Autowired private TraineeshipPositionService positionService;
    @Autowired private StudentService studentService;
    @Autowired private ProfessorService professorService;
    @Autowired private PositionSearchFactory searchFactory;
    @Autowired private ProfessorSearchFactory professorSearchFactory;

    @GetMapping("/profile")
    public String showProfileForm(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Committee existing = committeeService.getByUser(user);
        model.addAttribute("committee", existing != null ? existing : new Committee());
        return "committee/profile";
    }

    @PostMapping("/profile")
    public String saveProfile(@ModelAttribute Committee committee, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        committee.setUser(user);
        committeeService.saveCommittee(committee);
        return "redirect:/committee/dashboard";
    }

    @GetMapping("/applications")
    public String listApplications(Model model) {
        List<Student> students = studentService.getAllLooking();
        model.addAttribute("students", students);
        return "committee/applications";
    }

    @PostMapping("/applications/{studentId}/search")
    public String doSearch(
        @PathVariable Long studentId,
        @RequestParam("criteria") SearchCriteria criteria,
        Model model
    ) {
        Student student = studentService.getById(studentId);
        if (student == null) {
            return "redirect:/committee/applications";
        }
        List<TraineeshipPosition> matches =
            searchFactory.getStrategy(criteria).search(student);

        model.addAttribute("student", student);
        model.addAttribute("positions", matches);
        model.addAttribute("criteria", criteria);
        return "committee/match_positions";
    }

    @PostMapping("/applications/{studentId}/assign/{positionId}")
    public String assignPositionToStudent(
        @PathVariable Long studentId,
        @PathVariable Long positionId,
        RedirectAttributes attrs
    ) {
        Student student = studentService.getById(studentId);
        if (student == null) {
            attrs.addFlashAttribute("error", "Student not found");
            return "redirect:/committee/applications";
        }
        positionService.assignToStudent(positionId, student.getUser().getUsername());
        attrs.addFlashAttribute("message",
            "Assigned position #" + positionId + " to " + student.getFullName());
        return "redirect:/committee/applications";
    }

    @GetMapping("/assignments/assign-professor/{posId}")
    public String showProfessorForm(@PathVariable Long posId, @RequestParam(value="criteria", required=false) ProfessorSearchCriteria criteria, Model model) {
    	
      TraineeshipPosition pos = positionService.getById(posId);
      List<Professor> profs = (criteria != null)
        ? professorSearchFactory.getStrategy(criteria).search(pos)
        : professorService.getAll();

      Map<Long,Long> loadMap = profs.stream().collect(Collectors.toMap(
        Professor::getId,
        p -> positionService.countSupervisions(p)
      ));

      Set<Long> interestMatches = professorSearchFactory
        .getStrategy(ProfessorSearchCriteria.INTERESTS)
        .search(pos)
        .stream()
        .map(Professor::getId)
        .collect(Collectors.toSet());

      model.addAttribute("position", pos);
      model.addAttribute("professors", profs);
      model.addAttribute("loadMap", loadMap);
      model.addAttribute("matchSet", interestMatches);
      model.addAttribute("criteriaList", ProfessorSearchCriteria.values());
      model.addAttribute("selectedCriteria", criteria);

      return "committee/assign_form";
    }

    @GetMapping("/assignments/assign-professor")
    public String listForAssignment(Model model) {
      List<TraineeshipPosition> open = positionService.getUnsupervisedInProgress();
      model.addAttribute("positions", open);
      return "committee/assign_list";
    }

    @PostMapping("/assignments/assign-professor/{posId}")
    public String assignProfessor(
        @PathVariable Long posId,
        @RequestParam Long professorId,
        RedirectAttributes attrs
    ) {
      positionService.assignSupervisor(posId, professorId);
      attrs.addFlashAttribute("msg", "Supervisor assigned successfully!");
      return "redirect:/committee/assignments/assign-professor";
    }

    @GetMapping("/traineeships/in-progress")
    public String listInProgress(Model model) {
        List<TraineeshipPosition> inProgress = positionService.getInProgressPositions();
        model.addAttribute("positions", inProgress);
        return "committee/in_progress_positions";
    }
    
    
    @GetMapping("/traineeships/{id}/review")
    public String review(
        @PathVariable Long id,
        Model model
    ) {
        TraineeshipPosition p = committeeService.getById(id);

        if (p.getCommitteeResult() != CommitteeResult.PENDING) {
            return "redirect:/committee/traineeships/in-progress";
        }

        model.addAttribute("position", p);
        model.addAttribute("autoPass", p.getAverageRating() >= 2.5);
        return "committee/review_position";
    }
    
    @PostMapping("/traineeships/{id}/complete")
    public String complete(
        @PathVariable Long id,
        @RequestParam CommitteeResult decision,
        @RequestParam(name="notes", required=false) String notes,
        RedirectAttributes ra
    ) {
        committeeService.complete(id, decision, notes == null ? "" : notes);
        ra.addFlashAttribute("msg",
            "Traineeship marked " + decision);
        return "redirect:/committee/traineeships/in-progress";
    }
    
    @GetMapping("/traineeships/completed")
    public String listCompletedTraineeships(Model model) {
        List<TraineeshipPosition> completed = positionService.getCompletedPositions();
        model.addAttribute("positions", completed);
        return "committee/completed_positions";
    }
    
    
}
