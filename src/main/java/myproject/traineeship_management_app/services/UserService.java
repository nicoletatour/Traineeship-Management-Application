package myproject.traineeship_management_app.services;

import myproject.traineeship_management_app.domainmodel.User;

public interface UserService {
	public void saveUser(User user);
    public boolean isUserPresent(User user);
    User findByUsername(String username);
}
