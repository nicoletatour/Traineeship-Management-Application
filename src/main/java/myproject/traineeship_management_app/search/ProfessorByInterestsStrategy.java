package myproject.traineeship_management_app.search;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import myproject.traineeship_management_app.domainmodel.Professor;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.services.ProfessorService;

@Component
public class ProfessorByInterestsStrategy implements ProfessorSearchStrategy {

  @Autowired private ProfessorService profSvc;
  
  public ProfessorByInterestsStrategy(ProfessorService profSvs) {
	  this.profSvc = profSvc;
  }

  @Override
  public List<Professor> search(TraineeshipPosition pos) {
    List<String> positionTopics = Arrays.stream(pos.getTopics().split(","))
                                        .map(String::trim)
                                        .filter(s -> !s.isEmpty())
                                        .collect(Collectors.toList());

    return profSvc.getAll().stream()
      .filter(p -> {
        List<String> profInterests = Arrays.stream(p.getInterests().split(","))
                                           .map(String::trim)
                                           .filter(s -> !s.isEmpty())
                                           .collect(Collectors.toList());
        return !Collections.disjoint(profInterests, positionTopics);
      })
      .toList();
  }
}