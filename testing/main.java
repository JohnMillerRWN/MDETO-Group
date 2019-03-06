package testing;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
	
	protected static User current_user;
	
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        Scene scene = new Scene(root);
        String css = "LoginStyle.css";
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        
        stage.show();
    }
    
	public static void main(String[] args) {
		launch(args);
	}
}
