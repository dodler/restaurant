/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.IModel;
import nc.Server;

/**
 * реализация паттерна декоратор
 *
 * @author dodler
 */
public class CommandLineProcessor extends Thread{

    /**
     * канал для считывания команд пользователя
     */
    private BufferedReader commandInput;
    private IView view;
    private IModel model;
    private Server server;
    
    
    /**
     * конструктор позволяющий задать поток ввода
     * например консоль или сетевой поток
     * или поток байт
     * @param commandInput - поток откуда будут приходить команды
     */
    private CommandLineProcessor(InputStream commandInput) {
        this.commandInput = new BufferedReader(new InputStreamReader(commandInput));
    }
    
    public CommandLineProcessor(InputStream commandInput, IView view, IModel model, Server server){
        this(commandInput);
        this.view = view;
        this.model = model;
        this.server = server;
    }
  
    
    /**
     * констуруктор задающий в качестве входного потока
     * системный входной поток - консоль то бишь
     */
    public CommandLineProcessor(){
        this.commandInput = new BufferedReader(new InputStreamReader(System.in));
    }
    
    /**
     * метод вывода текущих тредов
     */
    private void showRunningThreads(){
        
    }
    
    @Override
    public void run(){
        String input;
        while(!isInterrupted()){
            try {
                input = commandInput.readLine();
                switch(input){
                    case "show":
                        showRunningThreads();
                        break;
                    case "stop":
                        return;
                }
            } catch (IOException ex) {
                Logger.getLogger(CommandLineProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
