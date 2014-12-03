/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import model.Dish;
import model.ICategory;

/**
 * интерфейс для вьюхи будет командная строка также нужно сделать возможность
 * вывода в файл что ли
 *
 * @author dodler
 */
public interface IConsoleView {
    void show(ICategory cat);
    void showWithDishes(ICategory cat);

    /**
     *
     * @param catList
     */
    void show(ArrayList<ICategory> catList);
    void showDish(ArrayList<Dish> dishList);
    void show(String source);
    
}
