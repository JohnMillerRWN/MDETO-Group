package BOE.view;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.common.eventbus.Subscribe;
import BOE.boe_tool;
import BOE.events.CLINChangeEvent;
import BOE.events.ProductChangeEvent;
import BOE.events.ProjectChangeEvent;
import BOE.events.Subscriber;
import BOE.util.db_import;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;

public class MainOverviewController implements Subscriber {
	
	private db_import db = new db_import();
	private ResultSet result;

	@FXML Label userLabel, prjLabel, clinLabel;
	@FXML AnchorPane switchPane;
	@FXML Accordion BOEA;
	@FXML VBox productVBox;
	@FXML TitledPane project, clin, product, management, reports;
	
	private ArrayList<Label> list = new ArrayList<Label>();
	
	@FXML
	private void initialize() {
		//registers class with EventBus for listener
		boe_tool.eventBus.register(this);
		
		//set current user !!Temporary!!
		userLabel.setText( boe_tool.shared.getUser().getFull_name() );
		
		//sets the project pane to be opened on startup
		expandMenu(project);
		setProjectList();
	}

	@FXML
	private void closeBtnControl() {
		System.exit(1);
	}
	
	public void setProjectList() {
		switchPaneView("view/projectList.fxml");
	}

	public void setProjectSummary()  {		
		switchPaneView("view/projectSummary.fxml");
	}
	
	public void setCLINSummary() {
		switchPaneView("view/CLINSummary.fxml");
	}
	
	public void setProductSummary() {
		switchPaneView("view/productSummary.fxml");
	}
	
	public void setManagement() {
		switchPaneView("view/managementSummary.fxml");
	}

	public void setSPYPrograms() {
		switchPaneView("view/productSummary.fxml");
	}

	public void setSDRLs() {
		switchPaneView("view/SDRLControl.fxml");
	}

	public void setSOWs() {
		switchPaneView("view/SOWRefControl.fxml");
	}
	
	public void switchPaneView(String resource_path) {
		try {
			AnchorPane pane = FXMLLoader.load(boe_tool.class.getResource(resource_path));
			switchPane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Queries the org table to populate the Product Accordion Menu
	 * @param clin_id id of the clin to query the org table
	 */
	private void getOrgs(int clin_id) {
		db.db_open();

		try {
			result = db.query("SELECT org_id, org_name, detailed_org FROM org WHERE clin_id = " + clin_id);

			while(result.next()) {
				Label product_name = new Label(result.getString(2));
				int product_id = result.getInt(1);
				list.add( product_name );
				
				//add listeners for labels
				product_name.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 1) {
			        	boe_tool.shared.setProduct(product_id);
			        	setProductSummary();
			        }
				});

				//adds the label to the Product VBox
				productVBox.getChildren().add( product_name );
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}
		
		//clears list
		list.clear();
	}
	
	private void clearOrgs() {
		productVBox.getChildren().clear();
	}
	
	private void expandMenu(TitledPane p) {
		BOEA.setExpandedPane(p);
	}
	
	/**
	 * Listener for EventBus. When triggered the Project will be reloaded to the id provided
	 * @param event requires a ProjectChangeEvent
	 */
	@Subscribe
	public void loadProject(ProjectChangeEvent event) {
		prjLabel.setText(event.getProject_name());
		clinLabel.setText("CLIN");
		
		boe_tool.shared.setCLIN(-1);
		boe_tool.shared.setProduct(-1);
		
		clearOrgs();
		
		setProjectSummary();
	}
	
	/**
	 * Listener for EventBus. When triggered the CLIN will be reloaded to the id provided
	 * @param event requires a CLINChangeEvent
	 */
	@Subscribe
	public void loadCLIN(CLINChangeEvent event) {
		int clin_num = event.getClin_num();
		
		clinLabel.setText( Integer.toString(clin_num) );
		boe_tool.shared.setCLIN(clin_num);
		
		clearOrgs();
		getOrgs(clin_num);
		expandMenu(clin);
		setCLINSummary();
	}
	
	@Subscribe
	public void loadProduct(ProductChangeEvent event) {
		int product_num = event.getProduct_id();
		
		boe_tool.shared.setProduct(product_num);
		expandMenu(product);
		setProductSummary();
	}
}