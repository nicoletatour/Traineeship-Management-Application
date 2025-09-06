package myproject.traineeship_management_app.search;

import java.util.List;

import myproject.traineeship_management_app.domainmodel.Professor;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;

public interface ProfessorSearchStrategy {
	  List<Professor> search(TraineeshipPosition pos);
	}
