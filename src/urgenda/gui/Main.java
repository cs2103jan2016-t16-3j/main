package urgenda.gui;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import urgenda.logic.Logic;
import urgenda.util.MultipleSlot;
import urgenda.util.StateFeedback;
import urgenda.util.StateFeedback.State;
import urgenda.util.Task;
import urgenda.util.TaskList;
import urgenda.util.UrgendaLogger;

public class Main extends Application {
	
	private static final String APP_NAME = "Urgenda";
	private static final String PATH_GUI_FXML = "MainView.fxml";
	private static final String PATH_ICON = "../../resources/urgenda_icon.png";
	public static final String PATH_REGULAR_FONT = new String("../../resources/Montserrat-Light.otf");
	public static final String PATH_BOLD_FONT = new String("../../resources/Montserrat-Regular.otf");
	public static final String PATH_LIGHT_FONT = new String("../../resources/Montserrat-UltraLight.ttf");
	
	private static final String HEADER_ALL_TASKS = "Showing ALL TASKS";
	private static final String HEADER_ALL_WITH_COMPLETED_TASKS = "Showing ALL TASKS WITH COMPLETED TASKS";
	private static final String HEADER_FREE_TIME = "Showing AVAILABLE TIME PERIODS";
	private static final String HEADER_SEARCH_RESULTS = "Showing SEARCH RESULTS";
	private static final String HEADER_MULTIPLE_MATCHES = "Showing MULTIPLE MATCHES";

	private static final int DEFAULT_REGULAR_FONT_SIZE = 20;
	private static final int DEFAULT_BOLD_FONT_SIZE = 20;
	private static final int DEFAULT_LIGHT_FONT_SIZE = 20;
	
	// Resources to load
	public static final Font REGULAR_FONT = Font.loadFont(Main.class.getResourceAsStream(PATH_REGULAR_FONT),
			DEFAULT_REGULAR_FONT_SIZE);
	public static final Font BOLD_FONT = Font.loadFont(Main.class.getResourceAsStream(PATH_BOLD_FONT),
			DEFAULT_BOLD_FONT_SIZE);
	public static final Font LIGHT_FONT = Font.loadFont(Main.class.getResourceAsStream(PATH_LIGHT_FONT),
			DEFAULT_LIGHT_FONT_SIZE);
	
	private BorderPane _rootLayout;
	private Scene _scene;
	private MainController _mainController;
	private DisplayController _displayController;
	private Logic _logic;
	private static Stage _primaryStage;

	@Override
	public void start(Stage primaryStage) {
		initLogger();
		initLogicComponent();
		initRootLayout();
		initDisplay();
		initStage(primaryStage);
	}

	private void initLogger() {
		UrgendaLogger.getInstance().getLogger().log(Level.INFO, "Successful initialisation of logger");
	}

	private void initLogicComponent() {
		_logic = Logic.getInstance();
		UrgendaLogger.getInstance().getLogger().log(Level.INFO, "Successful initialisation of logic");
	}

	private void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(PATH_GUI_FXML));
			_rootLayout = loader.load();
			_mainController = loader.getController();
			_mainController.setMain(this);
		} catch (IOException e) {
			UrgendaLogger.getInstance().getLogger().log(Level.SEVERE, "Initialisation of root layout failed!");
			e.printStackTrace();
		}
		UrgendaLogger.getInstance().getLogger().log(Level.INFO, "Successful initialisation of root layout");
	}

	private void initDisplay() {
		_displayController = _mainController.getDisplayController();
		StateFeedback state = retrieveStartupState();
		//TODO implement check settings for showing novice headers, change boolean below
		_displayController.initDisplay(state.getAllTasks(), createDisplayHeader(state), state.getDetailedIndexes(), state.getDisplayPosition(), true);
		UrgendaLogger.getInstance().getLogger().log(Level.INFO, "Successful initialisation of display view");
	}

	private void initStage(Stage primaryStage) {
		_scene = new Scene(_rootLayout);
		_primaryStage = primaryStage;
		_primaryStage.initStyle(StageStyle.DECORATED);
		_primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(PATH_ICON)));
		_primaryStage.setTitle(APP_NAME);
		_primaryStage.setResizable(false);
		_primaryStage.setScene(_scene);
		_primaryStage.sizeToScene();
		_primaryStage.show();
		UrgendaLogger.getInstance().getLogger().log(Level.INFO, "Successful initialisation of Urgenda window");
	}

	private StateFeedback retrieveStartupState() {
		StateFeedback state = _logic.retrieveStartupState();
		_mainController.displayFeedback(state.getFeedback());
		return state;
	}
	
	protected String handleCommandLine(String commandLine) {
		StateFeedback state = _logic.executeCommand(commandLine, _displayController.getSelectedTaskIndex());
		if(state.getState() == State.SHOW_HELP) {
			_mainController.showHelp();
		} else if(state.getState() == State.EXIT) {
			quit();
		}
		//TODO implement check settings for showing novice headers, change boolean below
		switch(state.getState()) {
		case FIND_FREE:
			_displayController.setDisplay(state.getAllTasks(), createDisplayHeader(state), state.getDetailedIndexes(), state.getDisplayPosition(), true, true);
			break;
		default:
			_displayController.setDisplay(state.getAllTasks(), createDisplayHeader(state), state.getDetailedIndexes(), state.getDisplayPosition(), true, false);
			break;
		}
		return state.getFeedback();
	}
	

	private String createDisplayHeader(StateFeedback state) {
		String display = "";
		switch (state.getState()) {
		case MULTIPLE_MATCHES:
			display = HEADER_MULTIPLE_MATCHES;
			break;
		case SHOW_SEARCH:
			display = HEADER_SEARCH_RESULTS;
			break;
		case ALL_TASK_AND_COMPLETED:
			display = HEADER_ALL_WITH_COMPLETED_TASKS;
			break;
			//TODO review
//		case DISPLAY:
//			break;
		case SHOW_HELP:
			display = null; //previous display header not changed
			break;
		case FIND_FREE:
			display = HEADER_FREE_TIME;
			break;
		case ARCHIVE:
			display = "Showing ARCHIVE TASKS";
			break;
		case ALL_TASKS: //fall-through
		default:
			display = HEADER_ALL_TASKS;
			break;
		}
		return display;
	}
	
	public String getHelpText() {
		return _logic.displayHelp();
	}

	public MainController getController() {
		return _mainController;
	}
	
	public Stage getPrimaryStage() {
		return _primaryStage;
	}

	private void quit() {
		if(_mainController.getHelpController() != null) {
			_mainController.getHelpController().closeHelpWindow();
		}
		System.exit(0);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	//dummy method to create dummy list of tasks
	protected String setupDummyList() {
		Task taskOverdue = new Task("Complete tutorial", null, null, LocalDateTime.now().minusDays(1).withHour(23).withMinute(59),
				new ArrayList<String>(), false);
		Task taskTodayImportant = new Task("Breakfast with mum and dad", null, LocalDateTime.now().withHour(7).withMinute(0), LocalDateTime.now().withHour(8).withMinute(0),
				new ArrayList<String>(), true);
		taskTodayImportant.setIsCompleted(true);
		Task taskToday1 = new Task("NUS Overseas Colleges Workshop", null, LocalDateTime.now().minusDays(1).withHour(10).withMinute(0), LocalDateTime.now().plusDays(1).withHour(22).withMinute(0),
				new ArrayList<String>(), false);
		Task taskTodayDetailed = new Task("Finish writing testimonial for scholarship application", "", null, LocalDateTime.now().withHour(23).withMinute(59),
				new ArrayList<String>(), false);
		Task taskImportant = new Task("Internship interview w/ Google", null, LocalDateTime.now().plusDays(6).withHour(10).withMinute(0), LocalDateTime.now().plusDays(6).withHour(11).withMinute(0),
				new ArrayList<String>(), true);
		taskImportant.setSlot(new MultipleSlot());
		taskImportant.getSlot().addTimeSlot(LocalDateTime.now().plusDays(6).withHour(11).withMinute(0), LocalDateTime.now().plusDays(6).withHour(12).withMinute(0));
		taskImportant.getSlot().addTimeSlot(LocalDateTime.now().plusDays(6).withHour(12).withMinute(0), LocalDateTime.now().plusDays(6).withHour(13).withMinute(0));
		Task taskOverrun = new Task("Get groceries", "Supermarket", LocalDateTime.now().plusDays(2).withHour(17).withMinute(0), LocalDateTime.now().plusDays(2).withHour(18).withMinute(0), new ArrayList<String>(),
				false);
		taskOverrun.setSlot(new MultipleSlot());
		taskOverrun.getSlot().addTimeSlot(LocalDateTime.now().plusDays(3).withHour(17).withMinute(0), LocalDateTime.now().plusDays(3).withHour(18).withMinute(0));
		taskOverrun.getSlot().addTimeSlot(LocalDateTime.now().plusDays(4).withHour(17).withMinute(0), LocalDateTime.now().plusDays(4).withHour(18).withMinute(0));
		Task taskDetailedLong = new Task("Success is the sum of small efforts, repeated day in and day out. - Robert Collier", null, null, null,
				new ArrayList<String>(), false);
		Task taskC = new Task("Submit Project Report", null, null, LocalDateTime.now().minusDays(4).withHour(23).withMinute(59),
				new ArrayList<String>(), false);
		Task taskC2 = new Task("Dental Appointment", null, LocalDateTime.now().minusDays(8).withHour(10).withMinute(30), LocalDateTime.now().minusDays(8).withHour(12).withMinute(00), new ArrayList<String>(),
				false);
		
		ArrayList<Task> tasks = new ArrayList<Task>();
		ArrayList<Task> archives = new ArrayList<Task>();
		tasks.add(taskOverdue);
		tasks.add(taskTodayImportant);
		tasks.add(taskToday1);
		tasks.add(taskTodayDetailed);
		tasks.add(taskImportant);
		tasks.add(taskOverrun);
		tasks.add(taskDetailedLong);
		archives.add(taskC);
		archives.add(taskC2);
		StateFeedback state = new StateFeedback();
		state.setAllTasks(new TaskList(tasks, archives, 1, 3, 3, 2));
		state.setFeedback("Welcome to Urgenda! Your task manager is ready for use.\nPress ALT + F1 for help.");
		state.addDetailedTaskIdx(3);
		state.addDetailedTaskIdx(5);
		state.addDetailedTaskIdx(6);
		state.setState(StateFeedback.State.ALL_TASKS);
		_displayController.setDisplay(state.getAllTasks(), createDisplayHeader(state), state.getDetailedIndexes(), 0, true, false);
		return state.getFeedback();
	}

	public File getSaveDirectory() {
		File file = new File(_logic.getCurrentSaveDirectory());
		return file;
	}

	public void setMultipleSlotMenuForSelected(boolean show) {
		_mainController.showMultipleSlotMenuOption(show);
	}
}