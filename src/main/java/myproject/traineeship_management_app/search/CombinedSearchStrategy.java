package myproject.traineeship_management_app.search;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import myproject.traineeship_management_app.domainmodel.Student;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;

@Component
public class CombinedSearchStrategy extends PositionSearchStrategy {

    private final InterestBasedSearchStrategy interestStrategy;
    private final LocationBasedSearchStrategy locationStrategy;

    @Autowired
    public CombinedSearchStrategy(InterestBasedSearchStrategy interestStrategy,
                                  LocationBasedSearchStrategy locationStrategy) {
        this.interestStrategy = interestStrategy;
        this.locationStrategy = locationStrategy;
    }


    @Override
    public List<TraineeshipPosition> search(Student student) {

        List<TraineeshipPosition> byInterest = interestStrategy.search(student);
        List<TraineeshipPosition> byLocation = locationStrategy.search(student);

        if (student.getPreferredLocation() == null ||
            student.getPreferredLocation().isBlank()) {
            return byInterest;
        }

        Set<Long> locIds = locationStrategy.search(student)
                .stream()
                .map(TraineeshipPosition::getId)
                .collect(Collectors.toSet());

        return interestStrategy.search(student).stream()
                .filter(p -> locIds.contains(p.getId()))
                .toList(); 
    }
}
