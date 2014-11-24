/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 * интерфейс для вьюхи будет командная строка также нужно сделать возможность
 * вывода в файл что ли
 *
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
     *
     * @param category
     */
    void showDishList(String category);

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
     * удаляет одно блюдо из категории
     *
     * @param category - категория, которая содержит блюдо
     * @param name - имя блюда
     */
    void deleteDish(String category, String name);

    /**
     * добавляет блюдо в корневую категорию
     *
     * @param name - имя блюда
     */
    void addDish(String name);

    /**
     * добавляет блюдо в произвольную категорию
     *
     * @param category - категория, в которую нужно добавить блюдо
     * @param name - имя нового блюда
     */
    void addDish(String category, String name);
    
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
