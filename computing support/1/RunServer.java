package server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import connection_details.ConnectionDetails;
import rmi_interfaces.Hello;

public class RunServer {
	
	
	public static void main(String[] args){
		
		System.setProperty ("java.rmi.server.hostname", ConnectionDetails.hostname) ;
						
		startRegistry();
		
		try {
		
			HelloServer obj = new HelloServer();
			
			// Bind this object instance with name "HelloServer" to registry
			Naming.rebind(ConnectionDetails.bindname, obj);
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	private static void startRegistry() {
		
		try {			
			
			LocateRegistry.createRegistry(ConnectionDetails.port);
		
			System.out.println("RMI registry is ready ...");
			
		}catch (Exception e) {
			System.out.println("Exception starting RMI Registry " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}