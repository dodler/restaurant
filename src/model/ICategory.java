/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.exceptions.IncorrectCostException;

import java.util.ArrayList;
import java.util.UUID;

public interface ICategory {

    /* МЕТОДЫ ОПЕРАЦИЙ С ВЫДЕЛЕННОЙ КАТЕГОРИЕЙ */
    // Метод получения id категории.
    public UUID getId();

    // Метод получения названия категории.
    public String getName();

    // Метод изменения названия категории.
    public void setName(String newName);

    // Метод получения списка дочерних категорий.
    public ArrayList<Category> getSubCategoryList();

    // Метод изменения списка дочерних категорий.
    public void addSubCategoryList(ArrayList<Category> categoryList);

    // Метод получения списка блюд.
    public ArrayList<Dish> getDishList();

    // Метод изменения списка блюд.
    public void addDishList(ArrayList<Dish> dishList);


    /* МЕТОДЫ ОПЕРАЦИЙ С ДОЧЕРНИМИ КАТЕГОРИЯМИ */
    // Метод получения дочерней категории по имени.
    public Category getSubCategory(UUID ID);

    // Метод добавления дочерней категории по названию.
    public void addCategory(String name);

    // Метод добавления дочерней категории по объекту.
    public void addCategory(Category newCategory);

    //  Метод удаления категории.
    public void removeCategory(Category categoryForDelete);


    /* МЕТОДЫ ОПЕРАЦИЙ С БЛЮДАМИ */
    // Метод добавления блюда по названию и цене.
    public void addDish(String name,double cost) throws IncorrectCostException;

    // Метод добавления блюда по названию.
    public void addDish(String name) throws IncorrectCostException;

    // Метод добавления блюда по объекту.
    public void addDish(Dish newDish);

    // Метод удаления блюда.
    public void removeDish(Dish dishForDelete);
}
