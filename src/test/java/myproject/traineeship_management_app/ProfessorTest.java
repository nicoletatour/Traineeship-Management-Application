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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfessorTest {

    @Mock  private TraineeshipPositionMapper repo;
    @Mock  private UserService               userService;   
    @Mock  private ProfessorMapper           profRepo;
    @InjectMocks private TraineeshipPositionServiceImpl service;

    private TraineeshipPosition position;
    private Professor supervisor;

    @BeforeEach
    void setUp() {
        supervisor = new Professor();
        supervisor.setId(11L);
        User supUser = new User();
        supUser.setUsername("profX");
        supervisor.setUser(supUser);

        position = new TraineeshipPosition();
        position.setId(55L);
        position.setSupervisor(supervisor);

        when(repo.findById(55L)).thenReturn(Optional.of(position));
    }

    @Test
    void professorCanEvaluate_whenSupervisorMatches() {
        service.evaluateByProfessor(
                55L,
                /* motivation   */ 5,
                /* effectiveness*/ 1,
                /* efficiency   */ 2,
                /* facilities   */ 3,
                /* guidance     */ 4,
                /* username     */ "profX");

        System.out.println("Motivation rating  : " + position.getMotivationRating());
        System.out.println("Effectiveness rating: " + position.getEffectivenessRating());
        System.out.println("Efficiency rating  : " + position.getEfficiencyRating());
        System.out.println("Facilities rating  : " + position.getFacilitiesRating());
        System.out.println("Guidance rating    : " + position.getGuidanceRating());

        assertThat(position.getMotivationRating()).isEqualTo(5);
        assertThat(position.getEffectivenessRating()).isEqualTo(1);
        assertThat(position.getEfficiencyRating()).isEqualTo(2);
        assertThat(position.getFacilitiesRating()).isEqualTo(3);
        assertThat(position.getGuidanceRating()).isEqualTo(4);

        verify(repo).save(position); 
    }

}
