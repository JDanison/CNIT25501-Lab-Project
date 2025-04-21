public class EmbeddedAlarmSystem {
    public static void main(String[] args) {
        // Show login dialog
        LoginDialog loginDialog = new LoginDialog();
        loginDialog.setVisible(true);
        if (!loginDialog.isLoggedIn()) {
            System.exit(0);
        }

        // Show sensor creation dialog
        SensorCreationDialog sensorDialog = new SensorCreationDialog();
        sensorDialog.setVisible(true);
        if (!sensorDialog.isConfirmed()) {
            System.exit(0);
        }

        // Create GUI
        SensorGUI gui = new SensorGUI();
        gui.setVisible(true);

        // Create sensor based on user input
        Sensor sensor = new Sensor(sensorDialog.getSensorType(), sensorDialog.getThreshold(), sensorDialog.getUnit());        SensorEventListener alarmSystem = new AdvancedAlarmSystem();
        sensor.addListener(alarmSystem);
        sensor.addListener(gui);
        sensor.startGeneratingData();
    }
}