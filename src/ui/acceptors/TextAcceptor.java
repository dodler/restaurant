/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import javax.swing.JTextField;
import static jdk.nashorn.internal.codegen.CompilerConstants.className;
import ui.MarkupLoader;

/**
 *
 * @author lyan
 */
public class TextAcceptor extends IUiAcceptor {

    @Override
    public boolean isApplicable(String type) {
        return type.equals("text");
    }

    @Override
    public Component acceptUI(MarkupLoaderPropertiesContainer mlpc, Component parent, HashMap<String, Component> components, MarkupLoader ml) throws Exception {

        if ((mlpc.className.equals("JTextField") || mlpc.className.equals("")) && !ml.containsFrameClass(mlpc.className)) {
            t = new JTextField(mlpc.label);
        } else {
            t = (JTextField) ml.getLabelClass(mlpc.className).getConstructor().newInstance();
        }
        t.setBounds(
                Integer.parseInt(mlpc.x),
                Integer.parseInt(mlpc.y),
                Integer.parseInt(mlpc.width),
                Integer.parseInt(mlpc.height));
        ((Container) parent).add(t);
        components.put(mlpc.name, t);
        return t;
    }

}
