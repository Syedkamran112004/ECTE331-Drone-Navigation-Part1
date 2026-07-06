package drone;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class EventLogger {

    private String fileName;

    public EventLogger(String fileName) {
        this.fileName = fileName;
    }

    public void writeLog(String message) {

        try {
            FileWriter writer = new FileWriter(fileName, true);

            writer.write(LocalDateTime.now() + " - " + message + "\n");

            writer.close();
        } catch (IOException e) {
            System.out.println("Could not write to log file.");
        }
    }
}