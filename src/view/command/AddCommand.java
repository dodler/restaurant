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
        if (arg.length == 3){
            // добавлениею определенного блюда из категории
            console.addDish(arg[1], arg[2]);
        }else if (arg.length == 2){
            // удаление блюда по имени с поиском
            console.addDish(arg[1]);
        }else{
            throw new CommandSyntaxException("Неверное число аргументов для команды add");
        }
    }

    @Override
    public boolean isApplicable(String[] arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showCorrectCommandFormat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showShortName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
