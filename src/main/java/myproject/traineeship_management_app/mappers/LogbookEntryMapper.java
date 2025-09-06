package myproject.traineeship_management_app.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import myproject.traineeship_management_app.domainmodel.LogbookEntry;
import java.util.List;

public interface LogbookEntryMapper extends JpaRepository<LogbookEntry, Long> {
    List<LogbookEntry> findAllByStudentIdOrderByDateDesc(Long studentId);
}