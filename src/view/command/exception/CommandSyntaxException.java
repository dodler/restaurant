
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command.exception;

/**
 *
 * @author dodler
 */
public class CommandSyntaxException extends Exception{

    public CommandSyntaxException(){
        super();
    }
    
    public CommandSyntaxException(String message) {
        super(message);
    }
    
}
