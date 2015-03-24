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
 * комада сохранения текущих изменений
 * @author lyan
 */
public class SaveCmd extends CmdHandler{

    @Override
    public boolean isApplicable(String cmd) {
        return cmd.equals("save");
    }

    @Override
    public void handle(String[] args) {
        Logger.getLogger(SaveCmd.class.getName()).log(Level.SEVERE, String.valueOf(args.length));
        if (args.length == 1){
            try {
                // зписывается в стандартный файл
                AppController.getAppControllerInstance().getModel().save(AppController.getAppControllerInstance().getConfig().get("data-source"));
            } catch (IOException ex) {
                Logger.getLogger(SaveCmd.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(SaveCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (args.length == 2){
            try {
                AppController.getAppControllerInstance().getModel().save(args[1]);
            } catch (IOException ex) {
                Logger.getLogger(SaveCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getDescription() {
        return "Команда save file позволит сохранить незафиксированные изменения в файле. Текушие данные пишутся в формате xml. \nЕсли параметр file не указан, то данные записываются в исходный файл";
    }
    
}
