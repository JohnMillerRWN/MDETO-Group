package BOE.view;

import java.io.IOException;
import BOE.boe_tool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;

public class projectOverviewController {

	@FXML
	Label user_Label;
	@FXML
	AnchorPane switchPane;
	@FXML
	Accordion BOEA;
	@FXML
	TitledPane project, product, radar, dm, management, reports;	

	@FXML
	private void initialize() {
		user_Label.setText( boe_tool.shared.getUser().getFull_name() );
		BOEA.setExpandedPane(project);
		setSummary();
	}

	@FXML
	private void closeBtnControl() {
		System.exit(1);
	}

	public void setSummary() {
		// Load PM layout from fxml file.
		FXMLLoader loader = new FXMLLoader();
		System.out.print("Project Summary Window : ");
		System.out.println(loader);
		loader.setLocation( boe_tool.class.getResource("view/pmMenu.fxml") );
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
		System.out.print("Summary Window : ");
		System.out.println(loader);
		loader.setLocation( boe_tool.class.getResource("view/pmMenu.fxml") );
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
		System.out.print("Management Summary Window : ");
		System.out.println(loader);
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
		System.out.print("SPY Window : ");
		System.out.println(loader);
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
		System.out.print("SDRL Window : ");
		System.out.println(loader);
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
		System.out.print("SOW Window : ");
		System.out.println(loader);
		loader.setLocation( boe_tool.class.getResource("view/SOWRefControl.fxml") );
		AnchorPane rootLayout = null;
		try {
			rootLayout = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switchPane.getChildren().setAll(rootLayout);
	}
}