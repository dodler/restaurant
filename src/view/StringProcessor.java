/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import view.command.CommandHandler;
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

    public StringProcessor(String source) {
        this.source = source;
        commandList = new ArrayList<>();
    }

    public void setConsole(IConsoleView console) {
        this.console = console;
    }

    /**
     * обработка слова
     *
     * @throws view.command.exception.CommandSyntaxException
     */
    public void handle() throws CommandSyntaxException {
        words = source.split(" "); // получили команды и аргументы
        for (CommandHandler ch : commandList) {
            try {
                if (ch.isApplicable(words)) {
                    ch.handle(words);
                }
            } catch (CommandSyntaxException sce) {
                ch.showCorrectCommandFormat();
            }
        }

    }
}
