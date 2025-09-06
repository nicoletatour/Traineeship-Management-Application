package myproject.traineeship_management_app.services;

import myproject.traineeship_management_app.domainmodel.Company;
import myproject.traineeship_management_app.domainmodel.Professor;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;

import java.util.List;


public interface TraineeshipPositionService {
    TraineeshipPosition save(TraineeshipPosition pos);
    List<TraineeshipPosition> getAvailableByCompany(Company company);
    List<TraineeshipPosition> getAssignedByCompany(Company company);
    
    List<TraineeshipPosition> getAllAvailable();
    
    TraineeshipPosition getById(Long id);
    void deleteById(Long id);
    List<TraineeshipPosition> getAllPositions();
    void assignToStudent(Long positionId, String studentUsername);
    List<TraineeshipPosition> getAllOpenPositions();

    List<TraineeshipPosition> getUnsupervisedInProgress();

    void assignSupervisor(Long positionId, Long professorId);
    long countSupervisions(Professor prof);

    List<TraineeshipPosition> getSupervisedByProfessor(Professor professor);
    
    List<TraineeshipPosition> getInProgressPositions();

    void evaluateByProfessor(Long positionId,
            int motivation,
            int effectiveness,
            int efficiency,
            int facilities,
            int guidance,
            String professorUsername);
    List<TraineeshipPosition> getPendingEvaluations(Professor professor);

    List<TraineeshipPosition> getReadyForCommitteeReview();
    
    List<TraineeshipPosition> getCompletedPositions();
    
}
