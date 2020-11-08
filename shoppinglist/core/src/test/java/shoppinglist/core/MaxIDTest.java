package shoppinglist.core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.assertj.core.api.Fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import shoppinglist.core.MaxID;


public class MaxIDTest {


  @Test
  public void MaxIDTestAll() {
    MaxID m = new MaxID();
    m.setId(3);
    MaxID m2 = new MaxID(3);
    assertTrue(m.getId() == m2.getId());
  }
}
