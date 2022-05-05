package com.mmhlego;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.mmhlego.model.Gene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		stage.setResizable(false);
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("mainPage.fxml"));
		stage.setScene(new Scene(fxmlLoader.load()));
		stage.show();
	}

	public static void main(String[] args) {
		Gene.loadGenes();
		clearFile();
		launch();
	}

	static void clearFile() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("values.txt"));
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

// Best: 423
