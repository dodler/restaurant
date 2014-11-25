/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command;

import view.command.exception.CommandSyntaxException;

/**
 *
 * @author dodler
 */
public class RenameCommand extends CommandHandler {

    /**
     *
     * @param arg
     * @throws CommandSyntaxException
     */
    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        switch (arg.length) {
            case 3: // переименование блюда
                console.editDishName(arg[1], arg[2]);
                break;
            case 4:
                if (!arg[1].equals("-c")) throw new CommandSyntaxException();
                
                console.editCategoryName(arg[2], arg[3]);
                
                break;
        }
    }

    @Override
    public boolean isApplicable(String[] arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
