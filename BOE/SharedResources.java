package BOE;

import java.sql.ResultSet;
import java.util.ArrayList;
import BOE.model.Project_BOE;
import BOE.util.db_import;

public class SharedResources {

	private User current_user;
	private int curr_project = -1;
	private int curr_clin = -1;
	
	private ArrayList<Project_BOE> project_list = new ArrayList<Project_BOE>();
	private db_import db = new db_import();
	private ResultSet result;

	public void setUser(User c_user) {
		this.current_user = c_user;
	}

	public User getUser() {
		return current_user;
	}

	public int getProject() {
		return curr_project;
	}

	public void setProject(int curr_project) {
		this.curr_project = curr_project;
	}

	public int getCLIN() {
		return curr_clin;
	}

	public void setCLIN(int curr_clin) {
		this.curr_clin = curr_clin;
	}

}
