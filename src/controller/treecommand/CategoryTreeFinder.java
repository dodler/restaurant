/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.treecommand;

import model.ICategory;

/**
 * команда с поддержкой события
 * когда находится категория с заданным именем вызывает метод обраобтки события
 * при этом найденная категория должна быть уже задана
 * @author dodler
 */
public class CategoryTreeFinder extends TreeCommand{
    
    private CategoryFoundEvent cfe;
    private String name;
    
    public CategoryTreeFinder(String name){
        this.name = name;
    }
    
    public CategoryTreeFinder(String name, CategoryFoundEvent cfe){
        this(name);
        this.cfe = cfe;
    }
    
    public void setEventHandler(CategoryFoundEvent cfe){
        this.cfe = cfe;
    }
    
    @Override
    public void handle(ICategory category){
        if (category.getName().equals(name)){
            cfe.setCategory(category);
            cfe.onCategoryFound();
        }
    }
}
