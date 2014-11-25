package controller;


import utils.Dish;
import java.util.ArrayList;
import model.exceptions.CategoryNotFoundException;
import model.exceptions.DishNotFoundException;
import model.Category;

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
     * метод для поиска определнной категории, имя которой известно
     * @param name - имя категории
     * @return - объект, содержащий имя, ид и блюда категории
     * @throws CategoryNotFoundException  - категория не найдена
     */
    Category findCategory(String name) throws CategoryNotFoundException;
    
    /**
     * метод поиска блюда по имени
     * @param category
     * @param name - имя блюда
     * @throws DishNotFoundException - если блюда нет
     * @return  - объект блюда
     */
    Dish findDish(String category, String name) throws DishNotFoundException;
    
    /**
     * поиск блюда по одному названию
     * @param name
     * @return
     * @throws DishNotFoundException 
     */
    Dish findDish(String name) throws DishNotFoundException;

    /**
     * добавление списка блюд в определенную категорию
     * @param category - категория, в которую нужно добавить блюдо
     * @param nameList - список блюд
     * @return
     * @throws Exception 
     */
    Dish addDish(String category, ArrayList<String> nameList) throws Exception;
    
    /**
     * выдает список блюд в категории
     * @param category - нужная категория
     * @return - список блюд соответствующей категории
     */
    public ArrayList<Dish> getCategoryDishList(Category category) throws CategoryNotFoundException;

    
    /**
     * удаляет конкретное блюдо
     * @param name - имя блбда
     * @throws DishNotFoundException  - блюдо не найдено
     */
    void removeDish(String name)  throws DishNotFoundException;

    /**
     * удаляет все блюда из списка
     * @param category - категория в которой находятся блюда
     * @param nameList - список блюд, которые надо удалить
     * @throws Exception 
     */
    void removeDish(String category, ArrayList<String> nameList)throws Exception;
    
    /**
     * удаление всех блюд из списка
     * @param nameList - список блюд к удалению
     * @throws Exception - если блюдо не найдено
     */
    void removeDish(ArrayList<String> nameList) throws Exception;
    
}
