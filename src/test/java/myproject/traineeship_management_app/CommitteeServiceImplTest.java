package myproject.traineeship_management_app;


import myproject.traineeship_management_app.domainmodel.*;
import myproject.traineeship_management_app.mappers.CommitteeMapper;
import myproject.traineeship_management_app.services.CommitteeServiceImpl;
import myproject.traineeship_management_app.services.TraineeshipPositionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommitteeServiceImplTest {

    @Mock  private CommitteeMapper committeeRepo;
    @Mock  private TraineeshipPositionService positionService;
    @InjectMocks private CommitteeServiceImpl service;

    private Committee committee;
    private User user;
    private TraineeshipPosition pos;

    @BeforeEach
    void init() {
        user = new User(); user.setUsername("comm1");
        committee = new Committee(); committee.setId(1L); committee.setUser(user);
        pos = new TraineeshipPosition(); pos.setId(40L); pos.setCommitteeResult(CommitteeResult.PENDING);
    }

    @Test
    void saveCommittee_returnsSavedEntity() {
        when(committeeRepo.save(committee)).thenReturn(committee);

        Committee saved = service.saveCommittee(committee);

        assertThat(saved).isSameAs(committee);
        verify(committeeRepo).save(committee);
    }

    @Test
    void getByUser_returnsCommittee() {
        when(committeeRepo.findByUser(user)).thenReturn(Optional.of(committee));

        Committee found = service.getByUser(user);

        assertThat(found).isSameAs(committee);
    }

    @Test
    void completeDecision_setsResultAndNotes_andSaves() {
        when(positionService.getById(40L)).thenReturn(pos);

        service.complete(40L, CommitteeResult.PASS, "all good");

        assertThat(pos.getCommitteeResult()).isEqualTo(CommitteeResult.PASS);
        assertThat(pos.getCommitteeNotes()).isEqualTo("all good");
        verify(positionService).save(pos);
    }

    @Test
    void completeDecision_throws_whenPositionMissing() {
        when(positionService.getById(99L))
            .thenThrow(new IllegalArgumentException("No position"));

        assertThrows(IllegalArgumentException.class,
                     () -> service.complete(99L, CommitteeResult.FAIL, "x"));
    }


    @Test
    void getInProgressPositions_delegatesToPositionService() {
        when(positionService.getInProgressPositions()).thenReturn(List.of(pos));

        List<TraineeshipPosition> list = service.getInProgress();

        assertThat(list).containsExactly(pos);
        verify(positionService).getInProgressPositions();
    }
}
