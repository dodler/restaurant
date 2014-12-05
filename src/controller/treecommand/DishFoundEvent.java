/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.treecommand;

import model.Dish;

/**
 *
 * @author dodler
 */
public abstract class DishFoundEvent {

    protected Dish d;

    public Dish getDish() {
        return this.d;
    }

    public void setDish(Dish d){
        this.d = d;
    }

    public abstract void onDishFound();
}
