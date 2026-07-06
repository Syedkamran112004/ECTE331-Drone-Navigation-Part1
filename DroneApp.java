package drone;

public class DroneApp {

    public static void main(String[] args) {
    	// TODO Auto-generated method stub

        EventLogger logger = new EventLogger("log.txt");

        AltitudeSensor sensorA = new AltitudeSensor("Sensor A");
        AltitudeSensor sensorB = new AltitudeSensor("Sensor B");
        AltitudeSensor sensorC = new AltitudeSensor("Sensor C");

        FlightComputer computer = new FlightComputer(logger);

        try {

            for (int i = 1; i <= 20; i++) {

                System.out.println("Cycle " + i + " -----");

                int readingA = readOneSensor(sensorA, logger);
                int readingB = readOneSensor(sensorB, logger);
                int readingC = readOneSensor(sensorC, logger);

                System.out.println("A = " + readingA);
                System.out.println("B = " + readingB);
                System.out.println("C = " + readingC);

                int finalAltitude = computer.checkAltitude(readingA, readingB, readingC);

                System.out.println("Final altitude = " + finalAltitude);
                System.out.println("System status = NORMAL");
                System.out.println();

            }

        } catch (SystemReliabilityException e) {

            System.out.println(e.getMessage());
            System.out.println("SAFE MODE");
            System.out.println("Check log.txt for recorded events.");
            logger.writeLog("Program stopped because drone entered SAFE MODE.");

        }

    }

    public static int readOneSensor(AltitudeSensor sensor, EventLogger logger) {

        int reading = -1;

        try {

            reading = sensor.readSensor();

            if (reading < 0 || reading > 200) {
                System.out.println(sensor.getSensorID() + " corrupted reading.");
                logger.writeLog(sensor.getSensorID() + " corrupted reading: " + reading);
            }

        } catch (SensorReadException e) {

            System.out.println(e.getMessage());
            logger.writeLog(e.getMessage());

        }

        return reading;
    }
}