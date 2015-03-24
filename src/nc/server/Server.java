/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server;

import controller.IModelController;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import model.IModel;

/**
 * класс, который отвечает за обработку серверных соединений на нем лежит
 * обработка до 100 соединений отправка данных из модели и подобная мелочь
 *
 * @author lyan
 */
public class Server extends Thread implements IServer {

    private final LinkedList<ConnectionHandler> connList; // набор обработчиков подключившихся пользователей
    private final IModel model; // модель откуда будут браться данные
    private final ServerSocket socket; // сокет, к которому будут подключаться пользователи
    private boolean working;
    private final IModelController controller;

    /**
     * конструктор задает модель и создает сокет
     *
     * @param model - модель, откуда берутся все данные
     * @param port - порт, по кототорому будет создан сокет
     * @param controller - контроллер. отчающий за манипуляцию данными
     * @throws java.io.IOException - ошибка создания сокета
     */
    public Server(IModel model, IModelController controller, int port) throws IOException {
        this.model = model;
        this.controller = controller;
        socket = new ServerSocket(port);
        working = false;
        connList = new LinkedList<>();
    }

    /**
     * метод позволяет получить все текущие соединения
     *
     * @return - набор соединений
     */
    @Override
    public LinkedList<ConnectionHandler> getConnectionSet() {
        return this.connList;
    }

    /**
     * метод запускает бесконечный цикл обработки
     */
    @Override
    public void run() {
        Logger.getLogger(Server.class.getName()).log(Level.SEVERE, "Сервер запущен и ожидает соединений");
        working = true;
        synchronized (model) {
            ConnectionHandler ch;
            while (!isInterrupted()) {
                try {
                    connList.add(new ConnectionHandler(
                            socket.accept(),
                            controller,
                            model
                    ));
                    connList.get(connList.size() - 1).start();
                } catch (IOException | ParserConfigurationException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void reset() {
        working = false;
        for (ConnectionHandler c : connList) {
            try {
                c.interrupt();
                c.reset();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        interrupt();
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
