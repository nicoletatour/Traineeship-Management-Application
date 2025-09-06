package myproject.traineeship_management_app.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import myproject.traineeship_management_app.domainmodel.LogbookEntry;
import myproject.traineeship_management_app.mappers.LogbookEntryMapper;
import java.util.List;

@Service
public class LogbookServiceImpl implements LogbookService {

    private final LogbookEntryMapper repo;

    @Autowired
    public LogbookServiceImpl(LogbookEntryMapper repo) {
        this.repo = repo;
    }

    @Override
    public LogbookEntry save(LogbookEntry entry) {
        return repo.save(entry);
    }

    @Override
    public List<LogbookEntry> getByStudent(Long studentId) {
        return repo.findAllByStudentIdOrderByDateDesc(studentId);
    }
}