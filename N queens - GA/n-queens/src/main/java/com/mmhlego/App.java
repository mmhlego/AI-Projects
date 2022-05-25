package com.mmhlego;

import java.io.IOException;
import com.mmhlego.model.Gene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
		launch();
	}
}
