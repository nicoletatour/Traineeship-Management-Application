package myproject.traineeship_management_app.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import myproject.traineeship_management_app.domainmodel.Committee;
import myproject.traineeship_management_app.domainmodel.User;
import java.util.Optional;

public interface CommitteeMapper extends JpaRepository<Committee, Long> {
    Optional<Committee> findByUser(User user);
}