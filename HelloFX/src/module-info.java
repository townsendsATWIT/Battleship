module HelloFX {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.base;
	requires javafx.media;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
