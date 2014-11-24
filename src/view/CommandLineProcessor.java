/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 * реализация паттерна декоратор
 * @author dodler
 */
public class CommandLineProcessor {
    
    private enum States{
        
    }
    
    IConsoleView console;
    
    public CommandLineProcessor(IConsoleView console){
        this.console = console;
    }
    
    /**
     * метод для анализа входной строки
     * @param source 
     */
    private void analyze(String source){
        for(int i = 0; i<source.length(); i++){
            
        }
    }
}
