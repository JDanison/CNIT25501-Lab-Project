import java.util.ArrayList;
import java.util.List;

public class Sensor {
    private String type;
    private double threshold;
    private String unit;
    private List<SensorEventListener> listeners = new ArrayList<>();

    public Sensor(String type, double threshold, String unit) {
        this.type = type;
        this.threshold = threshold;
        this.unit = unit;
    }

    public Sensor(String type, double threshold) {
        this.type = type;
        this.threshold = threshold;
        this.listeners = new ArrayList<>();
    }

    public void addListener(SensorEventListener listener) {
        listeners.add(listener);
    }

    public void generateData(double value) {
        System.out.println("[Sensor] " + type + " reported value: " + value + " " + unit);
        SensorEvent event = new SensorEvent(type, value, threshold, unit);
        for (SensorEventListener listener : listeners) {
            listener.onSensorEvent(event);
        }
    }

    /* Generate Random Data every 1 second */
    public void startGeneratingData() {
        javax.swing.Timer timer = new javax.swing.Timer(1000, e -> {
            double baseVariation;

            double chance = Math.random();
            if (chance < 0.85) {
                // 85% chance: small variation ±1
                baseVariation = (Math.random() * 2) - 1;
            } else if (chance < 0.95) {
                // 10% chance: moderate variation ±5
                baseVariation = (Math.random() * 10) - 5;
            } else {
                // 5% chance: large variation ±11
                baseVariation = (Math.random() * 22) - 11;
            }

            double randomValue = threshold + baseVariation;
            generateData(randomValue);
        });
        timer.start();
    }
}
