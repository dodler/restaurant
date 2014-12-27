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
 * по всей видимости это будет поискпо шаблону на выходе - полное имя
 *
 * @author dodler
 */
public class FindCommand extends CommandHandler {

    public FindCommand(IModelController controller, IConsoleView view) {
        super(controller, view);
    }

    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        double price = 0, left = 0, right = 0;
        if (arg.length < 2) {
            this.showCorrectCommandFormat();
            throw new CommandSyntaxException("Слишком мало аргументов для команды find.");
        }
        switch (arg[1]) {
            case "-p":
                try {
                    if (arg.length >= 4) {
                        for (char c : arg[3].toCharArray()) {
                            if (c > 57 || c < 48 && c != 46) {
                                view.show("Неправильно введено число");
                                return;
                            }
                        }
                        left = price = Double.parseDouble(arg[3]);
                    }
                    if (arg.length == 6) {
                        for (char c : arg[5].toCharArray()) {
                            if (c > 57 || c < 48 && c != 46) {
                                view.show("Неправильно введено число");
                                return;
                            }
                        }
                        right = Double.parseDouble(arg[5]);
                    } else {
                        throw new CommandSyntaxException("Слишком мало аргументов для команды find.");
                    }
                } catch (NumberFormatException nfe) {
                    view.show("Неправильно введено число");
                }
                switch (arg[2]) {
                    case ">":
                        switch (arg.length) {
                            case 4:
                                controller.findPriceMore(price);
                                break;
                            case 6:
                                if (left > right) {
                                    throw new CommandSyntaxException("Неверные данные для команды. Обратите внимание на цены");
                                }
                                controller.findPriceInterval(left, right);
                                break;
                        }
                        break;
                    case "<":
                        if (arg.length == 6) {
                            throw new CommandSyntaxException("Слишком много аргументов");
                        }
                        controller.findPriceLess(price);
                        break;
                    case "=":
                        controller.findPriceEqual(price);
                        break;
                    default:
                        break;
                }
                break;

            case "-n":
                if (arg.length == 3) {
                    controller.findPattern(arg[2]);
                } else {
                    throw new CommandSyntaxException("Слишком мало аргументов для команды find.");
                }
                break;
            default:
                throw new CommandSyntaxException();
        }
    }

    @Override
    public boolean isApplicable(String[] arg) {
        return arg[0].equals("find");
    }

    @Override
    public void showCorrectCommandFormat() {
        view.show("Формат команды: find [опции] [аргументы].\nОпции: -p - поиск в диапазоне цен. Диапазон "
                + "задается арифметическими знаками через пробел. > [цена], < [цена], = [цена], > [цена] < [цена]"
                + "\n-n - поиск по шаблону. Символ * заменяет любое количество символов. Можно ставить несколько звездочек. ");
    }

    @Override
    public void showShortName() {
        view.show("Пример: find -p > 50 - поиск блюд с ценой больше 50.");
    }

}
