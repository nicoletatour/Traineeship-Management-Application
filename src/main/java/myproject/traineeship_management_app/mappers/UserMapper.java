package myproject.traineeship_management_app.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myproject.traineeship_management_app.domainmodel.User;


public interface UserMapper extends JpaRepository<User, String> {
	Optional<User> findByUsername(String username);
}
