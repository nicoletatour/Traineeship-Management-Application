package myproject.traineeship_management_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import myproject.traineeship_management_app.domainmodel.Professor;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.mappers.ProfessorMapper;

@Service
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorMapper repo;
    

    @Autowired
    public ProfessorServiceImpl(ProfessorMapper repo) {
        this.repo = repo;
    }

    @Override
    public Professor saveProfessor(Professor prof) {
        return repo.save(prof);
    }

    @Override
    public Professor getByUser(User user) {
        return repo.findByUser(user).orElse(null);
    }
    
    @Override
    public List<Professor> getAllProfessors() {
        return repo.findAll();
    }

    @Override
    public Professor getById(Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("No professor with id "+id));
    }
    

	@Override
	public List<Professor> getAll() {
	    return repo.findAll();
	}
	    
    
}
