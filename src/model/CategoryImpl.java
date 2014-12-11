package model;

import model.exceptions.IncorrectCostException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class CategoryImpl implements Serializable,ICategory {
    private UUID id;
    private String name;
    ArrayList<Dish> dishList = new ArrayList<Dish>();
    ArrayList<CategoryImpl> subCategoryList = new ArrayList<CategoryImpl>();

    private static ModelImpl model;
    private CategoryImpl rootCategory = model.getRootCategory();

/* КОНСТРУКТОРЫ */
    // Конструктор категории с указанными названием, списками блюд и дочерних категорий.
    CategoryImpl(String name, ArrayList<Dish> dishList, ArrayList<CategoryImpl> categoryList) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.dishList = dishList;
        this.subCategoryList = categoryList;
    }

    // Конструктор категории с указанным названием.
    CategoryImpl(String name) {
        this(name, new ArrayList<Dish>(), new ArrayList<CategoryImpl>());
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
    public ArrayList<CategoryImpl> getSubCategoryList() {
        return this.subCategoryList;
    }

    // Метод изменения списка дочерних категорий.
    public void addSubCategoryList(ArrayList<CategoryImpl> categoryList) {
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
    // Метод получения дочерней категории по имени.
    public CategoryImpl getSubCategory(UUID ID) {
        CategoryImpl subCategory = null;
        for (int i = 0; i < subCategoryList.size(); i++) {
            if (subCategoryList.get(i).getId() == ID) {
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
    public void addCategory(CategoryImpl newCategory){
        subCategoryList.add(newCategory);
    }

    //  Метод удаления категории.
    public void removeCategory(CategoryImpl categoryForDelete) {
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