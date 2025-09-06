package myproject.traineeship_management_app.domainmodel;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "student_id")
 private Long studentId;

 @Column(name = "full_name", nullable = false)
 private String fullName;

 @Column(name = "university_id_number", nullable = false, unique = true)
 private String universityIdNumber;

 @Column(name = "interests")
 private String interests; 

 @Column(name = "skills")
 private String skills;  

 @Column(name = "preferred_location")
 private String preferredLocation;
 
 @Column(name = "looking", nullable = false)
 private boolean looking = false; 

 @OneToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "user_username", referencedColumnName = "username")
 private User user;
 
 @ManyToMany
 @JoinTable(
   name = "student_applications",
   joinColumns = @JoinColumn(name = "student_id"),
   inverseJoinColumns = @JoinColumn(name = "position_id")
 )
 private List<TraineeshipPosition> appliedPositions = new ArrayList<>();

 public List<TraineeshipPosition> getAppliedPositions() {
   return appliedPositions;
 }
 
 public void setAppliedPositions(List<TraineeshipPosition> appliedPositions) {
   this.appliedPositions = appliedPositions;
 }

 
 public Student() {
	 
 }
 
 public boolean isLooking() { 
	 return looking; 
 }
 
 public void setLooking(boolean looking) { 
	 this.looking = looking; 
 }
 
 public Long getId() { 
	 return studentId; 
 }
 
 public void setId(Long id) { 
	 this.studentId = id; 
 }

 public String getFullName() { 
	 return fullName; 
 }
 
 public void setFullName(String fullName) { 
	 this.fullName = fullName; 
 }

 public String getUniversityIdNumber() { 
	 return universityIdNumber; 
 }
 
 public void setUniversityIdNumber(String universityIdNumber) {
     this.universityIdNumber = universityIdNumber;
 }

 public String getInterests() { 
	 return interests; 
 }
 public void setInterests(String interests) { 
	 this.interests = interests; 
 }

 public String getSkills() { 
	 return skills; 
 }
 
 public void setSkills(String skills) { 
	 this.skills = skills; 
 }
 
 public String getPreferredLocation() { 
	 return preferredLocation; 
 }
 public void setPreferredLocation(String preferredLocation) {
     this.preferredLocation = preferredLocation;
 }

 public User getUser() { 
	 return user; 
 }
 public void setUser(User user) { 
	 this.user = user; 
 }
}
