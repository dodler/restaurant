/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.treecommand.CategoryFoundEvent;
import controller.treecommand.CategoryParentFinder;
import controller.treecommand.CategoryTreeFinder;
import controller.treecommand.ConsoleTreeWriter;
import controller.treecommand.ConsoleTreeWriterPriced;
import controller.treecommand.DishCategoryFinder;
import controller.treecommand.DishFoundEvent;
import controller.treecommand.TreeCommand;
import controller.treecommand.DishTreeFinder;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Dish;
import model.ICategory;
import view.IConsoleView;

/**
 *
 * @author Артем
 */
public class ModelControllerImpl implements IModelController {

    private ICategory rootCategory;

    private IConsoleView view;

    /**
     * конструктор контроллера создает контроллер с заданной рутовой категорией
     * соответственно до создания контроллера нужно создать модель
     *
     * @param rootCategory - ссылкана рутовую категорию
     */
    public ModelControllerImpl(ICategory rootCategory) {
        this.rootCategory = rootCategory;
    }

    public ModelControllerImpl(ICategory rootCategory, IConsoleView view) {
        this(rootCategory);
        this.view = view;
    }

    public void setView(IConsoleView view) {
        this.view = view;
    }

    /**
     * метод обхода рекурсивного обхода дерева
     *
     * @param command - команда, которую нужно применить к текущему элементу
     * @param category - категория с которой нужно начать обход
     */
    private void treeBypass(TreeCommand command, ICategory category) {
        if (command != null && category != null) {
            command.handle(category);
        }
        if (category.getSubCategoryList().size() > 0) {
            for (ICategory c : category.getSubCategoryList()) {
                treeBypass(command, c);
            }
        }
    }

    @Override
    public void showCategoryDishTreePriced() {
        treeBypass(new ConsoleTreeWriterPriced(view), rootCategory);
    }

    @Override
    public void showCategoryDishTree() {
        this.treeBypass(new ConsoleTreeWriter(view), rootCategory);
        // комментарий
    }

    @Override
    public void showDishListPriced(String category) {
        /**
         * сделал наблюдатель для поиска при нахождении просто запускает событие
         */
        CategoryFoundEvent cfe = new CategoryFoundEvent() {

            @Override
            public void onCategoryFound() {
                for (Dish d : cat.getDishList()) {
                    view.show(cat);// вывод найденных блюд в категории 
                }
            }

        };

        treeBypass(new CategoryTreeFinder(category, cfe), rootCategory); // Запуск поиска по дереву категорий категории 
        // с заданным именем

    }

    private void delete(ICategory category, String name) {
        for (Dish d : category.getDishList()) {
            if (d.getName().equals(name)) {
                category.getDishList().remove(d);
            }
        }
    }

    @Override
    public void deleteDishCategory(String category) {
        CategoryFoundEvent cfe = new CategoryFoundEvent() {

            @Override
            public void onCategoryFound() {
                cat.getDishList().clear(); // удаление блюд из найденной категории
            }

        };
        treeBypass(new CategoryTreeFinder(category, cfe), rootCategory); // запуск поиска плюс удаление
    }

    @Override
    public void deleteCategory(final String category) {
        CategoryFoundEvent cfe = new CategoryFoundEvent() {

            @Override
            public void onCategoryFound() {
                for (ICategory c : cat.getSubCategoryList()) {
                    if (cat.getName().equals(category)) {
                        cat.getSubCategoryList().remove(c); // удаление найенной категории
                    }
                }
            }
        };
        treeBypass(new CategoryParentFinder(category, cfe), rootCategory);
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
        treeBypass(new CategoryTreeFinder(category, cfe), rootCategory);
    }

    @Override
    public void editCategoryName(final String oldName, final String newName) {
        CategoryFoundEvent cfe = new CategoryFoundEvent() {

            @Override
            public void onCategoryFound() {
                cat.setName(newName);
            }

        };
        treeBypass(new CategoryTreeFinder(oldName, cfe), rootCategory);
    }

    @Override
    public void editDishPrice(String name, final double newPrice) {
        DishFoundEvent dfe = new DishFoundEvent() {

            @Override
            public void onDishFound() {
                try {
                    d.setCost(newPrice);
                } catch (Exception ex) {
                    Logger.getLogger(ModelControllerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        };
        treeBypass(new DishTreeFinder(name, dfe), rootCategory);
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
        treeBypass(new DishTreeFinder(oldName, dfe), rootCategory);
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
                while(dIt.hasNext()){
                    d = dIt.next();
                    if (d.getName().equals(name)) {
                        dIt.remove();
                    }
                }
            }

        };
        treeBypass(new DishCategoryFinder(name, cfe), rootCategory);
    }

    @Override
    public void showDishList(String category){
        /**
         * сделал наблюдатель для поиска при нахождении просто запускает событие
         */
        CategoryFoundEvent cfe = new CategoryFoundEvent() {

            @Override
            public void onCategoryFound() {
                for (Dish d : cat.getDishList()) {
                    view.show("Блюдо" + d.getName());// вывод найденных блюд в категории 
                }
            }

        };

        treeBypass(new CategoryTreeFinder(category, cfe), rootCategory); // Запуск поиска по дереву категорий категории 
        // с заданным именем
    }
    
    public void findName(String name) {
        DishFoundEvent dfe = new DishFoundEvent() {

            @Override
            public void onDishFound() {
                view.show("Цена блюда " + d.getName() + " составляет " + d.getCost() + ". Идентификатор: " + d.getId());
            }

        };
        treeBypass(new DishTreeFinder(name, dfe), rootCategory);
    }

    @Override
    public void findPriceMore(final double price) {
        DishFoundEvent dfe = new DishFoundEvent() {

            @Override
            public void onDishFound() {
                if (d.getCost() > price) {
                    view.show(d);
                }
            }

        };
        treeBypass(new DishTreeFinder("", dfe), rootCategory);
    }

    @Override
    public void findPriceLess(final double price) {
        DishFoundEvent dfe = new DishFoundEvent() {

            @Override
            public void onDishFound() {
                if (d.getCost() < price) {
                    view.show(d);
                }
            }

        };
        treeBypass(new DishTreeFinder("", dfe), rootCategory);
    }

    @Override
    public void findPriceEqual(final double price) {
        DishFoundEvent dfe = new DishFoundEvent() {

            @Override
            public void onDishFound() {
                if (d.getCost() == price) {
                    view.show(d);
                }
            }

        };
        treeBypass(new DishTreeFinder("", dfe), rootCategory);
    }

    @Override
    public void findPriceInterval(final double left, final double right) {
        DishFoundEvent dfe = new DishFoundEvent() {

            @Override
            public void onDishFound() {
                if (d.getCost() > left && d.getCost() < right) {
                    view.show(d);
                }
            }

        };
        treeBypass(new DishTreeFinder("", dfe), rootCategory);
    }

    @Override
    public void findPattern(String pattern) {
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
                    view.show(d);
                }

            }

        };
        treeBypass(new DishTreeFinder("", dfe), rootCategory);
    }

}
