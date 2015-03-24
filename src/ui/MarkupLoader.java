/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import haulmaunt.lyan.ui.markupexception.InvalidMarkupException;
import haulmaunt.lyan.ui.markupexception.MissingTableModelException;
import haulmaunt.lyan.ui.markupexception.MissingMouseListenerException;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ui.acceptors.ButtonAcceptor;
import ui.acceptors.DialogAcceptor;
import ui.acceptors.FrameAcceptor;
import ui.acceptors.IUiAcceptor;
import ui.acceptors.LabelAcceptor;
import ui.acceptors.MarkupLoaderPropertiesContainer;
import ui.acceptors.MenuAcceptor;
import ui.acceptors.MenuBarAcceptor;
import ui.acceptors.MenuItemAcceptor;
import ui.acceptors.TableAcceptor;
import ui.acceptors.TextAcceptor;

/**
 *
 * @author Артем
 */
public class MarkupLoader {

    private static final MarkupLoader markupLoaderInstance = new MarkupLoader();

    public static MarkupLoader getMarkupLoaderInstance() {
        return markupLoaderInstance;
    }
    private final HashMap<String, MouseListener> mouseListeners; // пока не буду заморачиваться с инкапсуяцией
    private final HashMap<String, Class> dialogClasses; // классы диалогов
    private final HashMap<String, TableModel> tableModels; // модели таблиц
    private final HashMap<String, Class> frameClasses; // классы фреймов, правда кажется нужен только один класс фрейма
    // но ладно
    private final HashMap<String, Component> components; // все объекты 
    private final HashMap<String, Class> tableClasses; // классы таблиц
    private final HashMap<String, Class> labelClasses;

    private final MarkupLoaderPropertiesContainer mlpc = MarkupLoaderPropertiesContainer.getInstance(); // хранилище свойств элементов гуи
    private ArrayList<IUiAcceptor> uiAcList;

    /**
     * возвращает модель таблицы для таблицы, указанной в разметке
     *
     * @param name - имя модели таблицы
     * @return
     */
    public TableModel getTableModel(String name) {
        return tableModels.get(name);
    }

    /**
     * метод проверяет была ли добавлена модель для таблицы
     *
     * @param name - имя модеил таблицы, указывается в разметке
     * @return
     */
    public boolean containsTableModel(String name) {
        return tableModels.containsKey(name);
    }

    public MouseListener getMouseListener(String name) {
        return mouseListeners.get(name);
    }

    public boolean containsMouseListener(String name) {
        return this.mouseListeners.containsKey(name);
    }

    public Class getFrameClass(String name) {
        return frameClasses.get(name);
    }

    public boolean containsFrameClass(String name) {
        return frameClasses.containsKey(name);
    }

    /**
     * добавляет класс для оформления и функциональности надписей
     *
     * @param c - класс
     */
    public void addLabelClass(Class c) {
        labelClasses.put(c.getName(), c);
    }

    /**
     * проверка наличия класса для надписей
     *
     * @param name - имя класса
     * @return
     */
    public boolean containsLabelClass(String name) {
        return labelClasses.containsKey(name);
    }

    public Class getLabelClass(String name) {
        return labelClasses.get(name);
    }

    /**
     * добавление новго класс для нестадартных возможностей или оформления
     *
     * @param c - объект класса. имя класс добавляется автоматически, берется из
     * класса
     */
    public void addDialogClass(Class c) {
        dialogClasses.put(c.getName(), c);
    }

    /**
     * возарщает класс заданный пользователем
     *
     * @param className
     * @return
     */
    public Class getDialogClass(String className) {
        return dialogClasses.get(className);
    }

    /**
     * проверка наличия класса
     *
     * @param name - имя класса
     * @return - есть или нет
     */
    public boolean containsDialogClass(String name) {
        return dialogClasses.containsKey(name);
    }

    /**
     * класс фрейма должен быть конструктор без параметров
     *
     * @param name
     * @param classInstance
     */
    public void addFrameClass(String name, Class classInstance) {
        frameClasses.put(name, classInstance);
    }

    public void addFrameClass(Class classInstance) {
        frameClasses.put(classInstance.getSimpleName(), classInstance);
    }

    public void removeFrameClass(String name) {
        frameClasses.remove(name);
    }

    /**
     * модель таблицы для таблицы обхявленной в разметке обязательно должен быть
     * конструктор без параметров
     *
     * @param name - имя модели таблицы
     * @param tm - модель таблицы
     */
    public void addTableModel(String name, TableModel tm) {
        tableModels.put(name, tm);
    }

    public void removeTableModel(String name) {
        tableModels.remove(name);
    }

    /**
     * класс для объекта объявленного в xml разметке класс должен обязательно
     * иметь конструктор без параметров
     *
     * @param name - имя класса
     * @param classInstance - класс
     */
    public void addDialogClass(String name, Class classInstance) {
        dialogClasses.put(name, classInstance);
    }

    /**
     * метод добавляет слушатель событий мыши в колелкцию имя - имя класса
     * вместе с пакетом
     *
     * @param ml - слушатель событий
     */
    public void addMouseListener(MouseListener ml) {
        mouseListeners.put(ml.getClass().getSimpleName(), ml);
    }

    /**
     * метод добавляет слушатель событий мыши в коллекцию с заданным именем
     *
     * @param name - имя слушателя событий
     * @param ml - слушатель событий
     */
    public void addMouseListener(String name, MouseListener ml) {
        mouseListeners.put(name, ml);
    }

    public void removeMouseListenerInstance(String name) {
        mouseListeners.remove(name);
    }

    public MarkupLoader() {
        mouseListeners = new HashMap<>();
        components = new HashMap<>();
        dialogClasses = new HashMap<>();
        tableModels = new HashMap<>();
        tableClasses = new HashMap<>();
        labelClasses = new HashMap<>();
        frameClasses = new HashMap<>();

        this.uiAcList = new ArrayList<>();
        uiAcList.add(new ButtonAcceptor());
        uiAcList.add(new DialogAcceptor());
        uiAcList.add(new FrameAcceptor());
        uiAcList.add(new LabelAcceptor());
        uiAcList.add(new MenuAcceptor());
        uiAcList.add(new MenuBarAcceptor());
        uiAcList.add(new MenuItemAcceptor());
        uiAcList.add(new TableAcceptor());
        uiAcList.add(new TextAcceptor());
    }
    private Document doc;

    /**
     * запускает инициализацию разметки, загруженной из xml файла путь до xml
     * файла надо подавать в качестве аргумента
     *
     * @param fileName - полный путь до файла разметки
     * @throws SAXException - исключение парсера xml
     * @throws IOException - ошибка считывания файла разметки
     * @throws ParserConfigurationException - ошибка парсера
     * @throws MissingMouseListenerException - ошибка в формате или коде - не
     * указан слушатель событий мыши для объекта
     * @throws Exception - не помню где и как но важно
     */
    public void loadMarkup(String fileName) throws SAXException, IOException, ParserConfigurationException, MissingMouseListenerException, Exception {
        File markupSource = new File(fileName); // загрузка разметки
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        doc = builder.parse(markupSource);
        initMarkup(null, doc);
    }

    /**
     * метод позволяет получить компонент из разметки возвращает общий тип
     * Component по строковому ключу - имени
     *
     * @param name - имя компоненента, который нужно получить, должен быть
     * прописан в разметке - тег name
     * @return - визуальный компонент с именем
     * @throws NoSuchElementException - если данный компонент с заданным именем
     * отсутствует, или имя компонента направильное
     */
    public Component getComponent(String name) throws NoSuchElementException {
        if (components.containsKey(name)) {
            return components.get(name); // если компонент есть в разметке
        } else {
            throw new NoSuchElementException();  // если его нету
        }
    }

    private Container p;

    public void setParent(Container p) {
        this.p = p;
    }

    public Container getParent() {
        return this.p;
    }

    /**
     * метод загрузки визуальных компонентов из файла xml поддерживает внешние
     * классы элементов, слушатели событий, пока работает все что есть, но есть
     * не все что надо нужно добавит другие элементы gui и сделать другие
     * обработчики события, дописать комментарии список доступных Layout можно
     * взять из
     *
     * @class Layouts
     * @param parent - родительский контейнер
     * @param node - узел xmlб содержащий инфорамацию об элементах
     * @throws MissingTableModelException - отсутствует модель для таблицы
     * @throws MissingMouseListenerException - отсутствует слушатель событий для
     * мыши
     * @throws Exception - что то отсутствует, надо выяснить что и заменить
     * исключение
     */
    private void initMarkup(Component parent, Node node) throws
            MissingTableModelException,
            MissingMouseListenerException,
            InvalidMarkupException,
            Exception {

        NodeList currentComponents = node.getChildNodes();
        NamedNodeMap attributes;
        for (int i = 0; i < currentComponents.getLength(); i++) { // проходим по всем layout
            attributes = currentComponents.item(i).getAttributes();

            if (attributes == null) {
                continue; // если атрибутов нет то смысла продолджать нету
            }
            mlpc.tooltip = convertNodeToString(attributes, "tooltip");
            mlpc.modal = convertNodeToString(attributes, "modal");
            mlpc.rows = convertNodeToString(attributes, "rows"); // стобцы?
            mlpc.model = convertNodeToString(attributes, "model"); // модель таблицы
            mlpc.fontStyle = convertNodeToString(attributes, "fontStyle"); // стиль шрифта
            mlpc.fontSize = convertNodeToString(attributes, "fontSize"); // размер
            mlpc.fontName = convertNodeToString(attributes, "fontName"); // название шрифта
            mlpc.focusable = convertNodeToString(attributes, "focusable"); // можно ли наводить и тыкать?
            mlpc.backgroundColor = convertNodeToString(attributes, "backgroundColor"); // цвет заднего фона
            mlpc.className = convertNodeToString(attributes, "class"); // загрузка нужного класса
            mlpc.name = convertNodeToString(attributes, "name"); // имя объекта
            mlpc.label = convertNodeToString(attributes, "label"); // текст, который будет выводиться в объекте

            mlpc.x = convertNodeToString(attributes, "x");
            mlpc.y = convertNodeToString(attributes, "y");
            mlpc.width = convertNodeToString(attributes, "width");
            mlpc.height = convertNodeToString(attributes, "height");

            mlpc.autoEnable = convertNodeToString(attributes, "autoEnable"); // почему то никак не задействовано
            mlpc.layout = convertNodeToString(attributes, "layout"); // декларация слоя
            mlpc.mouseListener = convertNodeToString(attributes, "mouseListener"); // слушатель мыши
            mlpc.nodeName = currentComponents.item(i).getNodeName(); // имя нода
            mlpc.iconUrl = convertNodeToString(attributes, "iconURL"); // юрл картинки для кнопки
            mlpc.transparent = convertNodeToString(attributes, "transparent"); // прозрачность

            switch (mlpc.nodeName) {

                case "menuBar":

                    initMarkup(new MenuBarAcceptor().acceptUI(mlpc, parent, components, this), currentComponents.item(i));

                    break;

                case "menuItem":
                    new MenuItemAcceptor().acceptUI(mlpc, parent, components, this);
                    break;

                case "menu":
                    initMarkup(new MenuAcceptor().acceptUI(mlpc, parent, components, this), currentComponents.item(i));

                    break;

                case "label":
                    new LabelAcceptor().acceptUI(mlpc, parent, components, this);
                    break;

                case "text":
                    new TextAcceptor().acceptUI(mlpc, parent, components, this);

                    break;

                case "frame":
                    // тут можно создать родительское окно
                    // не до конца еще знаю gui api java поэтому считаю что можно создать несколько фреймов

                    initMarkup((JFrame) new FrameAcceptor().acceptUI(mlpc, parent, components, this), currentComponents.item(i)); // элемент может содрежать дочерние элементы, поэтому запускаем рекурсивную обработку "детей"

                    break;
                case "button":
                    new ButtonAcceptor().acceptUI(mlpc, parent, components, this);

                    break;
                case "dialog":

                    initMarkup((JDialog) new DialogAcceptor().acceptUI(mlpc, parent, components, this), currentComponents.item(i));

                    break;
                case "table": // обработка таблиц
                    new TableAcceptor().acceptUI(mlpc, parent, components, this);

                    break;
                default:
            }
            if (parent != null) {
                parent.repaint();
            }
        }
    }

    private String convertNodeToString(NamedNodeMap attributes, String pattern) {
        if (attributes.getNamedItem(pattern) != null) { // получаем имя класса
            return attributes.getNamedItem(pattern).getTextContent();
        } else if (pattern.equals("x") || pattern.equals("y") || pattern.equals("width") || pattern.equals("height")) {
            return "0";
        } else {
            return "";
        }
    }

    public static enum Layouts {

        BorderLayout; // TODO дописать остальные
    }
}
