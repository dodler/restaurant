/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.treecommand.TreeCommand;

import java.io.*;

public class ModelImpl implements IModel, Serializable {
    private ICategory rootCategory;

    /**
     *
     * @return
     */
    @Override
    public ICategory getRootCategory() {
        if (rootCategory == null) {
            rootCategory =  new CategoryImpl("МЕНЮ");
        }
        return rootCategory;
    }

    /**
     *
     * @param name
     * @throws IOException
     */
    @Override
    public void save(String name) throws IOException {
        File file = new File(name);
        if (!file.exists()) file.createNewFile();
        ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(name)));
        out.writeObject(rootCategory);
        out.close();
    }

    /**
     *
     * @param name
     * @throws IOException
     */
    @Override
    public void load(String name) throws IOException {
        try {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(name)));
            rootCategory = (CategoryImpl) in.readObject();
        } catch (ClassNotFoundException e) {
            System.out.print(e + "Root-категория не найдена. Создаем новую root-категорию");
            rootCategory = new CategoryImpl("МЕНЮ");
        }
    }


    // Метод рекурсивного обхода дерева

    /**
     *
     * @param command
     * @param rootCategory
     */
        @Override
    public void treeBypass(TreeCommand command, ICategory rootCategory) {
        if (command != null && rootCategory != null) {
            command.handle(rootCategory);
        }
        if (!rootCategory.getSubCategoryList().isEmpty()) {
            for (ICategory c : rootCategory.getSubCategoryList()) {
                treeBypass(command, c);
            }
        }
    }

    /**
     *
     * @param rootCategory
     * @param searchCategory
     * @return
     */
    @Override
    public boolean checkUnique(ICategory rootCategory, ICategory searchCategory){
        for(ICategory category : rootCategory.getSubCategoryList()){
            if (category.getId() ==searchCategory.getId()) {
                return false;
            } else {
                if (!checkUnique(category, searchCategory)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     *
     * @param rootCategory
     * @param searchDish
     * @return
     */
    @Override
    public boolean checkUnique(ICategory rootCategory, Dish searchDish) {
        for (int i = 0; i < rootCategory.getSubCategoryList().size(); i++) {
            for (int j = 0; j < rootCategory.getDishList().size(); j++) {
                if (rootCategory.getDishList().get(j).getId() == searchDish.getId()) {
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