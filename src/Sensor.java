import java.util.ArrayList;
import java.util.List;

// Sensor class simulating data generation
class Sensor {
    private final String type;
    private final double threshold;
    private final List<SensorEventListener> listeners = new ArrayList<>();

    public Sensor(String type, double threshold) {
        this.type = type;
        this.threshold = threshold;
    }

    public void addListener(SensorEventListener listener) {
        listeners.add(listener);
    }

    public void generateData(double value) {
        System.out.println(type + " Sensor Reading: " + value);
        if (value >= threshold) {
            SensorEvent event = new SensorEvent(type, value);
            for (SensorEventListener listener : listeners) {
                listener.onSensorEvent(event);
            }
        }
    }
}
