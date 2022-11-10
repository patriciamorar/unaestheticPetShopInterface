package bll;

import dao.AnimalDAO;
import model.Animal;
import validator.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AnimalBLL {
    private AnimalDAO animalDAO;
    private List<Validator<Animal>> productValidators;

    public AnimalBLL() {
        animalDAO = new AnimalDAO();
        productValidators = new ArrayList<>();

    }

    /**
     * This method searches the database for an animal whose ID has been specified
     * The method calls the method that finds an Object by ID from DAO
     * @param idAnimal the ID of the animal that we're looking for
     * @return an instance of the class Animal, or null in the case in which the animal hasn't been found
     */
    public Animal findProductByID(int idAnimal) {
        Animal animal = animalDAO.findById(idAnimal);

        if (animal == null) {
            throw new NoSuchElementException("Error! This type of animal with ID " + idAnimal + " could not be found.");
        }

        return animal;
    }

    /**
     * This method searches the database for a animal whose name has been specified
     * The method calls the method that finds an Object by name from DAO
     * @param name the name of the animal that we're looking for
     * @return an instance of the object Animal, or null if the animal hasn't been found
     */
    public Animal findProductByName(String name) {
        Animal animal = animalDAO.findByName(name);

        if (animal == null) {
            throw new NoSuchElementException("Error! The animal [" + name + "] could not be found.");
        }

        return animal;
    }

    /**
     * This method creates a list of all the existing types of animal from the database
     * In the case in which there are no animals, the method can throw a NoSuchElementException exception
     * @return a list of all the existing animals
     */
    public List<Animal> findAllProducts() throws NoSuchElementException {
        List<Animal> animals = animalDAO.findAll();

        if (animals == null) {
            throw new NoSuchElementException("Error! There are no animals in the database.");
        }

        return animals;
    }

    /**
     * This method inserts a new animal into the database if the attributes of the animal are validated
     * If the validators are not respected, then the method throws an IllegalAccessException exception
     * @param animal the animal that is going to be inserted into the database
     * @throws IllegalAccessException
     */
    public void insertProduct(Animal animal) throws IllegalAccessException {
        for (Validator<Animal> productValidator : productValidators)
            productValidator.validate(animal);

        animalDAO.insert(animal);
    }

    /**
     * This method removes from the database an animal whose ID has been specified
     * @param idAnimal the ID corresponding to the animal that has to be deleted
     */
    public void deleteProduct(int idAnimal) {
        animalDAO.delete(idAnimal);
    }

    /**
     * This method edits the fields of an animal who has been specified, replacing them with new data if any changes occur
     * @param animal the animal whose data has to be updated
     * @throws IllegalAccessException the method throws this exception in the case in which the new data does not follow the validator restrictions
     */
    public void editProduct(Animal animal) throws IllegalAccessException {
        for (Validator<Animal> productValidator : productValidators)
            productValidator.validate(animal);

        animalDAO.update(animal);
    }

}
