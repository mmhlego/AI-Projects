module com.mmhlego {
	requires javafx.controls;
	requires javafx.fxml;

	opens com.mmhlego to javafx.fxml;
	opens com.mmhlego.controller to javafx.fxml;

	exports com.mmhlego;
}
