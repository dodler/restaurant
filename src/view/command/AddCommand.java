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
public class AddCommand extends CommandHandler{
    
    @Override
    public void handle(String[] arg) throws CommandSyntaxException{
        if (arg.length == 4){
            // добавлениею определенного блюда из категории
            controller.addDish(arg[1], arg[2], Double.parseDouble(arg[3]));
        }else if (arg.length == 3){
            controller.addDish(arg[1], Double.parseDouble(arg[2]));
        }else{
            throw new CommandSyntaxException("Неверное число аргументов для команды add");
        }
    }

    @Override
    public boolean isApplicable(String[] arg) {
        if (arg[0].equals("add")){
            return true;
        }
        return false;
    }

    @Override
    public void showCorrectCommandFormat() {
        System.out.println("Формат команды: add category name price или add name price, где category - категория, в которую нужно добавить блюдо, а name - имя блюда, price - цена блюда");
    }

    @Override
    public void showShortName() {
        System.out.println("Пример. add борщ 12 - добавляет блюдо борщ с ценой 12. ");
    }
}
