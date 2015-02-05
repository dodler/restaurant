/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import controller.IModelController;
import controller.treecommand.TreeCommand;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.CategoryImpl;
import model.Dish;
import model.ICategory;
import model.IModel;
import model.IncorrectCostException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * класс, который отвечает за обработку единственного соединения
 *
 * @author lyan
 */
public class ConnectionHandler implements Runnable {

    private final Socket s; // сокет через который сервер будет общаться с клиентом
    private final IModelController c; // контроллер, который пишет изменения
    private final BufferedReader reader;
    private final BufferedWriter writer;
    private final GZIPInputStream zipis;
    private final GZIPOutputStream zipos;
    private final StringBuffer sb; // для считывания строк
    private final IModel model; // модель для получения доступа напрямую к элементам
    private final Document doc;

    @Override
    public void run() {
        String s;
        if (reader != null) {
            try {
                while ((s = reader.readLine()) != null) {
                    sb.append(s);
                }
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document d = db.parse(sb.toString());
                parseDocument(d);
            } catch (IOException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

        }
    }

    private void parseDocument(Document d) {
        NodeList nl = d.getChildNodes();
        for (int i = 0; i < nl.getLength(); i++) {
            switch (nl.item(i).getNodeName()) {
                case "del":
                    delete(nl.item(i));
                    break;
                case "upd":
                    update(nl.item(i));
                    break;
                case "get":
                    get(nl.item(i));
                    break;
                case "add":
                    add(nl.item(i));
                    break;
            }
        }
    }

    /**
     * добавление нового элемента
     *
     * @param n - элемент
     */
    private void add(Node n) {
        int parent = Integer.parseInt(n.getAttributes().getNamedItem("parent").getTextContent());
        ICategory cat = model.getRootCategory();
        model.treeBypass(new TreeCommand() {

            @Override
            public void handle(ICategory category) {
                if (category.getId() == parent) { // если ид совпал с ид родителя из запроса
                    if (n.getAttributes().getNamedItem("type").getTextContent().equals("dish")) {
                        try {
                            category.addDish(new Dish(
                                    n.getAttributes().getNamedItem("name").getTextContent(),
                                    Double.parseDouble((n.getAttributes().getNamedItem("price").getTextContent()))) // добавление блюда
                            );
                        } catch (IncorrectCostException ex) {
                            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        category.addCategory(new CategoryImpl(
                                n.getAttributes().getNamedItem("name").getTextContent() // добавление категории
                        ));
                    }
                }
            }

        }, cat);
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

        if (cat.equals(model.getRootCategory())) { // добавление рутовой категории в документ
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
     * выдача всего дерева с сервера
     *
     * @param n
     */
    private void get(Node n) {

        if (model.getRootCategory() != null) {

            // собираем xml дерево
            writeTree(doc, model.getRootCategory());
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // парсинг
        StringWriter writer = new StringWriter();
        try {
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        String output = writer.getBuffer().toString().replaceAll(">", ">\n"); // фикс бага? для переноса строк
        if (this.writer != null) {
            writer.write(output); // запись в объект сокета
            writer.flush(); // очистка - отправка данных в сеть
        } else {
            try {
                zipos.write(output.getBytes());
                zipos.flush();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * удаление узла из дерева
     *
     * @param n - узел
     */
    private void update(Node n) {
        if (n.getAttributes().getNamedItem("type").getTextContent().equals("category")) {
            c.editCategoryName(n.getAttributes().getNamedItem("old").getTextContent(), n.getAttributes().getNamedItem("new").getTextContent());
        }else{
            c.editDishPrice(n.getAttributes().getNamedItem("old").getTextContent(), Integer.parseInt(n.getAttributes().getNamedItem("price").getTextContent()));
            c.editDishName(n.getAttributes().getNamedItem("old").getTextContent(), n.getAttributes().getNamedItem("new").getTextContent());
        }
    }

    /**
     * удаление нода из дерева
     *
     * @param n - тот самый узел
     */
    private void delete(Node n) {
        c.deleteCategory(n.getAttributes().getNamedItem("name").getTextContent());
    }

    public ConnectionHandler(Socket s, IModelController c, IModel model, boolean zip) throws IOException, ParserConfigurationException {
        this.s = s;
        this.c = c;
        this.model = model;
        sb = new StringBuffer();
        if (zip) {
            zipis = new GZIPInputStream(s.getInputStream());
            zipos = new GZIPOutputStream(s.getOutputStream());
            writer = null;
            reader = null;
        } else {
            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            zipis = null;
            zipos = null;
        }
        doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }

}
