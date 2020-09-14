package shoppinglist.core;

public class ShoppingElement {
    String name;
    MeasurementType measurementType;
    boolean shopped;

    public ShoppingElement() {
        name= "";
        measurementType = new MeasurementType();
        shopped = false;
    }

    public ShoppingElement(String name, double value, String measurementName, boolean allowDouble) {
        this.name= name;
        measurementType = new MeasurementType(measurementName,value,allowDouble);
        this.shopped = false;
    }

    public ShoppingElement(String name, double value, String measurementName) {
        this(name,value,measurementName,true);
    }

    public ShoppingElement(String name, MeasurementType m, boolean shopped) {
        this.name= name;
        measurementType = m;
        this.shopped = shopped;
    }

    public double getValue() {
        return measurementType.getValue();
    }

    public void setValue(double newValue) {
        measurementType.setValue(newValue);
    }

    public void changeValue(double difference) {
        measurementType.changeValue(difference);
    }

    public void toggleShopped() {
        shopped = !shopped;
    }

    public boolean isShopped() {
        return shopped;
    }

    public String getName() {
        return name;
    }

    public void getName(String newName) {
        name = newName;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' + "\n" +
                "value=" + getValue() + " " + measurementType.getBaseName() + "\n" +
                "shopped=" + shopped;
    }
}
