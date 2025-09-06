package myproject.traineeship_management_app.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import myproject.traineeship_management_app.domainmodel.Company;
import myproject.traineeship_management_app.domainmodel.User;
import java.util.Optional;

public interface  CompanyMapper  extends JpaRepository<Company, Long> {
    Optional<Company> findByUser(User user);
}
