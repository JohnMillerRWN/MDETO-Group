package testing;

public class User {
	private String user_name;
	private String first_name;
	private String last_name;
	private String full_name;
	private boolean is_project_manager;
	private String company;

	public User() {
		setUser_name("test");
		first_name = "First";
		last_name = "Last";
		full_name = last_name + ", " + first_name;
		is_project_manager = false;
	}

	public User(String uname, String fname, String lname, boolean pm) {
		setUser_name(uname);
		first_name = fname;
		last_name = lname;
		full_name = last_name + ", " + first_name;
		is_project_manager = pm;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public boolean is_project_manager() {
		return is_project_manager;
	}

	public void set_project_manager(boolean is_project_manager) {
		this.is_project_manager = is_project_manager;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
}
