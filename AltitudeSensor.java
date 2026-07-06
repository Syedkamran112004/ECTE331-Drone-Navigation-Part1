package drone;

import java.util.Random;

public class AltitudeSensor {

    private String sensorID;
    private Random generator;

    public AltitudeSensor(String sensorID) {
        this.sensorID = sensorID;
        generator = new Random();
    }

    public String getSensorID() {
        return sensorID;
    }

    public int readSensor() throws SensorReadException {

        int chance = generator.nextInt(100);

        if (chance < 15) {
            throw new SensorReadException(sensorID + " has failed.");
        }

        else if (chance < 30) {

            // Corrupted value
            return 250 + generator.nextInt(100);

        }

        else {

            // Valid altitude
        	return generator.nextInt(201);
        }

    }

}