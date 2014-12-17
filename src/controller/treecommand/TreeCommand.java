/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.treecommand;

import model.ICategory;
import view.IConsoleView;
import model.ITreeCommand;

/**
 *
 * @author dodler
 */
public abstract class TreeCommand implements ITreeCommand {
    protected IConsoleView view;
    
    public TreeCommand(){
        
    }
    
    public TreeCommand(IConsoleView view){
        this.view = view;
    }
    
    public abstract void handle(ICategory category);
}