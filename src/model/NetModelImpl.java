/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.treecommand.TreeCommand;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import org.w3c.dom.NodeList;

/**
 * класс для загрузки модели с сервера также имплементирует IModel как
 * ModelImplXml
 *
 * @author lyan
 */
public class NetModelImpl implements IModel {

    private int port; // порт по которому клиент будет соединяться с сервером
    private String ip; // соответствующий ип адрес
    private ICategory root; // рутовая категория
    private final Socket socket; // сокет соединения ксерверу
    private final BufferedReader reader; // объекты для чтения и записи запросов
    private final BufferedWriter writer;
    private ArrayList<Dish> totalDishList;
    private ArrayList<ICategory> totalCategoryList;
    private boolean isInited; 
    private Document doc; // переменная нужна для парсинга xml документа
    private XMLRequestGenerator xmlrg;
    private final GZIPInputStream zipis;
    private final GZIPOutputStream zipos;

    /**
     * конструктор
     *
     * @param ip - ip адрес для соединения с сервером через сокет
     * @param port - порт для соединения с сервером
     * @param zip - будет ли использовано сжатие. если нет, то данные будут передаваться через стандартный символьный канал.
     * @throws Exception - ошибка создания соединения/ просто подключения и т.д
     */
    public NetModelImpl(String ip, int port, boolean zip) throws Exception {
        this.isInited = false;
        socket = new Socket(InetAddress.getByName(ip), port);
        if (!zip) {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            zipis = null;
            zipos = null;
        } else {
            zipis = new GZIPInputStream(socket.getInputStream());
            zipos = new GZIPOutputStream(socket.getOutputStream());
            writer = null;
            reader = null;
        }
        xmlrg = XMLRequestGenerator.getInstance();
    }

    /**
     * чтобы нельзя быол создавать пустой объект без заданных параметров
     * соединения
     */
    private NetModelImpl() {
        this.isInited = false;
        socket = null;
        reader = null;
        writer = null;
        zipis = null;
        zipos = null;
    }

    /**
     * метод возвращает рутовую категорию которая позволяет манипулировать
     * данными и производить навигацию
     *
     * @return
     */
    @Override
    public ICategory getRootCategory() {
        return this.root;
    }

    /**
     * метод загрузки данных на сервера
     *
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
            Logger.getLogger(NetModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // парсинг
        StringWriter writer = new StringWriter();
        try {
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException ex) {
            Logger.getLogger(NetModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        String output = writer.getBuffer().toString().replaceAll(">", ">\n"); // фикс бага? для переноса строк
        if (this.writer != null) {
            writer.write(output); // запись в объект сокета
            writer.flush(); // очистка - отправка данных в сеть
        } else {
            zipos.write(output.getBytes());
            zipos.flush();
        }
    }

    /**
     * обновление категории на сервере те смена названия блюда
     *
     * @param cat - категория, которую нужно обновлять с изменениями
     */
    public void update(ICategory cat) throws IOException {
        xmlrg.putCategory(cat, XMLRequestGenerator.RequestType.U);
        write();
    }

    /**
     * обновление блюда те смена цены или названия
     *
     * @param d - блюдо, которое нужно обновить
     */
    public void update(Dish d) throws IOException {
        xmlrg.putDish(d, XMLRequestGenerator.RequestType.U);
        write();
    }

    /**
     * добавление категории в дерево на сервер
     *
     * @param cat - новая категория
     * @param parentID - ид родительской категории
     */
    public void add(ICategory cat, int parentID) throws IOException {
        xmlrg.putCategory(cat, XMLRequestGenerator.RequestType.A, parentID);
        write();
    }

    public void add(Dish d, int parentID) throws IOException {
        xmlrg.putDish(d, XMLRequestGenerator.RequestType.A, parentID);
        write();
    }

    public void delete(int id) throws IOException {
        xmlrg.del(id);
        write();
    }

    private void write() throws IOException {
        String s = xmlrg.getRequest();
        try {
            if (writer != null) {
                writer.write(xmlrg.getRequest());
                writer.flush();
            } else {
                zipos.write(s.getBytes(), 0, s.length());
                zipos.flush();
            }
        } catch (IOException ioe) {
            Logger.getLogger(NetModelImpl.class.getName()).log(Level.SEVERE, null, ioe);
            throw ioe;
        }
    }

    /**
     * рекурсивный метод создания xml дерева из дерева категорий после полного
     * прохода создается дерево xml в котором хранится дерево категорий грубо
     * говоря переводит дерево категорий в xml дерево рутовый узел в doc
     *
     * @param node - xml узел
     * @param cat - узел дерева категорий блюд
     */
    private void writeTree(Node node, ICategory cat) {
        Element e = doc.createElement("category"), dish, xmlCat; // это нужно только для рутовой категории
        e.setAttribute("name", cat.getName());
        e.setAttribute("id", Integer.toString(cat.getId()));

        if (cat.equals(root)) { // добавление рутовой категории в документ
            node.appendChild(e);
        } else {
            e = (Element) node;
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
     * метод парсинга строчки в xml в готвоую структуру поная загрузка дерева
     * производится в методе load там производится загрузка xml с сервера и
     * обраобтка в этом методе
     *
     * @param source - адрес сервера
     * @throws Exception - если загрузка не удалась
     */
    @Override
    public void load(String source) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document d = db.parse(source);
        try {
            for (int i = 0; i < d.getChildNodes().getLength(); i++) {
                //System.out.println(doc.getChildNodes().item(i).getAttributes().getNamedItem("name").getTextContent());
                initCat(null, d.getChildNodes().item(i));
            }
        } catch (Exception ex) {
            Logger.getLogger(NetModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * загрузка дерева с сервера наичнать работу с моделью нужно с вызова этого
     * метода
     *
     * @throws Exception
     */
    public void load() throws Exception {
        StringBuilder sb = new StringBuilder("");
        String inp;
        while ((inp = reader.readLine()) != null) {
            sb.append(inp);
        }
        load(sb.toString());
    }

    private void initCat(ICategory root, Node node) throws Exception {
        String type = node.getNodeName(), // получаем тип или еду
                name = node.getAttributes().getNamedItem("name").getTextContent(); // получили имя
        double price = 0;
        Node prc;
        if ((prc = node.getAttributes().getNamedItem("price")) != null) {
            price = Double.parseDouble(prc.getTextContent());
        }

        ICategory newRoot = null;
        switch (type) {
            case "dish":
                root.addDish(new Dish(name, price));
                break;
            case "category":
                if (this.root == null) {
                    this.root = new CategoryImpl(node.getAttributes().getNamedItem("name").getTextContent());
                    newRoot = this.root;
                } else {
                    newRoot = new CategoryImpl(name);
                    root.addCategory(newRoot);
                }
                break;
        }
        NodeList childs = node.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            //if (childs.item(i).getNodeName().equals("category") && newRoot != null) {
            if (childs.item(i).getNodeName().equals("category") || childs.item(i).getNodeName().equals("dish")) {
                initCat(newRoot, childs.item(i));
            }
            //} else if (childs.item(i).getNodeName().equals("dish")) {
            //  initCat(root, childs.item(i));
            //}
        }
    }

    /**
     *
     * @param command
     * @param rootCategory
     */
    @Override
    public void treeBypass(TreeCommand command, ICategory rootCategory) {
        if (command != null && rootCategory != null) {
            command.handle(rootCategory);
        }
        if (!rootCategory.getSubCategoryList().isEmpty()) {
            for (ICategory c : rootCategory.getSubCategoryList()) {
                treeBypass(command, c);
            }
        }
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
