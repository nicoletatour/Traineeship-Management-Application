package myproject.traineeship_management_app.services;

import java.util.List;

import myproject.traineeship_management_app.domainmodel.Committee;
import myproject.traineeship_management_app.domainmodel.CommitteeResult;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.domainmodel.User;

public interface CommitteeService {
    Committee saveCommittee(Committee committee);
    Committee getByUser(User user);
    List<TraineeshipPosition> getInProgress();                                
    TraineeshipPosition getById(Long id);               
    List<TraineeshipPosition> getPositionsForReview();
    void complete(Long positionId, CommitteeResult decision, String notes);

}