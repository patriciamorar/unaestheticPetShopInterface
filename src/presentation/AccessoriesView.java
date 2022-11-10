package presentation;

import javax.swing.*;

public class AccessoriesView extends JFrame {
    private JPanel accessoryPanel;
    private JTextField accessoryIdTxt;
    private JButton removeButton;
    private JTextField textFieldAccessoryName;
    private JTextField textFieldPrice;
    private JTextField textFieldCategory;
    private JTextField textFieldProduct;
    private JButton addAccessoryButton;
    private JButton editAccessoryButton;
    private JTable accessoryTable;
    private JButton viewAllTypesOfButton;
    private JButton goBackButton;
    private AccessoryController accessoryController;


    public AccessoriesView() {
        super("Accesories Manager");
        setContentPane(accessoryPanel);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        accessoryController = new AccessoryController(this);
        accessoryTable.setVisible(false);

        accessoryController.createAddButtonActionListener(addAccessoryButton);
        accessoryController.createEditButtonActionListener(editAccessoryButton);
        accessoryController.createDeleteButtonActionListener(removeButton);
        accessoryController.createViewProductsButtonActionListener(viewAllTypesOfButton);
        accessoryController.createBackButtonActionListener(goBackButton);
    }

    /**
     * Method that parses the value of the accessory ID retrieved from the textfield to an int
     * @return the int value of the accessory ID
     */
    public int getAccessoryIDAsInt() {
        int idAccessory = 0;

        try {
            idAccessory = Integer.parseInt(accessoryIdTxt.getText());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return idAccessory;
    }

    /**
     * Method that returns an instance of the JTextField in which the name of the accessory is written
     * @return an instance of the JTextField in which the name of the accessory is written
     */
    public JTextField getAccessoryNameTxt() {
        return textFieldAccessoryName;
    }

    /**
     * Method that parses the value of the accessory price retrieved from the textfield to a float
     * @return the float value of the accessory's price
     */
    public float getAccessoryPriceAsFloat() {
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
     * Method that parses the value of the idCategory retrieved from the textfield to an int
     * @return the int value of the idCategory
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
     * Method that parses the value of the product id retrieved from the textfield to an int
     * @return the int value of the product id
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
     * Method that returns an instance of the JTable in which the accessories are being displayed
     * @return an instance of the JTable in which the accessories are being displayed
     */
    public JTable getProductsTable() {
        return accessoryTable;
    }

}
