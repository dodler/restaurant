/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

import haulmaunt.lyan.ui.markupexception.InvalidMarkupException;
import java.awt.Component;
import java.util.HashMap;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import static jdk.nashorn.internal.codegen.CompilerConstants.className;
import ui.MarkupLoader;

/**
 *
 * @author lyan
 */
public class MenuItemAcceptor extends IUiAcceptor {

    @Override
    public boolean isApplicable(String type) {
        return type.equals("menuItem");
    }

    @Override
    public Component acceptUI(MarkupLoaderPropertiesContainer mlpc, Component parent, HashMap<String, Component> components, MarkupLoader ml) throws Exception {
        if (!parent.getClass().equals(JMenu.class)) {
            return null;
            //throw new InvalidMarkupException(); // произошла ошибка разметки
            // имеется в виду что родительский элемент не jmenu
        }

        if ((mlpc.className.equals("JMenuItem") || mlpc.className.equals("")) && !ml.containsFrameClass(mlpc.className)) {
            t = new JMenuItem();
        } else {
            t = (JMenuItem) ml.getLabelClass(mlpc.className).getConstructor().newInstance();
        }

        String mouseListenerName = "";

        if (!mlpc.mouseListener.equals("") && ml.containsMouseListener(mlpc.mouseListener)) {
            ((JMenuItem) t).addMouseListener(ml.getMouseListener(mlpc.mouseListener));
        } else if (!mouseListenerName.equals("")) {
        }

        if (!mlpc.label.equals("")) {
            ((JMenuItem) t).setText(mlpc.label);
        } else {
            throw new InvalidMarkupException();
        }

        ((JMenu) parent).add((JMenuItem) t);
        components.put(mlpc.name, t);
        return t;
    }

}
