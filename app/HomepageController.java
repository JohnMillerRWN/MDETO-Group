package app;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomepageController implements Initializable {
	
	@FXML
	private Label details_Label;
	
	@FXML
	private void det_action(ActionEvent event) throws IOException {
		details_Label.setText( "Testing" );
	}
	
	@FXML
	private void menuQuit(ActionEvent event) throws IOException {
		System.exit(1);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		details_Label.setText( Main.shared.getUser().getFull_name() );
	}

}
