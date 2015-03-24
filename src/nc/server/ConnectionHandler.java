/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server;

import controller.IModelController;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.CategoryImpl;
import model.ICategory;
import model.IModel;
import model.ModelImplXML;
import model.NetModelImpl;
import model.net.Request;
import nc.AppController;
import org.w3c.dom.Document;

/**
 * класс, который отвечает за обработку единственного соединения
 *
 * @author lyan
 */
public class ConnectionHandler extends Thread {

    private final Socket s; // сокет через который сервер будет общаться с клиентом
    private final IModelController c; // контроллер, который пишет изменения
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois; // потоки для считывани данных
    private final StringBuffer sb; // для считывания строк
    private final IModel model; // модель для получения доступа напрямую к элементам
    private final Document doc;

    /**
     * метод освобождает ресурсы, задейсовтванные обрабочиком события
     * закрывается сокет закрываются каналы ввода/вывода
     *
     * @throws IOException ошибка ввода/вывода
     */
    public void reset() throws IOException {
        s.close();
        oos.close();
        ois.close();
        AppController.getAppControllerInstance().getServer().getConnectionSet().remove(this);
    }

    public Socket getSocket() {
        return s;
    }
    private Request req, ans;

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                //if (ois.available() > 0) {
                req = (Request) ois.readObject();
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, "Получен запрос");
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, String.valueOf(req.getType()));
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, String.valueOf(req.getModel()));

                if (req.getType() == Request.GET) {
                    ans = new Request(model, Request.POST);
                    oos.writeObject(ans);
                    oos.flush();
                    Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, "Отправлена модель");
                    Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, "Размер:" + String.valueOf(((ModelImplXML)model).getCategoryLst().size()));
                } else if (req.getType() == Request.POST) {
                    Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, "Получена модель");
                    ((ModelImplXML)model).updateModel(req.getModel().getCategoryList());
                    Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, "Размер:" + String.valueOf(((ModelImplXML)model).getCategoryLst().size()));
                    Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, "Модель обновлена");
                }
                //}
                oos.flush();

            } catch (IOException | ClassNotFoundException ex) {
                interrupt();
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            reset();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ConnectionHandler(Socket s, IModelController c, IModel model) throws IOException, ParserConfigurationException {
        Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, "Запуск нити");
        this.s = s;
        this.c = c;
        this.model = model;
        sb = new StringBuffer();
        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        oos = new ObjectOutputStream(s.getOutputStream());
        ois = new ObjectInputStream(s.getInputStream());
    }

}
