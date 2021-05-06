
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;

public class CreateOptions extends GridPane {

	private static final int SPACING = 10;
	
	private static Label filenameLabel = new Label("New Puzzle Name:");
	private static TextField filenameField = new TextField();
	
	private static Label sizeListLabel = new Label("New Puzzle Size:");
	private static ComboBox<PuzzleSize> sizeList = new ComboBox<PuzzleSize>();
	
	private static Button startButton = new Button("Generate Grid");
	private static Button backButton = new Button("Back");
	
	public CreateOptions(Main main) {
		super();
		
		setHgap(SPACING);
		setVgap(SPACING);
		setPadding(new Insets(SPACING, SPACING, SPACING, SPACING));
		
		ObservableList<PuzzleSize> sizes = FXCollections.observableArrayList(PuzzleSize.SMALL, 
																			 PuzzleSize.MEDIUM, 
																			 PuzzleSize.LARGE);
		sizeList.setItems(sizes);
		sizeList.setValue(sizes.get(0));
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				CreateModel createModel = new CreateModel(sizeList.getValue());
				int size = createModel.getSize();
				
				startCreator(main, createModel, size);
			}
		});
		
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				Main.setScene(new MainMenu(main));
			}
		});
		
		add(filenameLabel, 0, 0);
		add(filenameField, 1, 0);
		add(sizeListLabel, 0, 1);
		add(sizeList, 1, 1);
		add(startButton, 1, 2);
		add(backButton, 0, 2);
	}
	
	private String getFilename() {
		String filename = filenameField.getText();
		
		if (filename != null && !filename.isEmpty()) {
			return filename;
		}
		
		throw new IllegalArgumentException();
	}
	
	private void startCreator(Main main, CreateModel createModel, int size) {
		try {
			CreateView createView = new CreateView(size, size, getFilename(), createModel, main);
			
			CreatePresenter presenter = new CreatePresenter(createModel, createView);
			createView.register(presenter);
			
			Main.setScene(createView);
		} catch (Exception e) {
			String message = "Could not create puzzle file.";
			ErrorAlert errorAlert = new ErrorAlert(AlertType.ERROR, message);
			errorAlert.show();
		}
	}
}
