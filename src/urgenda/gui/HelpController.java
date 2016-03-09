package urgenda.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HelpController implements Initializable {
	
	private static final String HELP_NAME = "Help";
	private static final String PATH_ICON = "../../resources/urgenda_icon.png";
	
	static Stage _helpStage;
	private static String _helpText;
	
	@FXML
	private TextArea helpContentPane;
	
	@FXML
	public void handleEscPressed(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			_helpStage.close();
		}
	}
	
	@FXML
	public void handleOkAction(ActionEvent e) {
		_helpStage.close();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		helpContentPane.setText(_helpText);
		helpContentPane.setEditable(false);
	}
	
	public void setupHelpStage() throws IOException {		
		Parent help = FXMLLoader.load(Main.class.getResource("HelpSplash.fxml"));
		_helpStage = new Stage();
		_helpStage.setScene(new Scene(help));
		_helpStage.initStyle(StageStyle.DECORATED);
		_helpStage.getIcons().add(new Image(getClass().getResourceAsStream(PATH_ICON)));
		_helpStage.setTitle(HELP_NAME);
		showHelpStage();
	}
	
	public void setHelpText(String text) {
		_helpText = text;
	}

	public void showHelpStage() {
		_helpStage.show();
	}

	public void closeHelpWindow() {
		_helpStage.close();		
	}
	
	public Stage getHelpStage() {
		return _helpStage;
	}
}