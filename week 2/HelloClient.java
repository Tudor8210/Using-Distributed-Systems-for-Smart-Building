import HelloApp.*; // The package containing generated stubs .
import org.omg.CORBA.*; // All CORBA
// needed for output to the file system .
import java.io.*;


public class HelloClient {
// Add the main method here in the next step .
	public static void main ( String args []) {
	// Put the try - catch block here in the next step .

	System.out.println("Client Starting");
	try { // Add the rest of the HelloStringifiedClient code here .
		// Initialize the ORB
		ORB orb = ORB.init ( args , null ) ;
		// Read the object Reference for the HelloServant
		BufferedReader br = new BufferedReader ( new FileReader ("HelloIOR ") ) ;
		String ior = br.readLine () ;
		// Convert the string object reference to an object
		org.omg.CORBA.Object obj = orb . string_to_object ( ior ) ;
		// Convert the object to the correct type i.e.Hello
		Hello helloRef = HelloHelper.narrow ( obj ) ;
		// Call theoperation on the servant
		String hello = helloRef.sayHello () ;
		System.out.println ( hello ) ;
	}
	catch ( Exception e ) {
	System.out.println (" ERROR : " + e) ;
	e.printStackTrace ( System.out ) ;
}
}
}