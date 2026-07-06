package drone;

public class FlightComputer {

    private int previousAltitude;
    private int failureCounter;
    private EventLogger logger;

    public FlightComputer(EventLogger logger) {
        previousAltitude = 100;
        failureCounter = 0;
        this.logger = logger;
    }

    public int checkAltitude(int valueA, int valueB, int valueC)
            throws SystemReliabilityException {

        boolean validA = isValid(valueA);
        boolean validB = isValid(valueB);
        boolean validC = isValid(valueC);

        int validCount = 0;

        if (validA) {
            validCount++;
        }

        if (validB) {
            validCount++;
        }

        if (validC) {
            validCount++;
        }

        if (validCount < 2) {
            failureCounter++;
            logger.writeLog("Reliability warning: fewer than two valid sensor readings.");
            checkSafeMode();
            return previousAltitude;
        }

        if (validA && validB && valueA == valueB) {
            logger.writeLog("Outlier detected: Sensor C");
            return majorityFound(valueA, "Sensor A and Sensor B");
        }

        if (validA && validC && valueA == valueC) {
            logger.writeLog("Outlier detected: Sensor B");
            return majorityFound(valueA, "Sensor A and Sensor C");
        }

        if (validB && validC && valueB == valueC) {
            logger.writeLog("Outlier detected: Sensor A");
            return majorityFound(valueB, "Sensor B and Sensor C");
        }

        failureCounter++;
        logger.writeLog("No majority found. Using previous altitude: " + previousAltitude);
        checkSafeMode();

        return previousAltitude;
    }

    private boolean isValid(int value) {
        return value >= 0 && value <= 200;
    }

    private int majorityFound(int altitude, String sensors) {
        failureCounter = 0;
        previousAltitude = altitude;

        logger.writeLog("Majority decision from " + sensors + ". Altitude = " + altitude);

        return altitude;
    }

    private void checkSafeMode() throws SystemReliabilityException {
        if (failureCounter >= 2) {
            logger.writeLog("SAFE MODE activated.");
            throw new SystemReliabilityException("System entered SAFE MODE.");
        }
    }
}