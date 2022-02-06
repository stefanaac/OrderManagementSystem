package dao;

/**
 * Aceasta clasa extinde clasa AbstractDao si contine merode specifice doar pentru clasa Client.
 */

import connection.ConnectionFactory;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class ClientDAO extends AbstractDAO<Client> {

    private static final String insertStatement = "INSERT INTO client (id,name,email,adress)" + "VALUES (?,?,?,?)";
    private final static String selectAllStatement = "SELECT * FROM client";
    private final static String deleteStatement = "DELETE FROM client WHERE name = ?";
    private final static String firstUpdateStatement ="UPDATE client SET name = ? WHERE id = ?";
    private final static String secondUpdateStatement ="UPDATE client SET adress = ? WHERE id = ?";
    private final static String thirdUpdateStatement ="UPDATE client SET email = ? WHERE id = ?";

    public Client findById(int idOfClientSearched) throws SQLException {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = dbConnection.prepareStatement(ClientDAO.selectAllStatement);
        findStatement.setLong(1, idOfClientSearched);
        ResultSet result = findStatement.executeQuery();;
        result.next();
        String nameString = result.getString("name");
        Client theClient = new Client();
        theClient.setName(nameString);
        ConnectionFactory.close(dbConnection);
        return theClient;
    }


    public ArrayList<Client> listAllClients() throws SQLException {
        ArrayList<Client> clientsList = new ArrayList<Client>();
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(selectAllStatement);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            int idClient = result.getInt("id");
            String clientNameString = result.getString("name");
            String clientAddressString = result.getString("adress");
            String emailAdressString = result.getString("email");
            Client c = new Client();
            c.setId(idClient);
            c.setName(clientNameString);
            c.setAddress(clientAddressString);
            c.setEmail(emailAdressString);
            clientsList.add(c);
        }
        ConnectionFactory.close(connection);
        return clientsList;
    }


    public int insertClient(Client clientToInsert) throws SQLException {
        Random random = new Random();
        int number = random.nextInt(50);
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, number);
        statement.setString(2, clientToInsert.getName());
        statement.setString(3, clientToInsert.getEmail());
        statement.setString(4, clientToInsert.getAddress());
        statement.executeUpdate();
        ConnectionFactory.close(connection);
        return number;
    }


    public void editClient(int id, String name, String adress, String email) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement =  connection.prepareStatement(firstUpdateStatement);
        statement.setString(1, name);
        statement.setInt(2, id);
        statement.executeUpdate();
        statement = connection.prepareStatement(secondUpdateStatement);
        statement.setString(1, adress);
        statement.setInt(2, id);
        statement.executeUpdate();
        statement = connection.prepareStatement(thirdUpdateStatement);
        statement.setString(1, email);
        statement.setInt(2, id);
        statement.executeUpdate();
        ConnectionFactory.close(connection);
    }

    public void deleteClient(String nameOfClientToDelete) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(deleteStatement);;
        statement.setString(1, nameOfClientToDelete);
        statement.executeUpdate();
        ConnectionFactory.close(connection);
    }


}
