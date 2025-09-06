package myproject.traineeship_management_app;


import myproject.traineeship_management_app.domainmodel.*;
import myproject.traineeship_management_app.search.CombinedSearchStrategy;
import myproject.traineeship_management_app.search.InterestBasedSearchStrategy;
import myproject.traineeship_management_app.search.LocationBasedSearchStrategy;
import myproject.traineeship_management_app.search.PositionSearchFactory;
import myproject.traineeship_management_app.search.SearchCriteria;
import myproject.traineeship_management_app.services.TraineeshipPositionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TraineeshipSearchTest {

    @Mock  private TraineeshipPositionService posService;

    private InterestBasedSearchStrategy interest;
    private LocationBasedSearchStrategy  location;
    private CombinedSearchStrategy       combined;
    private PositionSearchFactory        factory;

    private Student student;
    private TraineeshipPosition p1, p2, p3;

    @BeforeEach
    void setUp() {
        /* ---- test data ---- */
        student = new Student();
        student.setSkills("Java,Git");
        student.setInterests("Spring,Cloud");
        student.setPreferredLocation("Athens");

        p1 = makePos(1L,"Java","Spring","Athens");        // matches BOTH filters
        p2 = makePos(2L,"Python","Data","Athens");        // location only
        p3 = makePos(3L,"Java","Spring","Thessaloniki");  // interest only

        when(posService.getAllAvailable())
                .thenReturn(List.of(p1,p2,p3));

        /* ---- strategy beans ---- */
        interest  = new InterestBasedSearchStrategy(posService);
        location  = new LocationBasedSearchStrategy(posService);
        combined  = new CombinedSearchStrategy(interest, location);

        factory   = new PositionSearchFactory(interest, location, combined);
    }

    /* ---------- individual strategies ---------- */

    @Test
    void interestStrategy_returnsPositionsMatchingSkillsAndTopics() {
        List<TraineeshipPosition> found = interest.search(student);
        assertThat(found).containsExactlyInAnyOrder(p1, p3);
    }

    @Test
    void locationStrategy_returnsPositionsMatchingPreferredLocation() {
        List<TraineeshipPosition> found = location.search(student);
        assertThat(found).containsExactlyInAnyOrder(p1, p2);
    }

    @Test
    void combinedStrategy_returnsIntersectionOfBothLists() {
        List<TraineeshipPosition> found = combined.search(student);
        assertThat(found).containsExactly(p1);
    }

    /* ---------- helper ---------- */

    private static TraineeshipPosition makePos(Long id, String skills,
                                               String topics, String loc) {
        TraineeshipPosition p = new TraineeshipPosition();
        p.setId(id);
        p.setRequiredSkills(skills);
        p.setTopics(topics);
        Company c = new Company(); c.setLocation(loc);
        p.setCompany(c);
        return p;
    }
}
