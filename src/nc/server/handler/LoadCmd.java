/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import nc.AppController;
import nc.server.CmdHandler;

/**
 *
 * @author lyan
 */
public class LoadCmd extends CmdHandler{

    @Override
    public boolean isApplicable(String cmd) {
        return cmd.equals("load");
    }

    @Override
    public void handle(String[] args) {
        if (args.length == 1){
            try {
                AppController.getAppControllerInstance().getModel().load(AppController.getAppControllerInstance().getConfig().get("data-source"));
            } catch (Exception ex) {
                Logger.getLogger(LoadCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (args.length == 2){
            try {
                AppController.getAppControllerInstance().getModel().load(args[1]);
            } catch (Exception ex) {
                Logger.getLogger(LoadCmd.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getDescription() {
        return "Команда load file позволяет загрузить новые данные из файла. Файл должен иметь xml формат, иначе будет исключение. \nПараметр file необязательный. Если его не указать, то будет загружен файл из директории по умолчанию, которая указана в конфиге. ";
    }
    
}
