/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.treecommand.TreeCommand;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import model.net.Request;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * класс для загрузки модели с сервера также имплементирует IModel как
 * ModelImplXml класс рабоает по принципу репозитория git снчала изменения
 * сохраняются в локальной модели потом они отправляются на сервер при помощи
 * сериализации
 *
 * @author lyan
 */
public class NetModelImpl implements IModel, Serializable {

    private int maxDishId, maxCatId;
    private boolean fixed; // изменения в модели зафиксированы на сервере
    private int port; // порт по которому клиент будет соединяться с сервером
    private String ip; // соответствующий ип адрес
    private transient final Socket socket; // сокет соединения ксерверу
    private ArrayList<ICategory> cats;

    private transient ObjectInputStream ois;
    private transient ObjectOutputStream oos;

    /**
     * конструктор
     *
     * @param ip - ip адрес для соединения с сервером через сокет
     * @param port - порт для соединения с сервером
     * @throws java.io.IOException
     */
    public NetModelImpl(String ip, int port) throws IOException {
        socket = new Socket();
        this.cats = new ArrayList<>();
        connect(ip, port);
    }
    
    /**
     * при помощи этого метода производится подключение к серверу приложения
     * создаются нужные каналы на считывание и запись данных
     *
     * @param ip адрес по которому будет подключатья сокет. строка - байты через
     * точку. можно писать localhost для локального подключения
     * @param port порт по которому будет подключаться сокет. целое число от 0
     * до 65 с лишним тыся вроде
     * @throws IOException обозначает неполадки подключения
     */
    public final void connect(String ip, int port) throws IOException {
        if (socket.isConnected()) {
            return; // чтобы не было двойного подключения
        }

        try {
            socket.connect(new InetSocketAddress(InetAddress.getByName(ip), port));

        } catch (ConnectException e) {
            Logger.getLogger(NetModelImpl.class.getName()).log(Level.SEVERE, e.getMessage());

        } catch (IOException ex) {
            Logger.getLogger(NetModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
    }

    /**
     * метод закрывает соединение с сервером
     *
     * @throws IOException
     */
    public void reset() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
            ois.close();
            oos.close();
        }
    }

    /**
     * чтобы нельзя быол создавать пустой объект без заданных параметров
     * соединения
     */
    private NetModelImpl() {
        socket = null;
        ois = null;
        oos = null;
    }

    /**
     * метод возвращает рутовую категорию которая позволяет манипулировать
     * данными и производить навигацию upd 17 03: метод возвращает просто Null
     *
     * @return
     */
    @Deprecated
    @Override
    public ICategory getRootCategory() {
        return null;
    }

    /**
     * метод загрузки данных на сервера
     *
     * @param name - адрес сервера
     * @throws IOException - если загрузка не удалась
     */
    @Deprecated
    @Override
    public void save(String name) throws IOException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = tf.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(NetModelImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // парсинг
        StringWriter writer = new StringWriter();
        String output = writer.getBuffer().toString().replaceAll(">", ">\n"); // фикс бага? для переноса строк
        writer.write(output);
        writer.flush();
    }

    /**
     * метод сохранения изменений на сервере отправлятс запрос с обновленной
     * моделью модель на сервере обновляется
     *
     * @throws java.io.IOException ошибка записи модели на сервер
     */
    public void save() throws IOException {
        NetModelImpl mod = new NetModelImpl();
        mod.cats = this.cats;
        Request req = new Request(mod, Request.POST);
        oos.writeObject(req);
        oos.flush();
        fixed = true;
        Logger.getLogger(NetModelImpl.class.getName()).log(Level.SEVERE, "save");
    }

    /**
     * обновление категории в локальной мдоели для осхранения изменений нужно
     * отправить модель на сервер методом save() изменять можно только имя в cat
     * дложен быть ид категории, которую менять, а также нвое имя (или без
     * изменения)
     *
     * @param cat - категория, которую нужно обновлять с изменениями
     */
    public void update(ICategory cat) {
        for (ICategory c : this.cats) {
            if (cat.getId() == c.getId()) {
                c.setName(cat.getName());
                fixed = false;
                return; // дальше итерировать смысла нет, считается, что ид уникален
            }
        }
    }

    /**
     * обновление блюда те смена цены или названия
     *
     * @param d - блюдо, которое нужно обновить
     */
    public void update(Dish d) {
        for (ICategory c : cats) {
            for (Dish d1 : c.getDishList()) {
                if (d1.getId() == d.getId()) {
                    d1.setName(d.getName());
                    d1.setPrice(d.getPrice());
                    fixed = false;
                    return;
                }
            }
        }
    }

    /**
     * добавление категории в дерево на сервер
     *
     * @param cat - новая категория
     * @param parentID - ид родительской категории
     */
    @Deprecated
    public void add(ICategory cat, int parentID) {
    }

    public void add(Dish d, int parentID) throws IOException {
        for (ICategory c : cats) {
            if (c.getId() == parentID) {
                c.addDish(d);
                fixed = false;
                return;
            }
        }
    }

    /**
     * метод удаления объекта меню по ид ид либо категории, либо блюда, без
     * разницы
     *
     * @param id целочисленный параметр - ид нужного объекта
     */
    public void delete(int id) {
        for (ICategory c : cats) {
            if (id == c.getId()) {
                c.getDishList().clear();
                cats.remove(c);
                fixed = false;
                return; // находим удаляем старые блюда и удаляем категорию из списка категорий
            }
            for (Dish d : c.getDishList()) {
                if (d.getId() == id) { //находим нужный ид и удаляем его
                    c.getDishList().remove(d);
                    fixed = false;
                    return;
                }
            }
        }
    }

    /**
     * сам не пойму что это может пригодится не буду пока удалять
     *
     * @param params
     */
    public void pass(Map params) {

    }

    public void add(ICategory cat) {
        cats.add(cat);
        fixed = false;
    }

    /**
     * метод загружает данные с сервера модель хранится в запросе запрос
     * прихоидт сериазованным с сервера после прихода новой модели старая
     * подменяется
     *
     * @throws IOException ошибка считывания данных из входного потока или
     * отправки запроса на сервер
     * @throws java.lang.ClassNotFoundException ошибка сериализации
     */
    public void load() throws IOException, ClassNotFoundException {
        Request req = new Request(null, Request.GET);
        oos.writeObject(req);
        oos.flush();

        req = (Request) ois.readObject();
        cats.clear();
        cats = new ArrayList<>();
        ICategory tempC;
        Dish tempD; // временные переменные для копирования
        CopyOnWriteArrayList<ICategory> src = (CopyOnWriteArrayList) ((ModelImplXML) req.getModel()).getCategoryLst().clone();
        for (ICategory c : src) {
            tempC = new CategoryImpl(c.getName(), c.getId());
            for (Dish d : c.getDishList()) {
                tempD = new Dish(d.getName(), d.getPrice(), d.getId());
                tempC.addDish(tempD);
            }
            cats.add(tempC);

        }
        for (ICategory c : cats) {
            if (c.getId() > maxCatId) {
                maxCatId = c.getId();
            }
            for (Dish d : c.getDishList()) {
                if (d.getId() > maxDishId) {
                    maxDishId = d.getId();
                }
            }
        }
        fixed = true;
    }

    /**
     * методы инкрементируют текущий максимальный ид категории и возвращает
     * новое значение
     *
     * @return целое число, максимальное ид категории
     */
    public int getMaxCatId() {
        maxCatId = 1 + maxCatId;
        return maxCatId;
    }

    /**
     * метоз увелчивает значение максимального ид и возвращает новое значение
     *
     * @return целое число максимальный ид категории
     */
    public int getMaxDishId() {
        maxDishId = maxDishId + 1;
        return maxDishId;
    }

    /**
     * рекурсивный метод создания xml дерева из дерева категорий после полного
     * прохода создается дерево xml в котором хранится дерево категорий грубо
     * говоря переводит дерево категорий в xml дерево рутовый узел в doc
     *
     * @param node - xml узел
     * @param cat - узел дерева категорий блюд
     */
    @Deprecated
    private void writeTree(Node node, ICategory cat) {

    }

    /**
     * метод парсинга строчки в xml в готвоую структуру поная загрузка дерева
     * производится в методе load там производится загрузка xml с сервера и
     * обраобтка в этом методе upd 17/03 загрузка с фиксированного сервера
     *
     * @param source - адрес сервера
     * @throws Exception - если загрузка не удалась
     */
    @Deprecated
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
            Logger.getLogger(NetModelImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Deprecated
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
    @Deprecated
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

    @Deprecated
    @Override
    public boolean checkUnique(ICategory root, ICategory searchCategory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Deprecated
    @Override
    public boolean checkUnique(ICategory root, Dish searchDish) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ICategory> getCategoryList() {
        return this.cats;
    }

    /**
     * метод возвращает состояние модели а именно зафиксированы ли изменения на
     * сервере если нет, то при закрытии клиента, изменения будут утеряны если
     * да, то измеения висят на сервере
     *
     * @return boolean флаг, обозначющий наличие изменений, не отправленных на
     * сервер
     */
    public boolean isFixed() {
        return this.fixed;
    }
}
