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
    void showCategoryList(ICategory cat);
    
    /**
     * вывод блюд и категорий, которые являются дочерними
     * по отношению к категории
     * @param category - категория, подкатегории которой нужно выводить
     */
    void showDishTree(ICategory category);
    
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
    void showDishTreePriced(ICategory category);
    
    void show(ICategory cat);
    /**
     * вывод категории с блюдами
     * @param cat - категория, которую нужно вывести
     */
    void showWithDishes(ICategory cat);

    /**
     * вывод списа категорий без подкатегорий
     * @param catList
     */
    void show(ArrayList<ICategory> catList);
    /**
     * вывод списка блюд на экран с ценами
     * @param dishList 
     */
    void showDish(ArrayList<Dish> dishList);
    /**
     * вывод строки на экран
     * @param source - строка, которую нужно вывести на экран
     */
    void show(String source);
    /**
     * вывод единственного блюда на экран
     * @param d 
     */
    void show(Dish d);
    
}
