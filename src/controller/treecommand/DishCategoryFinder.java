/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.treecommand;

import model.Dish;
import model.ICategory;

/**
 * команда поиска категории, в которой расположено блюдо
 * @author dodler
 */
public class DishCategoryFinder extends TreeCommand{

    private String name;
    private CategoryFoundEvent cfe;
    
    public DishCategoryFinder(String name, CategoryFoundEvent cfe){
        this.name = name;
        this.cfe = cfe;
    }
    
    public void setEventHandler(CategoryFoundEvent cfe){
        this.cfe = cfe;
    }
    
    
    @Override
    public void handle(ICategory category) {
        for(Dish d:category.getDishList()){
            if (d.getName().equals(name)){
                cfe.setCategory(category);
                cfe.onCategoryFound();
            }
        }
    }
    
}
