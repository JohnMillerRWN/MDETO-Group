package app;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private Label invalid_label;

	@FXML
	private TextField username_box;

	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException {
		if(validCredentials()) {
			//System.out.println("accepted");
			Parent home_page_parent = FXMLLoader.load(getClass().getResource("Homepage.fxml"));

			Scene home_page_scene = new Scene(home_page_parent);
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			
			app_stage.hide();
			app_stage.setScene(home_page_scene);
			app_stage.show();
		}
		else {
			//System.out.println("rejected");
			username_box.clear();
			invalid_label.setText("Sorry, invalid credentials"); 
		}

	}

	private boolean validCredentials() {
		ArrayList<User> userList = new ArrayList<User>();
		userList.add( new User("estimator", "Estimator", "", false) );
		userList.add( new User("pm", "Project", "Manager", true) );

		for(User user: userList) {
			if(user.getUser_name().contentEquals(username_box.getText())) {
				Main.shared.setUser(user);
				return true;
			}
		}

		return false;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}
}
