package sr.ice.client;

import java.lang.Exception;

import demo.A;

public class Main {

	public static void main(String[] args) throws Exception {
		 Client client = new Client(args);
		 try {
			 System.out.println(client.add(2147483647, 2147483647));
		 } catch (Exception e){
			 System.out.println(e.getMessage());
		 }
		 System.out.println(client.subtract(10, 3));
		 System.out.println(client.op(new A(2, "multiply"), 2));
	}

}