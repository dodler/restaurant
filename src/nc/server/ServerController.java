/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import nc.server.handler.KillConnCmd;

/**
 *
 * @author lyan
 */
public class ServerController extends Thread {
    
    private final IServer server;

    private final ArrayList<CmdHandler> cmds;

    public ServerController(IServer server) {
        this.server = server;
        cmds = new ArrayList<>();
    }
    
    /**
     * метод добавляет новый обраотчик консольной команды
     * @param cmd команда для обработки
     */
    public void addCmdHandler(CmdHandler cmd) {
        cmds.add(cmd);
    }
    
    public IServer getServer(){
        return this.server;
    }

    @Override
    public void run() {
        String in;
        String[] args;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!isInterrupted()) {
            try {
                in = reader.readLine();
                args = in.split(" ");
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, "Команда: " +  args[0]);
                for(CmdHandler cmd:cmds){
                    if (cmd.isApplicable(args[0])){
                        cmd.handle(args);
                        break;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
