/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server.handler;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Dish;
import model.ICategory;
import model.IModel;
import model.ModelImplXML;
import nc.AppController;
import nc.server.CmdHandler;

/**
 * команда позволяет вывести на консоль все доступные категории и блюда
 *
 * @author lyan
 */
public class GetDBCmd extends CmdHandler {

    @Override
    public boolean isApplicable(String cmd) {
        return cmd.equals("get");
    }

    @Override
    public void handle(String[] args) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Команда get");
        IModel model=AppController.getAppControllerInstance().getModel();
        CopyOnWriteArrayList<ICategory> cats = ((ModelImplXML)model).getCategoryLst();
        for(ICategory c:cats){
            System.out.print("Блюд в категории: " + c.getDishList().size() + ". Имя категории: ");
            System.out.println(c.getName());
            for(Dish d:c.getDishList()){
                System.out.println("Ид блюда: " + d.getId() + ". Имя блюда: " + d.getName());
            }
        }
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
