module shoppinglist{
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.annotation;
	
	exports shoppinglist.core;
	exports shoppinglist.storage;
	exports shoppinglist.gui;

	opens shoppinglist.gui to javafx.fxml;
}
