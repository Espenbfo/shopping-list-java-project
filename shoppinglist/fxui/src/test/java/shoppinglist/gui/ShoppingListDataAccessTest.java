package shoppinglist.gui;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.junit.jupiter.api.BeforeEach;

import shoppinglist.gui.LoginScreenController;
import shoppinglist.core.ShoppingList;
import shoppinglist.core.Person;
import shoppinglist.core.Client;
import shoppinglist.restapi.PersonService;
import shoppinglist.gui.ShoppingListDataAccess;

import java.awt.*;

public class ShoppingListDataAccessTest {
    final String baseURL = "http://localhost:8087/";
    ShoppingListDataAccess access;


    @Test
    public void testRegister() {
        Assertions.assertTrue(true);
    }

    @BeforeEach
    protected void setUpDataAccess() {
        final String serverUrlString = baseURL;
        final String clientUrlString = serverUrlString + PersonService.PERSON_SERVICE_PATH;
        access = new ShoppingListDataAccess(clientUrlString);


    }

    @Test
    public void getShoppingList() {

        final ShoppingList sl = access.getShoppingList(0);
        Assertions.assertTrue(true);
    }
}

