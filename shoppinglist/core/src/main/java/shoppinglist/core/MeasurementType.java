package shoppinglist.core;

/**
 * Represents an elements measurement type.
 * The value is also stored in this class.
 */
public class MeasurementType {

  /**
   * The name of the measurementtype.
   */
  private String baseName;

  /**
   * The value of the ShoppingElement.
   */
  private double value;

  /**
   * Initialize a MeasurementType with default values.
   */
  public MeasurementType() {
    this.baseName = "";
    this.value = 0;
  }

  /**
   * Initialize a MeasurementType with provided baseName, value.
   *
   * @param baseName    the measurement name
   * @param value       the measurement value
   */
  public MeasurementType(final String baseName, final double value) {
    this.baseName = baseName;
    setValue(value);
  }

  /**
   * Initialize a MeasurementType as a copy of another MeasurementType.
   *
   * @param baseName the MeasurementType
   */
  public MeasurementType(final MeasurementType m) {
    baseName = m.getBaseName();
    value = m.getValue();
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public double getValue() {
    return value;
  }

  /**
   * Sets the value.
   *
   * @param newValue the new value
   */
  public void setValue(final double newValue) {
    if (newValue < 0.0) {
      throw new IllegalArgumentException();
    }
    value = newValue;
  }

  /**
   * Changes the value.
   *
   * @param difference the number to change the value by
   */
  public void changeValue(final double difference) {
    double newValue = value + difference;
    setValue(newValue);
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setBaseName(final String name) {
    baseName = name;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getBaseName() {
    return baseName;
  }


  /**
   * Checks if instance equals another.
   *
   * @return true if equal, false else
   */
  @Override
  public boolean equals(final Object o) {
    if (o == null) {
      return false;
    }
    if (!o.getClass().equals(this.getClass())) {
      return false;
    }
    MeasurementType m = (MeasurementType) o;
    if (!getBaseName().equals(m.getBaseName())) {
      return false;
    }
    if (getValue() != m.getValue()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    return 1;
  }
}
