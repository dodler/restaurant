/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command;

import controller.IModelController;
import view.IConsoleView;
import view.command.exception.CommandSyntaxException;

/**
 * класс обработки команд
 *
 * @author dodler
 */
public abstract class CommandHandler {

    protected IModelController controller;
    protected IConsoleView view;
    protected String desc;
    protected String ex;

    public CommandHandler(IModelController controller, IConsoleView view) {
        this.controller = controller;
        this.view = view;
        desc = "";
        ex = "";
    }

    /**
     * метод для задания описания команды
     * @param desc 
     */
    public void setDescription(String desc) {
        if (!desc.equals("")) {
            this.desc = desc;
        }
    }
    
    public void setExample(String ex){
        if (!ex.equals("")){
            this.ex = ex;
        }
    }

    public abstract void handle(String[] arg) throws CommandSyntaxException;

    public abstract boolean isApplicable(String[] arg);

    public abstract void showCorrectCommandFormat();

    public abstract void showShortName();
}
