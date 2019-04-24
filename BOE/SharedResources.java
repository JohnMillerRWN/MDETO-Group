package BOE;

public class SharedResources {

	private User current_user;
	private String window_title = "";
	
	private int project_id = -1;
	private int clin_id = -1;
	private int product_id = -1;
	private int wrk_pkg_id = -1;
	
	public User getUser() {
		return current_user;
	}

	public void setUser(User c_user) {
		this.current_user = c_user;
	}

	public String getWindow_title() {
		return window_title;
	}

	public void setWindow_title(String window_title) {
		this.window_title = window_title;
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

	public int getProduct() {
		return product_id;
	}

	public void setProduct(int product_id) {
		this.product_id = product_id;
	}

	public int getWrk_pkg() {
		return wrk_pkg_id;
	}

	public void setWrk_pkg(int wrk_pkg_id) {
		this.wrk_pkg_id = wrk_pkg_id;
	}

}
