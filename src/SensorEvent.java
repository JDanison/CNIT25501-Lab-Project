public class SensorEvent {
    private String type;
    private double value;
    private double threshold;
    private String unit;

    public SensorEvent(String type, double value, double threshold, String unit) {
        this.type = type;
        this.value = value;
        this.threshold = threshold;
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public double getThreshold() {
        return threshold;
    }
    public String getUnit() {
        return unit;
    }
}
