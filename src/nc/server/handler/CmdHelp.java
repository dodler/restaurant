/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import nc.server.CmdHandler;

/**
 *
 * @author lyan
 */
public class CmdHelp extends CmdHandler {
    
    String help;
    
    {
        StringBuilder sb = new StringBuilder();
        sb.append("----------------------------------------------------------\n");
        sb.append("Справка по консольным командам сервера \n");
        sb.append("Для вывода списка соединений наберите команду show \n");
        sb.append("Для вывода текущей базы данных наберите команду get \n");
        sb.append("Чтобы отключить определенного клиента от сервера наберите команду kill num, num - номер соединения, который можно получить набрав команду show \n");
        sb.append("Чтобы сохранить данные используется команду save file, которая позволит сохранить незафиксированные изменения в файле. \nТекушие данные пишутся в формате xml. Если параметр file не указан, то данные записываются в исходный файл");
        sb.append("Чтобы загрузить данные, используйте команду load file, которая позволяет загрузить новые данные из файла. Файл должен иметь xml формат, иначе будет исключение. \nПараметр file необязательный. Если его не указать, то будет загружен файл из директории по умолчанию, которая указана в конфиге. ");
        sb.append("Для завершения работы сервера наберите команду exit\n");
        sb.append("----------------------------------------------------------");
        help = sb.toString();
    }
    
    @Override
    public boolean isApplicable(String cmd) {
        return cmd.equals("help");
    }
    
    @Override
    public void handle(String[] args) {
        Logger.getLogger(CmdHelp.class.getName()).log(Level.SEVERE, help);
    }
    
    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
