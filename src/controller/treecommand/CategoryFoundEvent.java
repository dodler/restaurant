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
public abstract class CategoryFoundEvent {
    
    protected ICategory cat;
    
    public ICategory getCategory(){
        return this.cat;
    }
    public void setCategory(ICategory category){
        this.cat = category;
    }
    
    public abstract void onCategoryFound();
}
