package jmExample;

import java.io.IOException;
import java.net.InetAddress;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class ExampleServiceRegistration {

    public static void main(String[] args) throws InterruptedException {

        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            
            String service_type = "_http._tcp.local.";
            String service_name = "example";
            int service_port = 1234;
            String service_properties = "path=index.html";
            
            // Register a service
            ServiceInfo serviceInfo = ServiceInfo.create(service_type, service_name, service_port, service_properties);
            jmdns.registerService(serviceInfo);
            
            System.out.printf("registrering service with type %s and name %s \n", service_type, service_name);
            
            // Wait a bit
            Thread.sleep(10000);

            // Unregister all services
            jmdns.unregisterAllServices();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}