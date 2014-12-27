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
public class RenameCommand extends CommandHandler {

    public RenameCommand(IModelController controller, IConsoleView view) {
        super(controller, view);
    }

    /**
     *
     * @param arg
     * @throws CommandSyntaxException
     */
    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        if (arg.length < 4){
            this.showCorrectCommandFormat();
            throw new CommandSyntaxException("Мало аргументов для команды rename");
        }
        switch (arg[1]) {
            case "-d": // переименование блюда
                controller.editDishName(arg[2], arg[3]);
                break;
            case "-c":
                controller.editCategoryName(arg[2], arg[3]);
                break;
            default:
                throw new CommandSyntaxException();
        }
    }

    @Override
    public boolean isApplicable(String[] arg) {
        return arg[0].equals("rename");
    }

    @Override
    public void showCorrectCommandFormat() {
        System.out.println("Формат команды: rename [опции] [значения]\n"
                + "Опции: -с - переименовать категорию"
                + "-d - переименовать блюдо"
                + "\nЗначения: *oldname* *newname* - имя блюда или категории, которое надо сменить, newname - новое имя");
    }

    @Override
    public void showShortName() {
        System.out.println("Пример. rename -d борщ солянка");
    }

}
