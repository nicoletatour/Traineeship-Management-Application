package myproject.traineeship_management_app.services;

import java.util.List;

import myproject.traineeship_management_app.domainmodel.Professor;
import myproject.traineeship_management_app.domainmodel.User;

public interface ProfessorService {
    Professor saveProfessor(Professor prof);
    Professor getByUser(User user);
    List<Professor> getAllProfessors();
    Professor getById(Long id);
    List<Professor> getAll(); 

}
