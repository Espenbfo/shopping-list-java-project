module shoppinglist{
	requires javafx.fxml;
	requires transitive javafx.graphics;
	requires javafx.controls;
	
	exports shoppinglist;

	opens shoppinglist to javafx.fxml;
}
