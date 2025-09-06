package myproject.traineeship_management_app.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ProfessorSearchFactory {
  @Autowired private ApplicationContext ctx;
  
  public ProfessorSearchFactory(ApplicationContext ctx) {
	  this.ctx = ctx;
  }


public ProfessorSearchStrategy getStrategy(ProfessorSearchCriteria crit) {
    switch(crit) {
      case INTERESTS: return ctx.getBean(ProfessorByInterestsStrategy.class);
      case LOAD:      return ctx.getBean(ProfessorByLoadStrategy.class);
      default: throw new IllegalArgumentException();
    }
  }
}
