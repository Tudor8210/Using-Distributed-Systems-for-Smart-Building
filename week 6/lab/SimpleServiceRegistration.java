package simpleJMDNS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

public class SimpleServiceRegistration {

	public static void main(String[] args) {

		//get a jMDNS instance
		try {
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
			
			/*
			 * service information
			 */
			
			//the assumption is that we are registering a http server
			String service_type = "_http._tcp.local.";				//service-type.domain
			// service_name + service_type => simple_http._tcp.local
			String service_name = "simple";							
			int service_port = 9080;
			String service_desc = "example of jmDNS V1.0";
				
			
			//Create ServiceInfo - 
			//use the factory method to create the object			
			ServiceInfo serviceInfo = ServiceInfo.create(service_type, service_name, service_port, service_desc);
			
			/*
			 * register the service
			 */
			
			jmdns.registerService(serviceInfo);
			
			System.out.printf("registering service with type: %s and name: %s on port %d ", service_type, service_name, service_port);
			
			//sleep for 10 seconds
			Thread.sleep(10000);
			
			//unregister the services
			//jmdns.unregisterAllServices();   //you could also unregister a single service
			
			
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
