package BOE.events;

public class ProjectChangeEvent implements Postable{
	
	private int project_id;
	private String project_name;
	
	public ProjectChangeEvent(int project_id, String project_name) {
        this.project_id = project_id;
        this.setProject_name(project_name);
    }

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
}
