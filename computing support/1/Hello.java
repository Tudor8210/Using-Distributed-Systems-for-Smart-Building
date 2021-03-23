package rmi_interfaces;
import java.rmi.*;

public interface Hello extends java.rmi.Remote {
	
	public String sayHello(String name) throws RemoteException;

}