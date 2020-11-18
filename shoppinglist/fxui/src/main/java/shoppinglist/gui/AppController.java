package shoppinglist.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import shoppinglist.core.Client;
import shoppinglist.core.Person;
import shoppinglist.core.ShoppingElement;
import shoppinglist.core.ShoppingList;
import shoppinglist.storage.FileHandler;

public class AppController {

  @FXML
  Button addItemButton;
  @FXML
  Button newListButton;
  @FXML
  Button backToLoginButton;
  @FXML
  Button saveButton;
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
  @FXML
  CheckBox privateCheckBox;

  public ShoppingList currentShoppingList;
  private final ObservableList<ShoppingElement> data = FXCollections.observableArrayList();

  private PersonDataAccess dataAccess;
  private ShoppingListDataAccess shoppingAccess;

  /**
   * Getter for the PersonDataAccess object.
   *
   * @return dataAccess, the object
   */
  protected PersonDataAccess getDataAccess() {
    return dataAccess;
  }

  /**
   * Getter for the ShoppingListDataAccess object.
   *
   * @return dataAccess, the object
   */
  protected ShoppingListDataAccess getShoppingAccess() {
    return shoppingAccess;
  }
  

  /**
   * Setter for the PersonDataAccess object.
   *
   * @param dataAccess the PersonDataAccess object.
   */
  public void setDataAccess(final PersonDataAccess dataAccess) {
    this.dataAccess = dataAccess;
  }

  /**
   * Setter for the ShoppingListDataAccess object.
   *
   * @param shoppingDataAccess the ShoppingListDataAccess object.
   */
  public void setShoppingDataAccess(final ShoppingListDataAccess shoppingDataAccess) {
    this.shoppingAccess = shoppingDataAccess;
  }

  /**
   * Initializes the appscreen.
   */
  @FXML
  public void initialize() {

    //Setting up dataaccess.
    setDataAccess(new PersonDataAccess("http://localhost:8087"));
    shoppingAccess = new ShoppingListDataAccess("http://localhost:8087");

    //Creates an empty shoppinglist.
    currentShoppingList = new ShoppingList();

    //Checks if the client is logged in, and initializes all user-related fields.
    if (Client.getCurrentPerson() != null) {
      String userName = Client.getCurrentPerson().getUserName();

      //Capitalizes the first letter in the username
      userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);

      //Sets the current user as the owner of the empty list
      currentShoppingList.setOwner(Client.getCurrentPerson());

      //Fills in all related fields
      personInputField.setText(userName);
      fillTitleList();
      loginNameLabel.setText(userName);
    }

    //The rest of the method initializes the TableView where the user can view their shoppinglist.

    //Sets up each column
    TableColumn<ShoppingElement, Double> colNum = new TableColumn<>("Num");
    colNum.setCellValueFactory(new PropertyValueFactory<ShoppingElement, Double>("value"));

    TableColumn<ShoppingElement, String> colName = new TableColumn<>("Name");
    colName.setCellValueFactory(new PropertyValueFactory<ShoppingElement, String>("name"));

    TableColumn<ShoppingElement, String> colType = new TableColumn<>("Type");
    colType.setCellValueFactory(new PropertyValueFactory<ShoppingElement, 
        String>("measurementName"));

    //Sets the width of the columns
    colName.setPrefWidth(100);
    colType.setPrefWidth(50);
    colNum.setPrefWidth(50);

    //Adds the checkbox column
    addCheckBoxToTable();

    //Adds the middle columns
    shoppingList.getColumns().addAll(colNum, colType, colName);

    //Adds the button column
    addButtonToTable();

    //Sets the list of elements, data, as the content of the TableView
    shoppingList.setItems(data);
  }

  /**
   * Add element to shoppinglist when button is clicked.
   *
   * @Param e necessary for the error tooltip.
   */

  @FXML
  void handleAddItemButtonClicked(ActionEvent e) {

    //If any field is empty, isAdding turns false;
    boolean isAdding = true;

    //Checks if field is empty, and sets it to a visual illegal state if empty.
    //Else resets it to normal.
    if (itemInputField.getText().equals("")) {
      isAdding = false;
      itemInputField.getStyleClass().add("illegal");
    } else {
      itemInputField.getStyleClass().clear();
      itemInputField.getStyleClass().addAll("text-field", "text-input");
    }

    if (amountInputField.getText().equals("")) {
      isAdding = false;
      amountInputField.getStyleClass().add("illegal");
    } else {
      amountInputField.getStyleClass().clear();
      amountInputField.getStyleClass().addAll("text-field", "text-input");
    }

    if (measurementInputField.getText().equals("")) {
      isAdding = false;
      measurementInputField.getStyleClass().add("illegal");
    } else {
      measurementInputField.getStyleClass().clear();
      measurementInputField.getStyleClass().addAll("text-field", "text-input");
    }


    if (isAdding) {
      //Adds a new element
      ShoppingElement currentElement = new ShoppingElement(itemInputField.getText(),
              Double.parseDouble(amountInputField.getText()), measurementInputField.getText());
      //To the list the user sees
      data.add(currentElement);
      //And to the list that may be saved to the server.
      currentShoppingList.addElement(currentElement);

      //Resets the text if the input fields
      itemInputField.setText("");
      amountInputField.setText("");
      measurementInputField.setText("");

      //Sets the focus to the first field,
      // making it easy for the user to quickly write many elements in a row.
      amountInputField.requestFocus();
    } else {
      //Shows a tooltip with an error message if there are any errors.
      showError(measurementInputField,
              "Please fill all fields", e, -20);
    }
  }

  /**
   * Adds the buttonrow to the TableView.
   */
  private void addButtonToTable() {
    TableColumn<ShoppingElement, Void> colBtn = new TableColumn("Delete?");

    //Creates a TableCell with a button that automatically deletes when pressed
    Callback<TableColumn<ShoppingElement, Void>,
        TableCell<ShoppingElement, Void>> btnCell = new Callback
        <TableColumn<ShoppingElement, Void>,
        TableCell<ShoppingElement, Void>>() {
          @Override
      public TableCell<ShoppingElement, Void> call(final TableColumn<ShoppingElement, Void> param) {
            final TableCell<ShoppingElement, Void> cell = new TableCell<ShoppingElement, Void>() {

              //Button with text x
              private final Button btn = new Button("x");

              {
                btn.setOnAction((ActionEvent event) -> {
                  //Removes the element with the button that was clicked on.
                  ShoppingElement e = getTableView().getItems().get(getIndex());
                  currentShoppingList.removeElement(e);
                  data.remove(e);
                });
              }

              @Override
          public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setGraphic(null);
                } else {
                  //Sets the width and style of the button
                  btn.setPrefWidth(40);
                  btn.setPadding(Insets.EMPTY);
                  btn.getStyleClass().add("delete");
                    setGraphic(btn);
                }
              }
            };
            return cell;
          }
        };

    //Sets the cellfacotry
    colBtn.setCellFactory(btnCell);

    //Sets the width of the cell factory
    colBtn.setPrefWidth(60);

    //Adds the column
    shoppingList.getColumns().add(colBtn);
  }

  /**
   * Adds the CheckBoxrow to the TableView.
   */
  private void addCheckBoxToTable() {
    TableColumn<ShoppingElement, Void> colCb = new TableColumn("Done");

    //Creates a cell with a checkbox that toggles if a shoppingelement is shopped when checked
    Callback<TableColumn<ShoppingElement, Void>, TableCell<ShoppingElement, Void>> cbCell
         = new Callback<TableColumn<ShoppingElement, Void>, TableCell<ShoppingElement, Void>>() {
           @Override
      public TableCell<ShoppingElement, Void> call(final TableColumn<ShoppingElement, Void> param) {
             final TableCell<ShoppingElement, Void> cell = new TableCell<ShoppingElement, Void>() {

               //The checkbox
               private final CheckBox cb = new CheckBox();

               {
                 cb.setOnAction((ActionEvent event) -> {
                   //Toggles shopped of the item
                   ShoppingElement e = getTableView().getItems().get(getIndex());
                   e.toggleShopped();
                 });
               }

               @Override
          public void updateItem(Void item, boolean empty) {
                 super.updateItem(item, empty);
                 if (empty) {
                   setGraphic(null);
                 } else {
                   //Sets if the checkbox is checked or not based on the shoppingElement.
                   ShoppingElement e = getTableView().getItems().get(getIndex());
                   cb.setSelected(e.isShopped());
                   setGraphic(cb);
                 }
               }
             };
             return cell;
           }
         };

    //Sets the cellfactory
    colCb.setCellFactory(cbCell);

    //Sets the width
    colCb.setPrefWidth(40);

    //Adds the column
    shoppingList.getColumns().add(colCb);
  }

  /**
   * Saves shoppinglist to server.
   *
   * @Param e necessary for the error tooltip.
   */
  @FXML
  void saveShoppingList(ActionEvent e) {

    //Checks if the title is filled in, this is a requirement.
    if (shoppingTitleTextField.getText().equals("")) {
      shoppingTitleTextField.getStyleClass().add("illegal");
      showError(shoppingTitleTextField, "This field is empty", e, -20);
      //Returns if nor filled in.
      return;
    } else {
      shoppingTitleTextField.getStyleClass().clear();
      shoppingTitleTextField.getStyleClass().addAll("text-field", "text-input");
    }

    //Gets alle the people in the new list.
    // PersonInputField is the owner and peopleInputField are the guest owners.
    String peopleText = personInputField.getText().toLowerCase()
            + "," + peopleInputField.getText().toLowerCase();
    peopleText = peopleText.replaceAll("\\s", "");

    //Sets whether the list is public
    if (privateCheckBox.isSelected()) {
      currentShoppingList.setPublicList(false);
    } else {
      currentShoppingList.setPublicList(true);
    }

    //The users of the list, seperated by comma.
    List<String> peopleNames = Arrays.asList(peopleText.split(","));

    //Sets the owner.
    if (currentShoppingList.getOwner() == null) {
      String ownerText = loginNameLabel
          .getText().toLowerCase() + ","
          + loginNameLabel
          .getText().toLowerCase();
      currentShoppingList.setOwner(dataAccess.getPerson(ownerText));
      currentShoppingList.addPerson(ownerText);
    }

    //Adds the new people to the shoppinglist
    currentShoppingList.setPersonList(new ArrayList<String>());
    for (String name : peopleNames) {
      currentShoppingList.addPerson(name);
    }

    //Sets the title
    currentShoppingList.setTitle(shoppingTitleTextField.getText());

    //Saves the shoppinglist to server
    int newint = shoppingAccess.putShoppingList(currentShoppingList);

    //Updates the list of shoppinglists to the right
    fillTitleList();

    //Loads the same list back to the client
    loadShoppingListWithList(shoppingAccess.getShoppingList(newint));

  }


  /**
   * Loads existing shoppinglist onto screen from server.
   */
  @FXML
  void loadShoppingList() {
    loadShoppingListWithList(shoppingAccess.getShoppingList(Integer.parseInt(loadId.getText())));
  }

  /**
   * Loads existing shoppinglist onto screen from server.
   *
   * @param l shoppinglist to load
   */
  @FXML
  void loadShoppingListWithList(ShoppingList l) {
    currentShoppingList = l;

    //Clears the data displayed in the tableView.
    data.clear();

    //Adds the shoppingElements to the tableview.
    for (ShoppingElement x : currentShoppingList.getElementList()) {
      data.add(x);
    }

    //Sets the current user to the textfield above the list of shoppinglists.
    String currentUser = Client.getCurrentPerson().getUserName();
    if (currentShoppingList.getPersonList().contains(currentUser)) {
      personInputField.setText(currentUser);
    }

    //Adds all the people together in a string seperated by commas .
    String people = "";
    for (String name : currentShoppingList.getPersonList()) {
      if (!name.equals(l.getOwner().getUserName().toLowerCase())) {
        people += name.substring(0, 1).toUpperCase() + name.substring(1) + ", ";
      }
    }

    //Removes the last comma.
    if (people.length() > 2) {
      people = people.substring(0, people.length() - 2);
    }

    //Sets the owner textfield to the current owner.
    String ownerUserName = currentShoppingList.getOwner().getUserName();
    loginNameLabel.setText(
        ownerUserName.substring(0, 1)
        .toUpperCase() + ownerUserName
        .substring(1));

    //Sets the guest owner field to the other current guest owners .
    peopleInputField.setText(people);

    //Sets the titletextfield to the current title.
    shoppingTitleTextField.setText(currentShoppingList.getTitle());

    //Sets the publicity checbox to the correct value.
    privateCheckBox.setSelected(!currentShoppingList.getPublicList());

    //Disabled the option to change the publicity checkbox if you are not logged in as the owner
    if (!currentShoppingList.getOwner().getUserName()
            .equals(Client.getCurrentPerson().getUserName())) {
      privateCheckBox.setDisable(true);
    } else {
      privateCheckBox.setDisable(false);
    }
  }

  /**
   * Changes status of shoppingitem from not shopped to shopped.
   *
   * @param shoppingElement the element to toggle
   */
  @FXML
  void handleItemShopped(ShoppingElement shoppingElement) {
    shoppingElement.toggleShopped();

  }

  /**
   * Updates the list of shoppinglists, filtered by the person given in the inputfield.
   */
  void fillTitleList() {
    String personString = personInputField.getText().toLowerCase();
    if (personString.equals("")) {
      return;
    }

    //Gets the current person from the server.
    Person currentPerson = dataAccess.getPerson(personString);

    //Clears the list of lists.
    listsOverview.getChildren().clear();

    //Gets the logged in users username.
    String loggedIn = Client.getCurrentPerson().getUserName();

    //Loops through all the users shoppinglists and adds them to the TilePane.
    for (Integer id : currentPerson.getShoppingLists()) {

      //Gets the shoppinglist.
      ShoppingList l = shoppingAccess.getShoppingList(id);

      //Checks if the list is public and should be displayed.
      if (l.getPublicList() 
          || l.getOwner().getUserName().equals(loggedIn) 
          || l.getPersonList().contains(loggedIn)) {

        //Sets up the list to be added to the TilePane
        Label listName = new Label(l.getTitle());
        listName.setPrefWidth(1000.);

        //Sets the styleclass
        listName.getStyleClass().add("listTitleListElement");

        //Adds the new list to the TilePane
        listsOverview.getChildren().add(listName);

        //Adds the correct eventlistener.
        listName.setOnMouseClicked(event -> handleListButtonClicked(l));
      }

    }
  }

  /**
   * Updates the list of shoppinglists, displays the lists of the person logged in.
   */
  void fillTitleListByLogin() {
    String userName = Client.getCurrentPerson().getUserName();

    //Capitalizes the first letter in the username
    userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
    personInputField.setText(userName);
    fillTitleList();
  }

  /**
   * Finds and displays the lists of a given person when enter is pressed.
   *
   * @param enter key to press to evoke method
   */
  @FXML
  void handlePersonInput(KeyEvent enter) {
    if (enter.getCode() == KeyCode.ENTER) {
      fillTitleList();
    }

  }


  /**
   * Loads the ShoppingList clicked.
   *
   * @param shoppingList The ShoppingList to load
   */

  @FXML
  void handleListButtonClicked(ShoppingList shoppingList) {
    currentShoppingList = shoppingList;
    String ownerUserName = shoppingList.getOwner().getUserName();
    loginNameLabel.setText(
        ownerUserName.substring(0, 1)
        .toUpperCase() + ownerUserName.substring(1));
    loadShoppingListWithList(shoppingAccess.getShoppingList(currentShoppingList.getId()));
  }


  /**
   * Creates a new empty shoppinglist.
   *
   * @Param e necessary for the error tooltip
   */
  @FXML
  void newList(ActionEvent e) {
    fillTitleListByLogin();
    currentShoppingList = new ShoppingList("New List", Client.getCurrentPerson());
    loadShoppingListWithList(currentShoppingList);
    saveShoppingList(e);
  }

  /**
   * Filters the list of shoppinglists.
   */
  @FXML
  void sokList() {
    fillTitleList();
  }


  /**
   * Loads the loginscreen.
   *
   * @param e the event that calls the scenechange
   */
  @FXML
  void loginScreen(ActionEvent e) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/shoppinglist/gui/LoginScreen.fxml"));
    fxmlLoader.setController(new LoginScreenController());
    Parent loginParent = fxmlLoader.load();

    //Creates a new scene with the new parent.
    Scene loginScene = new Scene(loginParent);

    //Loads the stylecheet.
    loginScene.getStylesheets()
        .add(getClass().getResource("/resources/shoppinglist/gui/style.css")
        .toExternalForm());

    //Gets the current stage.
    Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();

    //Sets the new scene.
    appStage.setScene(loginScene);
  }

  /**
   * Shows an error message above a textfield.
   *
   * @param textfield The textfield
   * @param tooltipText The text in the error message
   * @param e the actionevent. This is used to find the right coordinates.
   */
  public static void showError(final TextField textfield,
                               final String tooltipText,
                               final ActionEvent e,
                               final int yshift) {

    //Creates a new tooltip
    final Tooltip customTooltip = new Tooltip();

    //Sets the text to the tooltip
    customTooltip.setText(tooltipText);

    //Gets the point2d of the textfield, used to find the coordinates later
    Point2D point2D = textfield.localToScene(0, 0);

    //Sets the tooltip to our textfield
    textfield.setTooltip(customTooltip);

    //Sets the tooltip to autohide
    customTooltip.setAutoHide(true);

    //Gets the current stage
    Stage owner = (Stage) ((Node) e.getSource()).getScene().getWindow();

    //Shows the tooltip at the textfield, shifted by yShift pixels in the y direction
    customTooltip.show(owner,
            point2D.getX() + textfield.getScene().getX() + textfield.getScene().getWindow().getX(),
            point2D.getY() + textfield.getScene().getY() + textfield.getScene().getWindow().getY()
                    + yshift);

  }
}

