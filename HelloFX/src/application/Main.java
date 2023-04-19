package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	int spacing = 2;
	int mx = 0, my = 0;
	int[][] shiplocation = new int[10][10];
	boolean[][] revealed = new boolean[10][10];

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Pane root = new Pane();
		root.setPrefSize(1000, 831);
		root.setStyle("-fx-background-color: DARKGRAY;");

		Rectangle[][] rectangles = new Rectangle[10][10];
		shiplocation[0][0]=1;
		shiplocation[0][1]=1;
		shiplocation[0][2]=1;
		shiplocation[0][3]=1;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				Rectangle rect = new Rectangle(spacing + i * 77 + 3, spacing + j * 77 + 15, 77 - 2 * spacing,
						77 - 2 * spacing);
				rect.setFill(Color.GRAY);
				rectangles[i][j] = rect;
				root.getChildren().add(rect);

				

				rect.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
					int x = -1;
					int y = -1;

					for (int k = 0; k < 10; k++) {
						for (int l = 0; l < 10; l++) {
							if (rectangles[k][l].equals(rect)) {
								x = k;
								y = l;
								break;
							}
						}
					}
					mx=x;
					my=y;
					if (x != -1 && y != -1) {
						revealed[x][y] = true;
					}

					System.out.println("Mouse is in the [" + x + "," + y + "]");

					if (shiplocation[x][y] == 1) {
						System.out.println("HIT!");
						rect.setFill(Color.BLUE);
					} else {
						System.out.println("Miss...");
						rect.setFill(Color.YELLOW);
					}
				});
			}
		}

		Rectangle r = new Rectangle(788, 25, 180, 752);
		r.setFill(Color.BLACK);
		root.getChildren().add(r);

		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("grid");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	@Override
	public void stop() {
		Platform.exit();
	}

}