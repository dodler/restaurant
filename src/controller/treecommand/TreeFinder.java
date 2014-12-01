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
public class TreeFinder extends TreeCommand{

    private String name;
    
    public TreeFinder(String name){
        this.name = name;
    }
    
    @Override
    public void handle(ICategory category) {
        for(Dish d:category.getDishList()){
            if (d.getName().equals(name)){
                System.out.println("gotcha:" + name);
            }
        }
    }
    
}
