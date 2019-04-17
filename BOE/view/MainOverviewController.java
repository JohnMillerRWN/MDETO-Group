package BOE.view;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import com.google.common.eventbus.Subscribe;
import BOE.boe_tool;
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
	private int curr_proj = 0;

	@FXML Label userLabel, prjLabel;
	@FXML AnchorPane switchPane, listPane;
	@FXML Accordion BOEA;
	@FXML TitledPane project, product, radar, dm, management, reports;	

	@FXML
	private void initialize() {
		//registers class with EventBus for listener
		boe_tool.eventBus.register(this);
		
		userLabel.setText( boe_tool.shared.getUser().getFull_name() );
		BOEA.setExpandedPane(project);
		setProjectSummary();
		setProjectTbl();
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
	
	public void setProjectTbl() {
		listPaneView("view/projectList.fxml");		
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
	
	public void listPaneView(String resource_path) {
		try {
			AnchorPane pane = FXMLLoader.load(boe_tool.class.getResource(resource_path));
			listPane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setProject(int id) {
		db.db_open();

		try {
			result = db.query("SELECT project_name FROM project WHERE project_id = " + id);

			while(result.next()) {
				prjLabel.setText(result.getString(1));
			}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			db.db_close();
		}
	}
	
	/**
	 * Listener for EventBus. When triggered the Project will be reloaded to the id provided
	 * @param event requires a ProjectChangeEvent
	 */
	@Subscribe
	public void reloadProject(ProjectChangeEvent event) {
		setProject( event.getProject_id() );
	}
}