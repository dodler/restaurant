/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 * @author dodler
 */
public class CategoryImpl implements ICategory {

    private final int id;
    private final String name;
    private final ArrayList<Dish> dishs;

    {
        this.dishs = new ArrayList<>();
    }
    
    public CategoryImpl(String name) {
        this.name = name;
        this.id = (int) Math.round(Math.random() * 1000000);
    }

    public CategoryImpl(String name, int id) {
        this.id = id;
        this.name = name;
    }

    @Override
    public ArrayList<Dish> getDishs() {
        return this.dishs;
    }

    @Override
    public void addDish(Dish d) {
        dishs.add(d);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

}
