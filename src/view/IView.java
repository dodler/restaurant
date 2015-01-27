/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import model.Dish;
import model.ICategory;

/**
 * интерфейс для вьюхи будет командная строка также нужно сделать возможность
 * вывода в файл что ли
 *
 * @author dodler
 */
public interface IView {
    
    /**
     * вывод всех категорий
     */
    void showCategoryList();
    
    /**
     * вывод подкатегорий категории cat
     * @param cat - имя категории, из которой нужно вывести
     */
    void showCategoryList(String cat);
    
    void showDishTree(String category);
    
    /**
     * вывод всех категорий и блюд с ценами
     */
    void showDishTreePriced();

    /**
     * вывод дерева категорий и блюд
     */
    void showDishTree();

    /**
     * вывод всех блюд из категории с ценами
     * @param category - имя категории, из которой надо все вывести
     */
    void showDishTreePriced(String category);
    
    void show(ICategory cat);
    void showWithDishes(ICategory cat);

    /**
     *
     * @param catList
     */
    void show(ArrayList<ICategory> catList);
    void showDish(ArrayList<Dish> dishList);
    void show(String source);
    void show(Dish d);
    
}
