package myproject.traineeship_management_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/student/dashboard")
    public String getStudentDashboard() {
        return "student/dashboard";
    }
        
    @GetMapping("/company/dashboard")
    public String getCompanyDashboard() {
    	return "company/dashboard";
    }
    
    @GetMapping("/professor/dashboard")
    public String getProfessorDashboard() {
        return "professor/dashboard";
    }
    
    @GetMapping("/committee/dashboard")
    public String getCommitteeDashboard() {
    	return "committee/dashboard";
    }
    
}
