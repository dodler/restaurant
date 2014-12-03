/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author dodler
 */
public class CategoryImpl implements ICategory{

    private int id;
    private String name;
    private ArrayList<Dish> dishList;
    private ArrayList<ICategory> subCategoryList;
    
    public CategoryImpl(String name){
        this.name = name;
        this.id = (int)Math.round(Math.random()*1000000);
        this.dishList = new ArrayList<>();
        this.subCategoryList = new ArrayList<>();
    }
    
    @Override
    public void setName(String name){
        this.name = name;
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
    public void addCategory(ICategory c) {
        this.subCategoryList.add(c);
    }

    @Override
    public void addDish(Dish d) {
        dishList.add(d);
    }

    @Override
    public ArrayList<Dish> getDishList() {
        return dishList;
    }

    @Override
    public ArrayList<ICategory> getSubCategoryList() {
        return subCategoryList;
    }
    
}
