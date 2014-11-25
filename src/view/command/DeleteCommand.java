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
public class DeleteCommand extends CommandHandler {

    public DeleteCommand(String[] source) {
        super(source);
    }

    @Override
    public void handle() throws CommandSyntaxException {
        if (source.length == 3) {
            switch (source[1]) {
                case "all":
                    console.deleteDishCategory(source[2]);
                    break;
                case "-c":
                    console.deleteCategory(source[2]);
                    break;
                default:
                    throw new CommandSyntaxException("Ошибка ввода команды delete");
            }
        } else if (source.length == 2) {
            console.deleteDish(source[1]); // сразу удаление по имени
        } else {
            throw new CommandSyntaxException("Неверное число аргументов для команды delete");
        }

    }

}
