/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.treecommand.TreeCommand;

import java.io.*;
import java.util.UUID;

public class ModelImpl implements IModel, Serializable {
    private ICategory rootCategory;

    public ICategory getRootCategory() {
        if (rootCategory == null) {
            rootCategory =  new CategoryImpl("МЕНЮ");
        }
        return rootCategory;
    }

    public void saveToFile(String name) throws IOException {
        File file = new File(name);
        if (!file.exists()) file.createNewFile();
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(name)));
        out.writeObject(rootCategory);
        out.close();
    }

    public void loadFromFile(String name) throws IOException {
        try {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(name)));
            rootCategory = (CategoryImpl) in.readObject();
        } catch (ClassNotFoundException e) {
            System.out.print(e + "Root-категория не найдена. Создаем новую root-категорию");
            rootCategory = new CategoryImpl("МЕНЮ");
        }
    }


    // Метод рекурсивного обхода дерева
    public void treeBypass(TreeCommand command, ICategory rootCategory) {
        if (command != null && rootCategory != null) {
            command.handle(rootCategory);
        }
        if (rootCategory.getSubCategoryList().size() > 0) {
            for (ICategory c : rootCategory.getSubCategoryList()) {
                treeBypass(command, c);
            }
        }
    }

    public boolean checkUnique(ICategory rootCategory, ICategory searchCategory){
        for(ICategory category : rootCategory.getSubCategoryList()){
            if (category.getId().equals(searchCategory.getId())) {
                return false;
            } else {
                if (!checkUnique(category, searchCategory)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkUnique(ICategory rootCategory, Dish searchDish) {
        for (ICategory category : rootCategory.getSubCategoryList()) {
            for (Dish dish :  category.getDishList()) {
                if (dish.getId() == searchDish.getId()) {
                    return false;
                }
            }
            if (!checkUnique(category, searchDish)) {
                return false;
            }
        }
        return true;
    }

}