package shoppinglist.core;

/**
 * Represents an element in a shoppingList.
 */

public class ShoppingElement {
    String name;
    MeasurementType measurementType;
    boolean shopped;

    /**
     * Initialize a ShoppingElement with default values.
     */
    public ShoppingElement() {
        name= "";
        measurementType = new MeasurementType();
        shopped = false;
    }

    /**
     * Initialize a ShoppingElement with name, value, measurementName, and whether the element can be represented by non-integers
     *
     * @param name the name
     * @param value the value
     * @param measurementName the measurement name
     * @param allowDouble whether the element can be represented by non-integers
     *
     */
    public ShoppingElement(String name, double value, String measurementName, boolean allowDouble) {
        this.name= name;
        measurementType = new MeasurementType(measurementName,value,allowDouble);
        this.shopped = false;
    }
    /**
     * Initialize a ShoppingElement with name, value, measurementName.
     *
     * @param name the name
     * @param value the value
     * @param measurementName the measurement name
     *
     */
    public ShoppingElement(String name, double value, String measurementName) {
        this(name,value,measurementName,true);
    }

    /**
     * Initialize a ShoppingElement with name, a predefined MeasurementType, and if the item has been shopped.
     *
     * @param name the name
     * @param m the MeasurementType instance
     * @param shopped the boolean representing if the element is shopped
     *
     */
    public ShoppingElement(String name, MeasurementType m, boolean shopped) {
        this.name= name;
        measurementType = m;
        this.shopped = shopped;
    }

    /**
     * Gets the value
     *
     * @return the value
     */
    public double getValue() {
        return measurementType.getValue();
    }

    /**
     * Sets the value
     *
     * @param newValue the new value
     *
     */
    public void setValue(double newValue) {
        measurementType.setValue(newValue);
    }

    /**
     * Changes the value
     *
     * @param difference the number to change the value by
     *
     */
    public void changeValue(double difference) {
        measurementType.changeValue(difference);
    }

    /**
     * Toggles if the element is shopped
     */
    public void toggleShopped() {
        shopped = !shopped;
    }

    /**
     * Gets whether the element is shopped or not
     *
     * @return shopped
     */
    public boolean isShopped() {
        return shopped;
    }

    /**
     * Gets the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param newName the new name
     */

    public String getBaseName(){return measurementType.getBaseName();}
    public void setName(String newName) {
        name = newName;
    }

    /**
     * Gets a description of the element
     *
     * @return a String representation of the element
     */
    @Override
    public String toString() {
        return "name='" + name + '\'' + "\n" +
                "value=" + getValue() + " " + measurementType.getBaseName() + "\n" +
                "shopped=" + shopped;
    }
}