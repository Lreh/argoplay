package org.example;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Micronaut Starting server...");

         HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", 8080), 0);

        server.createContext("/", exchange -> {
            String now = java.time.LocalDateTime.now().toString();
            String response = "{\"status\":\"UP\", \"serverTime\":\"" + now + "\"}";

            exchange.getResponseHeaders().set("Content-Type", "application/json");

            byte[] responseBytes = response.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, responseBytes.length);

            try (java.io.OutputStream os = exchange.getResponseBody()) {
                os.write(responseBytes);
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
