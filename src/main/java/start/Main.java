package start;

import presentation.ClientFrame;
import presentation.OrderFrame;
import presentation.ProductFrame;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Clasa din care pornim aplicatia.
 */
public class Main {
    public static void main(String[] args) throws IllegalAccessException, IntrospectionException, InvocationTargetException, SQLException  //static method
    {
        Scanner console = new Scanner(System.in);
        System.out.println("Which frame do you want to see? ");
        System.out.println("Pick from: Clients/Orders/Products");
        String s = console.next();
        if (s.equals("Clients")) {
            ClientFrame cl = new ClientFrame();
        } else {
            if (s.equals("Orders")) {
                OrderFrame of = new OrderFrame();
            } else {
                if (s.equals("Products")) {
                    ProductFrame pf = new ProductFrame();
                }
            }
        }
    }
}
