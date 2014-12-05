/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.treecommand;

import model.ICategory;

/**
 *
 * @author dodler
 */
public class CategoryParentFinder extends TreeCommand{

    private CategoryFoundEvent cfe;
    
    private String name;
    
    public CategoryParentFinder(String name, CategoryFoundEvent cfe){
        this.name = name;
        this.cfe = cfe;
    }
    
    public void setEventHandler(CategoryFoundEvent cfe){
        this.cfe = cfe;
    }
    
    @Override
    public void handle(ICategory category) {
        for(ICategory cat:category.getSubCategoryList()){
            if(cat.getName().equals(name)){
                cfe.setCategory(category);
                cfe.onCategoryFound();
            }
        }
    }
    
}
