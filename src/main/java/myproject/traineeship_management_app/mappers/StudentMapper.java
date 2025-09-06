package myproject.traineeship_management_app.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import myproject.traineeship_management_app.domainmodel.Student;
import myproject.traineeship_management_app.domainmodel.User;
import java.util.List;
import java.util.Optional;

public interface StudentMapper extends JpaRepository<Student, Long> {
    Optional<Student> findByUser(User user);
    Optional<Student> findByUniversityIdNumber(String universityIdNumber);
    List<Student> findAllByAppliedPositions_Id(Long positionId);
    List<Student> findAllByLookingTrue();
}
