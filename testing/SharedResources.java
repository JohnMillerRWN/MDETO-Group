package testing;

public class SharedResources {
	
	private User current_user;
	
	public void setUser(User c_user) {
		this.current_user = c_user;
	}
	
	public User getUser() {
		return current_user;
	}

}
