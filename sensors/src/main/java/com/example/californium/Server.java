package com.example.californium;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Server extends CoapServer {

    private static final int INITIAL_PORT = 64000;
    private static final int NUMBER_OF_SENSORS = 2;
    private final Map<Integer, TemperatureSensor> sensors = new HashMap<>();

    public Server() {
        super();
        
        TemperatureSensorResource resource = new TemperatureSensorResource(sensors);
        
        for (int i = 0; i < NUMBER_OF_SENSORS; i++) {
            final int port = INITIAL_PORT + i;
            addEndpoint(new CoapEndpoint(port));
            final TemperatureSensor sensor = new TemperatureSensor(port);
            sensor.setListener(resource);
            sensors.put(port, sensor);
        }

        add(resource);
    }

    public static void main(String[] args) {
        Server server = new Server();

        server.start();
    }

    @Override
    public void start() {
        final Collection<TemperatureSensor> values = sensors.values();
        for(TemperatureSensor sensor : values) {
            new Thread(sensor).start();
        }
        
        super.start();
    }

}
