// Event class to represent sensor events
class SensorEvent {
    private final String type;
    private final double value;

    public SensorEvent(String type, double value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }
}

