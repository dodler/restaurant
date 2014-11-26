/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import controller.IModelController;
import model.IModel;
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
