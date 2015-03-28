/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.treecommand.TreeCommand;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Артем
 */
public class ModelImplXML implements IModel, Serializable {

    private CopyOnWriteArrayList<ICategory> cats;
    private ICategory rootCategory;

    public ModelImplXML(ArrayList<ICategory> categoryList) {
        this.cats = new CopyOnWriteArrayList<>();
        for (ICategory c : categoryList) {
            cats.add(c);
        }
    }

    public ModelImplXML() {
        this.cats = new CopyOnWriteArrayList<>();
    }

    //TODO: нужно сделать сериализацию, и рут категорию
    @Deprecated
    @Override
    public ICategory getRootCategory() {
        return rootCategory;
    }

    @Override
    public void save(String name) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(name));
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<list>\n");
        for (ICategory c : cats) {
            //<category id="100000" name="tst">
            //<dish id="2" name="a" price="12" />
            sb.append("\t<category id=\"");
            sb.append(c.getId());
            sb.append("\" name=\"");
            sb.append(c.getName());
            sb.append("\">\n");
            for (Dish d : c.getDishList()) {
                sb.append("\t\t<dish id=\"");
                sb.append(d.getId());
                sb.append("\" name=\"");
                sb.append(d.getName());
                sb.append("\" price=\"");
                sb.append(d.getPrice());
                sb.append("\" /> \n");
            }
            sb.append("\t</category>\n");
        }
        sb.append("</list>");
        writer.write(sb.toString());
        writer.flush();
        writer.close();

    }

    /**
     * метод возвращает список доступных категорий в виде линейного массива
     *
     * @return
     */
    @Override
    public ArrayList<ICategory> getCategoryList() {
        throw new UnsupportedOperationException("Не работает для сервера");
    }

    public CopyOnWriteArrayList<ICategory> getCategoryLst() {
        return this.cats;
    }

    /**
     * загрузка файла данных из xml в xml данные хранятся в формате
     * последовательного списка со влжоенными блюдами
     *
     * @param name имя файла данных
     * @throws ParserConfigurationException ошибка настройки парсера xml
     * @throws SAXException ошибка парсинга
     * @throws IOException ошибка считыания файла
     */
    @Override
    public void load(String name) throws ParserConfigurationException, SAXException, IOException {
        File markupSource = new File(name);
        if (!markupSource.exists()) {
            markupSource.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(markupSource));
            bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<list>\n</list>");
            bw.flush();
            bw.close();
            return;
        }
        cats = new CopyOnWriteArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(markupSource);
        NodeList childs = doc.getElementsByTagName("category");
        Node n;
        ICategory c;
        Dish d;
        NamedNodeMap attr;
        for (int i = 0; i < childs.getLength(); i++) {// проход по списку категорий
            n = childs.item(i);
            attr = n.getAttributes();
            c = new CategoryImpl(attr.getNamedItem("name").getTextContent(),
                    Integer.parseInt(attr.getNamedItem("id").getTextContent())
            );
            for (int j = 0; j < n.getChildNodes().getLength(); j++) {
                if (n.getChildNodes().item(j).getNodeName().equals("#text")) {
                    continue; // пропускаю текст
                }
                attr = n.getChildNodes().item(j).getAttributes();

                d = new Dish(
                        attr.getNamedItem("name").getTextContent(),
                        Double.parseDouble(attr.getNamedItem("price").getTextContent()),
                        Integer.parseInt(attr.getNamedItem("id").getTextContent())
                );
                c.addDish(d);
            }
            cats.add(c);
        }
        Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, "Загружено из файла:");
        Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, String.valueOf(cats.size()) + " категорий");
    }

    public void updateModel(ArrayList<ICategory> cats) {
        Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, "Вход в метод обновления");
        Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, String.valueOf(cats.size()));

        this.cats.clear();

        Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, "Текущие категории очищены");
        for (ICategory c : cats) {
            this.cats.add(new CategoryImpl(c.getName(), c.getId()));
        }

        Logger.getLogger(ModelImplXML.class.getName()).log(Level.SEVERE, "Новые данные записаны в модель");
    }

    @Deprecated
    @Override
    public void treeBypass(TreeCommand command, ICategory rootCategory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public boolean checkUnique(ICategory rootCategory, ICategory searchCategory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public boolean checkUnique(ICategory rootCategory, Dish searchDish) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
