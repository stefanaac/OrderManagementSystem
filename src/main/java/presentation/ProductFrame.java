package presentation;


import connection.ConnectionFactory;
import dao.ProductDAO;
import model.Product;

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

/**
 * Clasa acre genereaza fereastra "Product" a aplicatiei.
 */
public class ProductFrame {
    Frame productsFrame;
    JPanel panel;


    JButton newProductButton;
    JButton editProductButton;
    JButton deleteProductButton;
    JButton viewProductsButton;


    JLabel id;
    JLabel productName;
    JLabel quantity;
    JLabel price;

    JTextField idTextField;
    JTextField priceTextField;
    JTextField quantityTextField;
    JTextField nameTextField;


    JTable productsTable;
    DefaultTableModel modelForProductsTable;
    Connection myConnection;
    ProductDAO pr = new ProductDAO();

    public ProductFrame() throws IllegalAccessException, IntrospectionException, InvocationTargetException, SQLException {
        myConnection = ConnectionFactory.getConnection();

        productsFrame = new JFrame();
        productsFrame.setSize(1530, 830);
        productsFrame.setTitle("PRODUCT FRAME");
        productsFrame.setResizable(false);
        productsFrame.setLayout(null);

        Border redline = BorderFactory.createLineBorder(new Color(204, 0, 0), 5);
        Border redline2 = BorderFactory.createLineBorder(new Color(204, 0, 0), 2);

        panel = new JPanel();
        panel.setSize(500, 530);
        panel.setBounds(150, 50, 1200, 700);
        panel.setBackground(new Color(255, 243, 230));
        panel.setLayout(null);
        panel.setVisible(true);
        productsFrame.add(panel);

        newProductButton = new JButton("NEW PRODUCT");
        newProductButton.setBounds(100, 90, 300, 70);
        newProductButton.setBackground(Color.WHITE);
        newProductButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        newProductButton.setForeground(new Color(204, 0, 0));
        newProductButton.setFocusable(false);
        newProductButton.setBorder(redline2);
        newProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == newProductButton) {
                    Product p = new Product(nameTextField.getText(), Integer.parseInt(quantityTextField.getText()), Float.parseFloat(priceTextField.getText()));
                    try {
                        pr.insertProduct(p);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });
        panel.add(newProductButton);

        editProductButton = new JButton("EDIT PRODUCT");
        editProductButton.setBounds(100, 200, 300, 70);
        editProductButton.setBackground(Color.WHITE);
        editProductButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        editProductButton.setForeground(new Color(204, 0, 0));
        editProductButton.setFocusable(false);
        editProductButton.setBorder(redline2);
        editProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == editProductButton) {

                    try {
                        pr.editProduct(Integer.parseInt(idTextField.getText()), nameTextField.getText(), Integer.parseInt(quantityTextField.getText()), Float.parseFloat(priceTextField.getText()));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            }
        });
        panel.add(editProductButton);


        deleteProductButton = new JButton("DELETE PRODUCT");
        deleteProductButton.setBounds(100, 310, 300, 70);
        deleteProductButton.setBackground(Color.WHITE);
        deleteProductButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        deleteProductButton.setForeground(new Color(204, 0, 0));
        deleteProductButton.setFocusable(false);
        deleteProductButton.setBorder(redline2);
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == deleteProductButton) {
                    String n = idTextField.getText();
                    try {
                        pr.deleteProduct(Integer.parseInt(n));
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    productsFrame.dispose();
                    try {
                        ProductFrame v = new ProductFrame();
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
        panel.add(deleteProductButton);

        viewProductsButton = new JButton("VIEW PRODUCTS");
        viewProductsButton.setBounds(100, 410, 300, 70);
        viewProductsButton.setBackground(Color.WHITE);
        viewProductsButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        viewProductsButton.setForeground(new Color(204, 0, 0));
        viewProductsButton.setFocusable(false);
        viewProductsButton.setBorder(redline2);
        viewProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == viewProductsButton) {
                    productsFrame.dispose();
                    try {
                        ProductFrame newProdFrame = new ProductFrame();
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
        panel.add(viewProductsButton);

        id = new JLabel("ID");
        id.setBounds(250, 610, 200, 40);
        id.setFont(new Font("Monospaced", Font.BOLD, 30));
        id.setForeground(new Color(204, 0, 0));
        panel.add(id);

        idTextField = new JTextField();
        idTextField.setBounds(230, 670, 180, 20);
        idTextField.setBorder(redline2);
        panel.add(idTextField);
        productsFrame.setVisible(true);


        productName = new JLabel("NAME");
        productName.setBounds(50, 510, 100, 40);
        productName.setFont(new Font("Monospaced", Font.BOLD, 30));
        productName.setForeground(new Color(204, 0, 0));
        panel.add(productName);

        nameTextField = new JTextField();
        nameTextField.setBounds(30, 570, 180, 20);
        nameTextField.setBorder(redline2);
        panel.add(nameTextField);

        quantity = new JLabel("QUANTITY");
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
        priceTextField.setBorder(redline2);
        panel.add(priceTextField);


        modelForProductsTable = new DefaultTableModel();
        pr.getMyTable(modelForProductsTable, pr.listAllProducts());
        modelForProductsTable.addColumn("Id");
        modelForProductsTable.addColumn("Name");
        modelForProductsTable.addColumn("email");
        modelForProductsTable.addColumn("adress");

        productsTable = new JTable(modelForProductsTable);
        try {
            for (Product p : pr.listAllProducts()) {
                String id = String.valueOf(p.getIdProduct());
                String name = String.valueOf(p.getProductName());
                String quan = String.valueOf(p.getQuantity());
                String price = String.valueOf(p.getPrice());
                modelForProductsTable.addRow(new Object[]{id, name, quan, price});
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        productsTable.setBounds(530, 100, 600, 350);
        productsTable.setBackground(new Color(204, 230, 255));
        productsTable.setBackground(Color.WHITE);
        productsTable.setFont(new Font("MV Boly", Font.BOLD, 15));
        productsTable.setForeground(new Color(204, 0, 0));
        productsTable.setBorder(redline2);

        panel.add(productName);
        panel.setBorder(redline2);
        panel.add(productsTable);


    }
}
