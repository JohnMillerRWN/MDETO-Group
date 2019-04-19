package BOE.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

	private db_import db = new db_import();
	private ResultSet result;
	private ArrayList<ProjectTable> list = new ArrayList<ProjectTable>();
	private ObservableList<ProjectTable> data = FXCollections.observableArrayList();

	@FXML private TableView<ProjectTable> project_list;
	@FXML private TableColumn<ProjectTable, Integer> idCol;
	@FXML private TableColumn<ProjectTable, String> nameCol;
	@FXML private Button loadButton;


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
			return;
		}
		
		int project_id = project.getId();
		String project_name = project.getName();
		
		//sends Project ID to EventBus
		boe_tool.eventBus.post(new ProjectChangeEvent(project_id, project_name));
		//adds Project ID to SharedResources
		boe_tool.shared.setProject(project_id);
	}
	
	private void loadProjectList() {
		idCol.setCellValueFactory(new PropertyValueFactory<ProjectTable, Integer>("id"));
		idCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		nameCol.setCellValueFactory(new PropertyValueFactory<ProjectTable, String>("name"));
		
		data = FXCollections.observableArrayList( parseProjectList() );
		
		project_list.getItems().setAll(data);
	}
	
 	private ArrayList<ProjectTable> parseProjectList() {
 		list.clear();
		db.db_open();

		try {
			result = db.query("SELECT project_id, project_name FROM project ORDER BY project_id DESC");

			while(result.next()) {
				list.add( new ProjectTable(result.getInt(1), result.getString(2)) );
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
		private String name;

		private ProjectTable(int id, String name) {
			this.id = id;
			this.name = name;
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
		
		public String toString() {
			return id + " " + name;
		}
	}
}
