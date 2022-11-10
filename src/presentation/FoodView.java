package presentation;

import javax.swing.*;

public class FoodView extends JFrame{
    private JPanel foodPanel;
    private JTextField foodIdTxt;
    private JButton removeButton;
    private JTextField textFieldName;
    private JTextField textFieldPrice;
    private JTextField textFieldWeight;
    private JTextField textFieldCategory;
    private JButton addFoodButton;
    private JButton editFoodButton;
    private JButton viewAllTypesOfButton;
    private JButton goBackButton;
    private JTable foodTable;
    private JTextField textFieldProduct;
    private FoodController foodController;



    public FoodView() {
        super("Food Manager");
        setContentPane(foodPanel);
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        foodController = new FoodController(this);
        foodTable.setVisible(false);

        foodController.createAddButtonActionListener(addFoodButton);
        foodController.createEditButtonActionListener(editFoodButton);
        foodController.createDeleteButtonActionListener(removeButton);
        foodController.createViewProductsButtonActionListener(viewAllTypesOfButton);
        foodController.createBackButtonActionListener(goBackButton);
    }

    /**
     * Method that parses the value of the product ID retrieved from the textfield to an int
     * @return the int value of the product ID
     */
    public int getProductIDAsInt() {
        int productID = 0;

        try {
            productID = Integer.parseInt(foodIdTxt.getText());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return productID;
    }

    /**
     * Method that returns an instance of the JTextField in which the name of the product is written
     * @return an instance of the JTextField in which the name of the product is written
     */
    public JTextField getProductNameTxt() {
        return textFieldName;
    }

    /**
     * Method that parses the value of the product price retrieved from the textfield to a float
     * @return the float value of the product's price
     */
    public float getProductPriceAsFloat() {
        float price = 0.0f;

        try {
            price = Float.parseFloat(textFieldPrice.getText());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return price;
    }

    /**
     * Method that parses the value of the ordered product quantity retrieved from the textfield to an int
     * @return the int value of the ordered product's quantity
     */
    public int getCategoryIDAsInt() {
        int categoryID = 0;

        try {
            categoryID = Integer.parseInt(textFieldCategory.getText());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return categoryID;
    }

    /**
     * Method that parses the value of the ordered product quantity retrieved from the textfield to an int
     * @return the int value of the ordered product's quantity
     */

    public int productIDAsInt() {
        int idProduct = 0;

        try {
            idProduct = Integer.parseInt(textFieldProduct.getText());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return idProduct;
    }

    /**
     * Method that parses the value of the ordered product weight retrieved from the textfield to a double
     * @return the double value of the ordered product's weight
     */
    public double getWeightAsDouble() {
        double weight = 0;

        try {
            weight = Double.parseDouble(textFieldWeight.getText());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return weight;
    }

    /**
     * Method that returns an instance of the JTable in which the products are being displayed
     * @return an instance of the JTable in which the products are being displayed
     */
    public JTable getProductsTable() {
        return foodTable;
    }


}


