public class TemperatureController {
    /*@ public invariant targetMin < targetMax; @*/
    /*@ public invariant targetMin > 0.0 && targetMax < 50.0; @*/
    private double targetMin = 18.0;
    private double targetMax = 24.0;
    private final ActuatorControl actuator;
    private final SystemMonitoring monitor;

    /*@ public normal_behavior
      @   requires actuator != null && monitor != null;
      @   ensures this.actuator == actuator && this.monitor == monitor;
      @*/
    public TemperatureController(ActuatorControl actuator, SystemMonitoring monitor) {
        this.actuator = actuator;
        this.monitor = monitor;
    }

    /*@ public normal_behavior
      @   requires !Double.isNaN(temp) && temp > -50.0 && temp < 100.0;
      @   assignable \nothing;
      @   // Postcondition: Actuator state must match the temperature logic [cite: 42, 47]
      @   ensures (temp < targetMin) ==> actuator.isHeating();
      @   ensures (temp > targetMax) ==> actuator.isCooling();
      @   ensures (temp >= targetMin && temp <= targetMax) ==>
      @           (!actuator.isHeating() && !actuator.isCooling());
      @*/
    public synchronized void processReading(int sensorId, double temp) {

        if (temp < targetMin) {
            if(!actuator.isHeating()) {
                actuator.activateHeating();
            }
        } else if (temp > targetMax) {
            if(!actuator.isCooling()) {
                actuator.activateCooling();
            }
        } else {
            actuator.setIdle();
        }

        monitor.logReading(sensorId, targetMin, targetMax, temp, actuator.getCurrentActuatorControlStatus());
    }

    /*@ public normal_behavior
      @   requires newTemp < targetMax && newTemp > 0;
      @   assignable targetMin;
      @   ensures targetMin == newTemp;
      @*/
    public void setTargetMinTemp(double newTemp) {
        this.targetMin = newTemp;
    }

    /*@ public normal_behavior
      @   requires newTemp > targetMin && newTemp < 40;
      @   assignable targetMax;
      @   ensures targetMax == newTemp;
      @*/
    public void setTargetMaxTemp(double newTemp) {
        this.targetMax = newTemp;
    }
}