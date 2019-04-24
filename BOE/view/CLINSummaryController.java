package BOE.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import BOE.boe_tool;
import BOE.events.ProductChangeEvent;
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

public class CLINSummaryController {
	
	private int curr_clin = boe_tool.shared.getCLIN();
	
	private db_import db = new db_import();
	private ResultSet result;
	private DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private ArrayList<ProductTable> list = new ArrayList<ProductTable>();
	private ObservableList<ProductTable> data = FXCollections.observableArrayList();

	@FXML private TableView<ProductTable> productTable;
	@FXML private TableColumn<ProductTable, Integer> productID;
	@FXML private TableColumn<ProductTable, String> productName;
	@FXML private TableColumn<ProductTable, String> productDetails;

	@FXML private TextField clinNumber;
	@FXML private Label pop;
	@FXML private DatePicker startDate, endDate;
	@FXML private TextArea clinDesc;

	public void initialize() {
		clearProductTable();
		//loads current project if there is any
		if (curr_clin>0) {
			setCLIN(curr_clin);
		} else 

		productListDoubleClick();
	}

	
	private void setCLIN(int id) {
		db.db_open();

		try {
			result = db.query("SELECT * FROM clin WHERE project_id = " + id);

			while(result.next()) {
				clinNumber.setText(result.getString(2));
				
				startDate.setValue( LocalDate.parse(result.getString(3), dateformatter) );
				endDate.setValue( LocalDate.parse(result.getString(4), dateformatter) );
				pop.setText( "PoP: " + Integer.toString( result.getInt(6) ) + 'd' );
				
				clinDesc.setText(result.getString(9));
				clinDesc.setWrapText(true);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}
		
		//sets current project variables locally and in shared resources
		curr_clin = id;
		boe_tool.shared.setCLIN(id);;
		
		clearProductTable();
		loadProductTable();
	}
	
	private void clearProductTable() {
		productTable.getItems().clear();
	}
	
	private void loadProductTable() {
		productID.setCellValueFactory(new PropertyValueFactory<ProductTable, Integer>("id"));
		productID.setStyle("-fx-alignment: CENTER-RIGHT;");
		
		productName.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("name"));
		productDetails.setCellValueFactory(new PropertyValueFactory<ProductTable, String>("details"));
		
		data = FXCollections.observableArrayList( parseORGList() );
		
		productTable.getItems().setAll(data);
	}
	
	private ArrayList<ProductTable> parseORGList() {
		list.clear();
		db.db_open();

		try {
			result = db.query("SELECT org_id, org_name, detailed_org FROM org WHERE clin_id =" + curr_clin);

			while(result.next()) {
				list.add( new ProductTable(result.getInt(1), result.getString(2), result.getString(3)) );
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}
		return list;
	}
	
	@FXML
	private void getProductFromList() {
		ProductTable product = productTable.getSelectionModel().getSelectedItem();
		
		if (product==null) {
			noProductAlert();
			return;
		}
		
		int product_id = product.getId();
		String product_name = product.getName();
		
		//adds Product ID to SharedResources
		boe_tool.shared.setProduct(product_id);

		//sends Project ID to EventBus
		boe_tool.eventBus.post(new ProductChangeEvent(product_id, product_name));
	}
	
 	private void noProductAlert() {
 		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select a Product from the list.");
 
        alert.showAndWait();
 	}
	
 	private void productListDoubleClick() {
		//adds doubleclick listener for list
 		productTable.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	getProductFromList();          
		        }
		    }
		});
 	}
	
	public class ProductTable {
		private Integer id;
		private String name, details;

		public ProductTable(Integer id, String name, String details) {
			this.setId(id);
			this.setName(name);
			this.setDetails(details);
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

		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}
	}
}
