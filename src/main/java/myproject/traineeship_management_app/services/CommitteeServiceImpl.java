package myproject.traineeship_management_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import myproject.traineeship_management_app.domainmodel.Committee;
import myproject.traineeship_management_app.domainmodel.CommitteeResult;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.mappers.CommitteeMapper;
import myproject.traineeship_management_app.mappers.TraineeshipPositionMapper;

@Service
public class CommitteeServiceImpl implements CommitteeService {

    private final CommitteeMapper          committeeRepo;
    private final TraineeshipPositionService positionService;

    public CommitteeServiceImpl(
        CommitteeMapper committeeRepo,
        TraineeshipPositionService positionService) {
        this.committeeRepo     = committeeRepo;
        this.positionService   = positionService;
    }

    @Override
    public Committee saveCommittee(Committee committee) {
        return committeeRepo.save(committee);
    }

    @Override
    public Committee getByUser(User user) {
        return committeeRepo.findByUser(user).orElse(null);
    }

    @Override
    public List<TraineeshipPosition> getInProgress() {
        return positionService.getInProgressPositions();
    }

    @Override
    public List<TraineeshipPosition> getPositionsForReview() {
        return positionService.getReadyForCommitteeReview();
    }

    @Override
    @Transactional
    public void complete(Long positionId, CommitteeResult decision, String notes) {
        TraineeshipPosition p = positionService.getById(positionId);
        p.setCommitteeResult(decision);
        p.setCommitteeNotes(notes == null ? "" : notes);
        positionService.save(p);
    }

    @Override
    public TraineeshipPosition getById(Long id) {
        return positionService.getById(id);
    }
}