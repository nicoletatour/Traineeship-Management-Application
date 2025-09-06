package myproject.traineeship_management_app.services;

import myproject.traineeship_management_app.domainmodel.LogbookEntry;
import java.util.List;

public interface LogbookService {
    LogbookEntry save(LogbookEntry entry);
    List<LogbookEntry> getByStudent(Long studentId);
}