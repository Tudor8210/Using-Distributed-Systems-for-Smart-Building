package client;
import java.rmi.Naming;
import java.rmi.RemoteException;

import connection_details.ConnectionDetails;
import rmi_interfaces.Hello;

// https://docs.oracle.com/javase/7/docs/api/java/rmi/Naming.html provides a useful resource understand the naming
public class HelloClient {

	public static void main(String args[]) {
		
		
		String bindLocation = "rmi://" + ConnectionDetails.hostname + ":" + ConnectionDetails.port + ConnectionDetails.bindname;
		 
		try {				
			
			//  obtain a proxy object (stub)-  based on a naming 
			Hello obj = (Hello) Naming.lookup( bindLocation ); 
			
			//making the remote procedure call
			String reply = obj.sayHello("Josh");
					
			System.out.println(reply);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
