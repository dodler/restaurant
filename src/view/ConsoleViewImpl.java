/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.PrintStream;
import java.util.ArrayList;
import model.Dish;
import model.ICategory;

/**
 *
 * @author dodler
 */
public class ConsoleViewImpl implements IView{

    PrintStream out;
    
    public ConsoleViewImpl(){
        out = System.out;
    }
    
    public ConsoleViewImpl(PrintStream out){
        this.out = out;
    }
    
    @Override
    public void show(ICategory cat) {
        out.print("Категория: " + cat.getName());
        out.println(" содержит " + cat.getDishList().size() + " блюд. "); 
    }

    @Override
    public void showWithDishes(ICategory cat) {
        show(cat);
        showDish(cat.getDishList());
    }

    @Override
    public void show(ArrayList<ICategory> catList) {
        for(ICategory cat:catList){
            show(cat);
        }
    }

    @Override
    public void showDish(ArrayList<Dish> dishList) {
        StringBuilder sb = new StringBuilder();
        for(Dish d:dishList){
            sb.append("Блюдо ");
            sb.append(d.getName());
            sb.append(" стоит ");
            sb.append(d.getPrice());
            out.println(sb.toString());
            sb.delete(0, sb.length());
        }
    }

    @Override
    public void show(String source) {
        out.println(source);
    }
    
}
