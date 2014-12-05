package controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dodler
 */
public interface IModelController {
    
    /**
     * вывод всех категорий и блюд с ценами
     */
    void showCategoryDishTreePriced();

    /**
     * вывод дерева категорий и блюд
     */
    void showCategoryDishTree();

    /**
     * вывод всех блюд из категории с ценами
     * @param category - имя категории, из которой надо все вывести
     */
    void showDishListPriced(String category);

    /**
     * удаляет блюдо. будет произведен поиск по всему дереву категорий
     *
     * @param name - имя блюда
     */
    void deleteDish(String name);

    /**
     * удаляет все блюда в категории но сохраняет категорию
     *
     * @param category - категория
     */
    void deleteDishCategory(String category);

    /**
     * удаляет категорию с блюдами целиком
     *
     * @param category - категория, которую нужно удалить
     */
    void deleteCategory(String category);

    /**
     * добавляет блюдо в корневую категорию
     *
     * @param name - имя блюда
     * @param price - цена блюда
     */
    void addDish(String name, double price);

    /**
     * добавляет блюдо в произвольную категорию
     *
     * @param category - категория, в которую нужно добавить блюдо
     * @param name - имя нового блюда
     */
    void addDish(String category, String name, double price);
    
    /**
     * метод переименования категории
     * @param oldName - старое имя категории
     * @param newName - новое имя категории
     */
    void editCategoryName(String oldName, String newName);

    void editDishPrice(String name, double newPrice);

    void editDishName(String oldName, String newName);

    void editDishCategory(String oldCategory, String name, String newCategory);
    
}
