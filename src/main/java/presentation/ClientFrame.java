package presentation;
/**
 * Clasa care genereaza fereastra "Client" a aplicatiei.
 */

import connection.ConnectionFactory;
import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class ClientFrame {
    Frame clientFrame;
    JPanel panel;
    JButton newClientButton;
    JButton editClientButton;
    JButton deleteClientButton;
    JButton viewClientsButton;

    JLabel name;
    JLabel adress;
    JLabel email;
    JLabel id;

    JTextField nameTextField;
    JTextField adressTextField;
    JTextField emailTextField;
    JTextField idTextField;

    JTable tableForMyClients;
    Connection myConnection;
    DefaultTableModel modelForClientsTable;

    ClientDAO client = new ClientDAO();

    public ClientFrame() throws IllegalAccessException, IntrospectionException, InvocationTargetException, SQLException {
        myConnection = ConnectionFactory.getConnection();

        clientFrame = new JFrame();
        clientFrame.setSize(1530, 830);
        clientFrame.setTitle("CLIENT FRAME");
        clientFrame.setResizable(false);
        clientFrame.setLayout(null);

        Border redline = BorderFactory.createLineBorder(new Color(204, 0, 0), 5);
        Border redline2 = BorderFactory.createLineBorder(new Color(204, 0, 0), 2);

        panel = new JPanel();
        panel.setSize(500, 530);
        panel.setBounds(150, 50, 1200, 700);
        //panel.setBackground(new Color(77, 169, 255));
        panel.setBackground(new Color(255, 243, 230));
        panel.setBorder(redline2);
        panel.setLayout(null);
        panel.setVisible(true);
        clientFrame.add(panel);


        viewClientsButton = new JButton("VIEW CLIENTS");
        viewClientsButton.setBounds(100, 410, 300, 70);
        viewClientsButton.setBackground(Color.WHITE);
        viewClientsButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        viewClientsButton.setForeground(new Color(204, 0, 0));
        viewClientsButton.setFocusable(false);
        viewClientsButton.setBorder(redline2);
        viewClientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == viewClientsButton) {

                    clientFrame.dispose();
                    try {
                        ClientFrame newCf = new ClientFrame();
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    } catch (IntrospectionException introspectionException) {
                        introspectionException.printStackTrace();
                    } catch (InvocationTargetException invocationTargetException) {
                        invocationTargetException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            }
        });
        panel.add(viewClientsButton);

        editClientButton = new JButton("EDIT CLIENT");
        editClientButton.setBounds(100, 200, 300, 70);
        //editClient.setBackground(new Color(204, 230, 255));
        editClientButton.setBackground(Color.WHITE);
        editClientButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        editClientButton.setForeground(new Color(204, 0, 0));
        editClientButton.setFocusable(false);
        editClientButton.setBorder(redline2);
        editClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == editClientButton) {
                    try {
                        client.editClient(Integer.parseInt(idTextField.getText()), nameTextField.getText(), adressTextField.getText(), emailTextField.getText());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        panel.add(editClientButton);

        newClientButton = new JButton("NEW CLIENT");
        newClientButton.setBounds(100, 90, 300, 70);
        newClientButton.setBackground(Color.WHITE);
        newClientButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        newClientButton.setForeground(new Color(204, 0, 0));
        newClientButton.setFocusable(false);
        newClientButton.setBorder(redline2);
        newClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == newClientButton) {
                    Client clientt = new Client(nameTextField.getText(), adressTextField.getText(), emailTextField.getText());
                    try {
                        client.insertClient(clientt);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

            }
        });
        panel.add(newClientButton);


        deleteClientButton = new JButton("DELETE CLIENT");
        deleteClientButton.setBounds(100, 310, 300, 70);
        deleteClientButton.setBackground(Color.WHITE);
        deleteClientButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        deleteClientButton.setForeground(new Color(204, 0, 0));
        deleteClientButton.setFocusable(false);
        deleteClientButton.setBorder(redline2);
        deleteClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == deleteClientButton) {

                    String n = nameTextField.getText();
                    try {
                        client.deleteClient(n);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    clientFrame.dispose();
                    try {
                        ClientFrame v = new ClientFrame();
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    } catch (IntrospectionException introspectionException) {
                        introspectionException.printStackTrace();
                    } catch (InvocationTargetException invocationTargetException) {
                        invocationTargetException.printStackTrace();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        panel.add(deleteClientButton);


        name = new JLabel("NAME");
        name.setBounds(50, 510, 100, 40);
        name.setFont(new Font("Monospaced", Font.BOLD, 30));
        name.setForeground(new Color(204, 0, 0));
        panel.add(name);

        nameTextField = new JTextField();
        nameTextField.setBounds(30, 570, 180, 20);
        nameTextField.setBorder(redline2);
        panel.add(nameTextField);

        adress = new JLabel("ADRESS");
        adress.setBounds(250, 510, 200, 40);
        adress.setFont(new Font("Monospaced", Font.BOLD, 30));
        adress.setForeground(new Color(204, 0, 0));
        panel.add(adress);

        adressTextField = new JTextField();
        adressTextField.setBounds(230, 570, 180, 20);
        adressTextField.setBorder(redline2);
        panel.add(adressTextField);

        email = new JLabel("EMAIL");
        email.setBounds(450, 510, 200, 40);
        email.setFont(new Font("Monospaced", Font.BOLD, 30));
        email.setForeground(new Color(204, 0, 0));
        panel.add(email);

        emailTextField = new JTextField();
        emailTextField.setBounds(430, 570, 180, 20);
        emailTextField.setBorder(redline2);
        panel.add(emailTextField);


        id = new JLabel("ID");
        id.setBounds(250, 610, 200, 40);
        id.setFont(new Font("Monospaced", Font.BOLD, 30));
        id.setForeground(new Color(204, 0, 0));
        panel.add(id);

        idTextField = new JTextField();
        idTextField.setBounds(230, 670, 180, 20);
        idTextField.setBorder(redline2);
        panel.add(idTextField);


        /**
         * Creating a model for my client table.
         */
        modelForClientsTable = new DefaultTableModel();
        client.getMyTable(modelForClientsTable, client.listAllClients());
        modelForClientsTable.addColumn("Id");
        modelForClientsTable.addColumn("Name");
        modelForClientsTable.addColumn("email");
        modelForClientsTable.addColumn("adress");

        /**
         * Creatig a table to store the clients.
         */
        tableForMyClients = new JTable(modelForClientsTable);
        tableForMyClients.setBounds(530, 100, 600, 350);
        tableForMyClients.setBackground(new Color(204, 230, 255));
        tableForMyClients.setBackground(Color.WHITE);
        tableForMyClients.setFont(new Font("MV Boly", Font.BOLD, 15));
        tableForMyClients.setForeground(new Color(204, 0, 0));
        tableForMyClients.setBorder(redline2);
        panel.add(tableForMyClients);
        try {
            //Client c:client.listAllClients()
            for (int i = 0; i < client.listAllClients().size(); i++) {
                Client c = client.listAllClients().get(i);
                String nameString = String.valueOf(c.getName());
                String idString = String.valueOf(c.getId());
                String emailString = String.valueOf(c.getEmail());
                String addressString = String.valueOf(c.getAddress());
                modelForClientsTable.addRow(new Object[]{idString, nameString, addressString, emailString});
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        clientFrame.setVisible(true);
    }
}
