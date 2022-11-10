package presentation;

import bll.AccessoryBLL;
import model.Accessory;
import start.Main;
import start.ReflectionTechnique;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccessoryController {

    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private AccessoriesView accessoriesView;
    private AccessoryBLL accessoryBLL;
    private int idAccessory;
    private String name;
    private float price;
    private int idcategory;
    private int idproduct;

    public AccessoryController(AccessoriesView accessoriesView){
        this.accessoriesView = accessoriesView;
        this.accessoryBLL = new AccessoryBLL();
    }
    /**
     * Method that stores the value of the accessory ID retrieved from the corresponding textfield in id
     */
    private void extractProductID() {
        this.idAccessory = accessoriesView.getAccessoryIDAsInt();
    }

    /**
     * Method that extracts the data about the product from the GUI textfields and stores it
     */
    private void extractProductData() {
        this.name = accessoriesView.getAccessoryNameTxt().getText();
        this.price = accessoriesView.getAccessoryPriceAsFloat();
        this.idcategory = accessoriesView.getCategoryIDAsInt();
        this.idproduct = accessoriesView.productIDAsInt();

    }

    /**
     * Method that creates the action listener of the addAccessoryButton
     * This method calls the insertProduct method of AccessoryBll
     * @param addAccessoryButton
     */
    public void createAddButtonActionListener(JButton addAccessoryButton) {
        addAccessoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractProductData();
                try {
                    accessoryBLL.insertProduct(new Accessory(idcategory, idproduct, name, price));
                    System.out.println("A new type of accessory has been added.");
                }
                catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Method that creates the action listener of the editAccessoryButton
     * This method calls the editProduct method of AccessoryBll
     * @param editAccessoryButton
     */
    public void createEditButtonActionListener(JButton editAccessoryButton) {
        editAccessoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractProductID();
                extractProductData();
                try {
                    accessoryBLL.editProduct(new Accessory(idAccessory, idcategory, idproduct, name, price));
                    System.out.println("Accessory with id = " + idAccessory + " has been updated.");
                }
                catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Method that creates the action listener corresponding to the removeButton
     * This method calls the delete method of FoodBll
     * @param removeButton
     */
    public void createDeleteButtonActionListener(JButton removeButton) {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractProductID();
                try {
                    accessoryBLL.deleteProduct(idAccessory);
                    System.out.println("Accessory with id = " + idAccessory + " has been removed.");
                }
                catch (Exception ex) {
                    LOGGER.log(Level.INFO, ex.getMessage());
                }
            }
        });
    }

    /**
     * Method that creates the action listener for the button that displays all the products from the database in a JTable
     * @param viewAllTypesOfButton
     */
    public void createViewProductsButtonActionListener(JButton viewAllTypesOfButton) {
        viewAllTypesOfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accessoriesView.getProductsTable().setVisible(true);
                DefaultTableModel tableModel = new DefaultTableModel();
                configureProductsTable(accessoriesView.getProductsTable(), tableModel);
            }
        });
    }

    /**
     * Method that configures the columns and rows of the JTable in which the accessories will be displayed
     * @param accessoryTable the table in which the accessories will be displayed
     * @param tableModel the table model the table is constructed after
     */
    public void configureProductsTable(JTable accessoryTable, DefaultTableModel tableModel) {
        String[] columnNames = {"Accessory ID", "Category ID", "Product ID", "Accessory Name", "Price"};

        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }

        addAccessoryToTable(tableModel);
        accessoryTable.setModel(tableModel);
    }

    /**
     * Method that adds a new row in the JTable of products
     * @param tableModel the model the table is constructed after
     */
    public void addAccessoryToTable(DefaultTableModel tableModel) {
        List<Accessory> allAccessories = null;
        try {
            allAccessories = accessoryBLL.findAllProducts();
        }
        catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }

        for (Accessory accessory : allAccessories) {
            ReflectionTechnique.retrieveProperties(accessory);
            tableModel.addRow(new Object[]{String.valueOf(accessory.getIdAccessory()), String.valueOf(accessory.getIdcategory()),
                    String.valueOf(accessory.getIdproduct()), accessory.getName(),
                    String.valueOf(accessory.getPrice())});
        }
    }

    /**
     * Method that creates an action listener for the button that takes the user back to the main window of the application
     * @param goBackButton
     */
    public void createBackButtonActionListener(JButton goBackButton) {
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductsView();
                accessoriesView.dispose();
            }
        });
    }





}
