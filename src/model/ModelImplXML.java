/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.treecommand.TreeCommand;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Артем
 */
public class ModelImplXML implements IModel, Serializable {

    private ICategory rootCategory = null;
    private ArrayList<Dish> totalDishList;
    private ArrayList<ICategory> totalCategoryList;

    private boolean isInited = false;
    
    //TODO: нужно сделать сериализацию, и рут категорию
    @Override
    public ICategory getRootCategory() {
        return rootCategory;
    }
    
    @Override
    public void save(String name) throws IOException {
        BufferedWriter bw;
        bw = new BufferedWriter(new FileWriter(name));
        if (rootCategory != null) {
            writeTree(doc, rootCategory);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        try {
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
        } catch (TransformerException ex) {
            Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, null, ex);
        }
        String output = writer.getBuffer().toString().replaceAll(">", ">\n");
        bw.write(output);
        bw.flush();
    }

    private Document doc;

    private void writeTree(Node node, ICategory cat) {
        Element e = doc.createElement("category"), dish, xmlCat; // это нужно только для рутовой категории
        e.setAttribute("name", cat.getName());
        e.setAttribute("id", Integer.toString(cat.getId()));
        
        if (cat.equals(rootCategory)){ // добавление рутовой категории в документ
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

    @Override
    public void load(String name) throws ParserConfigurationException, SAXException, IOException {
        File markupSource = new File(name);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(markupSource);
        //rootCategory = new CategoryImpl(doc.getChildNodes().item(0).getAttributes().getNamedItem("name").getTextContent());
        try {
            //System.out.println(doc.getChildNodes().item(0).getChildNodes().getLength());
            //initCat(rootCategory, doc.getChildNodes().item(0)); // начинаем рекурсивный обход

            if (rootCategory == null) { // загружаем в первый раз

                for (int i = 0; i < doc.getChildNodes().getLength(); i++) {
                    //System.out.println(doc.getChildNodes().item(i).getAttributes().getNamedItem("name").getTextContent());
                    initTree(null, doc.getChildNodes().item(i));
                }
            } else {
                for (int i = 0; i < doc.getChildNodes().getLength(); i++) {
                    //System.out.println(doc.getChildNodes().item(i).getAttributes().getNamedItem("name").getTextContent());
                    initTree(null, doc.getChildNodes().item(i));
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initTree(ICategory root, Node node) throws Exception {
        String type = node.getNodeName(), // получаем тип или еду
                name = node.getAttributes().getNamedItem("name").getTextContent(); // получили имя
        double price = 0;
        Node prc = node.getAttributes().getNamedItem("price"),
                id = node.getAttributes().getNamedItem("id");
        if (prc != null) {
            price = Double.parseDouble(prc.getTextContent()); // цена
        }
        int iid = 0;
        if (id != null) {
            iid = Integer.parseInt(id.getTextContent()); // получили ид
        }

        ICategory newRoot = null;
        boolean isDuplicated = false;
        switch (type) {
            case "dish":
                Dish newD = new Dish(name, price, iid);
                if (isInited) {
                    // дерево уже существует то есть добавление производится с мерджем
                    for (Dish d : totalDishList) {
                        if (d.equals(newD)) {
                            isDuplicated = true;
                            break;
                        }
                    }
                }
                if (!isDuplicated) {
                    totalDishList.add(newD);
                    root.addDish(newD);
                }
                break;
            case "category":
                if (rootCategory == null || (root == null && isInited)) {
                    if (rootCategory != null) { // для мержа
                        if (rootCategory.getId() == iid) { // тоеж самое для блюд
                            isDuplicated = true;
                        }
                    }
                    if (rootCategory == null) { // если загружаем в перый раз
                        rootCategory = new CategoryImpl(node.getAttributes().getNamedItem("name").getTextContent(), iid);
                    }
                    newRoot = rootCategory;
                } else {
                    newRoot = new CategoryImpl(name, iid);
                    if (isInited) { // проверка дубликатов
                        for (ICategory cat : this.totalCategoryList) { // сверяемся со списком
                            if (cat.equals(newRoot)) { // смотрим уникальность
                                isDuplicated = true; // всытавляем флаг
                            } // уникальность проверяется по ид
                        }
                    }

                    if (!isDuplicated) {
                        root.addCategory(newRoot); // если уникален то добавляем
                    }
                }
                if (!isDuplicated) {
                    totalCategoryList.add(newRoot); // тоеж самое
                }
                break;
        }
        NodeList childs = node.getChildNodes();
        for (int i = 0; i < childs.getLength(); i++) {
            //if (childs.item(i).getNodeName().equals("category") && newRoot != null) {
            if (childs.item(i).getNodeName().equals("category") || childs.item(i).getNodeName().equals("dish")) {
                initTree(newRoot, childs.item(i));
            }
            //} else if (childs.item(i).getNodeName().equals("dish")) {
            //  initCat(root, childs.item(i));
            //}
        }

        if (!isInited) {
            isInited = true; // ставим флаг, что дерево было проинициализировано
        }

    }

    @Override
    public boolean checkUnique(ICategory rootCategory, ICategory searchCategory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkUnique(ICategory rootCategory, Dish searchDish) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ModelImplXML() {
        this.totalDishList = new ArrayList<>();
        this.totalCategoryList = new ArrayList<>();

        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * метод обхода рекурсивного обхода дерева
     *
     * @param command - команда, которую нужно применить к текущему элементу
     * @param category - категория с которой нужно начать обход
     */
    @Override
    public void treeBypass(TreeCommand command, ICategory category) {
        if (command != null && category != null) {
            command.handle(category);
        } else if (category == null) {
            return;
        }
        if (category.getSubCategoryList().size() > 0) {
            for (ICategory c : category.getSubCategoryList()) {
                treeBypass(command, c);
            }
        }
    }

}
