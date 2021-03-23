package HelloApp;

import java.util.Random;

import HelloApp.*;

public class HelloServant extends _HelloImplBase{


	@Override
	public int sayHello () {
		Random r = new Random();
		// Add the method implementation here in the next step .
		return r.nextInt(9);
	}

}
