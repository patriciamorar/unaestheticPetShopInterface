package validator;

import model.Accessory;

public class AccessoryPriceValidator implements Validator<Accessory>{

    /**
     * Method that validates a given product depending on their price
     * @param accessory
     * @throws IllegalAccessException if the price is not validated
     */
    public void validate(Accessory accessory) throws IllegalAccessException {
        if (accessory.getPrice() <= 0) {
            throw new IllegalAccessException("The price must be greater than zero!");
        }
    }

}
