/* Main Application */
public class EmbeddedAlarmSystem {
    public static void main(String[] args) {
        Sensor temperatureSensor = new Sensor("Temperature", 75.0);
        Sensor pressureSensor = new Sensor("Pressure", 30.0);

        // Use the AdvancedAlarmSystem for both to see all alert levels
        SensorEventListener advancedAlarmSystem = new AdvancedAlarmSystem();

        temperatureSensor.addListener(advancedAlarmSystem);
        pressureSensor.addListener(advancedAlarmSystem);

        // Simulating sensor data to trigger all levels
        temperatureSensor.generateData(75.3);  // NO ALERT
        temperatureSensor.generateData(77.3);  // MILD ALERT
        temperatureSensor.generateData(81.0);  // WARNING
        temperatureSensor.generateData(88.0);  // CRITICAL ALERT

        pressureSensor.generateData(30.1);      // NO ALERT
        pressureSensor.generateData(32.5);     // MILD ALERT
        pressureSensor.generateData(36.2);     // WARNING
        pressureSensor.generateData(42.5);     // CRITICAL ALERT
    }
}

