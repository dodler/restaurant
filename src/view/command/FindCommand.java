/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command;

import view.command.exception.CommandSyntaxException;

/**
 * по всей видимости это будет поискпо шаблону 
 * на выходе - полное имя
 * @author dodler
 */
public class FindCommand extends CommandHandler{
    
    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        
    }

    @Override
    public boolean isApplicable(String[] arg) {
        if (arg[0].equals("find")){
            return true;
        }
        return false;
    }

    @Override
    public void showCorrectCommandFormat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showShortName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
