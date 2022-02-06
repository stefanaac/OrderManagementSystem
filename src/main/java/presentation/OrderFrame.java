package presentation;

import com.itextpdf.text.DocumentException;
import connection.ConnectionFactory;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Order;
import model.Product;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.IntrospectionException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clasa care genereaza fereastra "Orders" a aplicatiei.
 */

public class OrderFrame {
    Frame ordersFrame;
    JPanel panel;


    JButton addOrderButton;
    JButton generateBillButton;
    JButton viewOrders;


    JLabel quantity;
    JLabel clientName;
    JLabel price;
    JLabel id;


    JTextField nameTextField;
    JTextField quantityTextField;
    JTextField priceTextField;
    JTextField clientNameTextField;
    JTextField idTextField;


    JTable ordersTable;
    DefaultTableModel modelForOrdersTable;
    Connection myConnection;
    OrderDAO order = new OrderDAO();
    ProductDAO p = new ProductDAO();

    public OrderFrame() throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        myConnection = ConnectionFactory.getConnection();
        ordersFrame = new JFrame();
        ordersFrame.setSize(1530, 830);
        ordersFrame.setTitle("CLIENT FRAME");
        ordersFrame.setResizable(false);
        ordersFrame.setLayout(null);

        Border redline = BorderFactory.createLineBorder(new Color(204, 0, 0), 5);
        Border redline2 = BorderFactory.createLineBorder(new Color(204, 0, 0), 2);
        panel = new JPanel();
        panel.setSize(500, 530);
        panel.setBounds(150, 50, 1200, 700);
        panel.setBackground(new Color(255, 243, 230));
        panel.setLayout(null);
        panel.setVisible(true);
        panel.setBorder(redline2);
        ordersFrame.add(panel);


        id = new JLabel("ID");
        id.setBounds(420, 310, 50, 40);
        id.setFont(new Font("Monospaced", Font.BOLD, 30));
        id.setForeground(new Color(204, 0, 0));
        panel.add(id);

        idTextField = new JTextField();
        idTextField.setBounds(420, 350, 50, 20);
        idTextField.setBorder(redline2);
        panel.add(idTextField);


        viewOrders = new JButton("VIEW ORDERS");
        viewOrders.setBounds(100, 90, 300, 70);
        viewOrders.setBackground(Color.WHITE);
        viewOrders.setFont(new Font("Monospaced", Font.BOLD, 30));
        viewOrders.setForeground(new Color(204, 0, 0));
        viewOrders.setFocusable(false);
        viewOrders.setBorder(redline2);
        viewOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == viewOrders) {
                    ordersFrame.dispose();
                    try {
                        OrderFrame newOrderFrame = new OrderFrame();
                    } catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    } catch (IntrospectionException introspectionException) {
                        introspectionException.printStackTrace();
                    } catch (InvocationTargetException invocationTargetException) {
                        invocationTargetException.printStackTrace();
                    }
                }

            }
        });
        panel.add(viewOrders);

        addOrderButton = new JButton("ADD ORDER");
        addOrderButton.setBounds(100, 200, 300, 70);
        addOrderButton.setBackground(Color.WHITE);
        addOrderButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        addOrderButton.setForeground(new Color(204, 0, 0));
        addOrderButton.setBorder(redline2);
        addOrderButton.setFocusable(false);
        addOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addOrderButton) {


                    String numeProd = nameTextField.getText();
                    Integer cantitate = Integer.parseInt(quantityTextField.getText());

                    Product pp = null;
                    try {
                        pp = p.findByName(numeProd);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    if (pp == null) {
                        JOptionPane.showMessageDialog(null, "Nu exista acest produs!");
                    }
                    if (pp.getQuantity() < cantitate) {
                        JOptionPane.showMessageDialog(null, "There are no sufficient products in stock!");
                    } else {

                        pp.setQuantity(pp.getQuantity() - cantitate);
                    }
                    Order o = new Order(clientNameTextField.getText(), nameTextField.getText(), Integer.parseInt(quantityTextField.getText()), Float.parseFloat(priceTextField.getText()));
                    try {
                        order.insertMyOrder(o);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }


                }
            }
        });
        panel.add(addOrderButton);


        generateBillButton = new JButton("GENERATE BILL");
        generateBillButton.setBounds(100, 310, 300, 70);
        generateBillButton.setBackground(Color.WHITE);
        generateBillButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        generateBillButton.setForeground(new Color(204, 0, 0));
        generateBillButton.setBorder(redline2);
        generateBillButton.setFocusable(false);
        generateBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == generateBillButton) {
                    Order newOrder = null;
                    GeneratePdfBill billGenerator = null;
                    try {
                        newOrder = order.findById(Integer.parseInt(idTextField.getText()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                    try {
                        billGenerator = new GeneratePdfBill();
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    } catch (DocumentException documentException) {
                        documentException.printStackTrace();
                    }
                    try {
                        billGenerator.writePdf(newOrder);
                    } catch (DocumentException | FileNotFoundException documentException) {
                        documentException.printStackTrace();
                    }

                }
            }
        });
        panel.add(generateBillButton);


        clientName = new JLabel("CLIENT NAME");
        clientName.setBounds(80, 410, 400, 40);
        clientName.setFont(new Font("Monospaced", Font.BOLD, 30));
        clientName.setForeground(new Color(204, 0, 0));
        panel.add(clientName);

        clientNameTextField = new JTextField();
        clientNameTextField.setBounds(80, 470, 180, 20);
        panel.add(clientNameTextField);
        clientNameTextField.setBorder(redline2);


        price = new JLabel("NAME");
        price.setBounds(50, 510, 100, 40);
        price.setFont(new Font("Monospaced", Font.BOLD, 30));
        price.setForeground(new Color(204, 0, 0));
        panel.add(price);

        nameTextField = new JTextField();
        nameTextField.setBounds(30, 570, 180, 20);
        panel.add(nameTextField);
        nameTextField.setBorder(redline2);

        quantity = new JLabel("QTY");
        quantity.setBounds(250, 510, 200, 40);
        quantity.setFont(new Font("Monospaced", Font.BOLD, 30));
        quantity.setForeground(new Color(204, 0, 0));
        panel.add(quantity);

        quantityTextField = new JTextField();
        quantityTextField.setBounds(230, 570, 180, 20);
        panel.add(quantityTextField);
        quantityTextField.setBorder(redline2);

        price = new JLabel("PRICE");
        price.setBounds(450, 510, 200, 40);
        price.setFont(new Font("Monospaced", Font.BOLD, 30));
        price.setForeground(new Color(204, 0, 0));
        panel.add(price);

        priceTextField = new JTextField();
        priceTextField.setBounds(430, 570, 180, 20);
        panel.add(priceTextField);
        priceTextField.setBorder(redline2);

        // Column Names
        modelForOrdersTable = new DefaultTableModel();
        modelForOrdersTable.addColumn("Id");
        modelForOrdersTable.addColumn("Name");
        modelForOrdersTable.addColumn("ProductName");
        modelForOrdersTable.addColumn("Quantity");
        modelForOrdersTable.addColumn("Price");


        // Initializing the JTable
        ordersTable = new JTable(modelForOrdersTable);
        ordersTable.setBounds(530, 100, 600, 350);
        ordersTable.setBackground(new Color(204, 230, 255));
        ordersTable.setBackground(Color.WHITE);
        ordersTable.setFont(new Font("MV Boly", Font.BOLD, 15));
        ordersTable.setForeground(new Color(204, 0, 0));
        ordersTable.setBorder(redline2);
        panel.add(ordersTable);
        try {
            for (Order c : order.listAllOrders()) {
                String idString = String.valueOf(c.getIdOrder());
                String clientNameString = String.valueOf(c.getClient());
                String productString = String.valueOf(c.getProduct());
                String quantityString = String.valueOf(c.getQuantity());
                String totalString = String.valueOf(c.getTotal());
                modelForOrdersTable.addRow(new Object[]{idString, clientNameString, productString, quantityString, totalString});
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ordersFrame.setVisible(true);
    }

}
