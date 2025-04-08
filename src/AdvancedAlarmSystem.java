public class AdvancedAlarmSystem extends AlarmSystem {
    @Override
    public void onSensorEvent(SensorEvent event) {
        double deviation = event.getValue() - event.getThreshold();
        double absDeviation = Math.abs(deviation);
        String formattedDeviation = String.format("%.2f", absDeviation);
        String direction = deviation > 0 ? "above" : "below";

        if (absDeviation > 10) {
            System.out.println("CRITICAL ALERT! " + event.getType() + " is " + direction + " threshold by " + formattedDeviation);
        } else if (absDeviation > 5) {
            System.out.println("WARNING! " + event.getType() + " is moderately " + direction + " threshold. Deviation: " + formattedDeviation);
        } else if (absDeviation > 2) {
            System.out.println("MILD ALERT: " + event.getType() + " is slightly " + direction + " threshold. Deviation: " + formattedDeviation);
        }
    }
}