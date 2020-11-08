package shoppinglist.core;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shoppinglist.core.MeasurementType;

public class MeasurementTypeTest {


    @BeforeEach
    public void init() {
        MeasurementType m1 = new MeasurementType();
    }

    @Test
    public void testNonnegativeNum() {
        MeasurementType m1 = new MeasurementType();
        try {
            m1.setValue(-2.0);
            fail();
        } catch (Exception e) {

        }
    }

    @Test
    public void testChangeValue() {
        MeasurementType m1 = new MeasurementType();
        assertTrue(m1.getValue() == (0.0));
        m1.setValue(1.0);
        assertTrue(m1.getValue() == (1.0));
    }

    @Test
    public void testAllowDouble1() {
        try {
            MeasurementType mDouble = new MeasurementType("D", 0.0, true);
            mDouble.setValue(2.2);
            MeasurementType mNDouble = new MeasurementType("N", 0.0, false);
            mNDouble.setValue(2.2);
            fail();
        } catch (Exception e) {

        }

    }
}
