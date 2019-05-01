package BOE.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.google.common.eventbus.Subscribe;
import BOE.boe_tool;
import BOE.events.CLINChangeEvent;
import BOE.events.ProjectChangeEvent;
import BOE.events.Subscriber;
import BOE.util.db_import;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ProjectSummaryController implements Subscriber {

	private int curr_proj = boe_tool.shared.getProject();

	private db_import db = new db_import();
	private ResultSet result;
	private DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private ArrayList<CLINTable> list = new ArrayList<CLINTable>();
	private ObservableList<CLINTable> data = FXCollections.observableArrayList();

	@FXML private TableView<CLINTable> clinTable;
	@FXML private TableColumn<CLINTable, Integer> clinCol;
	@FXML private TableColumn<CLINTable, String> descCol;
	@FXML private TableColumn<CLINTable, String> startCol;
	@FXML private TableColumn<CLINTable, String> endCol;
	
	@FXML private TextField prjName, propNumber, prjManager;
	@FXML private Label pop;
	@FXML private DatePicker startDate, endDate;
	@FXML private TextArea prjDesc;
	
	public void initialize() {
		
		//loads current project if there is any
		if (curr_proj>0) {
			setProject(curr_proj);
		}
		
		//registers class with EventBus for listener
		boe_tool.eventBus.register(this);

		clinListDoubleClick();
	}
	
	private void setProject(int id) {
		db.db_open();

		try {
			result = db.query("SELECT * FROM project_view where project_id = " + id);

			result.next();
			prjName.setText(result.getString(2));
			prjManager.setText(result.getString(7));
			
			startDate.setValue( LocalDate.parse(result.getString(3), dateformatter) );
			endDate.setValue( LocalDate.parse(result.getString(4), dateformatter) );
			
			pop.setText( "PoP: " + Integer.toString( result.getInt(6) ) + 'd' );
			prjDesc.setText(result.getString(8));
			prjDesc.setWrapText(true);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}
		
		//sets current project variables locally and in shared resources
		curr_proj = id;
		boe_tool.shared.setProject(id);
		
		clearCLINTable();
		loadCLINTable();
	}
	
	private void clearCLINTable() {
		clinTable.getItems().clear();
	}
	
	private void loadCLINTable() {
		clinCol.setCellValueFactory(new PropertyValueFactory<CLINTable, Integer>("clinNum"));
		clinCol.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		descCol.setCellValueFactory(new PropertyValueFactory<CLINTable, String>("desc"));
		startCol.setCellValueFactory(new PropertyValueFactory<CLINTable, String>("startDate"));
		endCol.setCellValueFactory(new PropertyValueFactory<CLINTable, String>("endDate"));
		
		data = FXCollections.observableArrayList( parseCLINList() );
		
		clinTable.getItems().setAll(data);
	}
	
	private ArrayList<CLINTable> parseCLINList() {
		list.clear();
		db.db_open();

		try {
			result = db.query("SELECT clin_id, clin_num, short_desc, start_date, end_date FROM clin WHERE project_id = " + 	curr_proj);

			while(result.next()) {
				list.add( new CLINTable(result.getInt(1), result.getInt(2), result.getString(3), result.getString(4), result.getString(5)) );
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}
		return list;
	}
		
	@FXML
	private void getCLINFromList() {
		CLINTable clin = clinTable.getSelectionModel().getSelectedItem();
		
		if (clin==null) {
			noCLINAlert();
			return;
		}
		
		int clin_id = clin.getId();
		int clin_num = clin.getClinNum();
		String clin_desc = clin.getDesc();
		
		//adds CLIN ID to SharedResources
		boe_tool.shared.setCLIN(clin_id);

		//sends Project ID to EventBus
		boe_tool.eventBus.post(new CLINChangeEvent(clin_id, clin_num, clin_desc));
	}
	
 	private void noCLINAlert() {
 		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select a CLIN from the list.");
 
        alert.showAndWait();
 	}
	
 	private void clinListDoubleClick() {
		//adds doubleclick listener for list
 		clinTable.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	getCLINFromList();          
		        }
		    }
		});
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
		private Integer id, clinNum;
		private String desc, startDate, endDate;
		
		public CLINTable(Integer id, Integer clinNum, String desc, String startDate, String endDate) {
			this.id = id;
			this.clinNum = clinNum;
			this.desc = desc;
			this.startDate = startDate;
			this.endDate = endDate;
		}
		
		public Integer getId() {
			return id;
		}
		
		public void setId(Integer id) {
			this.id = id;
		}
		
		public Integer getClinNum() {
			return clinNum;
		}

		public void setClinNum(Integer clin) {
			this.clinNum = clin;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String name) {
			this.desc = name;
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
