/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author dodler
 */
public interface ICategory {

    void addDish(Dish d);

    ArrayList<Dish> getDishs();
    /**
     * возвращает ид категории
     *
     */
    int getId();
    
    /**
     * возвращает имя категории
     * @return имя категории
     */
    String getName();

    
}
