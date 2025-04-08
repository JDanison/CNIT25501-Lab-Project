public class SensorEvent {
    private String type;
    private double value;
    private double threshold;

    public SensorEvent(String type, double value, double threshold) {
        this.type = type;
        this.value = value;
        this.threshold = threshold;
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
}
