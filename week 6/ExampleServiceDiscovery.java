package jmExample;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

public class ExampleServiceDiscovery {
	
	
	private static ServiceInfo info ;
	
	
	private static class SampleListener implements ServiceListener {

		public void serviceAdded(ServiceEvent event) {
			System.out.println("Service added: " + event.getInfo());

		}

		public void serviceRemoved(ServiceEvent event) {
			System.out.println("Service removed: " + event.getInfo());

		}

		public void serviceResolved(ServiceEvent event) {
			System.out.println("Service resolved: " + event.getInfo());

			info = event.getInfo();

			String computer_name = info.getServer().replaceFirst(".local.", "");
			int port = info.getPort();

			String path = info.getNiceTextString().split("=")[1];
			
			System.out.println("comp.name: " + computer_name);
			System.out.println("port: " + port);
			System.out.println("type:"+ event.getType());
			System.out.println("name: " + event.getName());
			System.out.println("properties: " + info.getNiceTextString());
			System.out.println("path: " + path);
			System.out.println("host: " + info.getHostAddresses()[0]);
					
		}

	}

	
	
	public static void main(String[] args) throws InterruptedException {
		try {
			// Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			String service_type = "_http._tcp.local.";

			// Add a service listener
			jmdns.addServiceListener(service_type, new SampleListener());
			
			
			// Wait a bit
			Thread.sleep(15000);
			
			 jmdns.close();

		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}