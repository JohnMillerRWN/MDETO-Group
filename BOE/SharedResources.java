package BOE;

import java.sql.ResultSet;
import java.util.ArrayList;
import BOE.model.Project_BOE;
import BOE.util.db_import;

public class SharedResources {

	private User current_user;
	private ArrayList<Project_BOE> project_list = new ArrayList<Project_BOE>();
	private db_import db = new db_import();
	private ResultSet result;

	public void setUser(User c_user) {
		this.current_user = c_user;
	}

	public User getUser() {
		return current_user;
	}

	public boolean fillDBProjectList() {
		boolean listFilled = false;
		db.db_open();
		
		try {
			result = db.query("SELECT * FROM project_view");
			
			while( result.next() ) {
				project_list.add(  
						new Project_BOE( result.getInt(1), result.getString(2), 
										 result.getDate(3), result.getDate(4), 
										 result.getTimestamp(5), result.getString(6), 
										 result.getString(7))
						);
			}
			listFilled = true;
		} catch (Exception e) {
			System.out.println(e.getMessage()); 			
			db.db_close();
		}
		finally {
			db.db_close();
		}
		return listFilled;
	}
	
	public ArrayList<Project_BOE> getProjectList() {
		return this.project_list;
	}
}
