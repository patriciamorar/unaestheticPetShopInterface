package presentation;

import bll.AnimalBLL;
import start.Main;
import start.ReflectionTechnique;
import model.Animal;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnimalController {

    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private PetsView animalView;
    private AnimalBLL animalBLL;
    private int idanimal;
    private int idcategory;
    private String status;
    private String breed;

    public AnimalController(PetsView animalView){
        this.animalView = animalView;
        this.animalBLL = new AnimalBLL();
    }
    /**
     * Method that stores the value of the aniaml ID retrieved from the corresponding textfield in id
     */
    private void extractAnimalID() {
        this.idanimal = animalView.getAnimalIDAsInt();
    }

    /**
     * Method that extracts the data about the animal from the GUI textfields and stores it
     */
    private void extractProductData() {
        this.idcategory = animalView.getCategoryIDAsInt();
        this.status = animalView.getAnimalStatus().getText();
        this.breed = animalView.getAnimalBreed().getText();

    }

    /**
     * Method that creates the action listener of the addAnimalButton
     * This method calls the insertProduct method of AnimalBll
     * @param addAccessoryButton
     */
    public void createAddButtonActionListener(JButton addAccessoryButton) {
        addAccessoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractProductData();
                try {
                    animalBLL.insertProduct(new Animal(idcategory, status, breed));
                    System.out.println("A new animal has been added.");
                }
                catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Method that creates the action listener of the editAnimalButton
     * This method calls the editProduct method of AnimalBll
     * @param editAccessoryButton
     */
    public void createEditButtonActionListener(JButton editAccessoryButton) {
        editAccessoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractAnimalID();
                extractProductData();
                try {
                    animalBLL.editProduct(new Animal(idanimal, idcategory, status, breed));
                    System.out.println("Animal with id = " + idanimal + " has been updated.");
                }
                catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    /**
     * Method that creates the action listener corresponding to the removeButton
     * This method calls the delete method of AnimalBll
     * @param removeButton
     */
    public void createDeleteButtonActionListener(JButton removeButton) {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                extractAnimalID();
                try {
                    animalBLL.deleteProduct(idanimal);
                    System.out.println("Animal with id = " + idanimal + " has been removed.");
                }
                catch (Exception ex) {
                    LOGGER.log(Level.INFO, ex.getMessage());
                }
            }
        });
    }

    /**
     * Method that creates the action listener for the button that displays all the animals from the database in a JTable
     * @param viewAllTypesOfButton
     */
    public void createViewProductsButtonActionListener(JButton viewAllTypesOfButton) {
        viewAllTypesOfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animalView.getProductsTable().setVisible(true);
                DefaultTableModel tableModel = new DefaultTableModel();
                configureProductsTable(animalView.getProductsTable(), tableModel);
            }
        });
    }

    /**
     * Method that configures the columns and rows of the JTable in which the animals will be displayed
     * @param animalTable the table in which the animals will be displayed
     * @param tableModel the table model the table is constructed after
     */
    public void configureProductsTable(JTable animalTable, DefaultTableModel tableModel) {
        String[] columnNames = {"Animal ID", "Category ID", "Status", "Breed"};

        for (String columnName : columnNames) {
            tableModel.addColumn(columnName);
        }

        addAnimalToTable(tableModel);
        animalTable.setModel(tableModel);
    }

    /**
     * Method that adds a new row in the JTable of animals
     * @param tableModel the model the table is constructed after
     */
    public void addAnimalToTable(DefaultTableModel tableModel) {
        List<Animal> allAnimals = null;
        try {
            allAnimals = animalBLL.findAllProducts();
        }
        catch (Exception e) {
            LOGGER.log(Level.INFO, e.getMessage());
        }

        for (Animal animal : allAnimals) {
            ReflectionTechnique.retrieveProperties(animal);
            tableModel.addRow(new Object[]{String.valueOf(animal.getIdanimal()), String.valueOf(animal.getIdcategory()),
                    animal.getStatus(), animal.getBreed()});
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
                new MainView();
                animalView.dispose();
            }
        });
    }





}
