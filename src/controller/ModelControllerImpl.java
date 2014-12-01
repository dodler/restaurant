/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.treecommand.ConsoleTreeWriter;
import controller.treecommand.ConsoleTreeWriterPriced;
import controller.treecommand.TreeCommand;
import controller.treecommand.TreeFinder;
import model.ICategory;

/**
 *
 * @author Артем
 */
public class ModelControllerImpl implements IModelController {

    private ICategory rootCategory;

    /**
     * конструктор контроллера создает контроллер с заданной рутовой категорией
     * соответственно до создания контроллера нужно создать модель
     *
     * @param rootCategory - ссылкана рутовую категорию
     */
    public ModelControllerImpl(ICategory rootCategory) {
        this.rootCategory = rootCategory;
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

    @Override
    public void showCategoryDishTreePriced() {
        treeBypass(new ConsoleTreeWriterPriced(), rootCategory);
    }

    @Override
    public void showCategoryDishTree() {
        this.treeBypass(new ConsoleTreeWriter(), rootCategory);
    }

    @Override
    public void showDishListPriced(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showDishList(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteDish(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addDish(String category, String name, double price) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editCategoryName(String oldName, String newName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editDishPrice(String name, double newPrice) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
