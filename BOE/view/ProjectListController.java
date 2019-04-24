package BOE.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import BOE.SharedResources;
import BOE.boe_tool;
import BOE.events.ProjectChangeEvent;
import BOE.util.db_import;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProjectListController {
	
	private SharedResources shared = boe_tool.shared;

	private db_import db = new db_import();
	private ResultSet result;
	private ArrayList<ProjectTable> list = new ArrayList<ProjectTable>();
	private ObservableList<ProjectTable> data = FXCollections.observableArrayList();

	@FXML private TableView<ProjectTable> project_list;
	@FXML private TableColumn<ProjectTable, Integer> idCol;
	@FXML private TableColumn<ProjectTable, String> nameCol;
	@FXML private TableColumn<ProjectTable, String> sdateCol;
	@FXML private TableColumn<ProjectTable, String> edateCol;
	@FXML private TableColumn<ProjectTable, String> cdateCol;
	@FXML private TableColumn<ProjectTable, String> projectManagerCol;
	
	@FXML private Button loadProject;


	@FXML
	private void initialize() {
		loadProjectList();
		listDoubleClick();
	}
	
	@FXML
	private void getProjectFromList() {
		ProjectTable project = project_list.getSelectionModel().getSelectedItem();
		
		if (project==null) {
			noProjectAlert();
			shared.setWindow_title("");
			return;
		}
		
		int project_id = project.getId();
		String project_name = project.getName();
		shared.setWindow_title("| " + project.getName());
		
		//adds Project ID to SharedResources
		shared.setProject(project_id);
		//sends Project ID to EventBus
		boe_tool.eventBus.post(new ProjectChangeEvent(project_id, project_name));
	}
	
	private void loadProjectList() {
		idCol.setCellValueFactory(new PropertyValueFactory<ProjectTable, Integer>("id"));
		idCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		nameCol.setCellValueFactory(new PropertyValueFactory<ProjectTable, String>("name"));
		sdateCol.setCellValueFactory(new PropertyValueFactory<ProjectTable, String>("startDate"));
		edateCol.setCellValueFactory(new PropertyValueFactory<ProjectTable, String>("endDate"));
		cdateCol.setCellValueFactory(new PropertyValueFactory<ProjectTable, String>("createdDate"));
		projectManagerCol.setCellValueFactory(new PropertyValueFactory<ProjectTable, String>("projectManager"));
		
		data = FXCollections.observableArrayList( parseProjectList() );
		
		project_list.getItems().setAll(data);
	}
	
 	private ArrayList<ProjectTable> parseProjectList() {
 		list.clear();
		db.db_open();

		try {
			result = db.query("SELECT project_id, project_name, start_date, end_date, created_date, project_manager FROM project_view ORDER BY project_id DESC");

			while(result.next()) {
				list.add( new ProjectTable(result.getInt(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6)) );
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}
		return list;
	}
 	
 	private void noProjectAlert() {
 		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select a Project from the list.");
 
        alert.showAndWait();
 	}
 	
 	private void listDoubleClick() {
		//adds doubleclick listener for list
		project_list.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	getProjectFromList();          
		        }
		    }
		});
 	}
 	
	public class ProjectTable {
		private Integer id;
		private String name, startDate, endDate, createdDate, projectManager;

		private ProjectTable(int id, String name, String startDate, String endDate, String createdDate, String projectManager) {
			this.id = id;
			this.name = name;
			this.startDate = startDate;
			this.endDate = endDate;
			this.createdDate = createdDate;
			this.projectManager = projectManager;
		}
		
		public Integer getId() {
			return id;
		}
		
		public void setId(Integer id) {
			this.id = id; 
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public String getStartDate() {
			return startDate;
		}

		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}

		public String getEndDate() {
			return endDate;
		}

		public void setEndDate(String endDate) {
			this.endDate = endDate;
		}

		public String getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}

		public String getProjectManager() {
			return projectManager;
		}

		public void setProjectManager(String projectManager) {
			this.projectManager = projectManager;
		}

		public String toString() {
			return id + " " + name;
		}
	}
}
