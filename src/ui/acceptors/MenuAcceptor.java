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
import javax.swing.JMenuBar;
import static jdk.nashorn.internal.codegen.CompilerConstants.className;
import ui.MarkupLoader;

/**
 *
 * @author lyan
 */
public class MenuAcceptor extends IUiAcceptor {

    @Override
    public boolean isApplicable(String type) {
        return type.equals("menu");
    }

    @Override
    public Component acceptUI(MarkupLoaderPropertiesContainer mlpc, Component parent, HashMap<String, Component> components, MarkupLoader ml) throws Exception {

        if (!parent.getClass().equals(JMenuBar.class)) {
            throw new InvalidMarkupException(); // ошибка разметки
        }

        if ((mlpc.className.equals("JMenu") || mlpc.className.equals("")) && !ml.containsFrameClass(mlpc.className)) {
            t = new JMenu();
        } else {
            t = (JMenu) ml.getLabelClass(mlpc.className).getConstructor().newInstance();
        }

        if (!mlpc.label.equals("")) {
            ((JMenu) t).setText(mlpc.label);
        }
        ((JMenuBar) parent).add(t);
        return t;
    }

}
