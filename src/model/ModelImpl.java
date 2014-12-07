/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.treecommand.TreeCommand;

import java.io.*;
import java.util.UUID;

public class ModelImpl implements IModel,Serializable {
    private Category rootCategory;

    public Category getRootCategory() {
        if (rootCategory == null) {
            rootCategory =  new Category("МЕНЮ");
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
            rootCategory = (Category) in.readObject();
        } catch (ClassNotFoundException e) {
            System.out.print(e + "Root-категория не найдена. Создаем новую root-категорию");
            rootCategory = new Category("МЕНЮ");
        }
    }


    // Метод рекурсивного обхода дерева НЕ ОБРАЩАТЬ ВНИМАНИЯ! только начал разбираться
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


    public boolean checkUnique(Category rootCategory, Category searchCategory){
        for (int i = 0; i < rootCategory.subCategoryList.size();i++){
            if (rootCategory.subCategoryList.get(i).getId() == searchCategory.getId()){
                return false;
            } else {
                if (!checkUnique(rootCategory.subCategoryList.get(i), searchCategory)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkUnique(Category rootCategory, Dish searchDish) {
        for (int i = 0; i < rootCategory.subCategoryList.size(); i++) {
            for (int j = 0; j < rootCategory.dishList.size(); j++) {
                if (rootCategory.dishList.get(j).getId() == searchDish.getId()) {
                    return false;
                }
            }
            if (!checkUnique(rootCategory.subCategoryList.get(i), searchDish)) {
                return false;
            }
        }
        return true;
    }

}