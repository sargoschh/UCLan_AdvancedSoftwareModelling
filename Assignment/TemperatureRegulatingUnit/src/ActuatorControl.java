public class ActuatorControl {
    private String status = "IDLE";

    /*@ public invariant status.equals("IDLE") ||
      @                  status.equals("HEATING") ||
      @                  status.equals("COOLING");
      @*/

    /*@ public normal_behavior
      @   assignable status;
      @   ensures status.equals("HEATING");
      @*/
    public synchronized void activateHeating() {
        status = "HEATING";
    }

    /*@ public normal_behavior
      @   assignable status;
      @   ensures status.equals("COOLING");
      @*/
    public synchronized void activateCooling() {
        status = "COOLING";
    }

    /*@ public normal_behavior
      @   assignable status;
      @   ensures status.equals("IDLE");
      @*/
    public synchronized void setIdle() {
        status = "IDLE";
    }

    /*@ public pure behavior
      @   ensures \result == status.equals("HEATING");
      @*/
    public synchronized boolean isHeating() { return status.equals("HEATING"); }
}