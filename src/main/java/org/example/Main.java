package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting server...");

        // This opens the port so the Service has something to talk to
        // Force binding to 0.0.0.0 (all IPv4 interfaces)
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0);


        // Main Business Logic
        server.createContext("/", exchange -> {
            String response = "Current Server Time: " + java.time.LocalDateTime.now();
            exchange.sendResponseHeaders(200, response.length());
            try (java.io.OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });

        // Dedicated Health Check for Kubernetes Probes
        server.createContext("/health", exchange -> {
            String response = "OK";
            exchange.sendResponseHeaders(200, response.length());
            try (java.io.OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });

        server.start();
        System.out.println("Listening on port 8080 ...");
    }
}
