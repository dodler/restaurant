package model;

import model.IncorrectCostException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class CategoryImpl implements Serializable,ICategory {
    private UUID id;
    private String name;
    ArrayList<Dish> dishList = new ArrayList<Dish>();
    ArrayList<ICategory> subCategoryList = new ArrayList<ICategory>();


/* КОНСТРУКТОРЫ */
    // Конструктор категории с указанными названием, списками блюд и дочерних категорий.
    CategoryImpl(String name, ArrayList<Dish> dishList, ArrayList<ICategory> categoryList) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.dishList = dishList;
        this.subCategoryList = categoryList;
    }

    // Конструктор категории с указанным названием.
    CategoryImpl(String name) {
        this(name, new ArrayList<Dish>(), new ArrayList<ICategory>());
    }


/* МЕТОДЫ ОПЕРАЦИЙ С ВЫДЕЛЕННОЙ КАТЕГОРИЕЙ */
    // Метод получения id категории.
    public UUID getId() {
        return this.id;
    }

    // Метод получения названия категории.
    public String getName() {
        return this.name;
    }

    // Метод изменения названия категории.
    public void setName(String newName) {
        this.name = newName;
    }

    // Метод получения списка дочерних категорий.
    public ArrayList<ICategory> getSubCategoryList() {
        return this.subCategoryList;
    }

    // Метод изменения списка дочерних категорий.
    public void addSubCategoryList(ArrayList<ICategory> categoryList) {
        this.subCategoryList.addAll(categoryList);
    }

    // Метод получения списка блюд.
    public ArrayList<Dish> getDishList() {
        return this.dishList;
    }

    // Метод изменения списка блюд.
    public void addDishList(ArrayList<Dish> dishList) {
        this.dishList.addAll(dishList);
    }


/* МЕТОДЫ ОПЕРАЦИЙ С ДОЧЕРНИМИ КАТЕГОРИЯМИ */
    // Метод получения дочерней категории по ID.
    public ICategory getSubCategory(UUID ID) {
        ICategory subCategory = null;
        for (int i = 0; i < subCategoryList.size(); i++) {
            if (subCategoryList.get(i).getId().equals(ID)) {
                subCategory = subCategoryList.get(i);
            }
        }
        return subCategory;
    }

    // Метод добавления дочерней категории по названию.
    public void addCategory(String name){
        subCategoryList.add(new CategoryImpl(name));
    }

    // Метод добавления дочерней категории по объекту.
    public void addCategory(ICategory newCategory){
        subCategoryList.add(newCategory);
    }

    //  Метод удаления категории.
    public void removeCategory(ICategory categoryForDelete) {
        subCategoryList.remove(categoryForDelete);
    }


/* МЕТОДЫ ОПЕРАЦИЙ С БЛЮДАМИ */
    // Метод добавления блюда по названию и цене.
    public void addDish(String name,double cost) throws IncorrectCostException{
        dishList.add(new Dish(name,cost));
    }

    // Метод добавления блюда по названию.
    public void addDish(String name) throws IncorrectCostException{
        dishList.add(new Dish(name));
    }

    // Метод добавления блюда по объекту.
    public void addDish(Dish newDish){
        dishList.add(newDish);
    }

    // Метод удаления блюда.
    public void removeDish(Dish dishForDelete) {
        dishList.remove(dishForDelete);
    }

}