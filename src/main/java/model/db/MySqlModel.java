package model.db;

import model.CategoryImpl;
import model.Dish;
import model.ICategory;
import model.reqgen.SqlReqGen;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

/**
 * модель, которая позволяет получать данные из mysql бд а также манипулировать
 * данными - создавать, удалять, редактировать Created by lyan on 25.02.15.
 */
public class MySqlModel implements DBModel {
// TODO пока блокировка не рабоает

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/mysql?characterEncoding=utf8";

    private String user = new String();
    private String pass = new String();

    //если тру, значит запрос отправлен, ожидается ответ, работа других функций невозможна
    private boolean process = false; // флаг обработки запроса
    // если фолз, значит запросов в работе нет, можно отправлять

    private Statement st; // объект, через который проводятся все запросы
    private Connection connection; // объект соединения с базой данных
    private ResultSet rs; // объект, в котором хранятся ответы бд

    private SqlReqGen srq = SqlReqGen.getInstance(); // генератор запросов

    {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connection = null;
        st = null;

        categories = new ArrayList<>();
        dishs = new ArrayList<>();
    }

    private ArrayList<ICategory> categories; // список всех категорий
    private ArrayList<Dish> dishs; // список всех блюд

    public MySqlModel() {
        user = "root";
        pass = "1123";
    }

    public MySqlModel(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    /**
     * метод закрывает соединение с бд освобождает ресурсы удаляет все категории
     * удаляет все блюда без этого метода значения будут дубливаться многократно
     * именно - два раза
     *
     * @throws SQLException ошибка закрытия доступа к бд
     */
    @Override
    public void reset() throws SQLException {
        dishs.clear();
        categories.clear();
        connection.close();
        rs.close();
        st.close();
    }

    /**
     * метод загружает нужные ресурсы из бд загружаютс категории блюда и цена на
     * них хранится в массиве ArrayList<String> categories
     *
     * @throws SQLException ошибка загрузки из бд
     */
    @Override
    public void load() throws SQLException {
        rs = st.executeQuery(srq.selectSql(false, null, "restaurant.categories"));
        while (rs.next()) { // выборка и добавление
            categories.add(new CategoryImpl(
                    rs.getString("name"),
                    rs.getInt("id")
            ));
            if (rs.getInt("id") > maxCatId) {
                maxCatId = rs.getInt("id") + 1;
            }
        }
        rs = st.executeQuery(srq.selectSql(false, null, "restaurant.dish"));
        Dish d;
        while (rs.next()) {
            d = new Dish(
                    rs.getInt("id"),
                    rs.getString("name")
            );
            if (d.getID() > this.maxDishId) {
                maxDishId = d.getID() + 1;
            }
            d.setParent(rs.getInt("pCat"));
            dishs.add(d);

        }

        rs = st.executeQuery(srq.selectSql(false, null, "restaurant.price"));
        while (rs.next()) { // сначала нужно забить цены блюд
            for (Dish d1 : dishs) {
                if (d1.getID() == rs.getInt("id")) {
                    d1.setCost(rs.getDouble("price"));
                    if (rs.getInt("id") > maxPriceId) {
                        maxPriceId = rs.getInt("id") + 1;
                    }
                }
            }
        }

        for (ICategory categorie : categories) {
            for (Dish dish : dishs) {
                // сортируем блюда по категориям
                if (dish.getParent() == categorie.getId()) {
                    categorie.addDish(dish);
                }
            }
        }
    }

    @Override
    public void connect() throws SQLException {
        Properties properties = new Properties();
        properties.put("user", user);
        properties.put("password", pass);
        // properties.put("useUnicode", "true");
        properties.put("charSet", "utf8");
        connection = DriverManager.getConnection(URL, properties);
        st = connection.createStatement();
    }
    String addCategory = "INSERT INTO restaurant.categories (Id,Name) VALUES (?,?)";
    @Override

    public void add(ICategory category) throws SQLException {
        if (process) {
            return;
        }
        ps=connection.prepareStatement(addCategory);
        ps.setInt(1, maxCatId+1);
        ps.setString(2, category.getName());
        ps.executeUpdate();
    }

    private int maxDishId = -1, maxPriceId = -1, maxCatId = -1;

    String addDish = "INSERT INTO restaurant.dish (id, pCat, name) VALUES (?, ?, ?);",
            addPrice = "INSERT INTO restaurant.price (price, id, dishId) VALUES(?,?,?);";

    /**
     * метод добавления блюда в базу данных
     *
     * @param pId ид родительской категории
     * @param dish объект класса блюда, который хранит необходимые данные, в том
     * числе и новый ид
     * @throws SQLException если произошла ошибка при взаимодействии с бд
     */
    @Override
    public void add(int pId, Dish dish) throws SQLException {
        if (process) {
            return;
        }
        ps = connection.prepareStatement(addDish);
        ps.setInt(1, maxDishId + 1);
        ps.setInt(2, pId);
        ps.setString(3, dish.getName());
        ps.executeUpdate();

        ps = connection.prepareStatement(addPrice);
        ps.setDouble(1, dish.getCost());
        ps.setInt(2, maxDishId + 1); // немного перепутаны данные, ид - ид еды, которой соответствует цена, а dishId - собственнй ид цены
        ps.setInt(3, maxPriceId + 1);

    }
    
    private String delete="DELETE FROM restaurant.dish WHERE id=?";
    @Override
    public void delete(int id) throws SQLException {
        if (process) {
            return;
        }
        ps=connection.prepareStatement(delete);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    
    /**
     * проверка - блюдо ли или нет
     * проверка производится по совпадению ид с одной из ид категорий
     * если есть, то возвращается ложь
     * если нет, то правда
     * @param id ид нужного объекта
     * @return входит ли в список блюд
     */
    public boolean isDish(int id){
        for(ICategory c:categories){
            if (c.getId() == id){
                return true;
            }
        }
        return false;
    }
    
private String deleteCat="DELETE FROM restaurant.categories WHERE id=?";
    /**
     * метод дял удаления категории
     * @param id ид категории, которую нужно удалить
     * @param pId любой удаление по id
     * @throws SQLException 
     */
    public void delete(int id, int pId) throws SQLException {
        if (process) {
            return;
        }
        ps=connection.prepareStatement(deleteCat);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    @Override
    public void update(int id, String name) throws SQLException {
        if (process) {
            return;
        }
        st.executeUpdate(srq.updateSql(id, name));
    }

    private final String updatePrice = "UPDATE restaurant.price SET price = ? where id = ?",
            updateDish = "UPDATE restaurant.dish SET pCat = ?, name = ? where id = ?";

    PreparedStatement ps;

    public void update(int id, int pId, String name, double price) throws SQLException {
        if (process) {
            return;
        }
        ps = connection.prepareStatement(updatePrice);
        ps.setDouble(1, price);
        ps.setInt(2, id);
        ps.executeUpdate();
        ps = connection.prepareStatement(updateDish);
        ps.setInt(1, pId);
        ps.setString(2, name);
        ps.setInt(3, id);
        ps.executeUpdate();
    }

    @Override
    public ICategory getRootCategory() {
        return null;
    }

    @Override
    public void save(String name) throws IOException {

    }

    @Override
    public void load(String name) throws ParserConfigurationException, SAXException, IOException {

    }

    @Override
    public ArrayList<ICategory> getCategories() {
        return this.categories;
    }
}
