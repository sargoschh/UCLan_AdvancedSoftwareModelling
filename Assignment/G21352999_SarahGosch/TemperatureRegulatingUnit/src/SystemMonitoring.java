public class SystemMonitoring {

    /*@ public normal_behavior
      @   requires !Double.isNaN(temp);
      @   requires actuatorStatus != null;
      @   requires targetMin < targetMax;
      @   requires id >= 0;
      @   assignable \nothing;
      @*/
    public void logReading(int id, double targetMin, double targetMax, double temp, String actuatorStatus) {
        System.out.printf(
                "Log: Sensor %d read %.1f°C; Current Actuator Control Status: %s\n",
                id,
                temp,
                actuatorStatus
        );

        if (temp < 0.0 || temp > 40.0) {
            triggerAlert(id, temp);
        } else if(temp < targetMin){
            triggerWarning("low", id, temp);
        } else if(temp > targetMax) {
            triggerWarning("high", id, temp);
        }
    }

    /*@ private normal_behavior
      @   requires sensorId >= 0 && !Double.isNaN(temp);
      @   assignable \nothing;
      @*/
    private void triggerAlert(int sensorId, double temp) {
        System.err.printf(
                "ALERT: Critical Temperature Breach! Current Temp: %.1f°C; Current Sensor: %d",
                temp,
                sensorId
        );
    }

    /*@ private normal_behavior
      @   requires message != null && sensorId >= 0 && !Double.isNaN(temp);
      @   assignable \nothing;
      @*/
    private void triggerWarning(String message, int sensorId, double temp) {
        System.err.printf(
                "WARNING: Temperature too %s! Current Temp: %.1f°C; Current Sensor: %d",
                message,
                temp,
                sensorId
        );
    }
}