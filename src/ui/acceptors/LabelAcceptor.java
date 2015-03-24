/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import javax.swing.JLabel;
import ui.MarkupLoader;

/**
 *поддреживает свойства
 * x
 * y
 * width
 * height
 * label
 * @author lyan
 */
public class LabelAcceptor extends IUiAcceptor {

    @Override
    public boolean isApplicable(String type) {
        return type.equals(type);
    }

    @Override
    public Component acceptUI(MarkupLoaderPropertiesContainer mlpc, Component parent, HashMap<String, Component> components, MarkupLoader ml) throws Exception {
        if ((mlpc.className.equals("JLabel") || mlpc.className.equals("")) && !ml.containsFrameClass(mlpc.className)) {
            t = new JLabel(mlpc.label);
        } else {
            t = (JLabel) ml.getLabelClass(mlpc.className).getConstructor(String.class).newInstance(mlpc.label);
        }
        t.setBounds(
                Integer.parseInt(mlpc.x),
                Integer.parseInt(mlpc.y),
                Integer.parseInt(mlpc.width),
                Integer.parseInt(mlpc.height));
        t.setVisible(true);
        ((Container)parent).add(t);
        components.put(mlpc.name, t);
        return t;
    }

}
