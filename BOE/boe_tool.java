package BOE;

import java.io.IOException;

import BOE.model.*;
import BOE.view.projectOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;




/**
 * Constructor
 */
public class boe_tool extends Application {

	private Stage primaryStage;
	private VBox rootLayout;
	
	private Project_BOE project;
	
	private projectOverviewController controller;
	public static void main(String[] args) {
		launch(args);
	}

	@Override public void start(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("BOE Generator Tool");
        System.out.println("test");
        initRootLayout();
        showProjectLayout();
        
	}
	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
	        // Load root layout from fxml file.
	        FXMLLoader loader = new FXMLLoader();
	        System.out.println(loader);
	        loader.setLocation(boe_tool.class.getResource("view\\projectOverview.fxml"));
	        try {
				rootLayout = (VBox) loader.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        controller = loader.getController();
	        
	        // Show the scene containing the root layout.
	        Scene scene = new Scene(rootLayout);
	        primaryStage.setScene(scene);
	        primaryStage.show();
	 //       primaryStage.getScene().getRoot().se

	}
	
	public void showProjectLayout() {
        controller.setMainApp(this);
        controller.setSummary();
}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
}