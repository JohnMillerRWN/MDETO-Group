package BOE.view;

import java.io.IOException;
import java.util.ArrayList;
import BOE.boe_tool;
import BOE.model.Project_BOE;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;

public class MainOverviewController {

	@FXML Label user_Label;
	@FXML AnchorPane switchPane, listPane;
	@FXML Accordion BOEA;
	@FXML TitledPane project, product, radar, dm, management, reports;	

	@FXML
	private void initialize() {
		user_Label.setText( boe_tool.shared.getUser().getFull_name() );
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
}