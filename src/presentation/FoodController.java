package presentation;

import model.Food;
import bll.FoodBLL;
import start.Main;
import start.ReflectionTechnique;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FoodController {

    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private FoodView foodView;
    private FoodBLL foodBll;
    private int id;
    private String name;
    private float price;
    private double weight;
    private int categoryID;
    private int productID;

    public FoodController(FoodView foodView){
        this.foodView = foodView;
        this.foodBll = new FoodBLL();
    }
    /**
     * Method that stores the value of the product ID retrieved from the corresponding textfield in id
     */
    private void extractProductID() {
        this.id = foodView.getProductIDAsInt();
    }

    /**
     * Method that extracts the data about the product from the GUI textfields and stores it
     */
    private void extractProductData() {
        this.name = foodView.getProductNameTxt().getText();
        this.price = foodView.getProductPriceAsFloat();
        this.categoryID = foodView.getCategoryIDAsInt();
        this.weight = foodView.getWeightAsDouble();
        this.productID = foodView.productIDAsInt();

    }

    /**
     * Method that creates the action listener of the addFoodButton
     * This method calls the insertProduct method of FoodBll
     * @param addFoodButton
     */
    public void createAddButtonActionListener(JButton addFoodButton) {
        addFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractProductData();
                try {
                    foodBll.insertProduct(new Food(categoryID, productID, weight, name, price));
                    System.out.println("A new type of food has been added.");
                }
                catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Method that creates the action listener of the editFoodButton
     * This method calls the editProduct method of ProductBll
     * @param editFoodButton
     */
    public void createEditButtonActionListener(JButton editFoodButton) {
        editFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractProductID();
                extractProductData();
                try {
                    foodBll.editProduct(new Food(id, categoryID, name, weight, price)); ///productID,
                    System.out.println("Product with id = " + id + " has been updated.");
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
                    foodBll.deleteProduct(id);
                    System.out.println("Product with id = " + id + " has been removed.");
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
                foodView.getProductsTable().setVisible(true);
                DefaultTableModel tableModel = new DefaultTableModel();
                configureProductsTable(foodView.getProductsTable(), tableModel);
            }
        });
    }

    /**
     * Method that configures the columns and rows of the JTable in which the products will be displayed
     * @param productsTable the table in which the products will be displayed
     * @param tableModel the table model the table is constructed after
     */
    public void configureProductsTable(JTable productsTable, DefaultTableModel tableModel) {
        String[] columnNames = {"Food ID", "Product Name", "Price", "Product ID", "Category ID", " Weight"};

        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }

        addProductToTable(tableModel);
        productsTable.setModel(tableModel);
    }

    /**
     * Method that adds a new row in the JTable of products
     * @param tableModel the model the table is constructed after
     */
    public void addProductToTable(DefaultTableModel tableModel) {
        List<Food> allProducts = null;
        try {
            allProducts = foodBll.findAllProducts();
        }
        catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }

        for (Food product : allProducts) {
            ReflectionTechnique.retrieveProperties(product);
            tableModel.addRow(new Object[]{String.valueOf(product.getIdFood()), product.getName(),
                    String.valueOf(product.getPrice()), String.valueOf(product.getIdcategory()), String.valueOf(product.getIdproduct()),String.valueOf(product.getWeight())});
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
                foodView.dispose();
            }
        });
    }





}
