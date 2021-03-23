package HelloApp;

import org.omg.CORBA.*; // All CORBA
import java.io.*; // needed for output to the file system

public class HelloClient {
	public static void main(String args[]) {
		try {
			// Initialize the ORB
			ORB orb = ORB.init(args, null);
			// Read the object Reference for the HelloServant
			BufferedReader br = new BufferedReader(
				new FileReader("HelloIOR")
			);
			String ior = br.readLine();
			// Convert the string object reference to an object
			org.omg.CORBA.Object obj = orb.string_to_object(ior);

			// Convert the object to the correct type i . e . Hello
			Hello helloRef = HelloHelper.narrow(obj);

			// Call the operation on the servant
			
			int hello = helloRef.sayHello();
			System.out.println(hello);
		} catch (Exception e) {
			System.out.println(" ERROR : " + e);
			e.printStackTrace(System.out);
		}
	}
}