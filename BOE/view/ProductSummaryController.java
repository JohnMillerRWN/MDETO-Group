package BOE.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import BOE.boe_tool;
import BOE.util.db_import;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductSummaryController {

	private int curr_product = boe_tool.shared.getProduct();
	
	private db_import db = new db_import();
	private ResultSet result;

	@FXML private Label productName, productSummary, productID;
	
	private ArrayList<WRK_PKG_Table> list = new ArrayList<WRK_PKG_Table>();
	private ObservableList<WRK_PKG_Table> data = FXCollections.observableArrayList();

	@FXML private TableView<WRK_PKG_Table> wrkTable;
	@FXML private TableColumn<WRK_PKG_Table, String> nameCol;
	@FXML private TableColumn<WRK_PKG_Table, String> descCol;
	@FXML private TableColumn<WRK_PKG_Table, String> sDateCol;
	@FXML private TableColumn<WRK_PKG_Table, String> eDateCol;
	@FXML private TableColumn<WRK_PKG_Table, String> authorCol;
	@FXML private TableColumn<WRK_PKG_Table, String> scopeCol;
	
	public void initialize() {
		//loads current product if there is any
		if (curr_product>0) {
			setProduct(curr_product);
		} 
	}
	
	private void setProduct(int id) {
		db.db_open();

		try {
			result = db.query("SELECT * FROM org WHERE org_id = " + id);

			result.next(); 
			
			productName.setText(result.getString(2));
			
			productSummary.setText(result.getString(5));
		
			productID.setText(result.getString(1));
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}

		//sets current project variables locally and in shared resources
		curr_product = id;
		boe_tool.shared.setProduct(id);
		
		clearWRK_PKGTable();
		loadWRK_PKGTable();
	}
	
	private void clearWRK_PKGTable() {
		wrkTable.getItems().clear();
	}
	
	private void loadWRK_PKGTable() {
		nameCol.setCellValueFactory(new PropertyValueFactory<WRK_PKG_Table, String>("name"));
		descCol.setCellValueFactory(new PropertyValueFactory<WRK_PKG_Table, String>("desc"));
		
		sDateCol.setCellValueFactory(new PropertyValueFactory<WRK_PKG_Table, String>("startDate"));
		eDateCol.setCellValueFactory(new PropertyValueFactory<WRK_PKG_Table, String>("endDate"));
		
		authorCol.setCellValueFactory(new PropertyValueFactory<WRK_PKG_Table, String>("author"));
		scopeCol.setCellValueFactory(new PropertyValueFactory<WRK_PKG_Table, String>("scope"));
		
		data = FXCollections.observableArrayList( parseWRK_PKGList() );
		
		wrkTable.getItems().setAll(data);
	}
	
	private ArrayList<WRK_PKG_Table> parseWRK_PKGList() {
		list.clear();
		db.db_open();

		try {
			result = db.query("SELECT * FROM wrk_pkg_view WHERE org_id = " + curr_product);

			while(result.next()) {
				list.add( new WRK_PKG_Table( result.getInt(1), result.getString(2), result.getString(9), result.getString(3), result.getString(4), 
						result.getString(12), result.getString(14)) );
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}
		return list;
	}
	
	public class WRK_PKG_Table {
		private Integer id;
		private String name, desc, startDate, endDate, author, scope;
		
		public WRK_PKG_Table(Integer id, String name, String desc, String startDate, String endDate, String author, String scope) {
			this.id = id;
			this.name = name;
			this.desc = desc;
			this.startDate = startDate;
			this.endDate = endDate;
			this.author = author;
			this.scope = scope;
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

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
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

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}
	}
}
