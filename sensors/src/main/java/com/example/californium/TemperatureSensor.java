package com.example.californium;

import java.util.Random;

public class TemperatureSensor implements Runnable {

    private static final int MAX_TEMP = 35;
    private static final int MIN_TEMP = 15;
    private static final long TIMEOUT = 10000;
    private final Random random = new Random();
    private final int port;
    private Listener listener;
    private int temp = 0;

    public TemperatureSensor(int port) {
        this.port = port;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public int getTemperature() {
        return temp;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000 * random.nextInt(5));
            while (!Thread.interrupted()) {
                Thread.sleep(TIMEOUT);
                temp = MIN_TEMP + random.nextInt(MAX_TEMP - MIN_TEMP);
                if (listener != null) {
                    listener.update(port, temp);
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Sensor stopped!");
        }
    }

}
