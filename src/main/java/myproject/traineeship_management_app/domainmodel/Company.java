package myproject.traineeship_management_app.domainmodel;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String name;

    @Column(name = "location", nullable = false)
    private String location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_username", referencedColumnName = "username")
    private User user;
    
    @OneToMany(
      mappedBy   = "company",
      cascade    = CascadeType.ALL,
      orphanRemoval = true,
      fetch      = FetchType.LAZY
    )
    private List<TraineeshipPosition> positions = new ArrayList<>();

    public Company() {}


    public Long getId() { 
    	return id; 
    }
    public void setId(Long id) { 
    	this.id = id; 
    }

    public String getName() { 
    	return name; 
    }
    public void setName(String name) { 
    	this.name = name; 
    }

    public String getLocation() { 
    	return location; 
    }
    
    public void setLocation(String location) { 
    	this.location = location; 
    }

    public User getUser() { 
    	return user; 
    }
    
    public void setUser(User user) {
    	this.user = user; 
    }
    
    public List<TraineeshipPosition> getPositions() {
        return positions;
    }
    
    public void setPositions(List<TraineeshipPosition> positions) {
        this.positions = positions;
    }
}
