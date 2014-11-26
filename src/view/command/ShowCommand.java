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
public class ShowCommand extends CommandHandler {

    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        switch (arg.length) {
            case 2:
                if (arg[1].equals("all")) {
                    controller.showCategoryDishTree(); // вывод названий всех блюд без цен вместе с категориями
                } else {
                    controller.showDishList(arg[1]); // вывод определенной категории без цен
                }
                break;
            case 3:
                if (!arg[2].equals("-p")) {
                    throw new CommandSyntaxException("Неверный ключ для команды show.");
                }
                if (arg[1].equals("all")) {
                    controller.showCategoryDishTreePriced(); // вывод всех блюд с категориями с ценами
                }else{
                    controller.showDishListPriced(arg[2]); // вывод блюд категории с ценами
                }
            default:
                throw new CommandSyntaxException("Неверный формат команды show.");
        }
    }

    @Override
    public boolean isApplicable(String[] arg) {
        if (arg[0].equals("show")) {
            return true;
        }
        return false;
    }

    @Override
    public void showCorrectCommandFormat() {
        System.out.println("show [all|category] [-p]. all - вывод всех категорий вместе с блюдами. category - вывод блюд категории. -p - ключ для вывода цен. ");

    }

    @Override
    public void showShortName() {
        System.out.println("Пример. show all -p - вывод всех блюд с ценами.");
    }
}
