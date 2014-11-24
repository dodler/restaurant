/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 * интерфейс для вьюхи
 * будет командная строка
 * также нужно сделать возможность вывода в файл что ли
 * @author dodler
 */
public interface IConsoleView {
    /**
     * вывод всех категорий
     */
    void showCategoryTree();
    
    /**
     * вывод дерева категорий и блюд
     */
    void showCategoryDishTree();
    
    /**
     * вывод всех блюд
     */
    void showDishList();
    
    /**
     * вывод блюд категории
     * @param category 
     */
    void showDishList(String category);
}
