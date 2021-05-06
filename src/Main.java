import java.io.FileInputStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		
		appIcon = new Image(new FileInputStream(IMAGE_FOLDER + APP_ICON_FILENAME));
		primaryStage.getIcons().add(appIcon);
		
		primaryStage.setTitle(APP_TITLE);
		
		primaryStage.setResizable(RESIZABLE);
		
		MainMenu mainMenu = new MainMenu(this);
		setScene(mainMenu);
		
		primaryStage.show();
	}
	
	public void startPlay() {
		PlayOptions playOptions = new PlayOptions(this);
		setScene(playOptions);
	}
	
	public void startCreate() {
		CreateOptions createOptions = new CreateOptions(this);
		setScene(createOptions);
	}

	public static void setScene(Pane pane) {
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(STYLE_SHEET);
		
		primaryStage.setScene(scene);
	}
	
	public static Image getAppIcon() {
		return appIcon;
	}
	
	public static String getImageLocation() {
		return IMAGE_FOLDER;
	}
	
	public static String getPuzzleLocation() {
		return System.getProperty("user.dir") + PUZZLE_DIRECTORY;
	}
	
	public static String getPuzzleFileType() {
		return PUZZLE_FILE_TYPE;
	}
}
