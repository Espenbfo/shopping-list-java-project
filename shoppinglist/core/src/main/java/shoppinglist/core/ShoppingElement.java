package shoppinglist.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents an element in a shoppingList.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingElement {
  private String name;
  private MeasurementType measurementType;
  private boolean shopped;

  /**
   * Initialize a ShoppingElement with default values.
   */
  public ShoppingElement() {
    name = "";
    measurementType = new MeasurementType();
    shopped = false;
  }

  /**
   * Initialize a ShoppingElement with name,
   * value, and measurementName.
   *
   * @param name            the name
   * @param value           the value
   * @param measurementName the measurement name
   */
  public ShoppingElement(String name, double value, String measurementName) {
    this.name = name;
    measurementType = new MeasurementType(measurementName, value);
    this.shopped = false;
  }

  /**
   * Initialize a ShoppingElement with name, 
   * a predefined MeasurementType, 
   * and if the item has been shopped.
   *
   * @param name    the name
   * @param m       the MeasurementType instance
   * @param shopped the boolean representing if the element is shopped
   */
  public ShoppingElement(String name, MeasurementType m, boolean shopped) {
    this.name = name;
    measurementType = m;
    this.shopped = shopped;
  }

  /**
   * Initialize a ShoppingElement as a deep copy of another ShoppingElement.
   *
   * @param e    the ShoppingElement
   */
  public ShoppingElement(ShoppingElement e) {
    name = e.getName();
    measurementType = e.getMeasurementType();
    shopped = e.isShopped();
  }

  /**
   * Gets the value.
   *
   * @return the value
   */
  public double getValue() {
    return measurementType.getValue();
  }

  /**
   * Sets the value.
   *
   * @param newValue the new value
   */
  public void setValue(double newValue) {
    measurementType.setValue(newValue);
  }

  /**
   * Changes the value.
   *
   * @param difference the number to change the value by
   */
  public void changeValue(double difference) {
    measurementType.changeValue(difference);
  }

  /**
   * Toggles if the element is shopped.
   */
  public void toggleShopped() {
    shopped = !shopped;
  }

  /**
   * Gets whether the element is shopped or not.
   *
   * @return shopped
   */
  public boolean isShopped() {
    return shopped;
  }

  /**
   * set whether it is shopped.
   *
   * @param shopped to set to
   */
  public void setShopped(boolean shopped) {
    this.shopped = shopped;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param newName the new name
   */

  public void setName(String newName) {
    name = newName;
  }

  /**
   * Gets measurement name.
   *
   * @return measurement name
   */

  public String getMeasurementName() {
    return measurementType.getBaseName();
  }

  /**
   * Gets measurement instance.
   *
   * @return measurement instance
   */

  public MeasurementType getMeasurementType() {
    return measurementType;
  }

  /**
   * sets measurementtype.
   *
   * @param measurementType to set to
   */
  public void setMeasurementType(MeasurementType measurementType) {
    this.measurementType = measurementType;
  }

  /**
   * Gets a description of the element.
   *
   * @return a String representation of the element
   */
  @Override
  public String toString() {
    return "name='" + name + '\'' + "\n"
            + "value=" + getValue() + " " + measurementType.getBaseName() + "\n"
            + "shopped=" + shopped;
  }

  /**
   * Checks if instance equals another.
   *
   * @return true if equal, false else
   */
  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }
    if (!o.getClass().equals(this.getClass())) {
      return false;
    }

    ShoppingElement e = (ShoppingElement) o;

    if (!getMeasurementType().equals(e.getMeasurementType())) {
      return false;
    }

    if (!getName().equals(e.getName())) {
      return false;
    }

    if (isShopped() != e.isShopped()) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 3;
  }
}
