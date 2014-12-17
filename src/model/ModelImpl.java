/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.treecommand.TreeCommand;

import java.io.*;

public class ModelImpl implements IModel,Serializable {
    private ICategory rootCategory;

    @Override
    public ICategory getRootCategory() {
        if (rootCategory.equals(null)) {
            rootCategory =  new CategoryImpl("МЕНЮ");
        }
        return rootCategory;
    }

    @Override
    public void saveToFile(String name) throws IOException {
        File file = new File(name);
        if (!file.exists()) file.createNewFile();
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(name)));
        out.writeObject(rootCategory);
        out.close();
    }

    @Override
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
    @Override
    public void treeBypass(ITreeCommand command, ICategory rootCategory) {
        if (command != null && rootCategory != null) {
            command.handle(rootCategory);
        }
        if (rootCategory.getSubCategoryList().size() > 0) {
            for (ICategory c : rootCategory.getSubCategoryList()) {
                treeBypass(command, c);
            }
        }
    }

/*
    private boolean checkUnique(ICategory rootCategory, ICategory searchCategory){
        for (int i = 0; i < rootCategory.getSubCategoryList().size();i++){
            if (rootCategory.getSubCategoryList().get(i).getId().equals(searchCategory.getId())){
                return false;
            } else {
                if (!checkUnique(rootCategory.getSubCategoryList().get(i), searchCategory)){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkUnique(ICategory rootCategory, Dish searchDish) {
        for (int i = 0; i < rootCategory.getSubCategoryList().size(); i++) {
            for (int j = 0; j < rootCategory.getDishList().size(); j++) {
                if (rootCategory.getDishList().get(j).getId().equals(searchDish.getId())) {
                    return false;
                }
            }
            if (!checkUnique(rootCategory.getSubCategoryList().get(i), searchDish)) {
                return false;
            }
        }
        return true;
    }
*/
}