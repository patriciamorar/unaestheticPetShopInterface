package start;

import java.lang.reflect.Field;

public class ReflectionTechnique {

    /**
     * Method that retrieves all the properties stored in the database of a given object
     * @param object - the object whose data is being retrieved
     */
    public static void retrieveProperties(Object object) {

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;

            try {
                value = field.get(object);
                System.out.println(field.getName() + " = " + value);
                System.out.println();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
