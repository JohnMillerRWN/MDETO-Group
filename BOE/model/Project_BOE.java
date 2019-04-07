package BOE.model;

import java.util.*;

public class Project_BOE {
	private int project_id;
	private String project_name;
    private Date start_date, end_date, created_date;
    private int project_manager;
    private String short_desc;

	public Project_BOE(int pid, String p_name, Date sdate, Date edate, Date cdate, int pm, String desc) {
		this.project_id = pid;
		this.project_name = p_name;
		this.start_date = sdate;
		this.end_date = edate;
		this.created_date = cdate;
		this.project_manager = pm;
		this.short_desc = desc;
	}

	public String Project;

	public String CLIN;

	public String PM;

	public int Prop_Number;

	private Org_BOE[] org_boe;

	private SDRLs[] sdrl;

	private SOW_Ref[] sow_ref;

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

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public int getProject_manager() {
		return project_manager;
	}

	public void setProject_manager(int project_manager) {
		this.project_manager = project_manager;
	}

	public String getShort_desc() {
		return short_desc;
	}

	public void setShort_desc(String short_desc) {
		this.short_desc = short_desc;
	}
}