package myproject.traineeship_management_app.search;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import myproject.traineeship_management_app.domainmodel.Student;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.services.TraineeshipPositionService;

import java.text.Normalizer;
import java.util.*;

@Component
public class LocationBasedSearchStrategy extends PositionSearchStrategy {

    private final TraineeshipPositionService positionService;

    @Autowired
    public LocationBasedSearchStrategy(TraineeshipPositionService positionService) {
        this.positionService = positionService;
    }

    @Override
    public List<TraineeshipPosition> search(Student student) {

        String prefLoc = canonical(student.getPreferredLocation());
        if (prefLoc.isBlank()) {
            return List.of();    
        }

        return positionService.getAllAvailable().stream()
            .filter(pos -> {
                String compLoc = canonical(pos.getCompany().getLocation());
                return compLoc.contains(prefLoc) || prefLoc.contains(compLoc);
            })
            .toList();
    }

    private static String canonical(String s) {
        if (s == null) return "";
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                         .replaceAll("\\p{M}", "")   
                         .replaceAll("[^\\p{IsAlphabetic}\\d]", "") 
                         .toLowerCase(Locale.ROOT)
                         .trim();
    }

}
