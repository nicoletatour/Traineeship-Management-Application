package myproject.traineeship_management_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import myproject.traineeship_management_app.domainmodel.LogbookEntry;
import myproject.traineeship_management_app.domainmodel.Student;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.services.LogbookService;
import myproject.traineeship_management_app.services.StudentService;
import myproject.traineeship_management_app.services.UserService;

@Controller
@RequestMapping("/student/logbook")
public class LogbookController {

    @Autowired private LogbookService logbookService;
    @Autowired private UserService userService;
    @Autowired private StudentService studentService;

    @GetMapping
    public String listEntries(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Student student = studentService.getByUser(user);
        List<LogbookEntry> entries = logbookService.getByStudent(student.getId());
        model.addAttribute("entries", entries);
        return "student/logbook";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("entry", new LogbookEntry());
        return "student/logbook_form";
    }

    @PostMapping
    public String saveEntry(@ModelAttribute LogbookEntry entry, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        Student student = studentService.getByUser(user);
        entry.setStudent(student);
        entry.setDate(LocalDate.now());
        logbookService.save(entry);
        return "redirect:/student/logbook";
    }
}