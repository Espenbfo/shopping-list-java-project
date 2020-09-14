package shoppinglist.core;

public class MeasurementType {
    boolean allowDouble = false;
    String baseName;
    double value;

    public MeasurementType() {
        this.baseName = "";
        this.value = 0;
    }

    public MeasurementType(String baseName, double value, boolean allowDouble) {
        this.baseName = baseName;
        this.allowDouble = allowDouble;
        setValue(value);
    }

    public MeasurementType(String baseName, double value) {
        this(baseName,value,true);
    }

    public double getValue() {
        return value;
    }

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

    public void changeValue(double difference) {
        double newValue = value+difference;
        setValue(newValue);
    }


    public void setBaseName(String name) {
        baseName = name;
    }

    public String getBaseName() {
        return baseName;
    }

}
