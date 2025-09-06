package myproject.traineeship_management_app.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import myproject.traineeship_management_app.domainmodel.CommitteeResult;
import myproject.traineeship_management_app.domainmodel.Company;
import myproject.traineeship_management_app.domainmodel.Professor;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;

import java.util.List;

public interface TraineeshipPositionMapper extends JpaRepository<TraineeshipPosition, Long> {
    List<TraineeshipPosition> findAllByCompany(Company company);

    List<TraineeshipPosition> findAllByCompanyAndAssignedStudentIsNull(Company company);

    List<TraineeshipPosition> findAllByCompanyAndAssignedStudentIsNotNull(Company company);
    List<TraineeshipPosition> findAllByAssignedStudentIsNull();
    List<TraineeshipPosition> findAllByAssignedStudentIsNotNullAndSupervisorIsNull();

    long countBySupervisor(Professor prof);

    List<TraineeshipPosition> findAllBySupervisor(Professor supervisor);

    List<TraineeshipPosition> findAllByAssignedStudentIsNotNull();
    
    List<TraineeshipPosition>
    findAllByAssignedStudentIsNotNullAndCommitteeResult(CommitteeResult result);

    List<TraineeshipPosition>
    findAllByCommitteeResult(CommitteeResult result);
 
    
    @Query("""
    	      SELECT p
    	        FROM TraineeshipPosition p
    	       WHERE p.assignedStudent         IS NOT NULL
    	         AND p.motivationRating        IS NOT NULL
    	         AND p.effectivenessRating     IS NOT NULL
    	         AND p.efficiencyRating        IS NOT NULL
    	         AND p.facilitiesRating        IS NOT NULL
    	         AND p.guidanceRating          IS NOT NULL
    	         AND p.companyMotivationRating    IS NOT NULL
    	         AND p.companyEffectivenessRating IS NOT NULL
    	         AND p.companyEfficiencyRating    IS NOT NULL
    	         AND p.endDate <= CURRENT_DATE
    	         AND p.committeeResult = 'PENDING'
    	    """)
    	  List<TraineeshipPosition> findAllReadyForCommitteeReview();
    
    List<TraineeshipPosition> findByCommitteeResultIn(List<CommitteeResult> results);
    
 
}
