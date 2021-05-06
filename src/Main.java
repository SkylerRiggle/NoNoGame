import java.io.FileInputStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This class serves as the neutral point for the application, storing global data for the application and
 * starting the application in the first place.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class Main extends Application {
	
	private static Stage primaryStage;
	
	private static final String IMAGE_FOLDER = "Images/";
	
	private static Image appIcon;
	private static final String APP_ICON_FILENAME = "Logo.png";
	
	private static final String APP_TITLE = "NoNo | The Nonogram Puzzle Game";
	
	private static final boolean RESIZABLE = false;
	
	private static final String STYLE_SHEET = "style.css";
	
	private static final String PUZZLE_DIRECTORY = "/puzzles/";
	private static final String PUZZLE_FILE_TYPE = ".pz.txt";
	
	
	/**
	 * This main method launches the start method with a new stage.
	 * 
	 * @param args Default main argument.
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * This method starts the application and displays the main menu.
	 * 
	 * @param stage The default stage for this application.
	 * @throws Exception Thrown in case of error while getting image files.
	 */
	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		
		//Get and set the application icon.
		appIcon = new Image(new FileInputStream(IMAGE_FOLDER + APP_ICON_FILENAME));
		primaryStage.getIcons().add(appIcon);
		
		//Set the application title.
		primaryStage.setTitle(APP_TITLE);
		
		//Set the resize boolean state for the application.
		primaryStage.setResizable(RESIZABLE);
		
		//Create and display the main menu.
		MainMenu mainMenu = new MainMenu(this);
		setScene(mainMenu);
		
		primaryStage.show();
	}
	
	/**
	 * This method creates and displays the Play-Options menu.
	 */
	public void startPlay() {
		PlayOptions playOptions = new PlayOptions(this);
		setScene(playOptions);
	}
	
	/**
	 * This method creates and displays the Create-Options menu.
	 */
	public void startCreate() {
		CreateOptions createOptions = new CreateOptions(this);
		setScene(createOptions);
	}

	/**
	 * This method creates a new scene with a given pane to display.
	 * 
	 * @param pane The new pane to be displayed.
	 */
	public static void setScene(Pane pane) {
		//Create the scene and add the style sheet.
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(STYLE_SHEET);
		
		primaryStage.setScene(scene);
	}
	
	/**
	 * This method returns the application's icon.
	 * 
	 * @return The application's icon.
	 */
	public static Image getAppIcon() {
		return appIcon;
	}
	
	/**
	 * This method returns the location for the image files.
	 * 
	 * @return The location for the image files.
	 */
	public static String getImageLocation() {
		return IMAGE_FOLDER;
	}
	
	/**
	 * This method returns the location for the puzzle files.
	 * 
	 * @return The location for the puzzle files.
	 */
	public static String getPuzzleLocation() {
		return System.getProperty("user.dir") + PUZZLE_DIRECTORY;
	}
	
	/**
	 * This method returns the file type for a puzzle.
	 * 
	 * @return The file type for a puzzle.
	 */
	public static String getPuzzleFileType() {
		return PUZZLE_FILE_TYPE;
	}
}
