/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Iterator;
import model.Dish;
import model.ICategory;
import model.IModel;

/**
 * класс для обработки данных модели
 *
 * @author lyan
 */
public class LinearController implements IModelController {

    private IModel model;

    public LinearController(IModel model) {
        this.model = model;
    }

    /**
     * удаление объекта
     *
     * @param id ид нужного объекта
     */
    public void delete(int id) {
        for (ICategory c : model.getCategoryList()) {
            if (c.getId() == id) {
                model.getCategoryList().remove(c);
            }
            for (Dish d : c.getDishList()) {
                if (d.getId() == id) {
                    c.getDishList().remove(d);
                }
            }
        }
    }

    /**
     * метод находит в бд все элементы в ценовом диапазоне от low до high c
     * именем блюда совпадающим с dName и именем категории совпадающим с cName
     *
     * @param low нижняя цена. Значение double
     * @param high верхняя цена. Значение double
     * @param dName паттерн поиска для имени блюда. допустимо использование * в
     * качестве любого количества символов
     * @param cName паттерн поиска для имни категории. вроде никак не
     * используется
     * @return
     */
    public ArrayList<Dish> find(double low, double high, String dName, String cName) {
        if (low == 0) {
            low = -1;
        }
        if (high == 0) {
            high = Double.MAX_VALUE; // чтоыб поиск с пустым полем был невырожденным
        }
        if (dName.equals("")) {
            dName = "*";
        }
        if (cName.equals("")) {
            cName = "*";
        }

        ArrayList<Dish> result = new ArrayList<>(), fRes = new ArrayList<>();
        ArrayList<ICategory> catRes = new ArrayList<>();
        for (ICategory c : model.getCategoryList()) {
            if (cName.equals("") || cName.equals("*") || containPattern(c.getName(), cName)) {
                for (Dish d : c.getDishList()) {
                    if (d.getPrice() <= high && d.getPrice() >= low && !result.contains(d)) {
                        result.add(d); // проверка по цене
                    }
                }
            }
        }

        if (!dName.equals("") && !dName.equals("*")) {
            Dish d;
            for (Iterator<Dish> i = result.iterator(); i.hasNext();) {
                d = i.next();
                if (!containPattern(d.getName(), dName)) {
                    i.remove();
                }
            }
        }

        return result;
    }

    /**
     * провверка вхождения шаблона в строку в шаблоне могут быть символы
     * звездочка * означает любое количесво символов, в том число пустой символ
     *
     * @param name нужная строка
     * @param pattern нужный шаблон
     * @return подходит строка заданному шаблону
     */
    private boolean containPattern(String name, String pattern) {

        if (!pattern.contains("*")) {
            return name.equals(pattern);
        }

        String[] patterns = pattern.split("\\*");
        int pPos = -1, curPos;
        for (String p : patterns) {
            curPos = name.indexOf(p);
            if (curPos >= 0 && pPos < curPos) {
                pPos = curPos;
            } else {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    @Override
    /**
     * метод удаления не реализован для удаления требуется ид
     */
    public void deleteDish(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    /**
     * более универсальное удаление по ид
     */
    public void deleteDishCategory(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void deleteCategory(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void addDish(String name, double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void addDish(String category, String name, double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void addCategory(String category, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void addCategory(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void editCategoryName(String oldName, String newName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void editDishPrice(String name, double newPrice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void editDishName(String oldName, String newName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void editDishCategory(String oldCategory, String name, String newCategory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void findPriceMore(double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void findPriceLess(double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void findPriceEqual(double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void findPriceInterval(double left, double right) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void findPattern(String pattern) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
