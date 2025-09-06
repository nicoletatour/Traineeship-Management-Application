package myproject.traineeship_management_app.services;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.security.access.AccessDeniedException;

import myproject.traineeship_management_app.domainmodel.CommitteeResult;
import myproject.traineeship_management_app.domainmodel.Company;
import myproject.traineeship_management_app.domainmodel.Professor;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.mappers.ProfessorMapper;
import myproject.traineeship_management_app.mappers.TraineeshipPositionMapper;

import java.util.List;
import java.util.Objects;

@Service
public class TraineeshipPositionServiceImpl implements TraineeshipPositionService {

	 private final TraineeshipPositionMapper repo;
	    private final UserService userService;
	    private final ProfessorMapper profRepo;

	    public TraineeshipPositionServiceImpl(
	            TraineeshipPositionMapper repo,
	            UserService userService,
	            ProfessorMapper profRepo) {
	        this.repo        = repo;
	        this.userService = userService;
	        this.profRepo    = profRepo;
	    }

    @Override
    public TraineeshipPosition save(TraineeshipPosition pos) {
        return repo.save(pos);
    }

    @Override
    public List<TraineeshipPosition> getAvailableByCompany(Company company) {
        return repo.findAllByCompanyAndAssignedStudentIsNull(company);
    }
    
    @Override
    public List<TraineeshipPosition> getAssignedByCompany(Company company) {
        return repo.findAllByCompanyAndAssignedStudentIsNotNull(company);
    }
    

    @Override
    public List<TraineeshipPosition> getAllAvailable() {
        return repo.findAllByAssignedStudentIsNull();
    }
    
    @Override
    public TraineeshipPosition getById(Long id) {
        return repo.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("No position with id " + id));
    }
    
    @Override
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
    
    @Override
    public List<TraineeshipPosition> getAllPositions() {
        return repo.findAll();
    }
    
    @Override
    public List<TraineeshipPosition> getAllOpenPositions() {
        return repo.findAllByAssignedStudentIsNull();
    }
    
    @Override
    @Transactional
    public void assignToStudent(Long positionId, String studentUsername) {
        TraineeshipPosition pos = repo.findById(positionId)
            .orElseThrow(() -> new EntityNotFoundException("No position with id " + positionId));

        User studentUser = userService.findByUsername(studentUsername);
        pos.setAssignedStudent(studentUser);
        repo.save(pos);
    }
   
    @Override
    public List<TraineeshipPosition> getUnsupervisedInProgress() {
        return repo.findAllByAssignedStudentIsNotNullAndSupervisorIsNull();
    }

    @Override
    @Transactional
    public void assignSupervisor(Long positionId, Long professorId) {
        TraineeshipPosition pos = repo.findById(positionId)
            .orElseThrow(() -> new EntityNotFoundException("Position not found"));
        Professor prof = profRepo.findById(professorId)
            .orElseThrow(() -> new EntityNotFoundException("Professor not found"));

        pos.setSupervisor(prof);
        repo.save(pos);
    }
    
    @Override
    public long countSupervisions(Professor prof) {
      return repo.countBySupervisor(prof);
    }
    
    @Override
    public List<TraineeshipPosition> getSupervisedByProfessor(Professor professor) {
        return repo.findAllBySupervisor(professor);
    }
    

    @Override
    public List<TraineeshipPosition> getInProgressPositions() {
        return repo.findAllByAssignedStudentIsNotNullAndCommitteeResult(CommitteeResult.PENDING);
    }

    @Override @Transactional
    public void evaluateByProfessor(Long positionId,
                                    int motivation,
                                    int effectiveness,
                                    int efficiency,
                                    int facilities,
                                    int guidance,
                                    String professorUsername) {

        TraineeshipPosition pos = repo.findById(positionId)
            .orElseThrow(() -> new EntityNotFoundException("Position not found"));

        if (!Objects.equals(pos.getSupervisor().getUser().getUsername(),
                            professorUsername)) {
            throw new AccessDeniedException("Not your traineeship!");
        }
        pos.setMotivationRating(motivation);
        pos.setEffectivenessRating(effectiveness);
        pos.setEfficiencyRating(efficiency);
        pos.setFacilitiesRating(facilities);
        pos.setGuidanceRating(guidance);

        repo.save(pos);
    }
    
    @Override
    public List<TraineeshipPosition> getPendingEvaluations(Professor prof) {
        return repo.findAllBySupervisor(prof).stream()
                   .filter(p -> p.getMotivationRating() == null)
                   .toList();
    }
    
    @Override
    public List<TraineeshipPosition> getReadyForCommitteeReview() {
      return repo.findAllReadyForCommitteeReview();
    }
    
    @Override
    public List<TraineeshipPosition> getCompletedPositions() {
        return repo.findByCommitteeResultIn(List.of(CommitteeResult.PASS, CommitteeResult.FAIL));
    }

}
