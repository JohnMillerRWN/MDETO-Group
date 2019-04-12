package BOE.view;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import BOE.boe_tool;
import BOE.events.ProjectChangeEvent;
import BOE.events.Subscriber;
import BOE.util.db_import;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ProjectSummaryController implements Initializable, Subscriber {
	
	private db_import db = new db_import();
	private ResultSet result;
	private DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


	@FXML private TextField prjName, propNumber, prjManager;
	@FXML private DatePicker startDate, endDate;
	@FXML private TextArea prjDesc;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		boe_tool.eventBus.register(this);
		setProject(4);
	}
	
	private void setProject(int id) {
		db.db_open();

		try {
			result = db.query("SELECT * FROM project_view where project_id = " + id);

			while(result.next()) {
				prjName.setText(result.getString(2));
				prjManager.setText(result.getString(6));
				startDate.setValue( LocalDate.parse(result.getString(3), dateformatter) );
				endDate.setValue( LocalDate.parse(result.getString(4), dateformatter) );
				prjDesc.setText(result.getString(7));
				prjDesc.setWrapText(true);
			}
			
			db.db_close();
		} catch (SQLException e) {
			db.db_close();
			System.out.println(e.getMessage());
		}
	}

	@Subscribe
	public void reloadProject(ProjectChangeEvent event) {
		final int pid = event.getProject_id();
		setProject(pid);
	}
}
