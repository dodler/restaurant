/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import haulmaunt.lyan.ui.markupexception.MissingMouseListenerException;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import model.Dish;
import model.ICategory;
import ui.MarkupLoader;

/**
 * класс гуи для клиента справочной системы отображает содержимое меню в виде
 * таблицы
 *
 * @author Артем
 */
public class SwingView implements IView {

    private MarkupLoader loader;

    /**
     * контструктор, который позволяет загрузить разметку xml, на основе которой
     * будет построена программа
     *
     * @param xml - путь до файла xml c разметкой
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws haulmaunt.lyan.ui.markupexception.MissingMouseListenerException
     */
    public SwingView(String xml) throws IOException,
            ParserConfigurationException, MissingMouseListenerException, Exception {
        loader = new MarkupLoader();
        loader.loadMarkup(xml);
        
        
    }

    @Override
    public void showCategoryList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showCategoryList(String cat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showDishTree(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showDishTreePriced() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showDishTree() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showDishTreePriced(String category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void show(ICategory cat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showWithDishes(ICategory cat) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void show(ArrayList<ICategory> catList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void showDish(ArrayList<Dish> dishList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void show(String source) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void show(Dish d) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
