import java.util.ArrayList;
import java.util.List;

public class Sensor {
    private String type;
    private double threshold;
    private List<SensorEventListener> listeners;

    public Sensor(String type, double threshold) {
        this.type = type;
        this.threshold = threshold;
        this.listeners = new ArrayList<>();
    }

    public void addListener(SensorEventListener listener) {
        listeners.add(listener);
    }

    public void generateData(double value) {
        System.out.println("[Sensor] " + type + " reported value: " + value);
        if (value > threshold || value < threshold) {
            SensorEvent event = new SensorEvent(type, value, threshold);
            for (SensorEventListener listener : listeners) {
                listener.onSensorEvent(event);
            }
        }
    }
}
