/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.IModelController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.command.CommandHandler;
import view.command.ModelManipulator;
import view.command.exception.CommandSyntaxException;

/**
 * реализация паттерна декоратор
 *
 * @author dodler
 */
public class CommandLineProcessor extends Thread{

    BufferedReader commandInput;
    
    /**
     * конструктор позволяющий задать поток ввода
     * например консоль или сетевой поток
     * или поток байт
     * @param commandInput - поток откуда будут приходить команды
     */
    public CommandLineProcessor(InputStream commandInput) {
        this.commandInput = new BufferedReader(new InputStreamReader(commandInput));
        processor = new StringProcessor();
    }
    
    public ArrayList<CommandHandler> getCommandList(){
        return processor.getCommandList();
    }
    
    public CommandLineProcessor(InputStream commandInput, IModelController controller, IConsoleView view){
        this(commandInput);
        processor = new StringProcessor(controller, view);
    }
    
    public void setController(IModelController controller, IConsoleView view){
        processor = new StringProcessor(controller,view);
    }
    
    private StringProcessor processor;
    
    /**
     * констуруктор задающий в качестве входного потока
     * системный входной поток - консоль то бишь
     */
    public CommandLineProcessor(){
        this.commandInput = new BufferedReader(new InputStreamReader(System.in));
    }
    
    @Override
    public void run(){
        String input;
        while(!isInterrupted()){
            try {
                input = commandInput.readLine();
                switch (input) {
                    case "exit":
                        return; // остановка метода
                    case "help":
                        for(CommandHandler ch:processor.getCommandList()){
                            ch.showCorrectCommandFormat();
                        }
                        continue;
                }
                processor.handle(input);
            } catch (IOException | CommandSyntaxException ex) {
                Logger.getLogger(CommandLineProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
