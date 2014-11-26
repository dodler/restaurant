/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import controller.IModelController;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.IModel;
import model.ModelImpl;
import view.IConsoleView;

/**
 * AppController - синглтон.
 * @author Артем
 */
public class AppController{

    private IConsoleView view;
    private IModel model;
    private IModelController controller;
    
    static AppController instance;
    
    /**
     * в конструкторе будет 
     * загрузка кофига из файла
     * загрузка базы данных
     * инициализация контроллера и вьюхи
     */
    private AppController(){
        
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("config.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ArrayList<String> config = new ArrayList<>();
        String line = "";
        try {
            while((line = br.readLine()) != null){
            config.add(line); // считали конфиг
            }
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        model = new ModelImpl();
        model.loadFromFile(config.get(0)); // загружаем базу из файла
        
    }
    
    static{
        instance = new AppController();
    }
    /**
     * метод предоставляет доступ к единственному экземпляру AppController
     * @return - экземпляр AppController
     */
    public static AppController getAppControllerInstance(){
        return instance;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AppController.getAppControllerInstance().run();
    }
    
    /**
     * метод запускает справочную систему
     */
    public void run() {
        
    }
}
