public class SystemMonitoring {

    /*@ public normal_behavior
      @   requires !Double.isNaN(temp);
      @   assignable \nothing;
      @   // Logic: Alerts are triggered if temp is outside safety bounds
      @*/
    public void logReading(int id, double temp) {
        System.out.printf("Log: Sensor %d read %.1f°C\n", id, temp);

        if (temp < 0.0 || temp > 50.0) {
            triggerAlert("Critical Temperature Breach!");
        }
    }

    /*@ private normal_behavior
      @   requires message != null;
      @*/
    private void triggerAlert(String message) {
        System.err.println("ALERT: " + message);
    }
}