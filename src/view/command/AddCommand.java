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

    public AddCommand(IModelController controller, IConsoleView view) {
        super(controller, view);
    }

    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        switch (arg[1]) {
            case "-d":
                // добавлениею определенного блюда из категории
                if (arg.length == 5) {
                    controller.addDish(arg[2], arg[3], Double.parseDouble(arg[4])); // добавление в указанную категорию
                } else {
                    controller.addDish(arg[2], Double.parseDouble(arg[3])); // добавление в родительскую категоирю
                }
                break;
            case "-c":
                controller.addDish(arg[2], Double.parseDouble(arg[3]));
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
        view.show("Формат команды: add [опции] [значения]\nОпции: -c - добавление категории, -d - блюда\n"
                + "Значения: (-n) *category* - имя родительской категории. \n"
                + "(-c) *category* - не обязательное значение, категория, куда будет добавлено блюдо. \n"
                + "По умолчанию блюдо будет добавлено в главную категорию\n"
                + "*name* - имя блюда, *price* - цена блюда.");
    }

    @Override
    public void showShortName() {
        view.show("Пример. add -d борщ 12 - добавляет блюдо борщ с ценой 12 в главную категорию");
    }
}
