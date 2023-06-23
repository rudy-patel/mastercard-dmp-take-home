package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;

@SpringBootApplication(exclude = JacksonAutoConfiguration.class)
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}

// public class App {
//     public static void main(String[] args)throws Exception {
//         // Create the server instance
//         Server server = new Server();

//         // Create a server connector
//         ServerConnector connector = new ServerConnector(server);
//         connector.setPort(8080); // Set the desired port number

//         // Set the connector to the server
//         server.addConnector(connector);

//         // Create a servlet context handler
//         ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
//         contextHandler.setContextPath("/");

//         // Add the servlet to the context handler
//         contextHandler.addServlet(new ServletHolder(new YourServlet()), "/analyzeTransaction");

//         // Set the context handler to the server
//         server.setHandler(contextHandler);

//         try {
//             System.out.println("starting up...");

//             // Start the server
//             server.start();
//             server.join();
//         } catch (Exception e) {
//             e.printStackTrace();
//         } finally {
//             // Stop the server when finished
//             server.destroy();
//             System.out.println("winding down. goodbye.");
//         }
//     }
// }
