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
    private IConsoleView view;

    public StringProcessor() {
        this(null, null);
    }
    
    public ArrayList<CommandHandler> getCommandList(){
        return this.commandList;
    }
    
    public StringProcessor(IModelController controller, IConsoleView view){
        this.view = view;
        commandList = new ArrayList<>();
        commandList.add(new AddCommand(controller, view));
        commandList.add(new DeleteCommand(controller, view));
        commandList.add(new FindCommand(controller, view));
        commandList.add(new RenameCommand(controller, view));
        commandList.add(new ShowCommand(controller, view));
    }
    
    public void addCommand(CommandHandler handler){
        this.commandList.add(handler);
    }

    /**
     * обработка слова
     *
     * @param source - источник анализа
     * @throws view.command.exception.CommandSyntaxException - ошибка ввода команды. будет выброс эксепшена
     */
    public void handle(String source) throws CommandSyntaxException {
        //view.show("обработка " + source);
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
