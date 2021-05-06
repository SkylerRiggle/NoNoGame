
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
	
	public MainMenu(Main main) {
		super();
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				main.startPlay();
			}
		});
		
		createButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				main.startCreate();
			}
		});
		
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		try {
			menuLogo = new Image(new FileInputStream(Main.getImageLocation() + MENU_LOGO_FILE));
		} catch (FileNotFoundException e) {	}
		
		VBox buttons = new VBox(playButton, createButton, exitButton);
		buttons.setSpacing(SPACING);
		buttons.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
		buttons.setAlignment(Pos.CENTER_RIGHT);
		
		Region topSpace = new Region();
		topSpace.setMaxHeight(SPACING / 2);
		topSpace.setMinHeight(SPACING / 2);
		topSpace.setPrefHeight(SPACING / 2);
		
		HBox topBar = new HBox(topSpace);
		topBar.getStyleClass().add(PADDING_STYLE);
		
		HBox bottomBar = new HBox(creatorLabel);
		bottomBar.setAlignment(Pos.BOTTOM_LEFT);
		bottomBar.getStyleClass().add(PADDING_STYLE);
		
		ImageView menuLogoImage = new ImageView(menuLogo);
		RotateTransition logoRotation = new RotateTransition();
		logoRotation.setDuration(Duration.millis(1000));
		logoRotation.setByAngle(-15);
		logoRotation.setCycleCount((int) Double.POSITIVE_INFINITY);
		logoRotation.setNode(menuLogoImage);
		logoRotation.setAutoReverse(true);
		logoRotation.play();
		
		setTop(topBar);
		setCenter(menuLogoImage);
		setBottom(bottomBar);
		setRight(buttons);
	}
}
