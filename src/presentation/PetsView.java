package presentation;

import javax.swing.*;

public class PetsView extends JFrame{
    private JPanel animalPanel;
    private JTextField animalIdTxt;
    private JButton removeButton;
    private JTextField textFieldCategory;
    private JTextField textFieldStatus;
    private JTextField textFieldBreed;
    private JButton addAnimalButton;
    private JButton editAnimalButton;
    private JButton viewAllTypesOfButton;
    private JTable animalTable;
    private JButton goBackButton;
    private AnimalController animalController;

    public PetsView() {
        super("Animal Manager");
        setContentPane(animalPanel);
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        animalController = new AnimalController(this);
        animalTable.setVisible(false);

        animalController.createAddButtonActionListener(addAnimalButton);
        animalController.createEditButtonActionListener(editAnimalButton);
        animalController.createDeleteButtonActionListener(removeButton);
        animalController.createViewProductsButtonActionListener(viewAllTypesOfButton);
        animalController.createBackButtonActionListener(goBackButton);
    }

    /**
     * Method that parses the value of the animal ID retrieved from the textfield to an int
     * @return the int value of the animal ID
     */
    public int getAnimalIDAsInt() {
        int animalID = 0;

        try {
            animalID = Integer.parseInt(animalIdTxt.getText());
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return animalID;
    }

    /**
     * Method that returns an instance of the JTextField in which the breed of the animal is written
     * @return an instance of the JTextField in which the breed of the animal is written
     */
    public JTextField getAnimalBreed() {
        return textFieldBreed;
    }

    /**
     * Method that returns an instance of the JTextField in which the status of the animal is written
     * @return an instance of the JTextField in which the status of the animal is written
     */
    public JTextField getAnimalStatus() {
        return textFieldStatus;
    }



    /**
     * Method that parses the id value of the animal retrieved from the textfield to an int
     * @return the int value of the animal id
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
     * Method that returns an instance of the JTable in which the animals are being displayed
     * @return an instance of the JTable in which the animals are being displayed
     */
    public JTable getProductsTable() {
        return animalTable;
    }


}


