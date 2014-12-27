/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.command;

import controller.IModelController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.IModel;
import view.IConsoleView;
import view.command.exception.CommandSyntaxException;

/**
 * класс команды манипуляции моделью
 *
 * @author dodler
 */
public class ModelManipulator extends CommandHandler {

    private IModel model;

    /**
     *
     * @param controller - контроллер обработчик для модели
     * @param view - вывод на экран
     * @param model - модель данных
     */
    public ModelManipulator(IModelController controller, IConsoleView view, IModel model) {
        super(controller, view);
        this.model = model;
    }

    @Override
    public void handle(String[] arg) throws CommandSyntaxException {
        if (arg.length < 2) {
            this.showCorrectCommandFormat();
            throw new CommandSyntaxException("Недостаточно аргументов для команды model");
        }
        switch (arg[1]) {
            case "load":
                if (arg.length == 3) {
                    try {
                        model.loadFromFile(arg[2]);
                        // загрузка модели из файла
                    } catch (Exception ex) {
                        Logger.getLogger(ModelManipulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case "save":
                if (arg.length == 3) {
                    try {
                        model.saveToFile(arg[2]);
                    } catch (Exception ex) {
                        view.show("Произошла ошибка во время записи данных в файл. Проверьте лог.");
                        Logger.getLogger(ModelManipulator.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            default:
                this.showCorrectCommandFormat();
                throw new CommandSyntaxException();
        }
    }

    @Override
    public boolean isApplicable(String[] arg) {
        return arg[0].equals("model");
    }

    @Override
    public void showCorrectCommandFormat() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showShortName() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
