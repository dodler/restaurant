package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class CategoryImpl implements Serializable, ICategory, Cloneable {

    private int id;
    private String name;
    private ArrayList<Dish> dishList = new ArrayList<>();
    private ArrayList<ICategory> subCategoryList = new ArrayList<>();

    /**
     * сравнение по ид
     *
     * @param o - объект - категория
     * @return равенство ид
     */
    @Override
    public boolean equals(Object o) {
        if (!o.getClass().equals(this.getClass())) {
            return false;
        }

        return (((ICategory) o).getId() == this.id);

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.id;
        return hash;
    }

    /* КОНСТРУКТОРЫ */
    // Конструктор категории с указанными названием, списками блюд и дочерних категорий.
    CategoryImpl(String name, ArrayList<Dish> dishList, ArrayList<ICategory> categoryList) {
        this.id = (-1) * Calendar.getInstance().hashCode();// TODO переписать с автоинкрементом ид в модели
        this.name = name;
        this.dishList = dishList;
        this.subCategoryList = categoryList;
    }

    // Конструктор категории с указанным названием.
    public CategoryImpl(String name) {
        this(name, new ArrayList<>(), new ArrayList<>());
    }

    public CategoryImpl(String name, int id) {
        this(name);
        this.id = id;
    }


    /* МЕТОДЫ ОПЕРАЦИЙ С ВЫДЕЛЕННОЙ КАТЕГОРИЕЙ */
    // Метод получения id категории.
    @Override
    public int getId() {
        return this.id;
    }

    // Метод получения названия категории.
    @Override
    public String getName() {
        return this.name;
    }

    // Метод изменения названия категории.
    /**
     *
     * @param newName
     */
    public void setName(String newName) {
        this.name = newName;
    }

    // Метод получения списка дочерних категорий.
    /**
     *
     * @return
     */
    @Override
    public ArrayList<ICategory> getSubCategoryList() {
        return this.subCategoryList;
    }

    // Метод изменения списка дочерних категорий.
    @Override
    public void addSubCategoryList(ArrayList<CategoryImpl> categoryList) {
        this.subCategoryList.addAll(categoryList);
    }

    // Метод получения списка блюд.
    @Override
    public ArrayList<Dish> getDishList() {
        return this.dishList;
    }

    // Метод изменения списка блюд.
    public void addDishList(ArrayList<Dish> dishList) {
        this.dishList.addAll(dishList);
    }


    /* МЕТОДЫ ОПЕРАЦИЙ С ДОЧЕРНИМИ КАТЕГОРИЯМИ */
    // Метод получения дочерней категории по имени.
    /**
     *
     * @param id
     * @return
     */
    @Override
    public ICategory getSubCategory(int id) {
        ICategory subCategory = null;
        for (ICategory subCategoryList1 : subCategoryList) {
            if (subCategoryList1.getId() == id) {
                subCategory = subCategoryList1;
            }
        }
        return subCategory;
    }

    // Метод добавления дочерней категории по названию.
    /**
     *
     * @param name
     */
    @Override
    public void addCategory(String name) {
        subCategoryList.add(new CategoryImpl(name));
    }

    // Метод добавления дочерней категории по объекту.
    public void addCategory(CategoryImpl newCategory) {
        subCategoryList.add(newCategory);
    }

    //  Метод удаления категории.
    public void removeCategory(CategoryImpl categoryForDelete) {
        subCategoryList.remove(categoryForDelete);
    }


    /* МЕТОДЫ ОПЕРАЦИЙ С БЛЮДАМИ */
    // Метод добавления блюда по названию и цене.
    /**
     *
     * @param name
     * @param cost
     * @throws IncorrectCostException
     */
    @Override
    public void addDish(String name, double cost) throws IncorrectCostException {
        dishList.add(new Dish(name, cost));
    }

    // Метод добавления блюда по названию.
    /**
     *
     * @param name
     * @throws IncorrectCostException
     */
    @Override
    public void addDish(String name) throws IncorrectCostException {
        dishList.add(new Dish(name));
    }

    // Метод добавления блюда по объекту.
    /**
     *
     * @param newDish
     */
    @Override
    public void addDish(Dish newDish) {
        dishList.add(newDish);
    }

    // Метод удаления блюда.
    /**
     *
     * @param dishForDelete
     */
    @Override
    public void removeDish(Dish dishForDelete) {
        dishList.remove(dishForDelete);
    }

    @Override
    public void addCategory(ICategory newCategory) {
        this.subCategoryList.add(newCategory);
    }

    @Override
    public void removeCategory(ICategory categoryForDelete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * в этом клоне не копируется список подкатегорий subCategoryList
     * остальное копируется
     * @return
     * @throws CloneNotSupportedException такого не будет клон поддерживается
     */
    @Override
    public CategoryImpl clone() throws CloneNotSupportedException {
        CategoryImpl c = (CategoryImpl) super.clone();
        c.name = this.name;
        c.id = this.id;
        c.dishList=(ArrayList<Dish>) this.dishList.clone();
        return c;
    }

}
