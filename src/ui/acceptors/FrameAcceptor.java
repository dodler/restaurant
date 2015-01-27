/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import javax.swing.JFrame;
import ui.MarkupLoader;

/**
 *
 * @author lyan
 */
public class FrameAcceptor extends IUiAcceptor {

    @Override
    public boolean isApplicable(String type) {
        return type.equals("frame");
    }

    @Override
    public Component acceptUI(MarkupLoaderPropertiesContainer mlpc, Component parent, HashMap<String, Component> components, MarkupLoader ml)  throws Exception{
        // тут можно создать родительское окно
        // не до конца еще знаю gui api java поэтому считаю что можно создать несколько фреймов
        if ((mlpc.className.equals("JFrame") || mlpc.className.equals("")) && !ml.containsFrameClass(mlpc.className)) {
            t = new JFrame(mlpc.label); // выбирваем класс для создания фрейма
        } else { // либо стандартный
            t = (JFrame) ml.getFrameClass(mlpc.className).getConstructor().newInstance(); // либо заданный
        }
        int x_, y_, width_, height_; // числоые значения для значений указанных в разметкеы

        if (!mlpc.x.equals("")) {
            x_ = Integer.parseInt(mlpc.x);
        } else {
            x_ = 0;
        }
        if (!mlpc.y.equals("")) {
            y_ = Integer.parseInt(mlpc.y);
        } else {
            y_ = 0;
        }
        if (!mlpc.width.equals("")) {
            width_ = Integer.parseInt(mlpc.width);
        } else {
            width_ = 0;
        }
        if (!mlpc.height.equals("")) {
            height_ = Integer.parseInt(mlpc.height);
        } else {
            height_ = 0;
        }

        t.setBounds(x_, y_, width_, height_);

        switch (mlpc.layout) { // TODO добавьт другие layout
            case "BorderLayout":
                ((JFrame) t).setLayout(new BorderLayout());
                break;
            default:
                ((JFrame) t).setLayout(null); // выбираем тип разметки
        }
        ((JFrame) t).setVisible(Boolean.parseBoolean(mlpc.autoEnable)); // можно сразу сделать окно доступным
        ml.setParent((Container)t);
        components.put(mlpc.name, t);
        return t;
    }

}
