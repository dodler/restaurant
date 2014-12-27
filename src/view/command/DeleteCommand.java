/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command;

import controller.IModelController;
import view.IConsoleView;
import view.command.exception.CommandSyntaxException;

/**
 *
 * @author dodler
 */
public class DeleteCommand extends CommandHandler {

    public DeleteCommand(IModelController controller, IConsoleView view) {
        super(controller, view);
    }

    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        
        if (arg.length < 2){
            this.showCorrectCommandFormat();
            throw new CommandSyntaxException("Недостаточно аргументов для команды delete");
        }
        
        if (arg.length == 3) {
            switch (arg[1]) {
                case "-a":
                    controller.deleteDishCategory(arg[2]);
                    break;
                case "-c":
                    controller.deleteCategory(arg[2]);
                    break;
                case "-n":
                    controller.deleteDish(arg[2]);
                    break;
                default:
                    throw new CommandSyntaxException("Ошибка ввода команды delete");
            }
        } else {
            throw new CommandSyntaxException("Неверное число аргументов для команды delete");
        }

    }

    @Override
    public boolean isApplicable(String[] arg) {
        return arg[0].equals("delete");
    }

    @Override
    public void showCorrectCommandFormat() {
        view.show("Формат команды: delete [опции] [значения]. \n"
                + "Опции: -a - удаление блюд категории, \n-с - удаление категории с блюдами\n-n - удаление блюда по имени"
                + "\nЗначения: (-a) *name* - имя категории, в которой нужно удалять"
                + "\n(-c) *name* - имя категории, которую нужно удалить"
                + "\n(-n) *name* - имя блюда, которое нужно удалить");
    }

    @Override
    public void showShortName() {
        view.show("Пример. delete -a горячее - удалить все блюда из категории горячее.");
    }

}
