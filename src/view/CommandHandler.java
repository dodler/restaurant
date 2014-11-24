/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import utils.CommandSyntaxException;

/**
 * класс обработки команд
 * @author dodler
 */
public abstract class CommandHandler {
    String[] source;
    IConsoleView console;
    
    public CommandHandler(String[] source){
        this.source = source;
    }
    
    public void setConsole(IConsoleView console){
        this.console = console;
    }
    
    abstract void handle() throws CommandSyntaxException;
}
