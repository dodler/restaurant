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
                case "-a":
                    controller.deleteDishCategory(arg[2]);
                    break;
                case "-c":
                    controller.deleteCategory(arg[2]);
                    break;
                default:
                    throw new CommandSyntaxException("Ошибка ввода команды delete");
            }
        } else if (arg.length == 2) {
            controller.deleteDish(arg[1]); // сразу удаление по имени
        } else {
            throw new CommandSyntaxException("Неверное число аргументов для команды delete");
        }

    }

    @Override
    public boolean isApplicable(String[] arg) {
        if (arg[0].equals("delete")){
            return true;
        }
        return false;
    }

    @Override
    public void showCorrectCommandFormat() {
        System.out.println("Формат команды: delete [-a|-c] name. -a - удаление всех блюд в категории, -с - удаление категории с именем name со всеми блюдами. Запуск команды без ключей приведет к удалению блюда с именем name. ");
    }

    @Override
    public void showShortName() {
        System.out.println("Пример. delete -a горячее - удалить все блюда из категории горячее.");
    }

}
