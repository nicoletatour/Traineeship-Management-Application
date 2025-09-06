package myproject.traineeship_management_app.controller;


import myproject.traineeship_management_app.domainmodel.Student;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.services.StudentService;
import myproject.traineeship_management_app.services.TraineeshipPositionService;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import myproject.traineeship_management_app.services.UserService;


@Controller
@RequestMapping("/student")
public class StudentProfileController {

  private final StudentService studentService;
  private final UserService userService;
  private final TraineeshipPositionService positionService;

  @Autowired
  public StudentProfileController(StudentService studentService,
                                   UserService userService,
                                   TraineeshipPositionService positionService) {
    this.studentService  = studentService;
    this.userService     = userService;
    this.positionService = positionService;
  }

  @GetMapping("/profile")
  public String showForm(Model model, Principal principal) {
    User user = userService.findByUsername(principal.getName());
    Student existing = studentService.getByUser(user);
    model.addAttribute("student", existing != null ? existing : new Student());
    return "student/profile";
  }

  @PostMapping("/profile")
  public String saveProfile(@ModelAttribute Student formStudent,
                            Principal principal) {
      User user = userService.findByUsername(principal.getName());
      Student existing = studentService.getByUser(user);

      if (existing == null) {
        formStudent.setUser(user);
        studentService.saveStudent(formStudent);
      } else {
        existing.setFullName(formStudent.getFullName());
        existing.setInterests(formStudent.getInterests());
        existing.setPreferredLocation(formStudent.getPreferredLocation());
        existing.setSkills(formStudent.getSkills());
        studentService.saveStudent(existing);
      }
      return "redirect:/student/dashboard";
  }

  @GetMapping("/positions")
  public String listOpenPositions(Model model) {
    List<TraineeshipPosition> positions = positionService.getAllOpenPositions();
    model.addAttribute("positions", positions);
    return "student/positions";
  }

  @PostMapping("/apply")
  	public String applyForTraineeship(Principal principal,
	          RedirectAttributes attrs) {
	User user = userService.findByUsername(principal.getName());
	Student student = studentService.getByUser(user);
	studentService.setLooking(student.getId(), true);
	attrs.addFlashAttribute("message", "Application completed!");
	
	return "redirect:/student/dashboard";
	}
}