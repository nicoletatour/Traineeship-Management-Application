package myproject.traineeship_management_app.domainmodel;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "logbook_entries")
public class LogbookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false)
    private LocalDate date;

    @Column(length = 2000, nullable = false)
    private String content;

    public LogbookEntry() {
    	
    }

    public Long getId() { 
    	return id; 
    }
    
    public void setId(Long id) { 
    	this.id = id; 
    }

    public Student getStudent() { 
    	return student; 
    }
    
    public void setStudent(Student student) { 
    	this.student = student; 
    }

    public LocalDate getDate() { 
    	return date; 
    }
    
    public void setDate(LocalDate date) { 
    	this.date = date; 
    }

    public String getContent() { 
    	return content; 
    }
    public void setContent(String content) { 
    	this.content = content; 
    }
}