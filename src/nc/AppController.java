/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import controller.IModelController;
import haulmaunt.lyan.ui.markupexception.MissingMouseListenerException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import model.IModel;
import model.NetModelImpl;
import view.IView;
import view.SwingView;

/**
 * AppController - синглтон.
 *
 * @author Артем
 */
public class AppController {

    private IView view;
    private IModel model;
    private IModelController controller;
    static AppController instance;
    private Logger logger; // объект для логов

    {
        logger = Logger.getLogger(AppController.class.getName()); // инициализация логера
    }

    /**
     * в конструкторе будет загрузка конфига из файла загрузка базы данных
     * инициализация контроллера и вьюхи
     */
    private AppController() {
        Config config;
        try {
            config = new Config("config.txt");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return;
        }
        try {
            logger.addHandler(new FileHandler(config.get("logger"))); // настраиваем запись лога в файл
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        IModel model = null;

        try {
            model = new NetModelImpl(config.get("address"), Integer.parseInt(config.get("port")), Boolean.getBoolean(config.get("enable-zip")));
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        IView view = null;
        try {
            view = new SwingView(model);
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, null, ioe);
        } catch (ParserConfigurationException pce) {
            logger.log(Level.SEVERE, null, pce);
        } catch (MissingMouseListenerException mmle) {
            logger.log(Level.SEVERE, null, mmle);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        
        try {
            ((SwingView)view).init(config.get("xml-markup"));
        } catch (Exception ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static {
        instance = new AppController();
    }

    /**
     * метод предоставляет доступ к единственному экземпляру AppController
     *
     * @return - экземпляр AppController
     */
    public static AppController getAppControllerInstance() {
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
