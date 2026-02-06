void main() {

    ActuatorControl actuatorControl = new ActuatorControl();
    SystemMonitoring sysMonitor1 = new SystemMonitoring();
    TemperatureController tempController = new TemperatureController(actuatorControl, sysMonitor1);

    tempController.setTargetMinTemp(18.0);
    tempController.setTargetMaxTemp(24.0);

    SensorInput sensorInput1 = new SensorInput(tempController, 1);

    sensorInput1.start();

    SensorInput sensorInput2 = new SensorInput(tempController, 2);

    sensorInput2.start();

    SensorInput sensorInput3 = new SensorInput(tempController, 3);

    sensorInput3.start();

    SensorInput sensorInput4 = new SensorInput(tempController, 4);

    sensorInput4.start();
}