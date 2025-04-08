// Alarm system that listens to sensor events
class AlarmSystem implements SensorEventListener {
    @Override
    public void onSensorEvent(SensorEvent event) {
        System.out.println("ALERT! " + event.getType() + " threshold breached. Value: " + event.getValue());
    }
}