package myproject.traineeship_management_app.domainmodel;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "traineeship_positions")
public class TraineeshipPosition {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(length = 1000)
    private String description;

    @Column(name = "required_skills")
    private String requiredSkills;

    @Column(name = "topics")
    private String topics;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_student_username")
    private User assignedStudent;

    @Column(name = "motivation_rating")
    private Integer motivationRating;

    @Column(name = "effectiveness_rating")
    private Integer effectivenessRating;

    @Column(name = "efficiency_rating")
    private Integer efficiencyRating;
    
    /** supervising professor once assigned **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supervisor_id")
    private Professor supervisor;
    
    @Column(name = "compMotivation_rating")
    private Integer companyMotivationRating;
    
    @Column(name = "compEffectiveness_rating")
    private Integer companyEffectivenessRating;
    
    @Column(name = "compEfficiency_rating")
    private Integer companyEfficiencyRating;
    
    @Column(name = "facilities_rating")
    private Integer facilitiesRating;

    @Column(name = "guidance_rating")
    private Integer guidanceRating;
    
    
    @Column(name = "prof_eval_completed", nullable = false)
    private boolean professorEvaluationCompleted = false;

    public boolean isProfessorEvaluationCompleted() {
        return professorEvaluationCompleted;
    }
    
    public void setProfessorEvaluationCompleted(boolean completed) {
        this.professorEvaluationCompleted = completed;
    }

    public TraineeshipPosition() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getAssignedStudent() {
        return assignedStudent;
    }

    public void setAssignedStudent(User assignedStudent) {
        this.assignedStudent = assignedStudent;
    }
    
    public Integer getMotivationRating() {
        return motivationRating;
    }

    public void setMotivationRating(Integer motivationRating) {
        this.motivationRating = motivationRating;
    }

    public Integer getEffectivenessRating() {
        return effectivenessRating;
    }

    public void setEffectivenessRating(Integer effectivenessRating) {
        this.effectivenessRating = effectivenessRating;
    }

    public Integer getEfficiencyRating() {
        return efficiencyRating;
    }

    public void setEfficiencyRating(Integer efficiencyRating) {
        this.efficiencyRating = efficiencyRating;
    }
    

    public Integer getFacilitiesRating() {
        return facilitiesRating;
    }
    public void setFacilitiesRating(Integer facilitiesRating) {
        this.facilitiesRating = facilitiesRating;
    }

    public Integer getGuidanceRating() {
        return guidanceRating;
    }
    public void setGuidanceRating(Integer guidanceRating) {
        this.guidanceRating = guidanceRating;
    }
        
    public Professor getSupervisor() {
    	return supervisor; 
    }
    
    public void setSupervisor(Professor supervisor) { 
    	this.supervisor = supervisor; 
    }
    
    
    public Integer getCompanyMotivationRating() { 
    	return companyMotivationRating; 
    }
    
    public void setCompanyMotivationRating(Integer r) { 
    	this.companyMotivationRating = r; 
    }
    
    public Integer getCompanyEffectivenessRating() { 
    	return companyEffectivenessRating; 
    }
    
    public void setCompanyEffectivenessRating(Integer r) { 
    	this.companyEffectivenessRating = r; 
    }
    
    public Integer getCompanyEfficiencyRating() { 
    	return companyEfficiencyRating; 
    }
    public void setCompanyEfficiencyRating(Integer r) { 
    	this.companyEfficiencyRating = r; 
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "committee_result", nullable = false)
    private CommitteeResult committeeResult = CommitteeResult.PENDING;

    @Column(name = "committee_notes", length = 1000)
    private String committeeNotes;

    public CommitteeResult getCommitteeResult(){ 
    	return committeeResult; 
    }
    
    public void setCommitteeResult(CommitteeResult r){ 
    	this.committeeResult = r; 
    }
    
    public String getCommitteeNotes() { 
    	return committeeNotes; 
    }
    
    public void setCommitteeNotes(String notes){ 
    	this.committeeNotes = notes; 
    }

    @Transient
    public double getAverageRating() {
        double profAvg = (motivationRating + effectivenessRating +
                          efficiencyRating + facilitiesRating +
                          guidanceRating) / 5.0;

        double compAvg = (companyMotivationRating + companyEffectivenessRating +
                          companyEfficiencyRating) / 3.0;

        return (profAvg + compAvg) / 2.0;
    }
    
    
}
