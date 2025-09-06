package myproject.traineeship_management_app;


import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.mappers.UserMapper;
import myproject.traineeship_management_app.services.UserServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock  private BCryptPasswordEncoder encoder;
    @Mock  private UserMapper userRepo;
    @InjectMocks private UserServiceImpl service;


    @Test
    void saveUser_encodesPasswordAndSaves() {
        User u = new User(); u.setPassword("plain");

        when(encoder.encode("plain")).thenReturn("ENCODED");

        service.saveUser(u);

        assertThat(u.getPassword()).isEqualTo("ENCODED");
        verify(userRepo).save(u);
    }

    @Test
    void isUserPresent_returnsTrueWhenFound() {
        User u = new User(); u.setUsername("alice");
        when(userRepo.findByUsername("alice")).thenReturn(Optional.of(u));

        assertThat(service.isUserPresent(u)).isTrue();
    }

    @Test
    void loadUserByUsername_returnsUser() {
        User u = new User(); u.setUsername("bob");
        when(userRepo.findByUsername("bob")).thenReturn(Optional.of(u));

        assertThat(service.loadUserByUsername("bob")).isSameAs(u);
    }

    @Test
    void loadUserByUsername_throwsWhenMissing() {
        when(userRepo.findByUsername("nope")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                     () -> service.loadUserByUsername("nope"));
    }


    @Test
    void findByUsername_wrapsMissingInException() {
        when(userRepo.findByUsername("x")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                     () -> service.findByUsername("x"));
    }
}
