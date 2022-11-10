package dao;

import connection.ConnectionFactory;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * This method builds a statement used to select all the data from a table of the database
     * @return the built statement as a String
     */
    private String createSelectAllQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append(" * ");
        stringBuilder.append(" FROM ");
        stringBuilder.append(type.getSimpleName());

        return stringBuilder.toString();
    }

    /**
     * This method builds a statement used to select all the data from a table after a certain given field
     * @param givenField the field that filters the selection
     * @return the built statement as a String
     */
    private String createSelectQuery(String givenField) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append(" * ");
        stringBuilder.append(" FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" WHERE " + givenField + " = ?");

        return stringBuilder.toString();
    }

    /**
     * This method builds a statement used to insert an object into a database
     * @return the built statement as a String
     */
    private String createInsertQuery() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append("(");

        boolean reachedLastField = false;
        int fieldCnt = 0;

        for (Field field : type.getDeclaredFields()) {
            fieldCnt++;
            if (fieldCnt == type.getDeclaredFields().length) {
                reachedLastField = true;
                stringBuilder.append(field.getName() + ") VALUES (");
            }

            if (fieldCnt > 1 && reachedLastField == false)
                stringBuilder.append(field.getName() + ", ");
        }

        for (int i = 0; i < type.getDeclaredFields().length; ++i) {
            if (i == type.getDeclaredFields().length - 2) {
                stringBuilder.append("?)");
                break;
            }

            stringBuilder.append("?, ");
        }

        return stringBuilder.toString();
    }

    /**
     * This method builds a statement used to delete the data from a given ID
     * @param idField the ID corresponding to the object that will be deleted
     * @return the build statement as a String
     */
    private String createDeleteQuery(String idField) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" WHERE " + idField + " = ?");

        return stringBuilder.toString();
    }

    /**
     * This method builds a statement used to update the fields of existing data from the database
     * @param fields the fields of the object that has to be updated
     * @return the built statement as a String
     */
    private String createUpdateQuery(Field[] fields) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE ");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append(" SET ");

        for (int i = 1; i < fields.length - 1; ++i)
            stringBuilder.append(fields[i].getName() + " = ?, ");
        stringBuilder.append(fields[fields.length - 1].getName() + " = ? WHERE ");
        stringBuilder.append(fields[0].getName() + " = ?");

        return stringBuilder.toString();
    }

    /**
     * This method finds all the entries from a table of the database
     * @return a list of generic type T
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement findAllStatement = null;
        ResultSet resultSet = null;
        String findAllEntriesQuery = createSelectAllQuery();

        try {
            connection = ConnectionFactory.getConnection();
            findAllStatement = connection.prepareStatement(findAllEntriesQuery);
            resultSet = findAllStatement.executeQuery();

            return createObjects(resultSet);
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll" + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(findAllStatement);
            ConnectionFactory.close(connection);
        }

        return null;
    }


    /**
     * This method finds an entry from a table by ID
     * @param id the ID corresponding to the entry we're looking for
     * @return an object of generic type T
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement findByIdStatement = null;
        ResultSet resultSet = null;
        String findByIdQuery = createSelectQuery(type.getDeclaredFields()[0].getName());

        try {
            connection = ConnectionFactory.getConnection();
            findByIdStatement = connection.prepareStatement(findByIdQuery);
            findByIdStatement.setInt(1, id);
            resultSet = findByIdStatement.executeQuery();

            return createObjects(resultSet).get(0);
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(findByIdStatement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * This method searches for the entries that contain a given name
     * @param givenName the givenName corresponding to the entries we are looking for
     * @return an object of generic type T representing the result of the selection by name
     */
    public T findByName(String givenName) {
        Connection connection = null;
        PreparedStatement findByNameStatement = null;
        ResultSet resultSet = null;
        String selectByNameQuery = createSelectQuery(type.getDeclaredFields()[1].getName());

        try {
            connection = ConnectionFactory.getConnection();
            findByNameStatement = connection.prepareStatement(selectByNameQuery);
            findByNameStatement.setString(1, givenName);
            resultSet = findByNameStatement.executeQuery();

            List<T> foundEntriesList = createObjects(resultSet);
            if( foundEntriesList.size() == 0 )
                return null;
            else
                return foundEntriesList.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName" + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(findByNameStatement);
            ConnectionFactory.close(connection);
        }

        return null;
    }

    /**
     * This method inserts a new entry into the database
     * @param t a generic object of type T, that contains all the data that has to be inserted into the table
     * @return the ID at which data has been inserted, or -1 if no insertion occured
     */
    public int insert(T t) {
        Connection databaseConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedID = -1;

        try {
            insertStatement = databaseConnection.prepareStatement(createInsertQuery(), Statement.RETURN_GENERATED_KEYS);

            for (int i = 0; i < t.getClass().getDeclaredFields().length - 1; ++i) {
                Field field = t.getClass().getDeclaredFields()[i + 1];
                field.setAccessible(true);
                Object objValue = field.get(t);
                insertStatement.setObject(i + 1, objValue);
            }
            insertStatement.executeUpdate();

            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next()) {
                insertedID = resultSet.getInt(1);
            }
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, "DAO:insert " + e.getMessage());
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(databaseConnection);
        }

        return insertedID;
    }

    /**
     * This method deletes an entry at a specified ID from a database
     * @param id the ID corresponding to the entry that has to be deleted
     * @return the ID of the entry that has been removed, or -1 otherwise
     */
    public int delete(int id) {
        Connection connection = null;
        PreparedStatement removeByIdStatement = null;
        String removeByIdQuery = createDeleteQuery(type.getDeclaredFields()[0].getName());

        try {
            connection = ConnectionFactory.getConnection();
            removeByIdStatement = connection.prepareStatement(removeByIdQuery);
            removeByIdStatement.setInt(1, id);
            removeByIdStatement.executeUpdate();

            return id;
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        }
        finally {
            ConnectionFactory.close(removeByIdStatement);
            ConnectionFactory.close(connection);
        }

        return -1;
    }

    /**
     * This method updates an existing entry from a table of the database
     * @param t the object of generic type T which holds the data that will be updated
     * @return an object of generic type T which contains the updated data
     */
    public T update(T t) {
        Field[] fieldsToUpdate = type.getDeclaredFields();
        Connection databaseConnection = null;
        PreparedStatement updateStatement = null;
        String updateQuery = createUpdateQuery(fieldsToUpdate);

        try {
            databaseConnection = ConnectionFactory.getConnection();
            updateStatement = databaseConnection.prepareStatement(updateQuery);

            fieldsToUpdate[0].setAccessible(true);
            for (int i = 1; i < fieldsToUpdate.length; ++i) {     // start from i = 1, in order to skip over the possibility of updating the ID
                fieldsToUpdate[i].setAccessible(true);
                updateStatement.setObject(i, fieldsToUpdate[i].get(t));
            }
            fieldsToUpdate[fieldsToUpdate.length - 1].setAccessible(true);
            updateStatement.setObject(fieldsToUpdate.length, fieldsToUpdate[0].get(t));
            updateStatement.executeUpdate();
            return t;
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        }
        catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(databaseConnection);
        }

        return null;
    }

    /**
     * This method creates a set of one or more objects depending on the data received as parameter
     * Depending on the class that calls this method, object of the type of that certain class will be created
     * @param resultSet a set of one or more entries representing the created objects
     * @return a list of objects of generic type T
     */
    /*private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;

        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (IntrospectionException e) {
            e.printStackTrace();
        }

        return list;
    }*/
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;

        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();

                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (SecurityException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (IntrospectionException e) {
            e.printStackTrace();
        }

        return list;
    }
}