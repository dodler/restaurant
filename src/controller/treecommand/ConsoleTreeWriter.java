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
public class ConsoleTreeWriter extends TreeCommand {

    private StringBuilder categoryLevel = new StringBuilder();
    private StringBuilder output = new StringBuilder();

    public ConsoleTreeWriter(IConsoleView view) {
        super(view);
    }

    @Override
    public void handle(ICategory category) {
        categoryLevel.append("_:");
        output.append(categoryLevel);
        output.append(category.getName()); // для вывода имени категории с учетом уровня вложенности
        view.show(output.toString());
        output.replace(0, output.length(), "");
        for (Dish d : category.getDishList()) {
            output.append(categoryLevel);
            output.append(")-"); // может стоит переделать формат вывода
            output.append(d.getName());

            view.show(output.toString());
            output.replace(0, output.length(), "");
        }
        if (category.getSubCategoryList().isEmpty()) {
            categoryLevel.delete(categoryLevel.lastIndexOf("_"), categoryLevel.length()); // для корректного отображения 
            // вложенных категорий
        }
    }

}
