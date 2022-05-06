package com.mmhlego.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import com.mmhlego.model.Chromosome;
import com.mmhlego.model.Constants;
import com.mmhlego.model.Gene;
import com.mmhlego.model.Selection;
import com.mmhlego.model.Society;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

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

	private void showGraph(Chromosome best) {
		canvas.getChildren().clear();

		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			drawLine(best.genes[i], best.genes[(i + 1) % Constants.NODES_SIZE]);
		}

		for (int i = 0; i < Constants.NODES_SIZE; i++) {
			Circle circle = new Circle();
			circle.setLayoutX(best.genes[i].x * visualMultiplier);
			circle.setLayoutY(best.genes[i].y * visualMultiplier);

			circle.setCenterX(radius * 0.6);
			circle.setCenterY(radius * 0.7);

			circle.setStroke(Color.GRAY);
			circle.setFill(Color.ORANGE);
			circle.setRadius(radius);
			canvas.getChildren().add(circle);

			Label label = new Label(String.valueOf(best.genes[i].Number));
			label.setLayoutX(best.genes[i].x * visualMultiplier);
			label.setLayoutY(best.genes[i].y * visualMultiplier);
			canvas.getChildren().add(label);
		}
	}

	private void drawLine(Gene g1, Gene g2) {
		Line line =
				new Line(g1.x * visualMultiplier + radius / 2, g1.y * visualMultiplier + radius / 2,
						g2.x * visualMultiplier + radius / 2, g2.y * visualMultiplier + radius / 2);
		canvas.getChildren().add(line);
	}
}
