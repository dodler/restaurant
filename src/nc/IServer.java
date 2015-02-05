/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import java.util.HashSet;

/**
 *
 * @author lyan
 */
public interface IServer {

    /**
     * метод позволяет получить все текущие соединения
     * @return - набор соединений
     */
    HashSet<ConnectionHandler> getConnectionSet();

    void run();

    void stop();
    
}
