/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.treecommand.TreeCommand;
import haulmaunt.lyan.ui.markupexception.MissingMouseListenerException;
import java.awt.Cursor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.ParserConfigurationException;
import model.Dish;
import model.ICategory;
import model.IModel;
import model.NetModelImpl;
import nc.AppController;
import ui.InitFoo;
import ui.MarkupLoader;
import ui.NoSuchElementException;

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

    private IModel model;

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
        this.model = model;
        loader = new MarkupLoader();
        foos = new ArrayList<>();
    }

    private ArrayList<InitFoo> foos;

    /**
     * метод добавляет функции дополнительной обработки перед инициализацией
     * интерфейса например нужно отредактировать кнопку или таблицу или задать
     * начальные данные в таблице создаете класс наследующий интерфейс и кидаете
     * в аргумент функция будет выполнена при инициализации гуи
     *
     * @param foo нужная функция обрабоки
     */
    public void addInitFoo(InitFoo foo) {
        foos.add(foo);
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

        //dtm = (DefaultTableModel) (((JTable) loader.getComponent("mainTable")).getModel()); // получили модель таблицы
        for (InitFoo f : foos) {
            f.init();
        }

        dtm = (DefaultTableModel) ((JTable) loader.getComponent("mainTable")).getModel();
        updateTable();

    }

    /**
     * вывод полного дерева категорий в виде таблицы исходная таблица очищается
     * от записей
     *
     */
    @Deprecated
    @Override
    public void showCategoryList() {
        cleanTable();
    }

    /**
     * очистка таблицы удаляет все записи из таблицы необходимо вызывать каждый
     * раз перед обновлением данных
     */
    private void cleanTable() {
        while (dtm.getRowCount() > 0) {
            dtm.removeRow(0);
        }
    }

    public void setModel(IModel model) {
        this.model = model;
        updateTable();
    }

    /**
     * метод обновляет данные таблицы вью
     */
    public void updateTable() {
        String[] data;
        cleanTable();
        ArrayList<ICategory> cats = model.getCategoryList();
        for (ICategory c : cats) {
            data = new String[3];
            data[0] = String.valueOf(c.getId());
            data[1] = c.getName();
            data[2] = "";
            dtm.addRow(data);
            for (Dish d : c.getDishList()) {
                data = new String[3];
                data[0] = String.valueOf(d.getId());
                data[1] = d.getName();
                data[2] = String.valueOf(d.getPrice());
                dtm.addRow(data);
            }
        }
    }

    /**
     * вывод списка подкатегорий категории с именем cat в виде таблицы. исходная
     * таблица очищается
     *
     * @param cat - имя категории, подкатегории которой надо вывести
     */
    @Deprecated
    @Override
    public void showCategoryList(ICategory cat) {
        cleanTable();
    }

    /**
     * вывод блюд и подкатегорий заданной категории
     *
     * @param category - категоия, из которой нужно все выводить на экран
     */
    @Deprecated
    @Override
    public void showDishTree(ICategory category) {
    }

    @Deprecated
    @Override
    public void showDishTreePriced() {
    }

    @Deprecated
    @Override
    public void showDishTree() {
    }

    @Deprecated
    @Override
    public void showDishTreePriced(ICategory category) {
        cleanTable();
    }

    @Deprecated
    @Override
    public void show(ICategory cat) {
        cleanTable();
        data[0] = cat.getName();
        data[1] = "";
        dtm.addRow(data);
    }

    @Deprecated
    @Override
    public void showWithDishes(ICategory cat) {
        show(cat);
        for (Dish d : cat.getDishList()) {
            data[0] = d.getName();
            data[1] = Double.toString(d.getPrice());
            dtm.addRow(data);
        }
    }

    @Deprecated
    @Override
    public void show(ArrayList<ICategory> catList) {
        cleanTable();
        for (ICategory cat : catList) {
            data[0] = cat.getName();
            data[1] = "";
            dtm.addRow(data);
        }
    }

    @Deprecated
    @Override
    public void showDish(ArrayList<Dish> dishList) {
        cleanTable();
        for (Dish d : dishList) {
            data[0] = d.getName();
            data[1] = Double.toString(d.getPrice());
            dtm.addRow(data);
        }
    }

    @Deprecated
    @Override
    public void show(String source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public void show(Dish d) {
        cleanTable();
        data[0] = d.getName();
        data[1] = Double.toString(d.getPrice());
        dtm.addRow(data);
    }

}
