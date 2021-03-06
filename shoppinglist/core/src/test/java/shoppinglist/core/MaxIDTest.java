package shoppinglist.core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.assertj.core.api.Fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinglist.core.MaxId;


public class MaxIDTest {


  @Test
  public void MaxIDTestAll() {
    MaxId m = new MaxId();
    m.setId(3);
    MaxId m2 = new MaxId(3);
    assertTrue(m.getId() == m2.getId());
  }
}
