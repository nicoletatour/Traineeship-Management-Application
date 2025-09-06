package myproject.traineeship_management_app.services;

import myproject.traineeship_management_app.domainmodel.Student;
import myproject.traineeship_management_app.domainmodel.User;
import java.util.List;

public interface StudentService {
    Student saveStudent(Student student);
    Student getByUser(User user);
    List<Student> getAllStudents();
    Student getById(Long id);
    void applyForPosition(Long studentId, Long positionId);
    List<Student> getApplicantsForPosition(Long positionId);
    List<Student> getAllLooking();
    void setLooking(Long studentId, boolean looking);
}
