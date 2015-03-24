/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

import java.awt.Component;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JFrame;
import ui.MarkupLoader;

/**
 *
 * @author lyan
 */
public class DialogAcceptor extends IUiAcceptor {

    @Override
    public boolean isApplicable(String type) {
        return type.equals("dialog");
    }

    @Override
    public Component acceptUI(MarkupLoaderPropertiesContainer mlpc, Component parent, HashMap<String, Component> components, MarkupLoader ml) throws Exception {
        if (mlpc.className.equals("") || mlpc.className.equals("JDialog") ||  !ml.containsDialogClass(mlpc.className)) {
            t = new JDialog((JFrame) parent, mlpc.label);
        } else {
            t = (JDialog) ml.getDialogClass(mlpc.className).getConstructor().newInstance();
        }
        t.setBounds(
                Integer.parseInt(mlpc.x),
                Integer.parseInt(mlpc.y),
                Integer.parseInt(mlpc.width),
                Integer.parseInt(mlpc.height));
        boolean enable = Boolean.valueOf(mlpc.autoEnable);
        switch (mlpc.layout) { // TODO добавьт другие layout
            default:
                ((JDialog) t).setLayout(null); // выбираем тип разметки
        }
        ((JDialog) t).setVisible(enable);
        ((JDialog) t).setModal(Boolean.parseBoolean(mlpc.modal));

        components.put(mlpc.name, t);
        return t;
    }

}
