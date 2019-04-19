package BOE;

public class SharedResources {

	private User current_user;
	private int project_id = -1;
	private int clin_id = -1;
	
	public void setUser(User c_user) {
		this.current_user = c_user;
	}

	public User getUser() {
		return current_user;
	}

	public int getProject() {
		return project_id;
	}

	public void setProject(int project_id) {
		this.project_id = project_id;
	}

	public int getCLIN() {
		return clin_id;
	}

	public void setCLIN(int clin_id) {
		this.clin_id = clin_id;
	}

}
