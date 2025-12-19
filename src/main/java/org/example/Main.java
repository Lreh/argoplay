package org.example;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting argoplay server...");

        // This opens the port so the Service has something to talk to
        com.sun.net.httpserver.HttpServer server =
                com.sun.net.httpserver.HttpServer.create(
                        new java.net.InetSocketAddress(8080), 0);

        server.createContext("/", exchange -> {
            String response = "Hello from ArgoCD Java App!";
            exchange.sendResponseHeaders(200, response.length());
            java.io.OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.start();
        System.out.println("Listening on port 8080...");
    }
}