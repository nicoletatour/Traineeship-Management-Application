package myproject.traineeship_management_app.domainmodel;

public enum Role {
	STUDENT("Student"), 
	COMPANY("Company"),
	PROFESSOR("Professor"),
	COMMITTEE("Committee");
	
	
    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    
    }
}
