/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import model.CategoryImpl;
import model.Dish;
import model.ICategory;
import model.IncorrectCostException;

/**
 * данный класс является реализацией категорией для сервера отличие в
 * синхронизированных коллекциях
 *
 * @author lyan
 */
public class ServerCategoryImpl implements ICategory {

    private int id;
    private String name;

    private CopyOnWriteArrayList<Dish> dishList;

    {
        dishList = new CopyOnWriteArrayList<>();
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String newName) {
        if (!name.equals("")) {
            this.name = newName;
        }
    }

    @Override
    public ArrayList<ICategory> getSubCategoryList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addSubCategoryList(ArrayList<CategoryImpl> categoryList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Dish> getDishList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * метод возвращет блюда категории
     * @return 
     */
    public CopyOnWriteArrayList<Dish> getSynchonizedDishList(){
        return this.dishList;
    }

    @Override
    public void addDishList(ArrayList<Dish> dishList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ICategory getSubCategory(int ID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCategory(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addCategory(ICategory newCategory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeCategory(ICategory categoryForDelete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addDish(String name, double cost) throws IncorrectCostException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addDish(String name) throws IncorrectCostException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void addDish(Dish dish) {
        dishList.add(dish);
    }

    @Override
    public void removeDish(Dish dish) {
        dishList.remove(dish);
    }

}
