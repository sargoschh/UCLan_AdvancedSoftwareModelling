public class TemperatureController {
    // Requirements define a stable operating range [cite: 10, 16]
    private final double targetMin = 18.0;
    private final double targetMax = 24.0;
    private final ActuatorControl actuator;
    private final SystemMonitoring monitor;

    public TemperatureController(ActuatorControl actuator, SystemMonitoring monitor) {
        this.actuator = actuator;
        this.monitor = monitor;
    }

    /*@ public normal_behavior
      @   requires temp > -50.0 && temp < 100.0;
      @   ensures (temp < targetMin) ==> actuator.isHeating();
      @   ensures (temp > targetMax) ==> actuator.isCooling();
      @*/
    public synchronized void processReading(int sensorId, double temp) {
        monitor.logReading(sensorId, temp);

        if (temp < targetMin) {
            actuator.activateHeating();
        } else if (temp > targetMax) {
            actuator.activateCooling();
        } else {
            actuator.setIdle();
        }
    }
}