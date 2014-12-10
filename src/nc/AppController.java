/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import controller.IModelController;
import controller.ModelControllerImpl;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import model.IModel;
import model.ModelImplXML;
import org.xml.sax.SAXException;
import view.CommandLineProcessor;
import view.ConsoleViewImpl;
import view.IConsoleView;
import view.command.CommandHandler;

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
        
        ArrayList<String> config = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("config.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (reader == null){
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, "не удалось считать конфиг");
            return;
        }
        String in;
        try {
            while((in = reader.readLine())!= null){
                config.add(in);
            }
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        IModel model = new ModelImplXML();
        try {
            model.loadFromFile(config.get(0));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        IConsoleView view = new ConsoleViewImpl();
        ModelControllerImpl mci = new ModelControllerImpl(model.getRootCategory(), view);
        CommandLineProcessor clp = new CommandLineProcessor(System.in, mci, view);
        clp.start();
        view.show("Господин приветствую тебя. Позволь показать тебе доступные команды.");
        for(CommandHandler ch:clp.getCommandList()){
            ch.showCorrectCommandFormat();
        }
        view.show("Введи help если захочешь снова лицезреть это");
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
