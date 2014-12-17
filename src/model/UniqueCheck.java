/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.treecommand.TreeCommand;

public class UniqueCheck implements ITreeCommand {  // класс для проверки уникальности,
                                                // Артем мне объяснял что надо делать так,
                                                // чтобы перенести три бай пасс

    ICategory category;

    private void setCategory(ICategory category){
        this.category = category;
    }

    @Override
    public void handle(ICategory category) {

        for (int i = 0; i < category.getSubCategoryList().size();i++){
            if (category.getSubCategoryList().get(i).getId().equals(searchCategory.getId())){
                return false;
            } else {
                if (!checkUnique(category.getSubCategoryList().get(i), searchCategory)){
                    return false;
                }
            }
        }
        return true;

    }




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
}
