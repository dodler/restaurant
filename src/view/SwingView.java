/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.treecommand.TreeCommand;
import haulmaunt.lyan.ui.markupexception.MissingMouseListenerException;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import model.Dish;
import model.ICategory;
import model.IModel;
import model.NetModelImpl;
import ui.MarkupLoader;

/**
 * класс гуи для клиента справочной системы отображает содержимое меню в виде
 * таблицы все элементы гуи доступны только после вызова метода init(); если
 * попытаться получить доступ к элементам до init будет выброшен Exception
 * порядок инициализации конструктор - задать все слушатели - init.
 *
 * @author Артем
 */
public class SwingView implements IView {

    private MarkupLoader loader;
    private DefaultTableModel dtm;
    private NetModelImpl model;
    private final String[] data = new String[2]; // перемнная для записи строк в таблицу

    /**
     * метод дающий доступ к загрузчику интерфейса нужно для задания модели
     * таблиц, слушателей кнопок и т.д
     *
     * @return MarkupLoader - объект загрузчика интерфейса, который грузит
     * размиетку
     */
    public MarkupLoader getLoader() {
        return this.loader;
    }

    /**
     * контструктор, который позволяет загрузить разметку xml, на основе которой
     * будет построена программа
     *
     * @param model - модель данных для вью. должно быть NetModelImpl
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws haulmaunt.lyan.ui.markupexception.MissingMouseListenerException
     */
    public SwingView(IModel model) throws IOException,
            ParserConfigurationException, MissingMouseListenerException, Exception {
        loader = new MarkupLoader();

        try {
            this.model = (NetModelImpl) model;
        } catch (ClassCastException cce) {
            throw new ClassCastException("Класс должен наследовать NetModelImpl");
        }
        
        loader.addMouseListener("onConnectClick", new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                
            }

            @Override
            public void mousePressed(MouseEvent me) {
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }

        });

        loader.addMouseListener("onDelClick", new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                
            }

            @Override
            public void mousePressed(MouseEvent me) {
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }

        });

        loader.addMouseListener("onFindClick", new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {
                
            }

            @Override
            public void mousePressed(MouseEvent me) {
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }

        });

        loader.addMouseListener("onAddClick", new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent me) {

            }

            @Override
            public void mousePressed(MouseEvent me) {

            }

            @Override
            public void mouseReleased(MouseEvent me) {

            }

            @Override
            public void mouseEntered(MouseEvent me) {

            }

            @Override
            public void mouseExited(MouseEvent me) {

            }
        });
        
        loader.addMouseListener("onSortClick", new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent me) {
                
            }

            @Override
            public void mousePressed(MouseEvent me) {
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }
            
        });
    }

    /**
     * метод инициализации гуи
     *
     * @param xml - путь до файла с разметкой
     * @throws IOException - ошибка чтения
     * @throws ParserConfigurationException - неправильная разметка
     * @throws MissingMouseListenerException - нехватает слушателя событий для
     * мыши
     * @throws Exception - прочие ошибки
     */
    public void init(String xml) throws IOException, ParserConfigurationException, MissingMouseListenerException, Exception {
        loader.loadMarkup(xml);
        dtm = (DefaultTableModel) (((JTable) loader.getComponent("mainTable")).getModel()); // получили модель таблицы
        
        ((JButton)loader.getComponent("addBtn")).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ((JButton)loader.getComponent("connectBtn")).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ((JButton)loader.getComponent("findBtn")).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ((JButton)loader.getComponent("delBtn")).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ((JButton)loader.getComponent("sortBtn")).setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /**
     * вывод полного дерева категорий в виде таблицы исходная таблица очищается
     * от записей
     *
     */
    @Override
    public void showCategoryList() {
        cleanTable();
        model.treeBypass(new TreeCommand() {
            @Override
            public void handle(ICategory category) {
                data[0] = category.getName();
                data[1] = "";
                dtm.addRow(data);
            }
        }, model.getRootCategory());
    }

    /**
     * очистка таблицы удаляет все записи из таблицы необходимо вызывать каждый
     * раз перед обновлением данных
     */
    private void cleanTable() {
        int c = dtm.getRowCount();
        for (int i = 0; i < c; i++) {
            dtm.removeRow(i);
        }
    }

    /**
     * вывод списка подкатегорий категории с именем cat в виде таблицы. исходная
     * таблица очищается
     *
     * @param cat - имя категории, подкатегории которой надо вывести
     */
    @Override
    public void showCategoryList(ICategory cat) {
        cleanTable();
        model.treeBypass(new TreeCommand() {
            @Override
            public void handle(ICategory category) {
                data[0] = category.getName();
                data[1] = "";
                dtm.addRow(data);
            }
        }, cat);
    }

    /**
     * вывод блюд и подкатегорий заданной категории
     *
     * @param category - категоия, из которой нужно все выводить на экран
     */
    @Override
    public void showDishTree(ICategory category) {
        model.treeBypass(new TreeCommand() {

            @Override
            public void handle(ICategory category) {
                data[0] = category.getName();
                data[1] = "";
                dtm.addRow(data);
                for (Dish d : category.getDishList()) {
                    data[0] = d.getName();
                    data[1] = "";
                    dtm.addRow(data);
                }
            }
        }, category);
    }

    @Override
    public void showDishTreePriced() {
        showDishTreePriced(model.getRootCategory());
    }

    @Override
    public void showDishTree() {
        showDishTree(model.getRootCategory());
    }

    @Override
    public void showDishTreePriced(ICategory category) {
        cleanTable();
        model.treeBypass(new TreeCommand() {

            @Override
            public void handle(ICategory category) {
                data[0] = category.getName();
                data[1] = "";
                dtm.addRow(data);
                for (Dish d : category.getDishList()) {
                    data[0] = d.getName();
                    data[1] = Double.toString(d.getPrice());
                    dtm.addRow(data);
                }
            }
        }, category);
    }

    @Override
    public void show(ICategory cat) {
        cleanTable();
        data[0] = cat.getName();
        data[1] = "";
        dtm.addRow(data);
    }

    @Override
    public void showWithDishes(ICategory cat) {
        show(cat);
        for (Dish d : cat.getDishList()) {
            data[0] = d.getName();
            data[1] = Double.toString(d.getPrice());
            dtm.addRow(data);
        }
    }

    @Override
    public void show(ArrayList<ICategory> catList) {
        cleanTable();
        for (ICategory cat : catList) {
            data[0] = cat.getName();
            data[1] = "";
            dtm.addRow(data);
        }
    }

    @Override
    public void showDish(ArrayList<Dish> dishList) {
        cleanTable();
        for (Dish d : dishList) {
            data[0] = d.getName();
            data[1] = Double.toString(d.getPrice());
            dtm.addRow(data);
        }
    }

    @Override
    public void show(String source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void show(Dish d) {
        cleanTable();
        data[0] = d.getName();
        data[1] = Double.toString(d.getPrice());
        dtm.addRow(data);
    }

}
