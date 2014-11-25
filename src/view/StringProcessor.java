/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.command.FindCommand;
import view.command.RenameCommand;
import view.command.ShowCommand;
import view.command.DeleteCommand;
import view.command.CommandHandler;
import view.command.AddCommand;
import utils.CommandSyntaxException;

/**
 * обработка строки
 * @author dodler
 */
public class StringProcessor {
    private String source;
    private String[] words;
    private IConsoleView console;
    
    public StringProcessor(String source){
        this.source = source;
    }
    
    public void setConsole(IConsoleView console){
        this.console = console;
    }
    
    /**
     * обработка слова
     * @throws utils.CommandSyntaxException
     */
    public void handle() throws CommandSyntaxException{
        CommandHandler ch = null;
        words = source.split(" "); // получили команды и аргументы
        switch (words[0]) { 
            case "add": ch = new AddCommand(words); break;
            case "delete": ch = new DeleteCommand(words); break;
            case "rename": ch = new RenameCommand(words);break;
            case "show": ch = new ShowCommand(words);break;
            case "find":ch = new FindCommand(words);break;
            //default: throw new CommandSyntaxException(); break;
        }
        ch.setConsole(console);
        ch.handle();
        
    }
}
