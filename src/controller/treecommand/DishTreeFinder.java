/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.treecommand;

import model.Dish;
import model.ICategory;

/**
 *
 * @author dodler
 */
public class DishTreeFinder extends TreeCommand{
    
    private DishFoundEvent dfe;
    
    private String name;
    
    public DishTreeFinder(String name){
        this.name = name;
    }

    public DishTreeFinder(String name, DishFoundEvent dfe) {
        this(name);
        setEventHandler(dfe);
    }
    
    public void setEventHandler(DishFoundEvent dfe){
        this.dfe = dfe;
    }
    
    @Override 
    public void handle(ICategory category) {
        for(Dish d:category.getDishList()){
            if (d.getName().equals(name) || name.equals("")){
                dfe.setDish(d);
                dfe.onDishFound();
            }
        }
    }
    
}
