/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

public interface ICategory{

    /* МЕТОДЫ ОПЕРАЦИЙ С ВЫДЕЛЕННОЙ КАТЕГОРИЕЙ */
    // Метод получения id категории.
    public int getId();

    // Метод получения названия категории.
    public String getName();

    // Метод изменения названия категории.
    public void setName(String newName);

    // Метод получения списка дочерних категорий.
    public ArrayList<ICategory> getSubCategoryList();

    // Метод изменения списка дочерних категорий.
    public void addSubCategoryList(ArrayList<CategoryImpl> categoryList);

    // Метод получения списка блюд.
    public ArrayList<Dish> getDishList();

    // Метод изменения списка блюд.
    public void addDishList(ArrayList<Dish> dishList);


    /* МЕТОДЫ ОПЕРАЦИЙ С ДОЧЕРНИМИ КАТЕГОРИЯМИ */
    // Метод получения дочерней категории по имени.
    public ICategory getSubCategory(int ID);

    // Метод добавления дочерней категории по названию.
    public void addCategory(String name);

    // Метод добавления дочерней категории по объекту.
    public void addCategory(ICategory newCategory);

    //  Метод удаления категории.
    public void removeCategory(ICategory categoryForDelete);


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
