package by.smirnov.threadsstore.utils;

public class Sleeper {

    private Sleeper(){}

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
