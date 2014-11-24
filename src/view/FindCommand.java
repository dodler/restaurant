/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
    void handle() throws CommandSyntaxException {
        
    }
    
}
