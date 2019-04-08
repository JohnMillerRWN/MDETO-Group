package BOE.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import BOE.util.db_import;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class projectListController {

	private db_import db = new db_import();
	private ResultSet result;
	private ArrayList<Project> list = new ArrayList<Project>();
	private ObservableList<Project> data;

	@FXML private TableView<Project> project_list;
	@FXML private TableColumn<Project, String> idCol;
	@FXML private TableColumn<Project, String> nameCol;


	@FXML
	private void initialize() {
		idCol.setCellValueFactory(new PropertyValueFactory<Project, String>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Project, String>("name"));
		
		data = FXCollections.observableArrayList( parseProjectList() );
		
		project_list.getItems().setAll(data);
	}

	private ArrayList<Project> parseProjectList() {
		db.db_open();

		try {
			result = db.query("SELECT project_id, project_name FROM project");

			while(result.next()) {
				list.add( new Project(result.getString(1), result.getString(2)) );
			}
			
			db.db_close();
			return list;
		} catch (SQLException e) {
			db.db_close();
			System.out.println(e.getMessage());
			return list;
		}
	}

	public class Project {
		private SimpleStringProperty id;
		private SimpleStringProperty name;

		private Project(String idIn, String nameIn) {
			id = new SimpleStringProperty(idIn);
			name = new SimpleStringProperty(nameIn);
		}
		
		public String getId() {
			return id.get();
		}
		
		public void setId(String idIn) {
			id.set(idIn);
		}
		
		public String getName() {
			return name.get();
		}
		public void setName(String nameIn) {
			name.set(nameIn);
		}
		
		public String toString() {
			return id + " " + name;
		}
	}
}
