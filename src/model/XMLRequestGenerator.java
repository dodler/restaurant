/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * генератор запросов из блюд помещаете необходимые объекты и типы запросов,
 * навыходе получаете готовую строку, которую можно отправлять насервер сделан
 * через синлтон, что впрочем, сомнительно
 *
 * @author Артем
 */
public class XMLRequestGenerator {

    private static XMLRequestGenerator instance = new XMLRequestGenerator();

    public static XMLRequestGenerator getInstance() {
        return instance;
    }
    private final StringBuilder curReq;

    /**
     * типы запросов A - Add R - remove U - update
     */
    public static enum RequestType {

        A, R, U;
    }

    public void putCategory(ICategory cat, RequestType type) {
        curReq.append("<new id=\"");
        curReq.append(cat.getId()); // добавили ид
        curReq.append("\" name=\"");
        curReq.append(cat.getName()); // добавили имя
        curReq.append("\" />");
    }
    
    /**
     * для запроса добаления категории
     * @param cat - категория, которую нужно добавить
     * @param type - тип азапроса, должен быть RequestType.A
     * @param id 
     */
    public void putCategory(ICategory cat, RequestType type, int id){
        putCategory(cat, type);
        curReq.delete(curReq.length()-2, curReq.length());
        if (type.equals(RequestType.A) && id >= 0){
            curReq.append("\" parent=\"");
            curReq.append(id);
        }
        curReq.append("\" />");
    }
    
    /**
     * для запроса добавления блюда
     * @param d - блюдо
     * @param type - тип запроса, должен быть RequestType.A
     * @param id - ид родительской категории
     */
    public void putDish(Dish d, RequestType type, int id){
        putDish(d, type);
        curReq.delete(curReq.length()-2, curReq.length());
        if (type.equals(RequestType.A) && id >= 0){
            curReq.append("\" parent=\"");
            curReq.append(id);
        }
        curReq.append("\" />");
    }

    public void putDish(Dish d, RequestType type) {
        curReq.append("<new id=\"");
        curReq.append(d.getId()); // добавили ид
        curReq.append("\" name=\"");
        curReq.append(d.getName()); // добавили имя
        curReq.append("\" price=\"");
        curReq.append(d.getPrice()); // добавили имя
        curReq.append("\" />");
    }

    private XMLRequestGenerator() {
        curReq = new StringBuilder("<?xml version=\"1.0\"?> <upd>");
    }

    /**
     * метод получения итогового запроса, с учетом всех "положенных" объектов
     * при вызове метода текущий запрос сбрасывается. то есть двойной вызов
     * метода в первый раз выдаст запрос во второй раз пустую строку
     *
     * @return
     */
    public String getRequest() {
        curReq.append("</upd>");
        String result = curReq.toString();
        curReq.replace(0, result.length(), "<?xml version=\"1.0\"?> <upd>");
        return result;
    }
}
