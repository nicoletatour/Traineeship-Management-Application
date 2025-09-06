package myproject.traineeship_management_app;

import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.mappers.TraineeshipPositionMapper;
import myproject.traineeship_management_app.services.TraineeshipPositionServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeshipPositionServiceTest {

    @Mock private TraineeshipPositionMapper repo;
    @InjectMocks private TraineeshipPositionServiceImpl service;

    /** UC10: announce a new position */
    @Test
    void savePosition_returnsSaved() {
        TraineeshipPosition p = new TraineeshipPosition();
        p.setTitle("Intern");  
        when(repo.save(p)).thenReturn(p);

        TraineeshipPosition saved = service.save(p);

        assertThat(saved).isSameAs(p);
        verify(repo).save(p);
    }

    /** UC11: delete a position */
    @Test
    void deletePosition_invokesDeleteById() {
    	service.deleteById(77L);
        verify(repo).deleteById(77L);
    }

    /** UC08: list available (no assigned student) */
    @Test
    void getAllAvailable_filtersOutAssigned() {
    	TraineeshipPosition open     = new TraineeshipPosition(); open.setId(1L);
        TraineeshipPosition assigned = new TraineeshipPosition();
        assigned.setId(2L);
        assigned.setAssignedStudent(new User());

        
        when(repo.findAllByAssignedStudentIsNull())
            .thenReturn(Arrays.asList(open));

        List<TraineeshipPosition> available = service.getAllAvailable();

        assertThat(available).containsExactly(open);
        verify(repo).findAllByAssignedStudentIsNull();
    }
    

    
}
