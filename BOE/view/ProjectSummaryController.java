package BOE.view;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.google.common.eventbus.Subscribe;
import BOE.boe_tool;
import BOE.events.ProjectChangeEvent;
import BOE.events.Subscriber;
import BOE.util.db_import;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProjectSummaryController implements Subscriber {
	
	private db_import db = new db_import();
	private ResultSet result;
	private DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private int curr_proj = 0;
	
	private ArrayList<CLINTable> list = new ArrayList<CLINTable>();
	private ObservableList<CLINTable> data = FXCollections.observableArrayList();

	@FXML private TableView<CLINTable> productTable;
	@FXML private TableColumn<CLINTable, String> clinCol;
	@FXML private TableColumn<CLINTable, String> startCol;
	@FXML private TableColumn<CLINTable, String> endCol;
	
	@FXML private TextField prjName, propNumber, prjManager;
	@FXML private Label pop;
	@FXML private DatePicker startDate, endDate;
	@FXML private TextArea prjDesc;
	
	public void initialize() {
		//registers class with EventBus for listener
		boe_tool.eventBus.register(this);
	}
	
	private void setProject(int id) {
		db.db_open();

		try {
			result = db.query("SELECT * FROM project_view where project_id = " + id);

			while(result.next()) {
				prjName.setText(result.getString(2));
				prjManager.setText(result.getString(7));
				startDate.setValue( LocalDate.parse(result.getString(3), dateformatter) );
				endDate.setValue( LocalDate.parse(result.getString(4), dateformatter) );
				pop.setText( "PoP: " + Integer.toString( result.getInt(6) ) + 'd' );
				prjDesc.setText(result.getString(8));
				prjDesc.setWrapText(true);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}
		
		curr_proj = id;
		
		clearCLINTable();
		loadCLINTable();
	}
	
	private void clearCLINTable() {
		productTable.getItems().clear();;
	}
	
	private void loadCLINTable() {
		clinCol.setCellValueFactory(new PropertyValueFactory<CLINTable, String>("name"));
		startCol.setCellValueFactory(new PropertyValueFactory<CLINTable, String>("startDate"));
		endCol.setCellValueFactory(new PropertyValueFactory<CLINTable, String>("endDate"));
		
		data = FXCollections.observableArrayList( parseCLINList() );
		
		productTable.getItems().setAll(data);
	}
	
	private ArrayList<CLINTable> parseCLINList() {
		list.clear();
		db.db_open();

		try {
			result = db.query("SELECT clin_name, start_date, end_date FROM clin WHERE project_id = " + 	curr_proj);

			while(result.next()) {
				list.add( new CLINTable(result.getString(1), result.getString(2), result.getString(3)) );
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}
		return list;
	}
	
	/**
	 * Listener for EventBus. When triggered the Project will be reloaded to the id provided
	 * @param event requires a ProjectChangeEvent
	 */
	@Subscribe
	public void reloadProject(ProjectChangeEvent event) {
		setProject(event.getProject_id());
	}
	
	public class CLINTable {
		private String name, startDate, endDate;
		
		public CLINTable(String name, String startDate, String endDate) {
			this.name = name;
			this.startDate = startDate;
			this.endDate = endDate;
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
	}
}
