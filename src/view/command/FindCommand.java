/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command;

import view.command.CommandHandler;
import utils.CommandSyntaxException;

/**
 *
 * @author dodler
 */
public class FindCommand extends CommandHandler{

    public FindCommand(String[] source){
        super(source);
    }
    
    @Override
    public void handle() throws CommandSyntaxException {
        
    }
    
}
