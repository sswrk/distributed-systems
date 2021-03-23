package homework.supplier;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SupplierApp {

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("SUPPLIER NAME: ");
        String supplierName = reader.readLine();

        Supplier supplier = new Supplier(supplierName);
        supplier.start();

    }

}
