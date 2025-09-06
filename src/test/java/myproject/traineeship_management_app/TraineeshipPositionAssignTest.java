package myproject.traineeship_management_app;


import myproject.traineeship_management_app.domainmodel.*;
import myproject.traineeship_management_app.mappers.ProfessorMapper;
import myproject.traineeship_management_app.mappers.TraineeshipPositionMapper;
import myproject.traineeship_management_app.services.TraineeshipPositionServiceImpl;
import myproject.traineeship_management_app.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import jakarta.persistence.EntityNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeshipPositionAssignTest {

    @Mock private TraineeshipPositionMapper repo;
    @Mock private UserService userService;
    @Mock private ProfessorMapper profRepo;
    @InjectMocks private TraineeshipPositionServiceImpl service;

    private TraineeshipPosition position;
    private User  studentUser;
    private Professor professor;

    @BeforeEach
    void init() {
        position = new TraineeshipPosition(); position.setId(20L);
        studentUser = new User(); studentUser.setUsername("stud1");
        professor = new Professor(); professor.setId(5L);
        User profUser = new User(); profUser.setUsername("prof1");
        professor.setUser(profUser);
    }

    /* ---------- assignToStudent ---------- */

    @Test
    void assignToStudent_setsStudentAndSaves() {
        when(repo.findById(20L)).thenReturn(Optional.of(position));
        when(userService.findByUsername("stud1")).thenReturn(studentUser);

        service.assignToStudent(20L, "stud1");

        assertThat(position.getAssignedStudent()).isSameAs(studentUser);
        verify(repo).save(position);
    }

    @Test
    void assignToStudent_throws_whenPositionMissing() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                     () -> service.assignToStudent(99L, "stud1"));
    }

    /* ---------- assignSupervisor ---------- */

    @Test
    void assignSupervisor_setsProfessorAndSaves() {
        when(repo.findById(20L)).thenReturn(Optional.of(position));
        when(profRepo.findById(5L)).thenReturn(Optional.of(professor));

        service.assignSupervisor(20L, 5L);

        assertThat(position.getSupervisor()).isSameAs(professor);
        verify(repo).save(position);
    }

    
}
