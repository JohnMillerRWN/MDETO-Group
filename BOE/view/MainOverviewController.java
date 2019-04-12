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

	public void setSummary()  {		
		try {
			AnchorPane pane = FXMLLoader.load(boe_tool.class.getResource("view/projectSummary.fxml"));
			System.out.println("Project Summary Window");
			switchPane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setReportSummary() {
		try {
			AnchorPane pane = FXMLLoader.load(boe_tool.class.getResource("view/projectSummary.fxml"));
			System.out.println("Summary Window");
			switchPane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setManagement() {
		try {
			AnchorPane pane = FXMLLoader.load(boe_tool.class.getResource("view/managementSummary.fxml"));
			System.out.println("Management Summary Window");
			switchPane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSPYPrograms() {
		try {
			AnchorPane pane = FXMLLoader.load(boe_tool.class.getResource("view/productSummary.fxml"));
			System.out.println("SPY Window");
			switchPane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSDRLs() {
		try {
			AnchorPane pane = FXMLLoader.load(boe_tool.class.getResource("view/SDRLControl.fxml"));
			System.out.println("SDRL Window");
			switchPane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSOWs() {
		try {
			AnchorPane pane = FXMLLoader.load(boe_tool.class.getResource("view/SOWRefControl.fxml"));
			System.out.println("SOW Window");
			switchPane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setProjectTbl() {
		try {
			AnchorPane pane = FXMLLoader.load(boe_tool.class.getResource("view/projectList.fxml"));
			System.out.println("Project Table Window");
			listPane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}