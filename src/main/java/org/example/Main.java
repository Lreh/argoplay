package org.example;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting server...");

        // This opens the port so the Service has something to talk to
        com.sun.net.httpserver.HttpServer server =
                com.sun.net.httpserver.HttpServer.create(
                        new java.net.InetSocketAddress(8080), 0);

//        server.createContext("/", exchange -> {
//            // 1. Get the current time when the request hits
//            java.time.LocalDateTime now = java.time.LocalDateTime.now();
//            java.time.format.DateTimeFormatter formatter =
//                    java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//            // 2. Format the response string
//            String formattedTime = now.format(formatter);
//            String response = "Current Server Time: " + formattedTime;
//
//            // 3. Send the response
//            exchange.getResponseHeaders().set("Content-Type", "text/plain");
//            exchange.sendResponseHeaders(200, response.length());
//
//            try (java.io.OutputStream os = exchange.getResponseBody()) {
//                os.write(response.getBytes());
//            }
//
//            System.out.println("Served request at: " + formattedTime);
//        });


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
