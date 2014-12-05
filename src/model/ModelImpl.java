/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.*;
import java.util.UUID;

public class ModelImpl implements IModel,Serializable {
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
            rootCategory = (ICategory) in.readObject();
        } catch (ClassNotFoundException e) {
            System.out.print(e + "Root-категория не найдена. Создаем новую root-категорию");
            rootCategory = new CategoryImpl("МЕНЮ");
        }
    }

    public boolean checkUnique(ICategory rootCategory, ICategory searchCategory){
        for (int i = 0; i < rootCategory.getSubCategoryList().size();i++){
            if (rootCategory.getSubCategoryList().get(i).getId() == searchCategory.getId()){
                return false;
            } else {
                if (!checkUnique(rootCategory.getSubCategoryList().get(i), searchCategory)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkUnique(ICategory rootCategory, Dish searchDish) {
        for (int i = 0; i < rootCategory.getSubCategoryList().size(); i++) {
            for (int j = 0; j < rootCategory.getDishList().size(); j++) {
                if (rootCategory.getDishList().get(j).getID() == searchDish.getID()) {
                    return false;
                }
            }
            if (!checkUnique(rootCategory.getSubCategoryList().get(i), searchDish)) {
                return false;
            }
        }
        return true;
    }

}