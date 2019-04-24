package BOE.view;

import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import BOE.boe_tool;
import BOE.util.db_import;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProductSummaryController {

	private int curr_product = boe_tool.shared.getProduct();
	
	private db_import db = new db_import();
	private ResultSet result;
	private DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@FXML private Label productName, summary, productID;
	
	private ArrayList<WBSTable> list = new ArrayList<WBSTable>();
	private ObservableList<WBSTable> data = FXCollections.observableArrayList();

	@FXML private TableView<WBSTable> productTable;
	@FXML private TableColumn<WBSTable, Integer> wbsCol	;
	@FXML private TableColumn<WBSTable, String> taskCol;
	@FXML private TableColumn<WBSTable, String> typeCol;
	
	
	public class WBSTable {
		private Integer wbs;
	}
}
