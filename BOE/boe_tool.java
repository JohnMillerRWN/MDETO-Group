package BOE;

import BOE.events.MyEventBus;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class boe_tool extends Application {
	
	public static SharedResources shared = new SharedResources();
	public static MyEventBus eventBus = new MyEventBus();
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("view/0_Login.fxml"));
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("Metrics Database Estimation Tool");

		stage.show();	
	}

	public static void main() {
		launch();
	}
}
