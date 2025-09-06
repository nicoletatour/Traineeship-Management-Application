package myproject.traineeship_management_app.domainmodel;

import jakarta.persistence.*;

@Entity
@Table(name = "professors")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="full_name", nullable = false)
    private String fullName;

    @Column(length = 1000)
    private String interests;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;

    public Professor() {
    	
    }

    public Long getId() { 
    	return id; 
    }
    
    public void setId(Long id) { 
    	this.id = id; 
    }

    public String getFullName() { 
    	return fullName; 
    }
    
    public void setFullName(String fullName) { 
    	this.fullName = fullName; 
    }

    public String getInterests() { 
    	return interests; 
    }
    
    public void setInterests(String interests) { 
    	this.interests = interests; 
    }

    public User getUser() { 
    	return user; 
    }
    
    public void setUser(User user) { 
    	this.user = user; 
    }
}
