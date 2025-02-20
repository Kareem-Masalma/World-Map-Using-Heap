import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Driver extends Application {

    @Override
    public void start(Stage stage) {
        // Create the Welcome screen
        Label title = new Label("World Map");
        title.setStyle("-fx-font-size: 40; -fx-text-fill: #3a8ca0; -fx-font-weight: bold;");
        Image background = new Image("background.png");
        BackgroundImage backgroundImage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));

        BorderPane bp = new BorderPane();
        bp.setBackground(new Background(backgroundImage));
        bp.setCenter(title);

        Button btLoad = new Button("Load Data");

        bp.setBottom(btLoad);

        BorderPane.setAlignment(title, Pos.CENTER);
        BorderPane.setAlignment(btLoad, Pos.CENTER);

        Scene scene = new Scene(bp, 1200, 650);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.setTitle("World Map");
        stage.show();

        // Button to choose the file with the capitals
        btLoad.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose capitals file");
            fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Map map = new Map(stage, file);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error loading file");
                alert.setContentText("Please select a file");
                alert.showAndWait();
            }

        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}