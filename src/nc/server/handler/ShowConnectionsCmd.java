/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server.handler;

import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import nc.AppController;
import nc.server.CmdHandler;
import nc.server.ConnectionHandler;
import nc.server.IServer;

/**
 *
 * @author lyan
 */
public class ShowConnectionsCmd extends CmdHandler {

    @Override
    public boolean isApplicable(String cmd) {
        return cmd.equals("show");
    }

    @Override
    public synchronized void handle(String[] args) {
        Logger.getLogger(KillConnCmd.class.getName()).log(Level.SEVERE, "Вывод всех соединений");
        IServer server = AppController.getAppControllerInstance().getServer();
        LinkedList<ConnectionHandler> conns = server.getConnectionSet();
        int i = 0;
        Logger.getLogger(KillConnCmd.class.getName()).log(Level.SEVERE, "Всего " + conns.size() + " соединений");
        for(ConnectionHandler c:conns){
            Logger.getLogger(KillConnCmd.class.getName()).log(Level.SEVERE, "{0}:{1}", new Object[]{i++, c.getSocket().getInetAddress().getHostAddress()});
        }
        
    }
    
    @Override
    public String getDescription(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
