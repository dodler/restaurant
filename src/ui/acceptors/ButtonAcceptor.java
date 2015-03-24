/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import ui.MarkupLoader;

/**
 *
 * @author lyan
 */
public class ButtonAcceptor extends IUiAcceptor {

    @Override
    public boolean isApplicable(String type) {
        return type.equals("button");
    }

    @Override
    public Component acceptUI(MarkupLoaderPropertiesContainer mlpc, Component parent, HashMap<String, Component> components, MarkupLoader ml) throws Exception {
        if ((mlpc.className.equals("") || mlpc.className.equals("JButton")) && !ml.containsDialogClass(mlpc.className)) {
            t = new JButton(mlpc.label);
        } else {
            t = (JButton) ml.getDialogClass(mlpc.className).getConstructor(String.class).newInstance(mlpc.label);
        }

        if (mlpc.mouseListener != null && ml.containsMouseListener(mlpc.mouseListener)) { // ищем нужный обработчик
            t.addMouseListener(ml.getMouseListener(mlpc.mouseListener)); // если есть назначаем
        }
        t.setBounds(
                Integer.parseInt(mlpc.x),
                Integer.parseInt(mlpc.y),
                Integer.parseInt(mlpc.width),
                Integer.parseInt(mlpc.height));

        ((Container) parent).add(t);
        components.put(mlpc.name, t);

        if (mlpc.iconUrl != null) {
            ImageIcon ii; // иконка для кнопки
            if (!mlpc.iconUrl.equals("")) {
                ii = new ImageIcon(mlpc.iconUrl);
                ((JButton) t).setIcon(ii);
            }
        }
        if (mlpc.transparent.equals("true")) {
            ((JButton) t).setContentAreaFilled(false);
            ((JButton) t).setBorder(new EmptyBorder(0, 0, 0, 0));
        }
        if (!mlpc.tooltip.equals("")){
            ((JButton)t).setToolTipText(mlpc.tooltip);
        }
        ((JButton)t).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return t;
    }

}
