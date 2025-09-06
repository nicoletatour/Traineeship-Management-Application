package myproject.traineeship_management_app.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PositionSearchFactory {

    @Autowired private InterestBasedSearchStrategy interestStrategy;
    @Autowired private LocationBasedSearchStrategy locationStrategy;
    @Autowired private CombinedSearchStrategy combinedStrategy;
    
    public PositionSearchFactory(InterestBasedSearchStrategy interestStrategy, LocationBasedSearchStrategy locationStrategy, CombinedSearchStrategy combinedStrategy ) {
    	this.interestStrategy = interestStrategy;
    	this.locationStrategy = locationStrategy;
    	this.combinedStrategy = combinedStrategy;
    	
    }
    

    public PositionSearchStrategy getStrategy(SearchCriteria criteria) {
        return switch (criteria) {
            case INTERESTS -> interestStrategy;
            case LOCATION  -> locationStrategy;
            case BOTH      -> combinedStrategy;
        };
    }
}
