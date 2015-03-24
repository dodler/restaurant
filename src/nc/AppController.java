/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import nc.server.IServer;
import nc.server.Server;
import controller.IModelController;
import controller.ModelControllerImpl;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.IModel;
import model.ModelImplXML;
import nc.server.ServerController;
import nc.server.handler.CmdExit;
import nc.server.handler.CmdHelp;
import nc.server.handler.GetDBCmd;
import nc.server.handler.KillConnCmd;
import nc.server.handler.LoadCmd;
import nc.server.handler.SaveCmd;
import nc.server.handler.ShowConnectionsCmd;

/**
 * AppController - синглтон.
 *
 * @author Артем
 */
public class AppController extends Thread{

    static AppController instance;
    private Logger logger; // объект для логов

    private IServer server;
    private ServerController sc;
    private Config config;
    private IModel model;
    private IModelController controller;

    public Config getConfig(){
        return this.config;
    }
    
    public IServer getServer() {
        return this.server;
    }
    
    public IModel getModel(){
        return this.model;
    }

    /**
     * в конструкторе будет загрузка конфига из файла загрузка базы данных
     * инициализация контроллера и вьюхи
     */
    private AppController() {
        logger = Logger.getLogger(AppController.class.getName()); // инициализация логера
        try {
            config = new Config("/home/lyan/NetBeansProjects/nc_server_2/config.txt");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return;
        }
        try {
            logger.addHandler(new FileHandler(config.get("logger"))); // настраиваем запись лога в файл
            logger.addHandler(new ConsoleHandler());
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        model = new ModelImplXML();
        try {
            model.load(config.get("data-source"));
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, "Запущена модель");
        } catch (Exception ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }

        controller = new ModelControllerImpl(model.getRootCategory());

        try {
            server = new Server(model, controller, 10000);
            ((Server)server).start();
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, "Запущен сервер");
            sc = new ServerController(server);
            sc.addCmdHandler(new CmdHelp());
            sc.addCmdHandler(new CmdExit());
            sc.addCmdHandler(new GetDBCmd());
            sc.addCmdHandler(new KillConnCmd());
            sc.addCmdHandler(new ShowConnectionsCmd());
            sc.addCmdHandler(new SaveCmd());
            sc.addCmdHandler(new LoadCmd());
            sc.start();
        } catch (IOException ex) {
            Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, "Успешный запуск приложения");
    }

    public void reset() {
        server.reset();
        sc.interrupt();
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
