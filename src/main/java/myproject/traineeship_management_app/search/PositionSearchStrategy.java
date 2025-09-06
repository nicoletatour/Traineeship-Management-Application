package myproject.traineeship_management_app.search;

import java.util.List;
import myproject.traineeship_management_app.domainmodel.Student;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;

public abstract class PositionSearchStrategy {

    public abstract List<TraineeshipPosition> search(Student student);
}
