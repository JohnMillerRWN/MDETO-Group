package BOE.events;

public class ProjectChangeEvent implements Postable{
	
	private int project_id;
	
	public ProjectChangeEvent(int project_id) {
        this.setProject_id(project_id);
    }

	public int getProject_id() {
		return project_id;
	}

	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
}
