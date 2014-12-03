/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.context.TreePassContext;
import model.exceptions.CategoryNotFoundException;
import controller.treecommand.ConsoleTreeWriter;
import controller.treecommand.ConsoleTreeWriterPriced;
import controller.treecommand.IContextCommand;
import controller.treecommand.TreeCommand;
import controller.treecommand.TreeFinder;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Dish;
import model.ICategory;
import view.IConsoleView;

/**
 *
 * @author Артем
 */
public class ModelControllerImpl implements IModelController {

    private ICategory rootCategory;
    
    private IConsoleView view;

    /**
     * конструктор контроллера создает контроллер с заданной рутовой категорией
     * соответственно до создания контроллера нужно создать модель
     *
     * @param rootCategory - ссылкана рутовую категорию
     */
    public ModelControllerImpl(ICategory rootCategory) {
        this.rootCategory = rootCategory;
    }
    
    public ModelControllerImpl(ICategory rootCategory, IConsoleView view){
        this(rootCategory);
        this.view = view;
    }
    
    public void setView(IConsoleView view){
        this.view = view;
    }

    /**
     * метод обхода рекурсивного обхода дерева
     *
     * @param command - команда, которую нужно применить к текущему элементу
     * @param category - категория с которой нужно начать обход
     */
    private void treeBypass(TreeCommand command, ICategory category) {
        if (command != null && category != null) {
            command.handle(category);
        }
        if (category.getSubCategoryList().size() > 0) {
            for (ICategory c : category.getSubCategoryList()) {
                treeBypass(command, c);
            }
        }
    }
    
    private Object treeBypass(IContextCommand command, ICategory category, TreePassContext context){
        if (command != null && category != null) {
            command.handle(context, category);
        }
        
        context.parent = category;
        if (category.getSubCategoryList().size() > 0) {
            for (ICategory c : category.getSubCategoryList()) {
                treeBypass(command, c, context);
            }
        } // TODO доделать адекватный обход
        return null;
    }

    @Override
    public void showCategoryDishTreePriced() {
        treeBypass(new ConsoleTreeWriterPriced(view), rootCategory);
    }

    @Override
    public void showCategoryDishTree() {
        this.treeBypass(new ConsoleTreeWriter(view), rootCategory);
    }

    private ICategory findCategory(ICategory category, String name) throws CategoryNotFoundException {
        if (category.getName().equals(name)) {
            return category;
        }
        throw new CategoryNotFoundException();
    }

    @Override
    public void showDishListPriced(String category) {
        ICategory cat = null;
        try {
            cat = findCategory(rootCategory, category);
        } catch (CategoryNotFoundException cnfe) {
            System.out.println("Не найдено");
        }

        for (Dish d : cat.getDishList()) {
            System.out.println(d.getName() + " " + d.getCost());
        }
    }

    @Override
    public void showDishList(String category) {
        ICategory cat = null;
        try {
            cat = findCategory(rootCategory, category);
        } catch (CategoryNotFoundException cnfe) {
            System.out.println("Не найдено");
        }

        for (Dish d : cat.getDishList()) {
            System.out.println(d.getName());
        }
    }
    
    private void delete(ICategory category, String name){
        for(Dish d:category.getDishList()){
            if (d.getName().equals(name)){
                category.getDishList().remove(d);
            }
        }
    }
    
    @Override
    public void deleteDish(String name) {
        delete(rootCategory, name);
    }

    @Override
    public void deleteDishCategory(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteCategory(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteDish(String category, String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addDish(String name, double price) {
        try {
            rootCategory.addDish(new Dish(name, price));
        } catch (Exception ex) {
            Logger.getLogger(ModelControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addDish(String category, String name, double price) {
        ICategory cat = null;

        try {
            cat = findCategory(rootCategory, category);
        } catch (CategoryNotFoundException ex) {
            Logger.getLogger(ModelControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void editCategoryName(String oldName, String newName) {
        ICategory cat = null;

        try {
            cat = findCategory(rootCategory, oldName);
        } catch (CategoryNotFoundException ex) {
            Logger.getLogger(ModelControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        cat.setName(newName);
    }

    @Override
    public void editDishPrice(String name, double newPrice) {
        
    }

    @Override
    public void editDishName(String oldName, String newName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editDishCategory(String oldCategory, String name, String newCategory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void findName(String name) {
        treeBypass(new TreeFinder(name), rootCategory);
    }

}
