package shoppinglist.core;

/**
 * Represents an elements measurement type. The value is also stored in this class, as the value only has meaning related to a measurement type.
 */

public class MeasurementType {
    private boolean allowDouble = true;
    private String baseName;
    private double value;

    /**
     * Initialize a MeasurementType with default values.
     */
    public MeasurementType() {
        this.baseName = "";
        this.value = 0;
    }

    /**
     * Initialize a MeasurementType with provided baseName, value and if the measurement allows non-integer values.
     *
     * @param baseName the measurement name
     * @param value the measurement value
     * @param allowDouble allow double values?
     *
     */
    public MeasurementType(String baseName, double value, boolean allowDouble) {
        this.baseName = baseName;
        this.allowDouble = allowDouble;
        setValue(value);
    }

    /**
     * Initialize a MeasurementType with provided baseName, value.
     *
     * @param baseName the measurement name
     * @param value the measurement value
     *
     */
    public MeasurementType(String baseName, double value) {
        this(baseName,value,true);
    }

    /**
     * Gets the value
     *
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value
     *
     * @param newValue the new value
     */
    public void setValue(double newValue) {
        if (newValue < 0.0) {
            throw new IllegalArgumentException();
        }
        if (!allowDouble) {
            if (newValue%1 != 0.0) {
                throw new IllegalArgumentException();
            }
        }
        value = newValue;
    }

    /**
     * Changes the value
     *
     * @param difference the number to change the value by
     */
    public void changeValue(double difference) {
        double newValue = value+difference;
        setValue(newValue);
    }

    /**
     * Sets the name
     *
     * @param name the new name
     */
    public void setBaseName(String name) {
        baseName = name;
    }

    /**
     * Gets the name
     *
     * @return the name
     */
    public String getBaseName() {
        return baseName;
    }

    /**
     * whether value can be double
     *
     * @return whether value can be double
     */
    public boolean allowDouble() {
        return allowDouble;
    }

    /**
     * Checks if instance equals another
     *
     * @return true if equal, false else
     */
    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(this.getClass())) {
            return false;
        }

        MeasurementType m = (MeasurementType) o;
        if (getBaseName() != m.getBaseName()) {
            return false;
        }
        if (getValue() != m.getValue()) {
            return false;
        }
        if (getValue() != m.getValue()) {
            return false;
        }

        if (allowDouble() != m.allowDouble()) {
            return false;
        }
        return true;
    }

}
