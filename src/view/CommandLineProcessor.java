/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import view.command.exception.CommandSyntaxException;

/**
 * реализация паттерна декоратор
 *
 * @author dodler
 */
public class CommandLineProcessor {

    IConsoleView console;

    public CommandLineProcessor(IConsoleView console) {
        this.console = console;
    }

    public static void main(String[] args) {
        new CommandLineProcessor(null).analyze("delete all s");
    }

    /**
     * метод для анализа входной строки
     *
     * @param source
     */
    private void analyze(String source) {
        try {
            new StringProcessor(source).handle();
        } catch (CommandSyntaxException ex) {
            Logger.getLogger(CommandLineProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
