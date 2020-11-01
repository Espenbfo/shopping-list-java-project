package shoppinglist.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import shoppinglist.core.*;
import shoppinglist.storage.FileHandler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.util.Callback;

public class AppController {
    private static Person currentPerson;


    @FXML
    Button addItemButton;
    @FXML
    TextField itemInputField;
    @FXML
    TextField shoppingTitleTextField;
    @FXML
    TextField amountInputField;
    @FXML
    TextField measurementInputField;
    @FXML
    TableView shoppingList;
    @FXML 
    Label emptyListText;
    @FXML
    TextField loadId;
    @FXML 
    TextField personInputField;
    @FXML
    TilePane listsOverview;
    @FXML
    HBox hbox;
    @FXML
    TextField peopleInputField;
    @FXML
    Label loginNameLabel;

    private final ObservableList<ShoppingElement> data = FXCollections.observableArrayList();
    public ShoppingList currentShoppingList; //vil egt. ikke ha tittel her, men tror endringer må gjøres i ShoppingList.java
    
    String itemToAdd = null;

    /**
     * Sets up the application
     */
    @FXML
    public void initialize() {
        ShoppingList.setCurrentMaxID(FileHandler.readMaxID());
        currentShoppingList = new ShoppingList("test");

        if (Client.getCurrentPerson() != null) {
            String userName = Client.getCurrentPerson().getUserName();
            userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
            personInputField.setText(userName);
            fillTitleList();
            loginNameLabel.setText(userName);
        }


        TableColumn<ShoppingElement, Double> colNum = new TableColumn<>("Num");
        colNum.setCellValueFactory(new PropertyValueFactory<ShoppingElement, Double>("value"));

        TableColumn<ShoppingElement, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<ShoppingElement, String>("name"));

        TableColumn<ShoppingElement, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<ShoppingElement, String>("measurementName"));
        colName.setPrefWidth(100);
        colType.setPrefWidth(50);
        colNum.setPrefWidth(50);
        addCheckBoxToTable();
        shoppingList.getColumns().addAll(colNum, colType, colName);
        shoppingList.setItems(data);
        addButtonToTable();
    }

    /**
     * Add element to shoppinglist when button is clicked 
     */

    @FXML
    void handleAddItemButtonClicked() {

        ShoppingElement currentElement = new ShoppingElement(itemInputField.getText(), Double.parseDouble(amountInputField.getText()), measurementInputField.getText());
        data.add(currentElement);
        currentShoppingList.addElement(currentElement);
    }

    /**
     * Adds the buttonrow to the TableView
     */
    private void addButtonToTable() {
        TableColumn<ShoppingElement, Void> colBtn = new TableColumn("Delete?");

        Callback<TableColumn<ShoppingElement, Void>, TableCell<ShoppingElement, Void>> cellFactory = new Callback<TableColumn<ShoppingElement, Void>, TableCell<ShoppingElement, Void>>() {
            @Override
            public TableCell<ShoppingElement, Void> call(final TableColumn<ShoppingElement, Void> param) {
                final TableCell<ShoppingElement, Void> cell = new TableCell<ShoppingElement, Void>() {

                    private final Button btn = new Button("delete");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            ShoppingElement e = getTableView().getItems().get(getIndex());
                            currentShoppingList.removeElement(e);
                            data.remove(e);
                            System.out.println("selectedData: " + e);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) { ;
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn.setPrefWidth(50);
                            btn.getStyleClass().add("delete");
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);
        colBtn.setPrefWidth(60);
        shoppingList.getColumns().add(colBtn);
    }

    /**
     * Adds the CheckBoxrow to the TableView
     */
    private void addCheckBoxToTable() {
        TableColumn<ShoppingElement, Void> colCB = new TableColumn("Done");

        Callback<TableColumn<ShoppingElement, Void>, TableCell<ShoppingElement, Void>> cellFactory = new Callback<TableColumn<ShoppingElement, Void>, TableCell<ShoppingElement, Void>>() {
            @Override
            public TableCell<ShoppingElement, Void> call(final TableColumn<ShoppingElement, Void> param) {
                final TableCell<ShoppingElement, Void> cell = new TableCell<ShoppingElement, Void>() {

                    private final CheckBox cb = new CheckBox();

                    {
                        cb.setOnAction((ActionEvent event) -> {
                            ShoppingElement e = getTableView().getItems().get(getIndex());
                            e.toggleShopped();
                            System.out.println(e);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            ShoppingElement e = getTableView().getItems().get(getIndex());
                            cb.setSelected(e.isShopped());
                            setGraphic(cb);
                        }
                    }
                };
                return cell;
            }
        };

        colCB.setCellFactory(cellFactory);
        colCB.setPrefWidth(40);
        shoppingList.getColumns().add(colCB);
    }
    /**
     * Saves shoppinglist to file 
     */
    @FXML
    void saveShoppingList(){
        String peopleText = loginNameLabel.getText().toLowerCase() + "," + peopleInputField.getText().toLowerCase();
        peopleText = peopleText.replaceAll("\\s","");

        String[] peopleNames = peopleText.split(",");
        for (String name : peopleNames) {
            try {
                Person p = FileHandler.readPerson(name);
                System.out.println(p);
                Integer prevList = currentShoppingList.getId();
                if (!p.getShoppingLists().contains(prevList)) {
                    p.addShoppingList(prevList);
                    FileHandler.writePerson(p);
                }
            }
            catch (Exception ex) {
                System.out.println(ex);
            }
        }
        if (currentShoppingList.getId() > FileHandler.readMaxID()) {
            FileHandler.writeMaxID(currentShoppingList.getId());
        }
        currentShoppingList.setTitle(shoppingTitleTextField.getText());
        FileHandler.writeFile(currentShoppingList);
        fillTitleList();
    }

    /**
     * Loads existing shoppinglist from file
     */
    @FXML
    void loadShoppingList(){
        loadShoppingListWithList(FileHandler.readFile(Integer.parseInt(loadId.getText())));
    }

    /**
     * Loads existing shoppinglist from file
     */
    @FXML
    void loadShoppingListWithList(ShoppingList l){
        currentShoppingList = l;
        //shoppingList.getChildren().clear();
        data.clear();
        for(ShoppingElement x:currentShoppingList.getElementList()){
            data.add(x);
        }
        shoppingTitleTextField.setText(currentShoppingList.getTitle());
    }
    /**
     * Changes status of shoppingitem from not shopped to shopped 
     * 
     * @param shoppingElement
     */
    @FXML
    void handleItemShopped(ShoppingElement shoppingElement) {
        shoppingElement.toggleShopped();
 
    }

    /**
     * Updates the list of shoppinglists, filtered by person, on the right side of the gui
     */
    void fillTitleList() {
        String personString = personInputField.getText().toLowerCase();
        if(personString.equals("")) return;
        Person currenttPerson = FileHandler.readPerson(personString);// set currentPerson til inputperson, ikke lage ny – lagret i annen klasse?
        //currentPerson.addShoppingList(new ShoppingList("test")); //kun for testing
        //currentPerson.addShoppingList(new ShoppingList("test2")); //kun for testing
        listsOverview.getChildren().clear();
        for (Integer id : currenttPerson.getShoppingLists()) {
            ShoppingList l = FileHandler.readFile(id);
            System.out.println(l.getTitle());
            Pane list = new Pane();
            Label listName = new Label(l.getTitle());
            listName.setPrefWidth(1000.);
            listName.getStyleClass().add("listTitleListElement");
            listsOverview.getChildren().add(listName);


            listName.setOnMouseClicked(event -> handleListButtonClicked(l));
        }
    }
    /**
     * Finds and displays the lists of a given person
     * 
     * @param enter key to press to evoke method
     */
    @FXML
    void handlePersonInput(KeyEvent enter){

        if(enter.getCode() == KeyCode.ENTER) {
            fillTitleList();

        }
    	
    }

    /**
     * Loads the ShoppingList clicked
     * @param shoppingList The ShoppingList to load
     */
    @FXML 
    void handleListButtonClicked(ShoppingList shoppingList){
        System.out.println("clicked");
        currentShoppingList = shoppingList;
        String inputText = personInputField.getText();
        inputText = inputText.substring(0, 1).toUpperCase() + inputText.substring(1);
        loginNameLabel.setText(inputText);
        loadShoppingListWithList(FileHandler.readFile(currentShoppingList.getId()));

        //display clicked list
    }

    /**
     * Creates a new empty shoppinglist
     */
    @FXML
    void newList() {
        currentShoppingList = new ShoppingList("New List");
        loadShoppingListWithList(currentShoppingList);
        saveShoppingList();
    }

    /**
     * filters the list of shoppinglists
     */
    @FXML
    void sokList() {
        fillTitleList();
    }

    /**
     * loads the loginscreen
     * @param e the event that calls the scenechange
     * @throws IOException
     */
    @FXML
    void loginScreen(ActionEvent e) throws IOException {
        Parent loginParent =   FXMLLoader.load(getClass().getResource("/resources/shoppinglist/gui/LoginScreen.fxml"));
        Scene loginScene = new Scene(loginParent);
        loginScene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());
        Stage appStage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        appStage.setScene(loginScene);
    }
}
