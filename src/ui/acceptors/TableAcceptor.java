/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import ui.MarkupLoader;

/**
 * создание таблицы поддерживаются теги автоматически будет добавлена
 * прокрутка,если не хватает длины autoEnable - сразу инициализируется className
 * - класс таблицы, которым будет инициализирована таблица по умолчанию - JTable
 * model - модель таблицы, нужно просто укзаать имя, которое потом нужно будет
 * добавить в MarkupLoader указывать необязательно, по умолчанию добавляется
 * DefaultTableModel x - координата таблицы по х у - координата таблицы по y
 * width - длина таблицы height - высота таблицы
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

        if (ml.containsTableModel(mlpc.model)) { // назначаем модель данных
            t1.setModel(ml.getTableModel(mlpc.model));
            Logger.getLogger(TableAcceptor.class.getName()).log(Level.SEVERE, "Добавлена модель");
        } else { // если она не была задана 
            t1.setModel(new DefaultTableModel());
        }

        if (mlpc.rows != null && !mlpc.rows.equals("")) {
            String[] columnNames = mlpc.rows.split(";");
            TableModel dtm = t1.getModel();
            for (String s : columnNames) {
                ((DefaultTableModel)dtm).addColumn(s);
            }
            t1.setModel(dtm);
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
        components.put(mlpc.name, t1); // добавляем в список объектов
        return t;
    }

}
