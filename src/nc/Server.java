/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import controller.IModelController;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
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
public class Server implements IServer {

    private HashSet<ConnectionHandler> connectionSet; // набор обработчиков подключившихся пользователей
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
    }

    /**
     * метод позволяет получить все текущие соединения
     *
     * @return - набор соединений
     */
    @Override
    public HashSet<ConnectionHandler> getConnectionSet() {
        return this.connectionSet;
    }

    /**
     * метод запускает бесконечный цикл обработки
     */
    @Override
    public void run() {
        working = true;
        synchronized (model) {
            ConnectionHandler ch;
            while (working) {
                try {
                    connectionSet.add(new ConnectionHandler(
                            socket.accept(),
                            controller,
                            model,
                            true
                    ));
                } catch (IOException | ParserConfigurationException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void stop() {
        working = false;
    }
}
