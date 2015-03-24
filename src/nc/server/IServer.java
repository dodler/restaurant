/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server;

import java.util.LinkedList;

/**
 *
 * @author lyan
 */
public interface IServer extends Runnable{

    /**
     * метод позволяет получить все текущие соединения
     * @return - набор соединений
     */
    LinkedList<ConnectionHandler> getConnectionSet();

    void reset();
    
}
