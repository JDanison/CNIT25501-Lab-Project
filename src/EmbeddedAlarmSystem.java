/* Main Application */
public class EmbeddedAlarmSystem {
    public static void main(String[] args) {
        Sensor temperatureSensor = new Sensor("Temperature", 75.0);
        Sensor pressureSensor = new Sensor("Pressure", 30.0);

        // Create GUI first
        SensorGUI gui = new SensorGUI();
        gui.setVisible(true);

        // Register listeners
        SensorEventListener advancedAlarmSystem = new AdvancedAlarmSystem();

        // Add both alarm system and GUI as listeners
        temperatureSensor.addListener(advancedAlarmSystem);
        temperatureSensor.addListener(gui);

        pressureSensor.addListener(advancedAlarmSystem);
        pressureSensor.addListener(gui);

        // Start data generation
        temperatureSensor.startGeneratingData();
        pressureSensor.startGeneratingData();
    }
}