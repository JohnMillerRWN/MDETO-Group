package BOE.view;

import java.io.IOException;
import java.sql.ResultSet;
import com.google.common.eventbus.Subscribe;
import BOE.boe_tool;
import BOE.events.CLINChangeEvent;
import BOE.events.ProjectChangeEvent;
import BOE.events.Subscriber;
import BOE.util.db_import;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;

public class MainOverviewController implements Subscriber {
	
	private db_import db = new db_import();
	private ResultSet result;

	@FXML Label userLabel, prjLabel, clinLabel;
	@FXML AnchorPane switchPane;
	@FXML Accordion BOEA, productAccordion;
	@FXML TitledPane project, product, management, reports;	

	@FXML
	private void initialize() {
		//registers class with EventBus for listener
		boe_tool.eventBus.register(this);
		
		//set current user !!Temporary!!
		userLabel.setText( boe_tool.shared.getUser().getFull_name() );
		
		//sets the project pane to be opened on startup
		BOEA.setExpandedPane(project);
		setProjectSummary();
	}

	@FXML
	private void closeBtnControl() {
		System.exit(1);
	}

	public void setProjectSummary()  {		
		switchPaneView("view/projectSummary.fxml");
	}

	public void setReportSummary() {
		switchPaneView("view/projectSummary.fxml");
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
	
	public void addOrgs() {
		
	}
	
	
	/**
	 * Listener for EventBus. When triggered the Project will be reloaded to the id provided
	 * @param event requires a ProjectChangeEvent
	 */
	@Subscribe
	public void reloadProject(ProjectChangeEvent event) {
		prjLabel.setText(event.getProject_name());
		clinLabel.setText("CLIN");
	}
	
	/**
	 * Listener for EventBus. When triggered the CLIN will be reloaded to the id provided
	 * @param event requires a CLINChangeEvent
	 */
	@Subscribe
	public void reloadCLIN(CLINChangeEvent event) {
		clinLabel.setText( Integer.toString(event.getClin_num()) );
	}
}