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
		setSummary();
		setProjectTbl();
	}

	@FXML
	private void closeBtnControl() {
		System.exit(1);
	}

	public void setSummary() {
		// Load PM layout from fxml file.
		FXMLLoader loader = new FXMLLoader();
		
		System.out.println("Project Summary Window");
		
		loader.setLocation( boe_tool.class.getResource("view/projectSummary.fxml") );
		AnchorPane rootLayout = null;
		try {
			rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switchPane.getChildren().setAll(rootLayout);
	}

	public void setReportSummary() {
		// Load PM layout from fxml file.
		FXMLLoader loader = new FXMLLoader();
		
		System.out.println("Summary Window");
		
		loader.setLocation( boe_tool.class.getResource("view/projectSummary.fxml") );
		AnchorPane rootLayout = null;
		try {
			rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switchPane.getChildren().setAll(rootLayout);
	}

	public void setManagement() {
		FXMLLoader loader = new FXMLLoader();
		
		System.out.println("Management Summary Window");
		
		loader.setLocation( boe_tool.class.getResource("view/managementSummary.fxml") );
		AnchorPane rootLayout = null;
		try {
			rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switchPane.getChildren().setAll(rootLayout);
	}

	public void setSPYPrograms() {
		FXMLLoader loader = new FXMLLoader();
		
		System.out.println("SPY Window");
		
		loader.setLocation( boe_tool.class.getResource("view/productSummary.fxml") );
		AnchorPane rootLayout = null;
		try {
			rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switchPane.getChildren().setAll(rootLayout);
	}

	public void setSDRLs() {
		FXMLLoader loader = new FXMLLoader();
		
		System.out.println("SDRL Window");

		loader.setLocation( boe_tool.class.getResource("view/SDRLControl.fxml") );
		AnchorPane rootLayout = null;
		try {
			rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switchPane.getChildren().setAll(rootLayout);
	}

	public void setSOWs() {
		FXMLLoader loader = new FXMLLoader();
		
		System.out.println("SOW Window");

		loader.setLocation( boe_tool.class.getResource("view/SOWRefControl.fxml") );
		AnchorPane rootLayout = null;
		try {
			rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switchPane.getChildren().setAll(rootLayout);
	}
	
	public void setProjectTbl() {
		FXMLLoader loader = new FXMLLoader();
		
		System.out.println("Project Table Window");

		loader.setLocation( boe_tool.class.getResource("view/projectList.fxml") );
		AnchorPane rootLayout = null;
		try {
			rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		listPane.getChildren().setAll(rootLayout);
		
	}
}