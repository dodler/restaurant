/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.treecommand.CategoryFoundEvent;
import controller.treecommand.CategoryParentFinder;
import controller.treecommand.CategoryTreeFinder;
import controller.treecommand.DishCategoryFinder;
import controller.treecommand.DishFoundEvent;
import controller.treecommand.DishTreeFinder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.CategoryImpl;
import model.Dish;
import model.ICategory;
import model.IModel;
import view.IView;

/**
 *
 * @author Артем
 */
public class ModelControllerImpl implements IModelController {

    private ICategory rootCategory;
    private IView view;
    private IModel model;
    
    private final ArrayList<Dish> list; // список блюд к показу
    
    // TODO переопределить методы показа в слушателях событий
    // ибо там просто вывод тсроки
    // надо сделать вывод табличных данных
    // может стоит создать отдельный класс

    /**
     * конструктор контроллера создает контроллер с заданной рутовой категорией
     * соответственно до создания контроллера нужно создать модель
     *
     * @param rootCategory - ссылкана рутовую категорию
     */
    public ModelControllerImpl(ICategory rootCategory) {
        this.rootCategory = rootCategory;
        list = new ArrayList<>();
    }


    public ModelControllerImpl(IView view, IModel model) {
        this(model.getRootCategory());
        this.view = view;
        this.model = model;
    }

    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void deleteDishCategory(String category) {
        CategoryFoundEvent cfe = new CategoryFoundEvent() {
            @Override
            public void onCategoryFound() {
                cat.getDishList().clear(); // удаление блюд из найденной категории
            }
        };
        model.treeBypass(new CategoryTreeFinder(category, cfe), rootCategory); // запуск поиска плюс удаление
    }

    @Override
    public void deleteCategory(final String category) {
        if (category.equals(rootCategory.getName())){
            view.show("Нельзя удалять меню");
            return;
        }
        CategoryFoundEvent cfe = new CategoryFoundEvent() {
            @Override
            public void onCategoryFound() {
                for (ICategory c : cat.getSubCategoryList()) {
                    if (c.getName().equals(category)) {
                        cat.getSubCategoryList().remove(c); // удаление найенной категории
                        return;
                    }
                }
            }
        };
        model.treeBypass(new CategoryParentFinder(category, cfe), rootCategory);
    }

    @Override
    public void addDish(String name, double price) {
        try {
            rootCategory.addDish(new Dish(name, price));
        } catch (Exception ex) {
            Logger.getLogger(ModelControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void addDish(String category, final String name, final double price) {
        CategoryFoundEvent cfe = new CategoryFoundEvent() {
            @Override
            public void onCategoryFound() {
                try {
                    cat.addDish(new Dish(name, price));
                } catch (Exception ex) {
                    Logger.getLogger(ModelControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        model.treeBypass(new CategoryTreeFinder(category, cfe), rootCategory);
    }

    @Override
    public void editCategoryName(final String oldName, final String newName) {
        CategoryFoundEvent cfe = new CategoryFoundEvent() {
            @Override
            public void onCategoryFound() {
                cat.setName(newName);
            }
        };
        model.treeBypass(new CategoryTreeFinder(oldName, cfe), rootCategory);
    }

    @Override
    public void editDishPrice(String name, final double newPrice) {
        DishFoundEvent dfe = new DishFoundEvent() {
            @Override
            public void onDishFound() {
                try {
                    d.setPrice(newPrice);
                } catch (Exception ex) {
                    Logger.getLogger(ModelControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        model.treeBypass(new DishTreeFinder(name, dfe), rootCategory);
    }

    @Override
    public void editDishName(String oldName, final String newName) {
        DishFoundEvent dfe = new DishFoundEvent() {
            @Override
            public void onDishFound() {
                try {
                    d.setName(newName);
                } catch (Exception ex) {
                    Logger.getLogger(ModelControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        model.treeBypass(new DishTreeFinder(oldName, dfe), rootCategory);
    }

    @Override
    public void editDishCategory(String oldCategory, String name, String newCategory) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteDish(final String name) {
        CategoryFoundEvent cfe = new CategoryFoundEvent() {
            @Override
            public void onCategoryFound() {
                Iterator<Dish> dIt = cat.getDishList().iterator();
                Dish d;
                while (dIt.hasNext()) {
                    d = dIt.next();
                    if (d.getName().equals(name)) {
                        dIt.remove();
                    }
                }
            }
        };
        model.treeBypass(new DishCategoryFinder(name, cfe), rootCategory);
    }

    /**
     * поиск блюда по имени
     *
     * @param name
     */
    public void findName(String name) {
        DishFoundEvent dfe = new DishFoundEvent() {// обработчик нахождения блюда
            @Override
            public void onDishFound() {
                list.add(d);
                view.showDish(list);
                list.clear();
            }
        };
        model.treeBypass(new DishTreeFinder(name, dfe), rootCategory);
    }

    @Override
    public void findPriceMore(final double price) {
        DishFoundEvent dfe = new DishFoundEvent() {
            @Override
            public void onDishFound() {
                if (d.getPrice() > price) {
                    //view.show(d);
                }
            }
        };
        model.treeBypass(new DishTreeFinder("", dfe), rootCategory);
    }

    @Override
    public void findPriceLess(final double price) {
        DishFoundEvent dfe = new DishFoundEvent() {
            @Override
            public void onDishFound() {
                if (d.getPrice() < price) {
                    //view.show(d);
                }
            }
        };
        model.treeBypass(new DishTreeFinder("", dfe), rootCategory);
    }

    @Override
    public void findPriceEqual(final double price) {
        DishFoundEvent dfe = new DishFoundEvent() {
            @Override
            public void onDishFound() {
                if (d.getPrice() == price) {
                    //view.show(d);
                }
            }
        };
        model.treeBypass(new DishTreeFinder("", dfe), rootCategory);
    }

    @Override
    public void findPriceInterval(final double left, final double right) {
        DishFoundEvent dfe = new DishFoundEvent() {
            @Override
            public void onDishFound() {
                if (d.getPrice() > left && d.getPrice() < right) {
                    //view.show(d);
                }
            }
        };
        model.treeBypass(new DishTreeFinder("", dfe), rootCategory);
    }

    @Override
    public void findPattern(String pattern) {
        // TODO поиск работает некорректно - рассмотреть случаи типа
        // find n* - чтобы слова где n входит с разных сторон от * в одну выдачу не входили
        final String[] words = pattern.split("\\*"); // получили слова разделенные друг от друга 
        DishFoundEvent dfe = new DishFoundEvent() {
            @Override
            public void onDishFound() {
                final String name = d.getName();
                int maxI = -1, found = 0, curI = 0; // maxI - самое дальнее вхождение, curI - текущее вхождение
                // found - текущее число совпадений
                for (String word : words) {
                    if ((curI = name.indexOf(word)) > maxI) {
                        maxI = curI;
                        found++;
                    } else {
                        return;
                    }
                }
                if (found == words.length) {
                    //view.show(d);
                }

            }
        };
        model.treeBypass(new DishTreeFinder("", dfe), rootCategory);
    }

    @Override
    public void addCategory(String category, final String name) {
        CategoryFoundEvent cfe = new CategoryFoundEvent(){

            @Override
            public void onCategoryFound() {
                this.cat.addCategory(new CategoryImpl(name));
            }
            
        };
        model.treeBypass(new CategoryTreeFinder(category, cfe), rootCategory);
    }
    
    @Override
    public void addCategory(final String name){
        rootCategory.addCategory(new CategoryImpl(name));
    }
}
