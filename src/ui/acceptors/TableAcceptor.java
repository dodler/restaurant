/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

import java.awt.Component;
import java.awt.Container;
import static java.lang.Compiler.enable;
import java.util.HashMap;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import static jdk.nashorn.internal.codegen.CompilerConstants.className;
import ui.MarkupLoader;

/**
 *
 * @author lyan
 */
public class TableAcceptor extends IUiAcceptor {

    @Override
    public boolean isApplicable(String type) {
        return type.equals("table");
    }

    @Override
    public Component acceptUI(MarkupLoaderPropertiesContainer mlpc, Component parent, HashMap<String, Component> components, MarkupLoader ml) throws Exception {

        boolean enable = Boolean.valueOf(mlpc.autoEnable);

        JTable t1; // временный объект, если надо задействовать scrollpane

        if ((mlpc.className.equals("") || mlpc.className.equals("JTable")) && !ml.containsDialogClass(mlpc.className)) { // тут идет инициализация таблицы
            t1 = new JTable(); // либо стандартным классом
        } else {
            t1 = (JTable) ml.getDialogClass(mlpc.className).getConstructor().newInstance(); // либо указанным
        }

        if (ml.containsTableModel(mlpc.model)){ // назначаем модель данных
            //((JTable) t1).setModel((AbstractTableModel) ml.getTableModel(mlpc.model)).newInstance());
            return null; // TODO доделать таблицы
        } else { // если она не была задана 
            ((JTable) t1).setModel(new DefaultTableModel());
            //throw new MissingTableModelException(); // то выбрасываем исключение
        }

        if (enable) {
            t = new JScrollPane((JTable) t1); // если нужно добавляем в скрол пейн
        } else {
            t = t1; // иначе просто обновляем стандартную ссылку
        }

        t.setBounds(
                Integer.parseInt(mlpc.x),
                Integer.parseInt(mlpc.y),
                Integer.parseInt(mlpc.width),
                Integer.parseInt(mlpc.height)); // координаты
        ((Container) parent).add(t); // добавляем в список отображения родителя
        components.put(mlpc.name, t); // добавляем в список объектов
        return t;
    }

}
