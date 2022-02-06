package dao;
/**
 * Aceasta clasa extinde clasa AbstractDao si contine merode specifice doar pentru clasa Produs
 */

import connection.ConnectionFactory;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

public class ProductDAO extends AbstractDAO<Product> {
    private static final String insertProduct = "INSERT INTO product (id,name,quantity,price)" + " VALUES (?,?,?,?)";
    private final static String findStatement = "SELECT * FROM product where name = ?";


    /**
     * @param nameProduct numele produsului pe care il cautam in tabela.
     * @return returneaza un obiect de tip produs
     * Metoda cauta in tabela de produse produsul cu numele dat ca parametru.daca il gaseste returneaza obiecyul de tip produs,, daca nu returneaza null.
     */
    public Product findByName(String nameProduct) throws SQLException {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = dbConnection.prepareStatement(ProductDAO.findStatement);
        findStatement.setString(1, nameProduct);
        ResultSet result = findStatement.executeQuery();
        result.next();
        int pret = result.getInt("price");
        int cantitate = result.getInt("quantity");
        Product searchedProduct = new Product(nameProduct, pret, cantitate);
        ConnectionFactory.close(dbConnection);
        return searchedProduct;
    }

    /**
     * @return un arraylist cu produsele din tabela product
     */

    public ArrayList<Product> listAllProducts() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        ArrayList<Product> listOfAllPrroducts = new ArrayList<Product>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM product");
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int idProdus = result.getInt("id");
            String productNameString = result.getString("name");
            String quantityString = result.getString("quantity");
            int quantityParsed = Integer.parseInt(quantityString);
            String priceString = result.getString("price");
            float priceParced = Float.parseFloat(priceString);
            Product productFromList = new Product();
            productFromList.setQuantity(quantityParsed);
            productFromList.setPrice(priceParced);
            productFromList.setIdProduct(idProdus);
            productFromList.setProductName(productNameString);
            listOfAllPrroducts.add(productFromList);
        }
        ConnectionFactory.close(connection);
        return listOfAllPrroducts;
    }


    public int insertProduct(Product p) throws SQLException {
        Random random = new Random();
        int number = random.nextInt(50);
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(insertProduct, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, number);
        statement.setString(2, p.getProductName());
        statement.setInt(3, p.getQuantity());
        statement.setFloat(4, p.getPrice());
        statement.executeUpdate();
        ConnectionFactory.close(connection);
        return number;
    }


    public void deleteProduct(Integer productId) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM product WHERE id = ?");
        statement.setInt(1, productId);
        statement.executeUpdate();
        ConnectionFactory.close(connection);
    }


    public void editProduct(int id, String name, Integer quantity, Float price) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = statement = connection.prepareStatement("UPDATE product SET name = ? WHERE id = ?");
        statement.setString(1, name);
        statement.setInt(2, id);
        statement.executeUpdate();
        statement = connection.prepareStatement("UPDATE product SET quantity = ? WHERE id = ?");
        statement.setInt(1, quantity);
        statement.setInt(2, id);
        statement.executeUpdate();
        statement = connection.prepareStatement("UPDATE product SET price = ? WHERE id = ?");
        statement.setFloat(1, price);
        statement.setInt(2, id);
        statement.executeUpdate();
        ConnectionFactory.close(connection);
    }


    public int findQuantity(String name) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();;
        PreparedStatement statement =  connection.prepareStatement("SELECT cantitate FROM product WHERE name = ?;");
        statement.setString(1, name);
        ResultSet  rs = statement.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(3));
            rs.getInt(3);
        }
        ConnectionFactory.close(connection);
        ConnectionFactory.close(rs);
        ConnectionFactory.close(statement);

        return 0;
    }

}
