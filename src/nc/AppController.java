/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import controller.IModelController;
import controller.ModelControllerImpl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import model.IModel;
import model.ModelImpl;
import org.xml.sax.SAXException;
import view.CommandLineProcessor;
import view.ConsoleViewImpl;
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
        IModel model = new ModelImpl();
        try {
            model.loadFromFile("/home/dodler/NetBeansProjects/MyController/test.xml");
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        IConsoleView view = new ConsoleViewImpl();
        ModelControllerImpl mci = new ModelControllerImpl(model.getRootCategory(), view);
        CommandLineProcessor clp = new CommandLineProcessor(System.in, mci);
        clp.start();
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
