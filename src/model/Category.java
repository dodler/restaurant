/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import utils.Dish;

/**
 *
 * @author dodler
 */
public interface Category {
    
    int getId();
    
    /**
     * метод для получения доступа к родительскому элементу
     * @return родительская категория
     */
    Category getParent();
    
    /**
     * возвращает имя категории
     * @return имя категории
     */
    String getName();
    
    /**
     * добавляет категорию в список дочерних категорий
     * @param c категория, которую нужно добавить в список дочерних категорий
     */
    void addCategory(Category c);

    /**
     * добавляет блюдо в категорию
     * @param d - блюдо, которое нужно добавить
     */
    void addDish(Dish d);

    /**
     * метод для доступа в списку блюд категории
     * @return массив блюд категории
     */
    ArrayList<Dish> getDishList();

    /**
     * метод для доступа к дочерним категориям
     * @return - массив дочерних категорий
     */
    ArrayList<Category> getSubCategoryList();
    
}
