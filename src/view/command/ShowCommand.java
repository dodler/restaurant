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
 * класс обработки команды вывода выводит на реализацию IConsoleView, которую
 * надо задавать в конструкторе если введете просто show выдаст справку
 *
 * @author dodler
 */
public class ShowCommand extends CommandHandler {

    public ShowCommand(IModelController controller, IConsoleView view) {
        super(controller, view);
        this.desc = "Формат команды: show [опции][значения]\n"
                + "Опции: -d - вывод блюд\n-c - вывод категорий"
                + "\n -p - необязательная опция - вывод цен на блюда\n"
                + "Значения: (-a) *name* - необязательное значение, вывод блюд категории\n"
                + "(-c) *name* - необязательное значение, вывод подкатегорий категории";
    }

    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        if (arg.length < 2) {
            this.showCorrectCommandFormat();
            throw new CommandSyntaxException("Недостаточно аргументов для команды show");
        }
        switch (arg[1]) {
            case "-d":
                if (arg.length == 2) {
                    view.showDishTree(); // вывод названий всех блюд без цен вместе с категориями
                    return;
                }
                if (arg[2].equals("-p")) {
                    if (arg.length == 4) {
                        view.showDishTreePriced(arg[3]);
                    } else if (arg.length == 3) {
                        view.showDishTreePriced(); // тоже но с ценами
                    }
                } else {
                    if (arg.length == 3) {
                        view.showDishTree(arg[2]); // вывод из категории с с без цен
                    }
                }
                break;
            case "-c":
                if (arg.length == 2) {
                    view.showCategoryList(); // вывод с рутовой категории
                    //controller.show
                } else if (arg.length == 3) {
                    view.showCategoryList(arg[2]);
                } else {
                    throw new CommandSyntaxException(); // не подходит по числу аргументов
                }
                break;
            default:
                throw new CommandSyntaxException("Неверный формат команды show.");
        }
    }

    @Override
    public boolean isApplicable(String[] arg) {
        return arg[0].equals("show");
    }

    @Override
    public void showCorrectCommandFormat() {
        view.show(desc);

    }

    @Override
    public void showShortName() {
        view.show("Пример. show -d -p - вывод всех блюд с ценами.");
    }
}
