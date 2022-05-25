package com.mmhlego.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import com.mmhlego.model.Chromosome;
import com.mmhlego.model.Constants;
import com.mmhlego.model.Selection;
import com.mmhlego.model.Society;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class PageController implements Initializable {
	@FXML
	private AnchorPane canvas;
	@FXML
	private TextField SimulateCountTF;
	@FXML
	private Button SimulateBTN;
	@FXML
	private VBox ChromosomeList;
	@FXML
	private VBox BestChromosomesList;
	@FXML
	private Label GenerationLBL;
	@FXML
	private Button ToggleBTN;

	Society society;
	int generation = 1;
	double visualMultiplier = 6.5;
	double radius = 10;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		society = Society.randomSociety();
		society.printData();
		showData();

		ToggleBTN.setOnMouseClicked(e -> {
			new Thread(() -> {
				try {
					Process process = Runtime.getRuntime().exec("python chart.py");

					BufferedReader reader =
							new BufferedReader(new InputStreamReader(process.getInputStream()));
					String line = "";
					while ((line = reader.readLine()) != null) {
						System.out.println(line);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}).start();
		});

		SimulateCountTF.setOnKeyPressed(e -> {

			if (e.getCode().equals(KeyCode.ENTER))
				simulate();
		});
		SimulateBTN.setOnMouseClicked(e -> simulate());
	}

	void simulate() {
		int count = Integer.parseInt(SimulateCountTF.getText());

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 1; i <= count; i++) {
					generation++;
					if (Constants.SELECTION_MODE == 1) {
						society = Selection.tournamentSelection(society);
					} else if (Constants.SELECTION_MODE == 2) {
						society = Selection.proportionalSelection(society);
					}

					society.printData(i);

					if (i % 100 == 0 || i == count) {
						Platform.runLater(() -> {
							showData();
						});
					}
				}
			}
		}).start();
	}

	void showData() {
		GenerationLBL.setText("Generation " + generation);

		ChromosomeList.getChildren().clear();
		for (int i = 0; i < Constants.SOCIETY_SIZE; i++) {
			ChromosomeList.getChildren()
					.add(society.members[i].getView("Chromosome " + (i + 1) + " :"));
		}

		BestChromosomesList.getChildren()
				.add(society.bestChromosome().getView("Gen " + generation + " :"));

		showGraph(society.bestChromosome());
	}

	boolean minimumAlertShown = false;

	private void showGraph(Chromosome best) {
		canvas.getChildren().clear();

		double size = 500d / (double) Constants.NODES_SIZE;
		double radius = size / 3d;

		for (int i = 1; i < Constants.NODES_SIZE; i++) {
			Line line = new Line(0, i * size, 500, i * size);
			line.setStroke(Color.DARKGRAY);
			canvas.getChildren().add(line);
		}

		for (int i = 1; i < Constants.NODES_SIZE; i++) {
			Line line = new Line(i * size, 0, i * size, 500);
			line.setStroke(Color.DARKGRAY);
			canvas.getChildren().add(line);
		}

		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			for (int j = 0; j < Constants.NODES_SIZE; j++) {
				if (i % 2 == j % 2) {
					Rectangle rect = new Rectangle(i * size, j * size, size, size);
					rect.setFill(Color.GRAY);
					canvas.getChildren().add(rect);
				}
			}
		}

		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			Circle circle = new Circle();
			circle.setLayoutX((best.genes[i].Number) * size - size / 2);
			circle.setLayoutY(i * size + size / 2);

			circle.setStroke(Color.DARKGRAY);
			circle.setFill(Color.LIGHTBLUE);
			circle.setRadius(radius);

			canvas.getChildren().add(circle);
		}

		if (best.fitness() == 0 && !minimumAlertShown) {
			minimumAlertShown = true;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Minimum value reached");
			alert.setContentText(
					"You have reached the minimum value.\nSimulating more generations may not be useful.");
			alert.show();
		}
	}
}
