
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class UiTest extends GuiTest {
	
	private static final String TEST_FILE_PATH = "uiTestFiles\";

	@Test
	public void systemTest() {
		SystemTestDataParser testData = new SystemTestDataParser();
		ArrayList<String> inputs = testData.getInputs();
		ArrayList<ViewDataPackage> outputs = testData.getOutputs();

		assertEquals(inputs.size(), outputs.size());

		for (int i = 1; i < inputs.size(); i++) {
			input = inputs.get(i);
			data = outputs.get(i);
			commandInput.setText(input);
			System.out.println(String.format("Testing: \"%s\"", input));
			sleep(250, TimeUnit.MILLISECONDS);
			push(KeyCode.ENTER);
			sleep(250, TimeUnit.MILLISECONDS);
			verifyView(data);
		}
	}

	@Override
	protected Parent getRootNode() {
		return null;
	}

	@After
	public void cleanUp() {
		File f = new File(TEST_FILE_PATH + "data.txt");
		if (f.exists()) {
			f.delete();
		}
	}

	@Override
	public void setupStage() throws Throwable {
		GraphicalUserInterface.configureTestMode(true, TEST_FILE_PATH);

		new Thread(() -> {
			urgenda.gui.Main.launch();
		}).start();
		// let the application load
		sleep(2, TimeUnit.SECONDS);
		click("#inputBar");
		commandInput = (TextField) find("#commandInputBox");

	}
}