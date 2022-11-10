package validator;

import model.Food;

public class FoodPriceValidator implements Validator<Food>{

    /**
     * Method that validates a given product depending on their price
     * @param food
     * @throws IllegalAccessException if the price is not validated
     */
    public void validate(Food food) throws IllegalAccessException {
        if (food.getPrice() <= 0) {
            throw new IllegalAccessException("The price must be greater than zero!");
        }
    }

}
