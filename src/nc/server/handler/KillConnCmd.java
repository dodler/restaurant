/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server.handler;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nc.AppController;
import nc.server.CmdHandler;

/**
 *
 * @author lyan
 */
public class KillConnCmd extends CmdHandler {

    public int num = -1;

    @Override
    public boolean isApplicable(String cmd) {
        return cmd.equals("kill");
    }

    @Override
    public void handle(String args[]) {
        Logger.getLogger(KillConnCmd.class.getName()).log(Level.SEVERE, "Остановка процесса " + num);
        if (num == -1) return;
        try {
            AppController.getAppControllerInstance().getServer().getConnectionSet().get(num).reset();
            AppController.getAppControllerInstance().getServer().getConnectionSet().get(num).interrupt();
            Logger.getLogger(KillConnCmd.class.getName()).log(Level.SEVERE, "Процесс остановлен");
        } catch (IOException ex) {
            Logger.getLogger(KillConnCmd.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(KillConnCmd.class.getName());
        }
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
