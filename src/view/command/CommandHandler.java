/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command;

import view.IConsoleView;
import view.command.exception.CommandSyntaxException;

/**
 * класс обработки команд
 * @author dodler
 */
public abstract class CommandHandler {
    IConsoleView console;
    
    public CommandHandler(){
    }
    
    public abstract void handle(String[] arg) throws CommandSyntaxException;
    
    public abstract boolean isApplicable(String[] arg);
    
    public abstract void showCorrectCommandFormat();
    
    public abstract void showShortName();
}
