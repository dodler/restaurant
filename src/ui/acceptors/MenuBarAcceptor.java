/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import ui.MarkupLoader;

/**
 *
 * @author lyan
 */
public class MenuBarAcceptor extends IUiAcceptor {

    @Override
    public boolean isApplicable(String type) {
        return type.equals("menuBar");
    }

    @Override
    public Component acceptUI(MarkupLoaderPropertiesContainer mlpc, Component parent, HashMap<String, Component> components, MarkupLoader ml) throws Exception{
        if ((mlpc.className.equals("JMenuBar") || mlpc.className.equals("")) && !ml.containsFrameClass(mlpc.className)) {
            t = new JMenuBar();
        } else {
            t = (JMenuBar) ml.getLabelClass(mlpc.className).getConstructor().newInstance();
        }

        ((Container) parent).add(t);
        ((JFrame) parent).setJMenuBar((JMenuBar) t);

        components.put(mlpc.name, t);
        return t;
    }

}
