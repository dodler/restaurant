/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.treecommand.TreeCommand;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * класс для загрузки модели с сервера
 * также имплементирует IModel как ModelImplXml
 * @author lyan
 */
public class NetModelImpl implements IModel{

    private int port; // порт по которому клиент будет соединяться с сервером
    private String ip; // соответствующий ип адрес
    
    private ICategory root; // рутовая категория
    
    private final Socket socket; // сокет соединения ксерверу
    private final BufferedReader reader; // объекты для чтения и записи запросов
    private final BufferedWriter writer;
    
    private ArrayList<Dish> totalDishList;
    private ArrayList<ICategory> totalCategoryList;

    private boolean isInited = false; // флаг для проверки 
    
    private Document doc; // переменная нужна для парсинга xml документа
    
    /**
     * конструктор
     * @param ip - ip адрес для соединения с сервером через сокет
     * @param port - порт для соединения с сервером
     * @throws Exception - ошибка создания соединения/ просто подключения и т.д
     */
    public NetModelImpl(String ip, int port) throws Exception{
        socket = new Socket(InetAddress.getByName(ip), port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("Hello world!");
        writer.flush();
    }
    
    /**
     * чтобы нельзя быол создавать пустой объект без заданных параметров соединения
     */
    private NetModelImpl(){
        socket = null;
        reader = null;
        writer = null;
    }
    
    /**
     * метод возвращает рутовую категорию
     * которая позволяет манипулировать данными
     * и производить навигацию
     * @return 
     */
    @Override
    public ICategory getRootCategory() {
        return this.root;
    }

    /**
     * метод загрузки данных на сервера
     * @param name - адрес сервера
     * @throws IOException - если загрузка не удалась
     */
    @Override
    public void save(String name) throws IOException {
        if (root != null) { // собираем xml дерево
            writeTree(doc, root);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // парсинг
        StringWriter writer = new StringWriter();
        try {
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException ex) {
            Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        String output = writer.getBuffer().toString().replaceAll(">", ">\n"); // фикс бага? для переноса строк
        writer.write(output); // запись в объект сокета
        writer.flush(); // очистка - отправка данных в сеть
    }
    
    /**
     * рекурсивный метод создания xml дерева из дерева категорий
     * после полного прохода создается дерево xml в котором хранится дерево категорий
     * грубо говоря переводит дерево категорий в xml дерево
     * рутовый узел в doc
     * @param node - xml узел
     * @param cat - узел дерева категорий блюд
     */
    private void writeTree(Node node, ICategory cat) {
        Element e = doc.createElement("category"), dish, xmlCat; // это нужно только для рутовой категории
        e.setAttribute("name", cat.getName());
        e.setAttribute("id", Integer.toString(cat.getId()));
        
        if (cat.equals(root)){ // добавление рутовой категории в документ
            node.appendChild(e);
        }else{
            e = (Element)node;
        }
        
        for (Dish d : cat.getDishList()) {
            dish = doc.createElement("dish");
            dish.setAttribute("name", d.getName());
            dish.setAttribute("price", Double.toString(d.getPrice()));
            dish.setAttribute("id", Integer.toString(d.getId()));
            e.appendChild(dish);
        }
        for (ICategory c : cat.getSubCategoryList()) {
            xmlCat = doc.createElement("category");
            xmlCat.setAttribute("name", c.getName());
            xmlCat.setAttribute("id", Integer.toString(c.getId()));
            writeTree(xmlCat, c);
            e.appendChild(xmlCat);
        }
    }

    /**
     * метод загрузки данных на сервер
     * @param name - адрес сервера
     * @throws Exception - если загрузка не удалась
     */
    @Override
    public void load(String name) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void treeBypass(TreeCommand command, ICategory root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkUnique(ICategory root, ICategory searchCategory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkUnique(ICategory root, Dish searchDish) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
