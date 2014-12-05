/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.IModelController;
import java.util.ArrayList;
import view.command.AddCommand;
import view.command.CommandHandler;
import view.command.DeleteCommand;
import view.command.FindCommand;
import view.command.RenameCommand;
import view.command.ShowCommand;
import view.command.exception.CommandSyntaxException;

/**
 * обработка строки
 *
 * @author dodler
 */
public class StringProcessor {

    private String source;
    private String[] words;
    private IConsoleView console;
    private ArrayList<CommandHandler> commandList;

    public StringProcessor() {
        this(null);
    }
    
    public StringProcessor(IModelController controller){
        commandList = new ArrayList<>();
        commandList.add(new AddCommand(controller));
        commandList.add(new DeleteCommand(controller));
        commandList.add(new FindCommand(controller));
        commandList.add(new RenameCommand(controller));
        commandList.add(new ShowCommand(controller));
    }

    /**
     * обработка слова
     *
     * @param source - источник анализа
     * @throws view.command.exception.CommandSyntaxException - ошибка ввода команды. будет выброс эксепшена
     */
    public void handle(String source) throws CommandSyntaxException {
        System.out.println("обработка " + source);
        words = source.split(" "); // получили команды и аргументы
        boolean commandApplicated = false;
        for (CommandHandler ch : commandList) {
            try {
                if (ch.isApplicable(words)) {
                    commandApplicated = true;
                    ch.handle(words);
                }
            } catch (CommandSyntaxException sce) {
                ch.showCorrectCommandFormat();
            }
        }
        if (!commandApplicated){
            throw new CommandSyntaxException("Команда не подошла по синтаксису");
        }

    }
}
