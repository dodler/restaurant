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
//
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
     * @param price - цена блюда
     */
    void addDish(String category, String name, double price);
    
    /**
     * добавление пустой категории в указанную категорию
     * @param category - категория куда надо добавить
     * @param name - имя категории
     */
    void addCategory(String category, String name);
    
    /**
     * добавление пустой категории в корневую категорию
     * @param name - имя новой пустой категории
     */
    void addCategory(String name);
    
    /**
     * метод переименования категории
     * @param oldName - старое имя категории
     * @param newName - новое имя категории
     */
    void editCategoryName(String oldName, String newName);

    void editDishPrice(String name, double newPrice);

    void editDishName(String oldName, String newName);

    void editDishCategory(String oldCategory, String name, String newCategory);
    
    /**
     * метод выводит через IConsoleView все блюда с ценой больше заданной
     * @param price - пороговая цена
     */
    void findPriceMore(double price);
    
    /**
     * метод выводит через IConsoleView все блюда с ценой меньше заданной 
     * @param price - пороговая цена
     */
    void findPriceLess(double price);
    
    /**
     * метод выводит через IConsoleView все блюда с ценой равной заданной
     * @param price - заданная цена
     */
    void findPriceEqual(double price);
    
    /**
     * метод поиска блюд в диапазоне цен
     * также производится вывод через IConsoleView
     * всех найденных блюд
     * @param left - нижняя пороговая цена
     * @param right - верхняя пороговая цена
     */
    void findPriceInterval(double left, double right);
    
    /**
     * поиск блюда по шаблону с выводом черещ вью
     * шаблон - имя
     * * - любое количество символов
     * допустимо любоеколичество звездочек
     * @param pattern - шаблон поиска
     */
    void findPattern(String pattern);
    
}
