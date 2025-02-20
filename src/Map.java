import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.File;
import java.util.Scanner;

public class Map {
    private Stage stage;
    private double imageWidth = 900;
    private double imageHeight = 517.60;
    private File file;
    private ComboBox<Vertex> cbSource;
    private ComboBox<Vertex> cbDestination;
    private int numberOfVertices;
    private int numberOfEdges;
    private Graph graph;

    public Map(Stage stage, File file) {
        this.cbSource = new ComboBox<>();
        this.cbDestination = new ComboBox<>();
        this.stage = new Stage();
        this.file = file;
        readFile();
        createFX();
    }

    /*
     * This method to read the file and get the vertices and edges and add them to the graph
     * and add the vertices to the comboboxes, and set the coordinates of the vertices on the map
     * and set the action when the user clicks on the pin to set the source or destination.
     * */
    public void readFile() {
        try (Scanner in = new Scanner(file)) {
            // Read the number of vertices and edges
            String line = in.nextLine();
            String[] data = line.split(", ");
            numberOfVertices = Integer.parseInt(data[0].trim());
            numberOfEdges = Integer.parseInt(data[1].trim());
            // Create the graph with the number of vertices
            graph = new Graph(numberOfVertices);
            // Read the vertices with their coordinates and add them to the graph and comboboxes
            for (int i = 0; i < numberOfVertices; i++) {
                line = in.nextLine();
                data = line.split(", ");
                String capitalName = data[0].trim();
                double latitude = Double.parseDouble(data[1].trim());
                double longitude = Double.parseDouble(data[2].trim());
                // Calculate the x and y coordinates on the map
                double x = ((longitude + 180.0) / 360.0) * imageWidth;
                double y = ((90.0 - latitude) / 180.0) * imageHeight;
                Capital capital = new Capital(capitalName, latitude, longitude, x, y);
                Vertex vertex = new Vertex(capital);
                // Add the vertices to the comboboxes and the graph
                cbSource.getItems().add(vertex);
                cbDestination.getItems().add(vertex);
                graph.addVertex(vertex);
            }

            // Read the edges and add them to the graph
            for (int i = 0; i < numberOfEdges; i++) {
                line = in.nextLine();
                data = line.split(", ");
                // Get the source and destination vertices and the time and cost
                String source = data[0];
                String destination = data[1];
                int time = Integer.parseInt(data[2]);
                int cost = Integer.parseInt(data[3].trim());
                Vertex sourceVertex = graph.getVertix(source);
                Vertex destinationVertex = graph.getVertix(destination);

                // Calculate the distance between the source and destination vertices
                int distance = getDistance(sourceVertex, destinationVertex);

                // Create the edge and add it to the source vertex
                Edge edge = new Edge(sourceVertex, destinationVertex, time, cost, distance);
                sourceVertex.addEdge(edge);
            }
            System.out.println("File is valid");
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("File is not valid");
            alert.showAndWait();
        }
    }

    /*
     * This method to calculate the distance between two vertices using the Haversine formula
     * */
    private static int getDistance(Vertex sourceVertex, Vertex destinationVertex) {
        double srcLatitude = sourceVertex.getCapital().getLatitude();
        double destLatitude = destinationVertex.getCapital().getLatitude();
        double srcLongitude = sourceVertex.getCapital().getLongitude();
        double destLongitude = destinationVertex.getCapital().getLongitude();

        // Convert the latitude and longitude from degrees to radians
        double radSrcLatitude = Math.toRadians(srcLatitude);
        double radDestLatitude = Math.toRadians(destLatitude);
        double radSrcLongitude = Math.toRadians(srcLongitude);
        double radDestLongitude = Math.toRadians(destLongitude);

        double diffBetweenLat = radDestLatitude - radSrcLatitude;
        double diffBetweenLong = radDestLongitude - radSrcLongitude;

        // Haversine formula
        double res = Math.pow(Math.sin(diffBetweenLat / 2), 2) + Math.pow(Math.sin(diffBetweenLong / 2), 2) *
                Math.cos(radSrcLatitude) * Math.cos(radDestLatitude);
        double earthRad = 6378;
        double c = 2 * Math.asin(Math.sqrt(res));

        return (int) (earthRad * c);
    }

    /*
     * This method to create the JavaFX GUI with the map and the controls on the right side
     * and set the action when the user clicks on the pin to set the source or destination.
     * */
    public void createFX() {

        BorderPane bp = new BorderPane();
        // Set the background image
        Image background = new Image("background.png");
        BackgroundImage backgroundImage = new BackgroundImage(background,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                null,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));
        bp.setBackground(new Background(backgroundImage));

        ScrollPane mapPane = buildMap();

        // Set the image on grid pane
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(mapPane, 0, 0);


        bp.setLeft(gridPane);

        // Set the controls on the right side
        GridPane gpControls = new GridPane();
        gpControls.setVgap(10);
        // Set the right side of the border pane
        VBox vControls = new VBox(10);
        vControls.setMaxWidth(300);
        vControls.setAlignment(Pos.CENTER);
        Label lblSource = new Label("Source");

        Label lblDestination = new Label("Destination");

        Label lblFilter = new Label("Filter");
        // Combobox for filter with default value Distance
        ComboBox<String> cbFilter = new ComboBox<>();
        cbFilter.getItems().addAll("Distance", "Time", "Cost");
        cbFilter.setValue("Distance");


        Label lblPath = new Label("Path:");
        TextArea taPath = new TextArea();
        taPath.setEditable(false);
        taPath.setWrapText(true);
        taPath.setMaxWidth(200);
        taPath.setMaxHeight(100);


        Label lblDistance = new Label("Distance");
        TextField tfDistant = new TextField();
        tfDistant.setEditable(false);

        Label lblTime = new Label("Time");
        TextField tfTime = new TextField();
        tfTime.setEditable(false);

        Label lblCost = new Label("Cost");
        TextField tfCost = new TextField();
        tfCost.setEditable(false);


        Button btRun = new Button("Run");

        gpControls.add(lblSource, 0, 0);
        gpControls.add(cbSource, 1, 0);
        gpControls.add(lblDestination, 0, 1);
        gpControls.add(cbDestination, 1, 1);
        gpControls.add(lblFilter, 0, 2);
        gpControls.add(cbFilter, 1, 2);
        gpControls.add(btRun, 1, 3);
        gpControls.add(lblPath, 0, 4);
        gpControls.add(taPath, 1, 5);
        gpControls.add(lblDistance, 0, 6);
        gpControls.add(tfDistant, 1, 6);
        gpControls.add(lblTime, 0, 7);
        gpControls.add(tfTime, 1, 7);
        gpControls.add(lblCost, 0, 8);
        gpControls.add(tfCost, 1, 8);

        vControls.getChildren().add(gpControls);

        GridPane.setHalignment(btRun, HPos.CENTER);

        bp.setRight(vControls);

        Scene scene = new Scene(bp, 1200, 650);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.show();

        btRun.setOnAction(e -> {
            Pane pane = (Pane) mapPane.getContent();
            ObservableList<javafx.scene.Node> children = pane.getChildren();
            children.removeIf(n -> n instanceof Line);
            // Get the source and destination vertices
            Vertex source = cbSource.getValue();
            Vertex destination = cbDestination.getValue();
            // Get the filter value
            String filter = cbFilter.getValue();

            if (source == null || destination == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Please select source and destination");
                alert.showAndWait();
                return;
            }

            if (source.equals(destination)) {
                taPath.setText("The source and destination are the same");
                tfDistant.setText("0KM");
                tfTime.setText("0m");
                tfCost.setText("0$");
                return;
            }

            // Get the result from the graph
            HNode result = graph.getResult(source, destination, filter);
            if (result == null) {
                taPath.setText("No path found");
                tfDistant.setText("0KM");
                tfTime.setText("0m");
                tfCost.setText("0$");
                cbSource.setValue(null);
                cbDestination.setValue(null);
                return;
            }

            // Set the path, distance, time, and cost
            tfDistant.setText(result.getDistance() + "KM");
            tfTime.setText(result.getTime() + "m");
            tfCost.setText(result.getCost() + "$");

            // Set the path on the text area and the lines on the map
            Node node = result.getPath().getFront();
            StringBuilder path = new StringBuilder();
            node = node.getNext();
            // This loop to go through the path and add the vertices to the text area
            while (node != null) {
                if (filter.equals("Distance")) {
                    Edge edge = node.getElement();
                    path.append(edge.getSource().getCapital().getCapitalName()).append(" -> ");
                    path.append(edge.getDestination().getCapital().getCapitalName()).append(": ").append(edge.getDistance()).append("KM\n");
                    node = node.getNext();
                } else if (filter.equals("Time")) {
                    Edge edge = node.getElement();
                    path.append(edge.getSource().getCapital().getCapitalName()).append(" -> ");
                    path.append(edge.getDestination().getCapital().getCapitalName()).append(": ").append(edge.getTime()).append("m\n");
                    node = node.getNext();
                } else if (filter.equals("Cost")) {
                    Edge edge = node.getElement();
                    path.append(edge.getSource().getCapital().getCapitalName()).append(" -> ");
                    path.append(edge.getDestination().getCapital().getCapitalName()).append(": ").append(edge.getCost()).append("$\n");
                    node = node.getNext();
                }
            }

            taPath.setText(path.toString());

            node = result.getPath().getFront();
            node = node.getNext();

            // This loop to go through the path and add the lines on the map
            while (node != null) {
                Edge edge = node.getElement();
                double srcX = edge.getSource().getCapital().getX();
                double srcY = edge.getSource().getCapital().getY();
                double destX = edge.getDestination().getCapital().getX();
                double destY = edge.getDestination().getCapital().getY();

                Line line = new Line(srcX, srcY, destX, destY);
                line.setStroke(Color.RED);
                line.setStrokeWidth(2);
                pane.getChildren().add(line);
                node = node.getNext();
            }

            cbSource.setValue(null);
            cbDestination.setValue(null);

        });
        this.stage.show();
    }

    /*
     * This method to build the map image and add the pins on the map, and set the pins on the right coordinates
     * and set the action when the user clicks on the pin to set the source or destination.
     * */
    public ScrollPane buildMap() {
        // Load the map image
        Image mapImage = new Image("World_Map.jpg");
        ImageView map = new ImageView(mapImage);

        map.setFitWidth(imageWidth);
        map.setFitHeight(imageHeight);


        Pane pane = new Pane(map);

        // Set the map image on the left side
        ScrollPane scrollPane = new ScrollPane(pane);
        scrollPane.setPannable(true);
        scrollPane.setPrefViewportWidth(imageWidth - 15);
        scrollPane.setPrefViewportHeight(imageHeight - 9);

        // This loop to go through all vertices and add a pin image on its coordinates on the map
        for (Vertex vertex : cbSource.getItems()) {
            // The pin image
            Image pin = new Image("pin.png");
            ImageView ivPin = new ImageView(pin);
            ivPin.setFitWidth(20);
            ivPin.setFitHeight(20);
            Label lblCapital = new Label(vertex.getCapital().getCapitalName());
            lblCapital.setMaxHeight(20);
            // Get the vertices coordinates
            Capital capital = vertex.getCapital();
            double x = capital.getX();
            double y = capital.getY();
            Tooltip tooltip = new Tooltip(capital.getCapitalName());
            Tooltip.install(ivPin, tooltip);

            // Set the pin image on the map on the right coordinates
            double layoutX = x - 10;
            double layoutY = y - 20;
            ivPin.setLayoutX(layoutX);
            ivPin.setLayoutY(layoutY);
            lblCapital.setLayoutX(layoutX + 20);
            lblCapital.setLayoutY(layoutY);
            pane.getChildren().add(ivPin);
            pane.getChildren().add(lblCapital);

            // When the user clicks on the pin, set the source or destination
            ivPin.setOnMouseClicked(e -> {
                if (cbSource.getValue() == null) {
                    cbSource.setValue(vertex);
                } else if (cbDestination.getValue() == null) {
                    cbDestination.setValue(vertex);
                }
            });
        }
        return scrollPane;
    }
}