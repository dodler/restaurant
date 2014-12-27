/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.treecommand.CategoryFoundEvent;
import controller.treecommand.CategoryTreeFinder;
import controller.treecommand.CategoryWriter;
import controller.treecommand.ConsoleTreeWriter;
import controller.treecommand.ConsoleTreeWriterPriced;
import java.io.PrintStream;
import java.util.ArrayList;
import model.Dish;
import model.ICategory;
import model.IModel;

/**
 *
 * @author dodler
 */
public class ConsoleViewImpl implements IConsoleView {

    private IModel model;
    private ICategory rootCategory;
    
    /**
     * вывод подкатегорий категории категория задается именем cat производится
     * сначала поиск категории потом вывод ее потомков работает крайне медленно,
     * но работает
     *
     * @param cat - имя категории
     */
    @Override
    public void showCategoryList(String cat) {
        final IConsoleView  view = this;
        CategoryFoundEvent cfe = new CategoryFoundEvent() {
            @Override
            public void onCategoryFound() {
                model.treeBypass(new CategoryWriter(view), this.cat);
            }
        };
        model.treeBypass(new CategoryTreeFinder(cat, cfe), rootCategory);
    }
    
    @Override
    public void showCategoryList() {
        model.treeBypass(new CategoryWriter(this), rootCategory);
    }
    
    @Override
    public void showDishTree(String category) {
        /**
         * сделал наблюдатель для поиска при нахождении просто запускает событие
         */
        CategoryFoundEvent cfe = new CategoryFoundEvent() {
            @Override
            public void onCategoryFound() {
                for (Dish d : cat.getDishList()) {
                    show("Блюдо" + d.getName());// вывод найденных блюд в категории 
                }
            }
        };

        model.treeBypass(new CategoryTreeFinder(category, cfe), rootCategory); // Запуск поиска по дереву категорий категории 
        // с заданным именем
    }
    
    @Override
    public void showDishTreePriced() {
        model.treeBypass(new ConsoleTreeWriterPriced(this), rootCategory);
    }

    @Override
    public void showDishTree() {
        model.treeBypass(new ConsoleTreeWriter(this), rootCategory);
    }

    @Override
    public void showDishTreePriced(String category) {
        /**
         * сделал наблюдатель для поиска при нахождении просто запускает событие
         */
        CategoryFoundEvent cfe = new CategoryFoundEvent() {
            @Override
            public void onCategoryFound() {
                for (Dish d : cat.getDishList()) {
                    show(d);// вывод найденных блюд в категории 
                }
            }
        };

        model.treeBypass(new CategoryTreeFinder(category, cfe), rootCategory); // Запуск поиска по дереву категорий категории 
        // с заданным именем

    }
    
    PrintStream out;

    public ConsoleViewImpl() {
        out = System.out;
    }

    public ConsoleViewImpl(PrintStream out, IModel model){
        this.out = out;
        this.rootCategory = model.getRootCategory();
        this.model = model;
    }

    @Override
    public void show(ICategory cat) {
        out.print("Категория: " + cat.getName());
        out.println(" содержит " + cat.getDishList().size() + " блюд. ");
    }

    @Override
    public void showWithDishes(ICategory cat) {
        show(cat);
        showDish(cat.getDishList());
    }

    @Override
    public void show(ArrayList<ICategory> catList) {
        for (ICategory cat : catList) {
            show(cat);
        }
    }

    @Override
    public void showDish(ArrayList<Dish> dishList) {
        StringBuilder sb = new StringBuilder();
        for (Dish d : dishList) {
            sb.append("Блюдо ");
            sb.append(d.getName());
            sb.append(" стоит ");
            sb.append(d.getPrice());
            out.println(sb.toString());
            sb.delete(0, sb.length());
        }
    }

    @Override
    public void show(String source) {
        out.println(source);
    }

    @Override
    public void show(Dish d) {
        StringBuilder sb = new StringBuilder();
        sb.append("Блюдо ");
        sb.append(d.getName());
        sb.append(" стоит ");
        sb.append(d.getPrice());
        out.println(sb.toString());
    }

}
