package dao;

import connection.ConnectionFactory;
import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class OrderDAO extends AbstractDAO<Order> {
    private final static String findStatement = "SELECT * FROM orders where id = ?";
    private static final String insertStatement = "INSERT INTO orders (id,name,product,price,quantity)" + " VALUES (?,?,?,?,?)";


    public Order findById(int orderID) throws SQLException {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement =  dbConnection.prepareStatement(OrderDAO.findStatement);;
        findStatement.setLong(1, orderID);
        ResultSet result = findStatement.executeQuery();
        result.next();
        String nameString = result.getString("name");
        String productNameString = result.getString("product");
        int quantityWanted = result.getInt("quantity");
        float poductPrice = result.getFloat("price");
        Order searchedOrder = new Order();
        searchedOrder.setIdOrder(orderID);
        searchedOrder.setClient(nameString);
        searchedOrder.setIdProduct(productNameString);
        searchedOrder.setQuantity(quantityWanted);
        searchedOrder.setTotal(poductPrice);
        ConnectionFactory.close(dbConnection);
        return searchedOrder;
    }

    public ArrayList<Order> listAllOrders() throws SQLException {
        ArrayList<Order> ordersList = new ArrayList<Order>();
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders");;
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int idOrder = result.getInt("id");
            String clientNameString = result.getString("name");
            String productNameString = result.getString("product");
            String quantityString = result.getString("quantity");
            String priceString = result.getString("price");
            int quantityParsed= Integer.parseInt(quantityString);
            float priceParced=Float.parseFloat(priceString);
            Order o = new Order();
            o.setIdOrder(idOrder);
            o.setClient(clientNameString);
            o.setIdProduct(productNameString);
            o.setQuantity(quantityParsed);
            o.setTotal(priceParced);
            o.setIdOrder(idOrder);
            ordersList.add(o);
        }
        ConnectionFactory.close(connection);
        return ordersList;
    }


    public int insertMyOrder(Order orderToInsert) throws SQLException {
        Random random = new Random();
        int number = random.nextInt(50);
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);;
        statement.setInt(1, number);
        statement.setString(2, orderToInsert.getClient());
        statement.setString(3, orderToInsert.getProduct());
        statement.setInt(4, orderToInsert.getQuantity());
        statement.setFloat(5, orderToInsert.getTotal());
        statement.executeUpdate();
        ConnectionFactory.close(connection);
        return number;
    }

}
