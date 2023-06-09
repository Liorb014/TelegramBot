package org.example;

public class Utils {
    private static final int SECOND = 1000;
    public static void sleep(int seconds){
        try {
            Thread.sleep(SECOND*seconds);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static final int WINDOW_SIZE = 500;
}
