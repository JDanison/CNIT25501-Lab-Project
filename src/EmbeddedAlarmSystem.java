// Main application
public class EmbeddedAlarmSystem {
    public static void main(String[] args) {
        Sensor temperatureSensor = new Sensor("Temperature", 75.0);
        Sensor pressureSensor = new Sensor("Pressure", 30.0);

        AlarmSystem alarmSystem = new AlarmSystem();
        temperatureSensor.addListener(alarmSystem);
        pressureSensor.addListener(alarmSystem);

        // Simulating sensor data
        temperatureSensor.generateData(72.5);
        temperatureSensor.generateData(76.3);
        pressureSensor.generateData(28.7);
        pressureSensor.generateData(30.5);
    }
}