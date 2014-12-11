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
        if (arg.length <2){
            throw new CommandSyntaxException("Слишком мало аргументов для команды find.");
        }
        switch (arg[1]) {
            case "-p":
                try {
                    price = Double.parseDouble(arg[3]);
                    if (arg.length == 6) {
                        left = price;
                        right = Double.parseDouble(arg[5]);
                    }
                } catch (NumberFormatException nfe) {
                    view.show("Неправильно введено число. Сообщение об ошибке: ");
                    view.show(nfe.getMessage());
                    showCorrectCommandFormat();
                }
                switch (arg[2]) {

                    case ">":
                        if (arg.length == 3) {
                            controller.findPriceMore(price);
                        } else if (arg.length == 6) {
                            controller.findPriceInterval(left, right);
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
                controller.findPattern(arg[2]);
                ;
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
