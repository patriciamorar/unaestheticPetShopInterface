package validator;

import model.Food;

public class ProductIDFoodValidator implements Validator<Food> {
    /**
     * Method that validates a given product depending on the productID
     * @param food
     * @throws IllegalAccessException if the productID is not validated
     */
    public void validate(Food food) throws IllegalAccessException {
        if (food.getIdproduct() != 1) {
            throw new IllegalAccessException("The productID must be 1!");
        }
    }
}

