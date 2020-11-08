package shoppinglist.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shoppinglist.core.Person;
import shoppinglist.core.Passwords;
import shoppinglist.core.ShoppingElement;
import shoppinglist.core.ShoppingList;


public class PasswordsTest {

  Passwords passwords;

  @BeforeEach
  public void setup() {
    passwords = new Passwords();
  }

  @Test
  public void testHashing() {
    Person p = new Person("Per");
    Person k = new Person("Kari");
    passwords.setPassword(p, "testPassord");
    passwords.setPassword(k, "testPassord");
    assertNotEquals(passwords.getPassword(p), passwords.getPassword(k));
  }

  @Test
  public void testChecking() {
    Person p = new Person("Per");
    passwords.setPassword(p, "testPassord");
    assertTrue(passwords.checkPassword(p, "testPassord"));
    assertTrue(passwords.checkPassword(p.getUserName(), "testPassord", p.getSalt()));
    assertFalse(passwords.checkPassword(p, "feilPassord"));
  }
}

