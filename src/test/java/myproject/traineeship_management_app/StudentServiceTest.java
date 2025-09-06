package myproject.traineeship_management_app;

import myproject.traineeship_management_app.domainmodel.Student;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.mappers.StudentMapper;
import myproject.traineeship_management_app.mappers.TraineeshipPositionMapper;
import myproject.traineeship_management_app.services.StudentServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock private StudentMapper studentMapper;
    @Mock private TraineeshipPositionMapper posMapper;
    @InjectMocks private StudentServiceImpl studentService;


    @Test
    void saveStudent_persistsEntity() {
        Student s = new Student();
        s.setId(123L);
        when(studentMapper.save(s)).thenReturn(s);

        Student saved = studentService.saveStudent(s);

        assertThat(saved).isSameAs(s);
        verify(studentMapper).save(s);
    }

    @Test
    void applyForPosition_happyPath() {
        Student s = new Student();                 s.setId(1L);
        TraineeshipPosition p = new TraineeshipPosition(); p.setId(2L);

        when(studentMapper.findById(1L)).thenReturn(Optional.of(s));
        when(posMapper.findById(2L)).thenReturn(Optional.of(p));
        when(studentMapper.save(s)).thenReturn(s);

        studentService.applyForPosition(1L, 2L);

        assertThat(s.getAppliedPositions()).containsExactly(p);

        verify(studentMapper).save(s);
        verifyNoMoreInteractions(posMapper); 
    }

    /** UC05 alt: student not found */
    @Test
    void applyForPosition_noStudent_throws() {
        when(studentMapper.findById(9L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.applyForPosition(9L, 2L))
            .isInstanceOf(IllegalArgumentException.class);
    }

    /** UC05 alt: position not found */
    @Test
    void applyForPosition_noPosition_throws() {
        Student s = new Student(); s.setId(1L);
        when(studentMapper.findById(1L)).thenReturn(Optional.of(s));
        when(posMapper.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.applyForPosition(1L, 99L))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
