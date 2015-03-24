/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server.handler;

import java.util.logging.Logger;
import nc.AppController;
import nc.server.CmdHandler;
import nc.server.IServer;
import nc.server.Server;

/**
 * команда выхода и освобождения ресурсов
 *
 * @author lyan
 */
public class CmdExit extends CmdHandler {

    @Override
    public boolean isApplicable(String cmd) {
        return cmd.equals("exit");
    }

    @Override
    public void handle(String[] args) {
        //Logger.getLogger(CmdExit.class.getName(), "Остановка сервера");
        AppController.getAppControllerInstance().getServer().reset();
        ((Server)AppController.getAppControllerInstance().getServer()).interrupt();
        AppController.getAppControllerInstance().reset();
        AppController.getAppControllerInstance().interrupt();
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
