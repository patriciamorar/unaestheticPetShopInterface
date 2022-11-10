package bll;

import dao.FoodDAO;
import model.Food;
import validator.FoodPriceValidator;
import validator.ProductIDFoodValidator;
import validator.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FoodBLL {
    private FoodDAO foodDAO;
    private List<Validator<Food>> productValidators;

    public FoodBLL() {
        foodDAO = new FoodDAO();
        productValidators = new ArrayList<>();
        productValidators.add(new FoodPriceValidator());
        productValidators.add(new ProductIDFoodValidator());

    }

    /**
     * This method searches the database for a product whose ID has been specified
     * The method calls the method that finds an Object by ID from DAO
     * @param foodID the ID of the product that we're looking for
     * @return an instance of the class Food, or null in the case in which the product hasn't been found
     */
    public Food findProductByID(int foodID) {
        Food food = foodDAO.findById(foodID);

        if (food == null) {
            throw new NoSuchElementException("Error! This type of food with ID " + foodID + " could not be found.");
        }

        return food;
    }

    /**
     * This method searches the database for a food whose name has been specified
     * The method calls the method that finds an Object by name from DAO
     * @param name the name of the product that we're looking for
     * @return an instance of the object Food, or null if the food hasn't been found
     */
    public Food findProductByName(String name) {
        Food food = foodDAO.findByName(name);

        if (food == null) {
            throw new NoSuchElementException("Error! The product [" + name + "] could not be found.");
        }

        return food;
    }

    /**
     * This method creates a list of all the existing types of food from the database
     * In the case in which there are no products, the method can throw a NoSuchElementException exception
     * @return a list of all the existing products
     */
    public List<Food> findAllProducts() throws NoSuchElementException {
        List<Food> products = foodDAO.findAll();

        if (products == null) {
            throw new NoSuchElementException("Error! There are no types of food in the database.");
        }

        return products;
    }

    /**
     * This method inserts a new type of food into the database if the attributes of the product are validated
     * If the validators are not respected, then the method throws an IllegalAccessException exception
     * @param product the product that is going to be inserted into the database
     * @throws IllegalAccessException
     */
    public void insertProduct(Food product) throws IllegalAccessException {
        for (Validator<Food> productValidator : productValidators)
            productValidator.validate(product);

        foodDAO.insert(product);
    }

    /**
     * This method removes from the database a product whose ID has been specified
     * @param foodID the ID corresponding to the product that has to be deleted
     */
    public void deleteProduct(int foodID) {
        foodDAO.delete(foodID);
    }

    /**
     * This method edits the fields of a product who has been specified, replacing them with new data if any changes occur
     * @param product the product whose data has to be updated
     * @throws IllegalAccessException the method throws this exception in the case in which the new data does not follow the validator restrictions
     */
    public void editProduct(Food product) throws IllegalAccessException {
        for (Validator<Food> productValidator : productValidators)
            productValidator.validate(product);

        foodDAO.update(product);
    }
/*
    /**
     * This method checks if the amount of products in stock is enough for an amount of wantedProductsQuantity products to be bought and for the order to be created
     * @param productID the ID corresponding to the product the stock checking is applied to
     * @param wantedProductsQuantity the quantity of products that a client wants to order
     * @return true if the client can order the wanted quantity, otherwise false

    public boolean thereAreEnoughProducts(int productID, int wantedProductsQuantity) {
        if (foodDAO.findById(productID).getCurrentStock() >= wantedProductsQuantity)
            return true;

        return false;
    }

    /**
     * This method updates the amount of products in stock after an order has been placed
     * The method calls the editProduct() method defined previously, in order to update the currentStock of the product
     * @param productID the ID of the product which has been ordered
     * @param orderedProductsAmount the amount of products that has been ordered -> this amount will be decremented from the previously existing stock

    public void updateProductStock(int productID, int orderedProductsAmount) {
        Product product = foodDAO.findById(productID);

        try {
            editProduct(new Product(productID, product.getProductName(), product.getPrice(), product.getCurrentStock() - orderedProductsAmount));
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }*/
}
