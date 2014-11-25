/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import model.Category;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/**
 * класс - черновая реализация категории

* ид генерируется в зависимости от времени, минус случайное число
* имя задается в конструкторе
* есть ссылка на родительский элемент, а также на массив дочерних
* плюс контейнер для блюд
 * @author dodler
 */
public class MenuCategory implements Category{

    private int id;
    private String name;
    private ArrayList<Category> categoryList;
    private ArrayList<Dish> dishList;
    private Category parent;
    
    @Override
    public Category getParent(){
        return this.parent;
    }
    
    @Override
    public int getId(){
        return this.id;
    }
    
    @Override
    public String getName(){
        return this.name;
    }
    
    
    public MenuCategory(){
        this.dishList = new ArrayList<>();
        this.categoryList = new ArrayList<>();
    }
    
    public MenuCategory(String name, Category parent) {
        this();
        this.id = (int) (Objects.hashCode(Calendar.getInstance()) - Math.round(Math.random()*1000));
        this.name = name;
        this.parent = parent;
    }
    
    @Override
    public void addCategory(Category c){
        categoryList.add(c);
    }
    
    @Override
    public void addDish(Dish d){
        dishList.add(d);
    }

    @Override
    public ArrayList<Dish> getDishList() {
        return this.dishList;
    }

    @Override
    public ArrayList<Category> getSubCategoryList() {
        return this.categoryList;
    }
    
    
}
