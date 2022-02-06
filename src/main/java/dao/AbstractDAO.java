package dao;
/**
 * clasa abstracta folosita pentru accesarea baze de date: crearea unui obiect, editarea unui obiect, stergerea unui obiect si gasirea unui obiect.
 */

import connection.ConnectionFactory;

import javax.swing.table.DefaultTableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AbstractDAO<T> {

    private final Class<T> type;
    Integer element = 0;

    @SuppressWarnings({"unchecked", "unchecked"})

    /**
     * constructor ce extrage numele clasei
     */
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * @param fieldToSelectBy reprezinta numele unei tabele din baza de date
     * @return string ce reprezinta o interogare din sql
     */
    private String createSelectQuery(String fieldToSelectBy) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM "+type.getSimpleName()+" WHERE " + fieldToSelectBy + " =?");
        return sb.toString();
    }

    /**
     * @param searchedId numarul de identificare dintr-o tabela dupa care se cauta un tuplu
     * @return un obiect de tipul T
     */
    public T findById(int searchedId) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        String query = createSelectQuery("id");
        Connection connection= ConnectionFactory.getConnection();
        PreparedStatement statement =  connection.prepareStatement(query);
        statement.setInt(1, searchedId);
        ResultSet resultSet = statement.executeQuery();
        ConnectionFactory.close(connection);
        return returnObjects(resultSet).get(0);
    }


    /**
     * Aceasta metoda extrage toate tuplele dintr-o tabela si le returneaza sub forma de lista de obiecte.
     *
     * @return Returneaza o lista de obiecte de tip T.
     */
    public List<T> findAll() throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM "+type.getSimpleName().toLowerCase());
        String query = sb.toString();
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        ConnectionFactory.close(connection);
        return returnObjects(resultSet);
    }

    /**
     * Aceasta metoda construieste un string care reprezinta sintaxa interogarii update din sql.
     *
     * @return Un string care reprezinta sintaxa interogarii update din sql.
     */
    private String createUpdateQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE "+type.getSimpleName().toLowerCase()+" SET ");
        for (Field f : type.getDeclaredFields()) {
            sb.append(f.getName() + " = ?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("WHERE id=?");
        return sb.toString();
    }


    /**
     * @param t Obiectul caruia dorim sa ii facem update in tabela.
     * @return Obiectul t modificat
     */
    public T update(T t) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        String query = createUpdateQuery();
        Connection connection =  ConnectionFactory.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        int i = 1;
        for (Field f : t.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object value = f.get(t);
            statement.setObject(i, value);
            i++;
            f.setAccessible(false);
        }
        ConnectionFactory.close(connection);
        return t;
    }

    /**
     * @param field reprezinta fieldul dintr-o tabela dupa care facem delete.
     * @return Un string reprezentand query-ul delete din sql.
     */

    private String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM " + type.getSimpleName().toLowerCase()+" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * @param t Obiectul caruia dorim sa il stergem din tabela.
     * @return Un numar intreg care indica daca s-a acut corect stergerea.
     */
    public int delete(T t) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        Field f = t.getClass().getDeclaredFields()[0];
        f.setAccessible(true);
        String query = createDeleteQuery(f.getName());
        Connection connection = ConnectionFactory.getConnection();;
        PreparedStatement statement =  connection.prepareStatement(query);
        statement.setInt(1, (int) f.get(t));
        int deletedItem = statement.executeUpdate();
        ConnectionFactory.close(connection);
        return deletedItem;
    }



    /**
     * @return Un string echivalent cu query-ul insert din sql.
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO "+type.getSimpleName().toLowerCase()+" VALUES ( ");
        for (Field f : type.getDeclaredFields()) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }


    /**
     * @param t Obiectul pe acre dorim sa il inseram.
     * @return Obiectul pe acre dorim sa il inseram.
     */
    public T insert(T t) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        String query = createInsertQuery();
        Connection connection = ConnectionFactory.getConnection();;
        PreparedStatement statement = connection.prepareStatement(query);
        int i = 1;
        for (Field f : t.getClass().getDeclaredFields()) {
            Object value = f.get(t);
            statement.setObject(i, value);
            i++;
        }
        ConnectionFactory.close(connection);
        return null;
    }


    /**
     * @param resultSet
     * @return
     */
    private List<T> returnObjects(ResultSet resultSet) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
        List<T> list = new ArrayList<T>();
        Constructor[] constructors = type.getDeclaredConstructors();
        for (int i = 0; i < constructors.length; i++) {
            Constructor constructor = constructors[i];
            if (constructor.getGenericParameterTypes().length == 0) {
                break;
            }
        }
        Constructor constructor=null;
        while (resultSet.next()) {
            constructor.setAccessible(true);
            T instance = (T) constructor.newInstance();
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                Object value = resultSet.getObject(fieldName);
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getWriteMethod();
                method.invoke(instance, value);
            }
            list.add(instance);
        }
        return list;
    }


    /**
     * Aceatsa metoda genereaza headerul unui tabel si il populeaza cu date
     *
     * @param myList lista de obiecte
     * @return
     * @throws IntrospectionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void getMyTable(DefaultTableModel model, ArrayList<T> myList) {
        model = new DefaultTableModel();
        Field[] myFields = type.getDeclaredFields();

        Integer columns = type.getDeclaredFields().length;
        String[] colNames = new String[columns];
        Object[][] content = new Object[myList.size()][columns];

        for (T myElement = myList.get(element); element < myList.size(); element++) {
            int j = 0;
            for (Field field : myFields) {
                PropertyDescriptor propertyDescriptor = null;
                try {
                    propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                } catch (IntrospectionException e) {
                    e.printStackTrace();
                }
                Object value = null;
                try {
                    value = propertyDescriptor.getReadMethod().invoke(myElement);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                content[element][j] = value;
                j++;

            }
        }


    }

}
