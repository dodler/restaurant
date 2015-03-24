/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import controller.IModelController;
import controller.LinearController;
import haulmaunt.lyan.ui.markupexception.MissingMouseListenerException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultRowSorter;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.parsers.ParserConfigurationException;
import model.CategoryImpl;
import model.Dish;
import model.ICategory;
import model.IModel;
import model.NetModelImpl;
import model.compare.IdComparator;
import model.compare.NameComparator;
import model.compare.PriceComparator;
import ui.MarkupLoader;
import ui.NoSuchElementException;
import view.IView;
import view.SwingView;

/**
 * AppController - синглтон.
 *
 * @author Артем
 */
public class AppController {

    private IView view;
    private IModel model;
    private IModelController controller;
    static AppController instance;
    private Logger logger; // объект для логов

    public IView getView() {
        return this.view;
    }

    public IModel getModel() {
        return this.model;
    }

    {
        logger = Logger.getLogger(AppController.class.getName()); // инициализация логера
    }

    /**
     * в конструкторе будет загрузка конфига из файла загрузка базы данных
     * инициализация контроллера и вьюхи
     */
    private AppController() {
        Config config;
        try {
            config = new Config("config.txt");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
            return;
        }
        try {
            logger.addHandler(new FileHandler(config.get("logger"))); // настраиваем запись лога в файл
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        try {
            model = new NetModelImpl(config.get("address"), Integer.parseInt(config.get("port")));
            ((NetModelImpl) model).load();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        controller = new LinearController(model);
        try {
            view = new SwingView(model);
            final MarkupLoader loader = ((SwingView) view).getLoader();
            final IModel mdl = model;// wtf плохой костыль
            final IModelController cntrl = controller;

            //((SwingView) view).addInitFoo(() -> {
            ((SwingView) view).getLoader().addMouseListener("onConnectClick", new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    try {
                        ((NetModelImpl) model).connect(config.get("address"), Integer.parseInt(config.get("port")));
                    } catch (IOException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                @Override
                public void mousePressed(MouseEvent me) {

                }

                @Override
                public void mouseReleased(MouseEvent me) {

                }

                @Override
                public void mouseEntered(MouseEvent me) {

                }

                @Override
                public void mouseExited(MouseEvent me) {

                }

            });

            ((SwingView) view).getLoader().addMouseListener("onDelClick", new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "delete item");
                    try {
                        int i = ((JTable) loader.getComponent("mainTable")).getSelectedRow(),
                                id = Integer.valueOf((String) ((JTable) loader.getComponent("mainTable")).getValueAt(i, 0)), j;
                        for (ICategory c : model.getCategoryList()) {
                            if (c.getId() == id) {
                                model.getCategoryList().remove(c);
                                break;
                            }
                            for (Dish d : c.getDishList()) {
                                if (d.getId() == id) {
                                    c.getDishList().remove(d);
                                    break;
                                }
                            }
                        }
                        ((SwingView) view).setModel(model);

                    } catch (NoSuchElementException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                @Override
                public void mousePressed(MouseEvent me) {

                }

                @Override
                public void mouseReleased(MouseEvent me) {

                }

                @Override
                public void mouseEntered(MouseEvent me) {

                }

                @Override
                public void mouseExited(MouseEvent me) {

                }

            });

            ((SwingView) view).getLoader().addMouseListener("onAccept", new MouseListener() {
                private String price = "", cat = "", name = "";

                @Override
                public void mouseClicked(MouseEvent me) {
                    DefaultTableModel m = null;

                    try {
                        m = (DefaultTableModel) ((JTable) ((SwingView) view).getLoader().getComponent("mainTable")).getModel();
                        price = ((JTextField) ((SwingView) view).getLoader().getComponent("inputPrice")).getText();
                        name = ((JTextField) ((SwingView) view).getLoader().getComponent("inputName")).getText();
                        cat = ((JTextField) ((SwingView) view).getLoader().getComponent("inputCat")).getText();
                    } catch (NoSuchElementException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (!price.equals("")) {
                        for (ICategory c : model.getCategoryList()) {
                            if (Integer.parseInt(cat) == c.getId()) {
                                Dish d1 = new Dish(name, Double.parseDouble(price), ((NetModelImpl) mdl).getMaxDishId());
                                c.addDish(d1);
                            }

                        }
                    } else {
                        ICategory c1 = new CategoryImpl(name, ((NetModelImpl) model).getMaxCatId());
                        ((NetModelImpl) model).add(c1);
                    }
                    try {
                        ((SwingView) view).setModel(model);
                        ((JDialog) ((SwingView) view).getLoader().getComponent("addItem")).setVisible(false);
                    } catch (NoSuchElementException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                @Override
                public void mousePressed(MouseEvent me) {

                }

                @Override
                public void mouseReleased(MouseEvent me) {

                }

                @Override
                public void mouseEntered(MouseEvent me) {

                }

                @Override
                public void mouseExited(MouseEvent me) {

                }

            });
            ((SwingView) view).getLoader().addMouseListener("onDecline", new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    try {
                        ((JTextField) ((SwingView) view).getLoader().getComponent("inputName")).setText("");
                        ((JTextField) ((SwingView) view).getLoader().getComponent("inputName")).setText("");
                        ((JTextField) ((SwingView) view).getLoader().getComponent("inputName")).setText("");
                        ((SwingView) view).getLoader().getComponent("addItem").setVisible(false);
                    } catch (NoSuchElementException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void mousePressed(MouseEvent me) {

                }

                @Override
                public void mouseReleased(MouseEvent me) {

                }

                @Override
                public void mouseEntered(MouseEvent me) {

                }

                @Override
                public void mouseExited(MouseEvent me) {

                }

            });

            ((SwingView) view).getLoader().addMouseListener("onFindClick", new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    double lowPrice = 0, highPrice = 0;
                    String pattern = "", cat = "";
                    try {
                        if (!((JTextField) ((SwingView) view).getLoader().getComponent("findName")).getText().equals("")) {
                            pattern = ((JTextField) ((SwingView) view).getLoader().getComponent("findName")).getText();
                        }
                        if (!((JTextField) ((SwingView) view).getLoader().getComponent("findCat")).getText().equals("")) {
                            cat = ((JTextField) ((SwingView) view).getLoader().getComponent("findCat")).getText();
                        }

                        if (!((JTextField) ((SwingView) view).getLoader().getComponent("findPriceMore")).getText().equals("")) {
                            lowPrice = Double.parseDouble(((JTextField) ((SwingView) view).getLoader().getComponent("findPriceMore")).getText());
                        }

                        if (!((JTextField) ((SwingView) view).getLoader().getComponent("findPriceLess")).getText().equals("")) {
                            highPrice = Double.parseDouble(((JTextField) ((SwingView) view).getLoader().getComponent("findPriceLess")).getText());
                        }

                    } catch (NoSuchElementException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ArrayList<Dish> result = ((LinearController) cntrl).find(lowPrice, highPrice, pattern, cat);
                    DefaultTableModel mdl = null;
                    try {
                        mdl = (DefaultTableModel) ((JTable) ((SwingView) view).getLoader().getComponent("mainTable")).getModel();
                    } catch (NoSuchElementException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    while (mdl.getRowCount() > 0) {
                        mdl.removeRow(0);
                    }
                    for (Dish d : result) {
                        mdl.addRow(new String[]{String.valueOf(d.getId()), d.getName(), String.valueOf(d.getPrice())});

                    }
                    try {
                        ((JTable) ((SwingView) view).getLoader().getComponent("mainTable")).setModel(mdl);
                    } catch (NoSuchElementException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                @Override
                public void mousePressed(MouseEvent me) {

                }

                @Override
                public void mouseReleased(MouseEvent me) {

                }

                @Override
                public void mouseEntered(MouseEvent me) {

                }

                @Override
                public void mouseExited(MouseEvent me) {

                }

            });

            ((SwingView) view).getLoader().addMouseListener("onAddClick", new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    try {
                        JDialog d = (JDialog) loader.getComponent("addItem");
                        ((JTextField) loader.getComponent("inputName")).setText("");
                        ((JTextField) loader.getComponent("inputCat")).setText("");
                        ((JTextField) loader.getComponent("inputPrice")).setText("");

                        d.setVisible(true);
                    } catch (NoSuchElementException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                @Override
                public void mousePressed(MouseEvent me) {

                }

                @Override
                public void mouseReleased(MouseEvent me) {

                }

                @Override
                public void mouseEntered(MouseEvent me) {

                }

                @Override
                public void mouseExited(MouseEvent me) {

                }
            });

            ((SwingView) view).getLoader().addMouseListener("onSortClick", new MouseListener() {
                /**
                 * теперь это будет кнопка обновления
                 *
                 * @param me
                 */
                @Override
                public void mouseClicked(MouseEvent me) {
                    try {
                        ((NetModelImpl) mdl).load();
                        ((SwingView) view).updateTable();

                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

                @Override
                public void mousePressed(MouseEvent me) {

                }

                @Override
                public void mouseReleased(MouseEvent me) {

                }

                @Override
                public void mouseEntered(MouseEvent me) {

                }

                @Override
                public void mouseExited(MouseEvent me) {

                }

            });

            ((SwingView) view).getLoader().addMouseListener("onSave", new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent me) {
                    Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, "onSave");
                    try {
                        ((NetModelImpl) mdl).save();
                    } catch (IOException ex) {
                        Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                @Override
                public void mousePressed(MouseEvent me) {

                }

                @Override
                public void mouseReleased(MouseEvent me) {

                }

                @Override
                public void mouseEntered(MouseEvent me) {

                }

                @Override
                public void mouseExited(MouseEvent me) {

                }
            });
            ((SwingView) view).getLoader().addTableModel("CustomModel", new CustomTableModel(model, "Код", "Имя", "Цена"));
            //});

            ((SwingView) view).init(config.get("xml-markup"));
            TableRowSorter<CustomTableModel> sorter;
            sorter = new TableRowSorter<>((CustomTableModel) ((JTable) ((SwingView) view).getLoader().getComponent("mainTable")).getModel());
            sorter.setComparator(0, new IdComparator());
            sorter.setComparator(1, new NameComparator());
            sorter.setComparator(2, new PriceComparator());
            ((JTable) ((SwingView) view).getLoader().getComponent("mainTable")).setRowSorter(sorter);

            JFrame frame;
            frame = (JFrame) loader.getComponent("main");
            frame.addWindowListener(new WindowListener() {

                @Override
                public void windowOpened(WindowEvent we) {

                }

                @Override
                public void windowClosing(WindowEvent we) {
                    if (!((NetModelImpl) model).isFixed()) {
                        int save = JOptionPane.showConfirmDialog(
                                frame,
                                "Сохранить изменения в таблице?",
                                "Есть изменения",
                                JOptionPane.YES_NO_OPTION);
                        if (save == 0) {
                            try {
                                ((NetModelImpl) model).save();
                            } catch (IOException ex) {
                                Logger.getLogger(AppController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    System.exit(0);
                }

                @Override
                public void windowClosed(WindowEvent we) {

                }

                @Override
                public void windowIconified(WindowEvent we) {

                }

                @Override
                public void windowDeiconified(WindowEvent we) {

                }

                @Override
                public void windowActivated(WindowEvent we) {

                }

                @Override
                public void windowDeactivated(WindowEvent we) {

                }

            });

        } catch (IOException ioe) {
            logger.log(Level.SEVERE, null, ioe);
        } catch (ParserConfigurationException pce) {
            logger.log(Level.SEVERE, null, pce);
        } catch (MissingMouseListenerException mmle) {
            logger.log(Level.SEVERE, null, mmle);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    static {
        instance = new AppController();
    }

    /**
     * метод предоставляет доступ к единственному экземпляру AppController
     *
     * @return - экземпляр AppController
     */
    public static AppController getAppControllerInstance() {
        return instance;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        AppController.getAppControllerInstance().run();
    }

    /**
     * метод запускает справочную систему
     */
    public void run() {
    }
}
