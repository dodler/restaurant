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
public class RenameCommand extends CommandHandler {

    public RenameCommand(String[] source) {
        super(source);
    }

    @Override
    public void handle() throws CommandSyntaxException {
        switch (source.length) {
            case 3: // переименование блюда
                console.editDishName(source[1], source[2]);
                break;
            case 4:
                if (!source[1].equals("-c")) throw new CommandSyntaxException("Неверный аргумент для команды rename");
                
                console.editCategoryName(source[2], source[3]);
                
                break;
        }
    }

}
