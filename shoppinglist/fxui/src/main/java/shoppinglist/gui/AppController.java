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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import shoppinglist.core.*;
import shoppinglist.storage.FileHandler;

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
  @FXML
  CheckBox privateCheckBox;

  public ShoppingList currentShoppingList;
  private final ObservableList<ShoppingElement> data = FXCollections.observableArrayList();

  String itemToAdd = null;

  private PersonDataAccess dataAccess;
  private ShoppingListDataAccess shoppingAccess;

  protected PersonDataAccess getDataAccess() {
    return dataAccess;
  }

  public void setDataAccess(final PersonDataAccess dataAccess) {
    this.dataAccess = dataAccess;
  }

  @FXML
  public void initialize() {
    setDataAccess(new PersonDataAccess("http://localhost:8087/index"));
    shoppingAccess = new ShoppingListDataAccess("http://localhost:8087/index");
    currentShoppingList = new ShoppingList();
    if (Client.getCurrentPerson() != null) {
      String userName = Client.getCurrentPerson().getUserName();
      userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
      currentShoppingList.setOwner(Client.getCurrentPerson());
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
   * Add element to shoppinglist when button is clicked.
   */

  @FXML
  void handleAddItemButtonClicked() {

    ShoppingElement currentElement = new ShoppingElement(itemInputField.getText(), Double.parseDouble(amountInputField.getText()), measurementInputField.getText());
    data.add(currentElement);
    currentShoppingList.addElement(currentElement);
  }

  /**
   * Adds the buttonrow to the TableView.
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
            });
          }

          @Override
          public void updateItem(Void item, boolean empty) {
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
   * Adds the CheckBoxrow to the TableView.
   */
  private void addCheckBoxToTable() {
    TableColumn<ShoppingElement, Void> colCb = new TableColumn("Done");
    Callback<TableColumn<ShoppingElement, Void>, TableCell<ShoppingElement, Void>> cellFactory = new Callback<TableColumn<ShoppingElement, Void>, TableCell<ShoppingElement, Void>>() {
      @Override
      public TableCell<ShoppingElement, Void> call(final TableColumn<ShoppingElement, Void> param) {
        final TableCell<ShoppingElement, Void> cell = new TableCell<ShoppingElement, Void>() {

          private final CheckBox cb = new CheckBox();

          {
            cb.setOnAction((ActionEvent event) -> {
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
              ShoppingElement e = getTableView().getItems().get(getIndex());
              cb.setSelected(e.isShopped());
              setGraphic(cb);
            }
          }
        };
        return cell;
      }
    };

    colCb.setCellFactory(cellFactory);
    colCb.setPrefWidth(40);
    shoppingList.getColumns().add(colCb);
  }

  /**
   * Saves shoppinglist to server
   */
  @FXML
  void saveShoppingList() {
    String peopleText = personInputField.getText().toLowerCase() + "," + peopleInputField.getText().toLowerCase();
    peopleText = peopleText.replaceAll("\\s", "");

    if (privateCheckBox.isSelected()) {
      currentShoppingList.setPublicList(false);
    } else {
      currentShoppingList.setPublicList(true);
    }

    List<String> peopleNames = Arrays.asList(peopleText.split(","));
    ArrayList<String> toBeRemoved = new ArrayList<String>();

    if (currentShoppingList.getOwner() == null) {
      String ownerText = loginNameLabel.getText().toLowerCase() + "," + loginNameLabel.getText().toLowerCase();
      currentShoppingList.setOwner(dataAccess.getPerson(ownerText));
      currentShoppingList.addPerson(ownerText);
    }
    for (String p : currentShoppingList.getPersonList()) {
      try {
        if (!peopleNames.contains(p)) {
          Person person = FileHandler.readPerson(p);
          person.removeShoppingListById(currentShoppingList.getId());
          FileHandler.writePerson(person);
          toBeRemoved.add(person.getUserName());
        }
      } catch (Exception ex) {
        System.out.println(ex);
      }
    }
    currentShoppingList.getPersonList().removeAll(toBeRemoved);
    for (String name : peopleNames) {
      try {
        Person p = dataAccess.getPerson(name);
        currentShoppingList.addPerson(name);
      } catch (Exception ex) {
        System.out.println(ex);
      }
    }
    currentShoppingList.setTitle(shoppingTitleTextField.getText());
    int newint = shoppingAccess.putShoppingList(currentShoppingList);
    fillTitleList();
    loadShoppingListWithList(shoppingAccess.getShoppingList(newint));

  }


  /**
   * Loads existing shoppinglist from server
   */
  @FXML
  void loadShoppingList() {
    loadShoppingListWithList(shoppingAccess.getShoppingList(Integer.parseInt(loadId.getText())));
  }

  /**
   * Loads existing shoppinglist from server
   *
   * @param l shoppinglist to load
   */
  @FXML
  void loadShoppingListWithList(ShoppingList l) {
    currentShoppingList = l;
    //shoppingList.getChildren().clear();
    data.clear();
    for (ShoppingElement x : currentShoppingList.getElementList()) {
      data.add(x);
    }
    String currentUser = Client.getCurrentPerson().getUserName();
    if (currentShoppingList.getPersonList().contains(Client.getCurrentPerson())) {
      personInputField.setText(currentUser);
    }
    String people = "";
    for (String name : currentShoppingList.getPersonList()) {
      if (!name.equals(l.getOwner().getUserName().toLowerCase())) {
        people += name.substring(0, 1).toUpperCase() + name.substring(1) + ", ";
      }
    }
    if (people.length() > 2) {
      people = people.substring(0, people.length() - 2);
    }
    String ownerUserName = currentShoppingList.getOwner().getUserName();
    loginNameLabel.setText(ownerUserName.substring(0, 1).toUpperCase() + ownerUserName.substring(1));
    peopleInputField.setText(people);
    shoppingTitleTextField.setText(currentShoppingList.getTitle());
    privateCheckBox.setSelected(!currentShoppingList.getPublicList());
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
   * Updates the list of shoppinglists, filtered by person
   */
  void fillTitleList() {
    String personString = personInputField.getText().toLowerCase();
    if (personString.equals("")) return;
    Person currenttPerson = dataAccess.getPerson(personString);
    listsOverview.getChildren().clear();
    for (Integer id : currenttPerson.getShoppingLists()) {
      ShoppingList l = shoppingAccess.getShoppingList(id);
      if (l.getPublicList() || l.getOwner().getUserName().equals(Client.getCurrentPerson().getUserName())) {
        Pane list = new Pane();
        Label listName = new Label(l.getTitle());
        listName.setPrefWidth(1000.);
        listName.getStyleClass().add("listTitleListElement");
        listsOverview.getChildren().add(listName);

        listName.setOnMouseClicked(event -> handleListButtonClicked(l));
      }

    }
  }

  /**
   * Updates the list of shoppinglists, displays the lists of the person logged in
   */
  void fillTitleListByLogin() {
    String userName = Client.getCurrentPerson().getUserName();
    userName = userName.substring(0, 1).toUpperCase() + userName.substring(1);
    personInputField.setText(userName);
    fillTitleList();
  }

  /**
   * Finds and displays the lists of a given person
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
   * Loads the ShoppingList clicked
   *
   * @param shoppingList The ShoppingList to load
   */

  @FXML
  void handleListButtonClicked(ShoppingList shoppingList) {
    currentShoppingList = shoppingList;
    String ownerUserName = shoppingList.getOwner().getUserName();
    loginNameLabel.setText(ownerUserName.substring(0, 1).toUpperCase() + ownerUserName.substring(1));
    loadShoppingListWithList(shoppingAccess.getShoppingList(currentShoppingList.getId()));

  }


  /**
   * Creates a new empty shoppinglist
   */
  @FXML
  void newList() {
    fillTitleListByLogin();
    currentShoppingList = new ShoppingList("New List", Client.getCurrentPerson());
    loadShoppingListWithList(currentShoppingList);
    saveShoppingList();
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
   * @throws IOException
   */
  @FXML
  void loginScreen(ActionEvent e) throws IOException {
    Parent loginParent = FXMLLoader.load(getClass().getResource("/resources/shoppinglist/gui/LoginScreen.fxml"));
    Scene loginScene = new Scene(loginParent);
    loginScene.getStylesheets().add(getClass().getResource("/resources/shoppinglist/gui/style.css").toExternalForm());
    Stage appStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    appStage.setScene(loginScene);
  }
}

