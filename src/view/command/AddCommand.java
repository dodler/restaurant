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
public class AddCommand extends CommandHandler {

    private String desc = "Формат команды: add [опции] [значения]\nОпции: -c - добавление категории, -d - блюда, [-n] - необязательная опция, родительская категория\nЗначения: *category* - необязательное значение, имя родительской категории. \n"
            + "По умолчанию блюдо будет добавлено в главную категорию\n"
            + "*name* - имя блюда, *price* - цена блюда.";

    //"Пример. add -d борщ 12 - добавляет блюдо борщ с ценой 12 в главную категорию"
    public AddCommand(IModelController controller, IConsoleView view) {
        super(controller, view);
    }

    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        if (arg.length < 2) {
            throw new CommandSyntaxException("Слишком мало аргументов для команды add");
        }
        switch (arg[1]) {
            case "-d":
                // добавлениею определенного блюда из категории
                switch (arg.length) {
                    case 6:
                        controller.addDish(arg[3], arg[4], Double.parseDouble(arg[5])); // добавление в указанную категорию
                        break;
                    case 5:
                        controller.addDish(arg[2], Double.parseDouble(arg[3])); // добавление в родительскую категоирю
                        break;
                    default:
                        throw new CommandSyntaxException("Неверное число аргументов для команды add");
                }
                break;
            case "-c":
                if (arg.length == 3) {
                    controller.addCategory(arg[2]);
                } else if (arg.length == 5 && arg[2].equals("-n")) {
                    controller.addCategory(arg[3], arg[4]);
                } else {
                    throw new CommandSyntaxException("Неверное число аргументов для команды add");
                }
                break;
            default:
                throw new CommandSyntaxException("Неверное число аргументов для команды add");
        }
    }

    @Override
    public boolean isApplicable(String[] arg) {
        return arg[0].equals("add");
    }

    @Override
    public void showCorrectCommandFormat() {
        view.show(desc);
    }

    @Override
    public void showShortName() {
        view.show(ex);
    }
}
