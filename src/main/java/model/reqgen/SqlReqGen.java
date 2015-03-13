package model.reqgen;

/**
 * генератор sql запросов для модели
 * модель MySqlModel
 * оформлен в виде синглтона
 * Created by lyan on 25.02.15.
 */
public class SqlReqGen {
    private static SqlReqGen instance;
    private StringBuilder req;

    private SqlReqGen() {
        req = new StringBuilder();
    }


    public static SqlReqGen getInstance() {
        if (instance == null) {
            instance = new SqlReqGen();
        }
        return instance;
    }

    /**
     * метод генерирует запрос выборки из бд
     * правда пока получается только хуже лол
     * @param unique - должны ли результаты быть уникальны
     * @param pattern - поле, которое нужно выбрать
     * @param table - таблица из которй нужно выбрать
     * @return - запрос, который нужно отправитьб в бд
     * пока вызов метода длинне чем просот запрос
     */
    public String selectSql(boolean unique, String pattern, String table) {
        req.delete(0, req.length());
        req.append("select ");
        if (unique) {
            req.append("distinct ");
        }
        if (pattern == null || pattern.equals("")) {
            req.append("* ");
        } else {
            req.append(pattern);
            req.append(" ");
        }
        req.append("from ");
        req.append(table);
        req.append(";");
        return req.toString();

    }

    public String updateSql(int id, String name) {
        req.delete(0, req.length());
        req.append("update top(1) restaurant.dish set ");
        if (name != null) {
            req.append(" name=");
            req.append(name);
        }
        req.append(" where id = ");
        req.append(id);
        return req.toString();
    }

    /**
     * обновление какой либо сущности
     * если не хотите обновлять задайте отрицательные значения либо Null
     *
     * @param id
     * @param pId
     * @param name
     * @param price
     * @return 
     */
    // `id`='111', `pCat`='12', `name`='Бифштекс1' WHERE `id`='11';
    public String updateSql(int id, int pId, String name, double price) {
        req.delete(0, req.length());
        req.append("UPDATE restaurant.dish SET ");
        if (pId > 0) {
            req.append("pCat=\'");
            req.append(pId);
        }
        if (name != null) {
            req.append("\' , name=\'");
            req.append(name);
        }
        req.append("\' WHERE id=\'");
        req.append(id);
        req.append("\';\n");
        // правильное обновление цены
        req.append("UPDATE restaurant.price SET ");
        if (price >= 0) { // а вдруг распродажа
            req.append("price=");
            req.append(price);
        }
        req.append(" WHERE id=");
        req.append(id);
        req.append(";");
        
        return req.toString();

    }

    /**
     * удаление категории
     *
     * @param id - ид категории
     * @return - готовый запрос
     */
    public String deleteSql(int id) {
        req.delete(0, req.length());
        req.append("delete from restaurant.categories where id=");
        req.append(id);
        req.append("\n");
        return req.toString();
    }

    public String deleteSql(int id, int pId) {
        req.delete(0, req.length());
        req.append("delete from restaurant.dish where id=");
        req.append(id);
        req.append("and pCat=");
        req.append(pId);
        req.append("\n");
        return req.toString();
    }

    /**
     * формирует sql запрос "insert" для добавления категории
     *
     * @param name - имя
     * @param id   - ид категории
     * @return - возвращает готвыой sql запрос
     */
    public String insertSql(int id, String name) {
        req.delete(0, req.length());
        req.append("insert into restaurant.categories values(");
        req.append(id);
        req.append(",");
        req.append("\'");
        req.append(name);
        req.append("\'");
        req.append("\n");
        return req.toString();
    }

    /**
     * формирование sql запроса для добавления блюда
     * проверка не производится
     *
     * @param id       - ид блюда
     * @param parentId - ид родительской категории
     * @param name     - имя
     * @param price    - цена
     * @return - sql запрос
     */
    public String insertSql(int id, int parentId, String name, double price) {
        req.delete(0, req.length());
        req.append("insert into restaurant.dish values(");
        req.append(id);
        req.append(",");
        req.append(parentId);
        req.append(",");
        req.append("\'");
        req.append(name);
        req.append("\',");
        req.append(price);
        req.append("\n");
        return req.toString();
    }
}
