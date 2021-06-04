package sr.ice.client;

import java.lang.Exception;

public class Main {

	public static void main(String[] args) throws Exception {
		 Client client = new Client(args);
		 System.out.println(client.add(1, 2));
		 System.out.println(client.subtract(10, 3));
	}

}