/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Артем
 */
public class ModelImpl implements IModel, Serializable {

    private ICategory rootCategory;

    //TODO: нужно сделать сериализацию, и рут категорию
    @Override
    public ICategory getRootCategory() {
        return rootCategory;
    }

    @Override
    public void saveToFile(String name) throws IOException {
        BufferedReader br;
        br = new BufferedReader(new FileReader(name));

    }

    @Override
    public void loadFromFile(String name) throws ParserConfigurationException, SAXException, IOException {
        File markupSource = new File(name);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(markupSource);
        //rootCategory = new CategoryImpl(doc.getChildNodes().item(0).getAttributes().getNamedItem("name").getTextContent());
        try {
            //System.out.println(doc.getChildNodes().item(0).getChildNodes().getLength());
            //initCat(rootCategory, doc.getChildNodes().item(0)); // начинаем рекурсивный обход

            for (int i = 0; i < doc.getChildNodes().getLength(); i++) {
                //System.out.println(doc.getChildNodes().item(i).getAttributes().getNamedItem("name").getTextContent());
                initCat(null, doc.getChildNodes().item(i));
            }
        } catch (Exception ex) {
            Logger.getLogger(ModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                if (rootCategory == null) {
                    rootCategory = new CategoryImpl(node.getAttributes().getNamedItem("name").getTextContent());
                    newRoot = rootCategory;
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

}
