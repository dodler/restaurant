/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.treecommand;

import model.Dish;
import model.ICategory;
import view.IConsoleView;

/**
 *
 * @author dodler
 */
public class ConsoleTreeWriterPriced extends TreeCommand{

    private StringBuilder categoryLevel = new StringBuilder();
    private StringBuilder output = new StringBuilder();
    
    public ConsoleTreeWriterPriced(IConsoleView view){
        super(view);
    }
    
    @Override
    public void handle(ICategory category){
        categoryLevel.append("_:");
        output.append(categoryLevel);
        output.append(category.getName()); // для вывода имени категории с учетом уровня вложенности
        view.show(output.toString());
        output.replace(0, output.length(), "");
        for(Dish d:category.getDishList()){
            output.append(categoryLevel);
            output.append(")-"); // может стоит переделать формат вывода
            output.append(d.getName());
            output.append(". Цена: ");
            output.append(d.getPrice());
            
            view.show(output.toString());
            output.replace(0, output.length(), "");
        }
    }
    
}
