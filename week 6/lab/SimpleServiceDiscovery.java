package simpleJMDNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

public class SimpleServiceDiscovery {
	
	//option 1
	private static class MyServiceListener implements ServiceListener{

		public void serviceAdded(ServiceEvent event) {
			// TODO Auto-generated method stub
			System.out.println("Service Added " + event.getInfo());
			
		}

		public void serviceRemoved(ServiceEvent event) {
			// TODO Auto-generated method stub
			System.out.println("Service Removed " + event.getInfo());
			
		}

		public void serviceResolved(ServiceEvent event) {
			// TODO Auto-generated method stub
			System.out.println("Service Resolved " + event.getInfo());
			
			ServiceInfo serviceInfo = event.getInfo();
			System.out.println("host " + serviceInfo.getHostAddress());
			System.out.println("port " + serviceInfo.getPort());
			System.out.println("type " + serviceInfo.getType());
			System.out.println("name " + serviceInfo.getName());
			System.out.println("Computername " + serviceInfo.getServer());
			System.out.println("desc/properties " + serviceInfo.getNiceTextString());
			
		}
		
	}

	public static void main(String[] args) {
		// get an instance of jmDNS
		
		
		try {
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			//will discover the service based on service type
			String service_type = "_http._tcp.local";	
			
			
			//need to listen for services added/removed etc.
			//jmdns.addServiceListener(service_type, new MyServiceListener());         //listen fro specified type
			jmdns.addServiceListener("_http._tcp.local.", new MyServiceListener());
			
			
			//sleep for 10 seconds
			Thread.sleep(10000);
			
			jmdns.close();
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
