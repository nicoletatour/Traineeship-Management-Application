package myproject.traineeship_management_app.search;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import myproject.traineeship_management_app.domainmodel.Student;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.services.TraineeshipPositionService;

import java.util.*;
import java.util.stream.*;

@Component
public class InterestBasedSearchStrategy extends PositionSearchStrategy {

    private final TraineeshipPositionService positionService;

    @Autowired
    public InterestBasedSearchStrategy(TraineeshipPositionService positionService) {
        this.positionService = positionService;
    }

    @Override
    public List<TraineeshipPosition> search(Student student) {
        List<String> interests = parseCsv(student.getInterests());
        List<String> skills    = parseCsv(student.getSkills());

        return positionService.getAllAvailable().stream()
            .filter(pos -> {
                List<String> topics    = parseCsv(pos.getTopics());
                List<String> reqSkills = parseCsv(pos.getRequiredSkills());

                boolean skillMatch = skills.containsAll(reqSkills);
                boolean interestMatch = interests.stream()
                    .anyMatch(i -> topics.stream().anyMatch(t -> t.equalsIgnoreCase(i)));

                return skillMatch && interestMatch;
            })
            .collect(Collectors.toList());
    }

    private List<String> parseCsv(String csv) {
        if (csv == null || csv.isBlank()) return List.of();
        return Arrays.stream(csv.split(","))
                     .map(String::trim)
                     .filter(s -> !s.isEmpty())
                     .collect(Collectors.toList());
    }
}
