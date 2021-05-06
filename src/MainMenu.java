
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * The main menu acts as a landing point for the player to decide whether or not they want
 * to play or create puzzles.
 * 
 * @author Skyler Riggle
 * @version 1.0
 *
 */
public class MainMenu extends BorderPane {
	
	private static final int SPACING = 40;
	private static final int PADDING = 10;
	
	private static final String PADDING_STYLE = "HBox-filled";
	
	private static Button playButton = new Button("Play");
	private static Button createButton = new Button("Create New Puzzle");
	private static Button exitButton = new Button("Exit Game");
	
	private static final String MENU_LOGO_FILE = "MenuLogo.png";
	private static Image menuLogo;
	
	private static Label creatorLabel = new Label("Created by: Skyler Riggle");
	
	
	/**
	 * This constructor creates a main menu with all of its elements.
	 * 
	 * @param main A reference to the Main class for this application.
	 */
	public MainMenu(Main main) {
		super();
		
		//The play button navigates to the Play-Options menu.
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				main.startPlay();
			}
		});
		
		//The create button navigates to the Create-Options menu.
		createButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				main.startCreate();
			}
		});
		
		//The exit button leaves the application.
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		//Get the main menu logo.
		try {
			menuLogo = new Image(new FileInputStream(Main.getImageLocation() + MENU_LOGO_FILE));
		} catch (FileNotFoundException e) {	}
		
		//This VBox contains each button on the main menu.
		VBox buttons = new VBox(playButton, createButton, exitButton);
		buttons.setSpacing(SPACING);
		buttons.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
		buttons.setAlignment(Pos.CENTER_RIGHT);
		
		//The region that sets the height of the top bar.
		Region topSpace = new Region();
		topSpace.setMaxHeight(SPACING / 2);
		topSpace.setMinHeight(SPACING / 2);
		topSpace.setPrefHeight(SPACING / 2);
		
		//Top bar for aesthetic purposes.
		HBox topBar = new HBox(topSpace);
		topBar.getStyleClass().add(PADDING_STYLE);
		
		//Bottom bar containing the create label.
		HBox bottomBar = new HBox(creatorLabel);
		bottomBar.setAlignment(Pos.BOTTOM_LEFT);
		bottomBar.getStyleClass().add(PADDING_STYLE);
		
		//Get the main menu logo and animate it to tilt back and forth.
		ImageView menuLogoImage = new ImageView(menuLogo);
		RotateTransition logoRotation = new RotateTransition();
		logoRotation.setDuration(Duration.millis(1000));
		logoRotation.setByAngle(-15);
		logoRotation.setCycleCount((int) Double.POSITIVE_INFINITY);
		logoRotation.setNode(menuLogoImage);
		logoRotation.setAutoReverse(true);
		logoRotation.play();
		
		//Add the elements to the main menu.
		setTop(topBar);
		setCenter(menuLogoImage);
		setBottom(bottomBar);
		setRight(buttons);
	}
}
