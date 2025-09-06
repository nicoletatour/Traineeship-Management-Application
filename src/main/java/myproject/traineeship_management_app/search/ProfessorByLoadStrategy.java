package myproject.traineeship_management_app.search;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import myproject.traineeship_management_app.domainmodel.Professor;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.services.ProfessorService;
import myproject.traineeship_management_app.services.TraineeshipPositionService;

@Component
public class ProfessorByLoadStrategy implements ProfessorSearchStrategy {
  
  @Autowired 
  private ProfessorService profSvc;
  
  @Autowired 
  private TraineeshipPositionService posfSvc;
  
  
  public ProfessorByLoadStrategy(ProfessorService profSvc, TraineeshipPositionService posfSvc) {
	  this.profSvc = profSvc;
	  this.posSvc = posSvc;
	  
  }
  
  @Autowired 
  private TraineeshipPositionService posSvc;

  @Override
  public List<Professor> search(TraineeshipPosition pos) {
    return profSvc.getAll().stream()
      .sorted(Comparator.comparingLong(p -> posSvc.countSupervisions(p)))
      .toList();
  }
}