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
public class ShowCommand extends CommandHandler {

    public ShowCommand(IModelController controller, IConsoleView view) {
        super(controller, view);
    }

    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        switch (arg[1]) {
            case "-d":
                if (arg.length == 4 && arg[2].equals("-p")) {// c ценами
                    if (arg.length == 4) {
                        controller.showDishListPriced(arg[3]);
                    } else {
                        controller.showCategoryDishTreePriced(); // тоже но с ценами
                    }
                }else{
                    if (arg.length == 3){
                        controller.showDishList(arg[2]); // вывод из категории с с без цен
                    }else{
                        controller.showCategoryDishTree(); // вывод названий всех блюд без цен вместе с категориями
                    }
                }
                break;
            case "-c":
                if (arg.length == 2) {
                    view.show("доделать");
                    //controller.show
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
        System.out.println("Формат команды: show [опции][значения]\n"
                + "Опции: -d - вывод блюд\n-c - вывод категорий"
                + "\n -p - необязательная опция - вывод цен на блюда"
                + "Значения: (-a) *name* - необязательное значение, вывод блюд категории\n"
                + "(-c) *name* - необязательное значение, вывод подкатегорий категории");

    }

    @Override
    public void showShortName() {
        System.out.println("Пример. show -d -p - вывод всех блюд с ценами.");
    }
}
