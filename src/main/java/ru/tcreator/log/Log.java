package ru.tcreator.log;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    /**
     * пишет лог в файл дублируя в консоль
     * @param clazz класс
     * @param level уровень лога
     * @param msg сообщение
     */
    //TODO Logger пишет лог из текущего окружения и плевать, какое имя класса ему передаёшь
    //это маразм уже
    static public void toLog(Class<?> clazz, Level level, String msg) {
        Logger logger = Logger.getLogger(clazz.getName());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("Y-M-d");
        format.format(date);

        boolean append = true;
        try {
            FileHandler fh = new FileHandler("src/main/resources/logs/" + format.format(date) + ".log", append);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            fh.setLevel(level);
            logger.log(level, msg);
            fh.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * пишет исключение в файл лога дублируя в консоль
     * @param clazz класс в котором произошло исключение
     * @param method метод в котором произошло исключение
     * @param ex объект исключения
     */
    //TODO Logger пишет лог из текущего окружения и плевать, какое имя класса ему передаёшь
    //это маразм уже
    public static void logTrow(Class<?> clazz, String method, Exception ex) {
        Logger logger = Logger.getLogger(clazz.getName());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("Y-M-d");
        format.format(date);

        boolean append = true;
        try {
            FileHandler fh = new FileHandler("src/main/resources/logs/" + format.format(date) + ".log", append);
            logger.addHandler(fh);
            fh.setFormatter(new SimpleFormatter());

            logger.throwing(clazz.getName(), method, ex);
            fh.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
