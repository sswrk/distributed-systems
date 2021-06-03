package server;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectAdapter;

public class Server {
	public static void main(String[] args) {
		int status = 0;
		Communicator communicator = null;

		try {
			communicator = Util.initialize(args);
			ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");

			ServantLocatorImpl locator = new ServantLocatorImpl();
			adapter.addServantLocator(locator, "");

			adapter.activate();

			System.out.println("Server started");
			communicator.waitForShutdown();
		}
		catch (Exception e) {
			System.err.println(e);
			status = 1;
		}
		if (communicator != null) {
			try {
				communicator.destroy();
			}
			catch (Exception e) {
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}
}
