package BOE;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class boe_tool extends Application {
	
	public static SharedResources shared = new SharedResources();
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("view/0_login.fxml"));

		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("BOE Tool");

		stage.show();
	}

	public static void main() {
		launch();
	}
}
