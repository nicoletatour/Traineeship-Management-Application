package myproject.traineeship_management_app.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import myproject.traineeship_management_app.domainmodel.Professor;
import myproject.traineeship_management_app.domainmodel.User;

import java.util.List;
import java.util.Optional;

public interface ProfessorMapper extends JpaRepository<Professor, Long> {
    Optional<Professor> findByUser(User user);
    List<Professor> findAll();
}
