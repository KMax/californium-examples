package com.example.californium;

import java.util.Map;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import static org.eclipse.californium.core.coap.MediaTypeRegistry.APPLICATION_JSON;
import org.eclipse.californium.core.network.Exchange;
import org.eclipse.californium.core.server.resources.CoapExchange;

public class TemperatureSensorResource extends CoapResource implements Listener {

    private final Map<Integer, TemperatureSensor> sensors;

    public TemperatureSensorResource(Map<Integer, TemperatureSensor> sensors) {
        super("sensor");

        this.sensors = sensors;

        getAttributes().setTitle("Temperature Sensor Resource");
        getAttributes().setObservable();

        setObservable(true);
        setObserveType(CoAP.Type.CON);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        System.out.println("TemperatureSensor.handleGet called");
        final int port = exchange.advanced().getEndpoint().getAddress().getPort();
        exchange.respond(CoAP.ResponseCode.CONTENT,
                "{ \"temp\": " + sensors.get(port).getTemperature()
                + ", \"sensor\": " + port
                + ", \"time\": " + System.currentTimeMillis() + " }",
                APPLICATION_JSON);
    }

    @Override
    public void handleDELETE(CoapExchange exchange) {
        System.out.println("TemperatureSensor.handleDELETE called");
        exchange.respond(CoAP.ResponseCode.DELETED);
    }

    @Override
    public void handlePUT(CoapExchange exchange) {
        System.out.println("TemperatureSensor.handlePUT called");
        exchange.respond(CoAP.ResponseCode.CHANGED);
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        System.out.println("TemperatureSensor.handlePOST called");
        exchange.respond(CoAP.ResponseCode.CREATED);
    }

    @Override
    public void handleRequest(Exchange exchange) {
        System.out.println("TemperatureSensor.handleRequest called");
        super.handleRequest(exchange);
    }

    @Override
    public void update(int port, int temp) {
        System.out.println(System.currentTimeMillis() + " .update called: " + port + " " + temp);
        changed();
    }

}
