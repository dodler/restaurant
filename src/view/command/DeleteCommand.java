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
public class DeleteCommand extends CommandHandler {

    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        if (arg.length == 3) {
            switch (arg[1]) {
                case "all":
                    console.deleteDishCategory(arg[2]);
                    break;
                case "-c":
                    console.deleteCategory(arg[2]);
                    break;
                default:
                    throw new CommandSyntaxException("Ошибка ввода команды delete");
            }
        } else if (arg.length == 2) {
            console.deleteDish(arg[1]); // сразу удаление по имени
        } else {
            throw new CommandSyntaxException("Неверное число аргументов для команды delete");
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
