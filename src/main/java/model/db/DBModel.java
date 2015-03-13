package model.db;

import model.Dish;
import model.ICategory;
import model.IModel;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * интерфейс, который дает функциональность манипулировать данными
 * в базе данных
 * в этой версии я решил сделать блюдо линейным списком, а не деревом
 * так удобнее и практичнее
 * проще ненамного
 * Created by lyan on 25.02.15.
 */
public interface DBModel extends IModel {

    /**
     * класс обретка для параметров обновления
     * объект, который нужно обновить, значения переменных
     * и т.д.
     */
    class Update{

    }

    ArrayList<ICategory> getCategories();
    
    /**
     * метод соединения с бд
     * @throws java.sql.SQLException
     */
    void connect() throws SQLException;

    
    /**
     * выгрузка ресурсов
     * @throws java.sql.SQLException
     */
    void reset() throws SQLException;
    
    /**
     * метод загрузки данных из бд
     * @throws SQLException
     */
    void load() throws Exception;

    /**
     * добавление категории в базу данных
     * @param category - нужная категория
     */
    void add(ICategory category) throws SQLException;

    /**
     * добавление блюда
     * @param dish
     */
    void add(int pId, Dish dish) throws SQLException;

    /**
     * метод удаления объекта из бд
     * @param id - ид блюда или категории
     */
    void delete(int id) throws SQLException;

    /**
     * обновление категории
     * @param id - ид категории
     * @param name - имя ктаегории
     */
    void update(int id, String name) throws SQLException;


}
