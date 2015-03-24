/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import model.Dish;
import model.ICategory;
import model.IModel;
import view.SwingView;

/**
 *
 * @author lyan
 */
public class CustomTableModel extends DefaultTableModel {
    private final IModel model;

    public CustomTableModel(IModel model, String ...columns) {
        super();
        this.model = model;
        for(String s:columns){
            super.addColumn(s);
        }
    }

    @Override
    public boolean isCellEditable(int i, int i1) {
        return !(i1 == 0);
    }

    @Override
    public void setValueAt(Object o, int i, int i1) {
        super.setValueAt(o, i, i1);
        int id = Integer.parseInt((String) super.getValueAt(i, 0));

        for (ICategory c : model.getCategoryList()) {
            if (c.getId() == id) {
                c.setName((String) super.getValueAt(i, 1));
                break;
            }
            for (Dish d : c.getDishList()) {
                if (d.getId() == id) {
                    if (i1 == 1) {
                        d.setName((String) super.getValueAt(i, 1));
                    } else {
                        d.setPrice(Double.parseDouble((String) super.getValueAt(i, 2)));
                    }
                    break;
                }
            }
        }
        ((SwingView)AppController.instance.getView()).setModel(model);
    }
}
