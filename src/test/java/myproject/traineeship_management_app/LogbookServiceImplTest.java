package myproject.traineeship_management_app;

import myproject.traineeship_management_app.domainmodel.LogbookEntry;
import myproject.traineeship_management_app.mappers.LogbookEntryMapper;
import myproject.traineeship_management_app.services.LogbookServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogbookServiceImplTest {

    @Mock  private LogbookEntryMapper repo;
    @InjectMocks private LogbookServiceImpl service;

    @Test
    void saveEntry_passesEntityToRepo() {
        LogbookEntry e = new LogbookEntry(); e.setId(5L);
        when(repo.save(e)).thenReturn(e);

        LogbookEntry saved = service.save(e);

        assertThat(saved).isSameAs(e);
        verify(repo).save(e);
    }

    @Test
    void getEntriesForStudent_queriesRepoDescending() {
        LogbookEntry e1 = new LogbookEntry(); e1.setDate(LocalDate.now());
        when(repo.findAllByStudentIdOrderByDateDesc(7L)).thenReturn(List.of(e1));

        List<LogbookEntry> list = service.getByStudent(7L);

        assertThat(list).containsExactly(e1);
        verify(repo).findAllByStudentIdOrderByDateDesc(7L);
    }
}
