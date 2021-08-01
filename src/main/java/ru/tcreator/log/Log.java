package ru.tcreator.log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    public static Logger logger = Logger.getLogger("Logger");

    static {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("Y-M-d");
        format.format(date);

        boolean append = true;
        try {
            FileHandler fh = new FileHandler("src/main/resources/logs/" + format.format(date) + ".log", append);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
