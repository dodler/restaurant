/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.treecommand;

import model.ICategory;
import view.IConsoleView;

/**
 *
 * @author Артем
 */
public class CategoryWriter extends TreeCommand{

    public CategoryWriter(IConsoleView view){
        super(view);
    }
    
    @Override
    public void handle(ICategory category) {
        view.show(category);
    }
    
}
