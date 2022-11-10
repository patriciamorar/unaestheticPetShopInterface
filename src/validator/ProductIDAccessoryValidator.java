package validator;

import model.Accessory;

public class ProductIDAccessoryValidator implements Validator<Accessory> {
    /**
     * Method that validates a given product depending on the productID
     * @param accessory
     * @throws IllegalAccessException if the productID is not validated
     */
    public void validate(Accessory accessory) throws IllegalAccessException {
        if (accessory.getIdproduct() != 2) {
            throw new IllegalAccessException("The productID must be 2!");
        }
    }
}

