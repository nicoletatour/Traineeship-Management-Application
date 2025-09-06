package myproject.traineeship_management_app.services;

import myproject.traineeship_management_app.domainmodel.Student;
import myproject.traineeship_management_app.domainmodel.TraineeshipPosition;
import myproject.traineeship_management_app.domainmodel.User;
import myproject.traineeship_management_app.mappers.StudentMapper;
import myproject.traineeship_management_app.mappers.TraineeshipPositionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentMapper studentRepo;
    private final TraineeshipPositionMapper positionRepo;

    @Autowired
    public StudentServiceImpl(StudentMapper studentRepo,
                              TraineeshipPositionMapper positionRepo) {
        this.studentRepo = studentRepo;
        this.positionRepo = positionRepo;
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepo.save(student);
    }

    @Override
    public Student getByUser(User user) {
        return studentRepo.findByUser(user).orElse(null);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    @Override
    public Student getById(Long id) {
        return studentRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No student with id " + id));
    }

    @Override
    public void applyForPosition(Long studentId, Long positionId) {
        Student s = getById(studentId);
        TraineeshipPosition p = positionRepo.findById(positionId)
            .orElseThrow(() -> new IllegalArgumentException("No position " + positionId));
        s.getAppliedPositions().add(p);
        studentRepo.save(s);
    }

    @Override
    public List<Student> getApplicantsForPosition(Long positionId) {
        return studentRepo.findAllByAppliedPositions_Id(positionId);
    }

    @Override
    public List<Student> getAllLooking() {
        return studentRepo.findAllByLookingTrue();
    }

    @Override
    public void setLooking(Long studentId, boolean looking) {
        Student s = studentRepo.findById(studentId)
            .orElseThrow(() -> 
                new IllegalArgumentException("No student with id " + studentId));
        s.setLooking(looking);
        studentRepo.save(s);
    }
}
