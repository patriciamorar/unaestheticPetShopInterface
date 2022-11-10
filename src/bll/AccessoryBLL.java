package bll;

import dao.AccessoryDAO;
import model.Accessory;
import validator.AccessoryPriceValidator;
import validator.ProductIDAccessoryValidator;
import validator.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AccessoryBLL {
    private AccessoryDAO accessoryDAO;
    private List<Validator<Accessory>> productValidators;

    public AccessoryBLL() {
        accessoryDAO = new AccessoryDAO();
        productValidators = new ArrayList<>();
        productValidators.add(new ProductIDAccessoryValidator());
        productValidators.add(new AccessoryPriceValidator());


    }

    /**
     * This method searches the database for a product whose ID has been specified
     * The method calls the method that finds an Object by ID from DAO
     * @param idAccessory the ID of the product that we're looking for
     * @return an instance of the class Food, or null in the case in which the product hasn't been found
     */
    public Accessory findProductByID(int idAccessory) {
        Accessory accessory = accessoryDAO.findById(idAccessory);

        if (accessory == null) {
            throw new NoSuchElementException("Error! This type of accessory with ID " + idAccessory + " could not be found.");
        }

        return accessory;
    }

    /**
     * This method searches the database for a food whose name has been specified
     * The method calls the method that finds an Object by name from DAO
     * @param name the name of the product that we're looking for
     * @return an instance of the object Food, or null if the food hasn't been found
     */
    public Accessory findProductByName(String name) {
        Accessory accessory = accessoryDAO.findByName(name);

        if (accessory == null) {
            throw new NoSuchElementException("Error! The product [" + name + "] could not be found.");
        }

        return accessory;
    }

    /**
     * This method creates a list of all the existing types of food from the database
     * In the case in which there are no accessories, the method can throw a NoSuchElementException exception
     * @return a list of all the existing accessories
     */
    public List<Accessory> findAllProducts() throws NoSuchElementException {
        List<Accessory> accessories = accessoryDAO.findAll();

        if (accessories == null) {
            throw new NoSuchElementException("Error! There are no types of accessories in the database.");
        }

        return accessories;
    }

    /**
     * This method inserts a new type of accessories into the database if the attributes of the accessory are validated
     * If the validators are not respected, then the method throws an IllegalAccessException exception
     * @param accessory the accessory that is going to be inserted into the database
     * @throws IllegalAccessException
     */
    public void insertProduct(Accessory accessory) throws IllegalAccessException {
        for (Validator<Accessory> productValidator : productValidators)
            productValidator.validate(accessory);

        accessoryDAO.insert(accessory);
    }

    /**
     * This method removes from the database a product whose ID has been specified
     * @param idAccessory the ID corresponding to the product that has to be deleted
     */
    public void deleteProduct(int idAccessory) {
        accessoryDAO.delete(idAccessory);
    }

    /**
     * This method edits the fields of a accessory who has been specified, replacing them with new data if any changes occur
     * @param accessory the accessory whose data has to be updated
     * @throws IllegalAccessException the method throws this exception in the case in which the new data does not follow the validator restrictions
     */
    public void editProduct(Accessory accessory) throws IllegalAccessException {
        for (Validator<Accessory> productValidator : productValidators)
            productValidator.validate(accessory);

        accessoryDAO.update(accessory);
    }

}
