public class SensorInput extends Thread {
    private final TemperatureController controller;
    /*@ public invariant sensorId >= 0; @*/
    private final int sensorId;
    private volatile boolean running = true;

    /*@ public normal_behavior
      @   requires controller != null && id >= 0;
      @   ensures this.controller == controller && this.sensorId == id;
      @*/
    public SensorInput(TemperatureController controller, int id) {
        this.controller = controller;
        this.sensorId = id;
    }

    @Override
    public void run() {
        while (running) {
            double temp = readHardwareSensor();

            controller.processReading(sensorId, temp);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }

    /*@ public normal_behavior
      @   ensures \result >= -50.0 && \result <= 100.0;
      @*/
    private double readHardwareSensor() {
        return 15.0 + (Math.random() * 15.0);
    }
}