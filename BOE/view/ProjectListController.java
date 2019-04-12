package BOE.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import com.google.common.eventbus.EventBus;

import BOE.boe_tool;
import BOE.events.ProjectChangeEvent;
import BOE.util.db_import;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProjectListController {

	private db_import db = new db_import();
	private ResultSet result;
	private ArrayList<Project> list = new ArrayList<Project>();
	private ObservableList<Project> data;

	@FXML private TableView<Project> project_list;
	@FXML private TableColumn<Project, Integer> idCol;
	@FXML private TableColumn<Project, String> nameCol;
	@FXML private Button loadButton;


	@FXML
	private void initialize() {
		idCol.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
		
		data = FXCollections.observableArrayList( parseProjectList() );
		
		project_list.getItems().setAll(data);
	}
	
	@FXML
	private void getProjectFromList() {
		int project_id = project_list.getSelectionModel().getSelectedItem().getId();
		
		//sends Project ID to EventBus
		boe_tool.eventBus.post(new ProjectChangeEvent(project_id));
		
		System.out.println(project_id);
	}
	
 	private ArrayList<Project> parseProjectList() {
		db.db_open();

		try {
			result = db.query("SELECT project_id, project_name FROM project");

			while(result.next()) {
				list.add( new Project(result.getInt(1), result.getString(2)) );
			}
			Collections.reverse(list);
			
			db.db_close();
			return list;
		} catch (SQLException e) {
			db.db_close();
			System.out.println(e.getMessage());
			return list;
		}
	}

	public class Project {
		private Integer id;
		private String name;

		private Project(int idIn, String nameIn) {
			id = idIn;
			name = nameIn;
		}
		
		public Integer getId() {
			return id;
		}
		
		public void setId(Integer idIn) {
			id = idIn; 
		}
		
		public String getName() {
			return name;
		}
		public void setName(String nameIn) {
			name = nameIn;
		}
		
		public String toString() {
			return id + " " + name;
		}
	}
}
