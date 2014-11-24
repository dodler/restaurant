/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import utils.CommandSyntaxException;

/**
 *
 * @author dodler
 */
public class AddCommand extends CommandHandler{

    public AddCommand(String[] source) {
        super(source);
    }
    
    @Override
    public void handle() throws CommandSyntaxException{
        if (source.length == 3){
            // добавлениею определенного блюда из категории
            console.addDish(source[1], source[2]);
        }else if (source.length == 2){
            // удаление блюда по имени с поиском
            console.addDish(source[1]);
        }else{
            throw new CommandSyntaxException("Неверное число аргументов для команды add");
        }
    }
}
