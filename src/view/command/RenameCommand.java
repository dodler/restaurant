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
public class RenameCommand extends CommandHandler {

    /**
     *
     * @param arg
     * @throws CommandSyntaxException
     */
    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        switch (arg.length) {
            case 3: // переименование блюда
                controller.editDishName(arg[1], arg[2]);
                break;
            case 4:
                if (!arg[1].equals("-c")) throw new CommandSyntaxException();
                
                controller.editCategoryName(arg[2], arg[3]);
                
                break;
        }
    }

    @Override
    public boolean isApplicable(String[] arg) {
        if (arg[0].equals("rename")){
            return true;
        }
        return false;
    }

    @Override
    public void showCorrectCommandFormat() {
        System.out.println("Формат команды: rename [-c] oldname newname - переименовать заменить имя блюда oldname на newname. \n -c - переименовать категорию");
    }

    @Override
    public void showShortName() {
        System.out.println("Пример. rename борщ солянка");
    }

}
