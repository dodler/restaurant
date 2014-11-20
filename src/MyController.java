import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dodler
 * @param <Model> - класс модели, где хранятся данныеs
 */
public class MyController<Model> implements IMyController {

    public static final String GET_ARRAY_METHOD = "GET_ARRAY_METHOD";
    public static final String ADD_DISH_LIST = "ADD_DISH_LIST";

    public static enum DishCategory {

        HOT_DISH, SNACK, SALAD, BEVERAGE, DESSERT, ROLLS, SUSHI
    }

    Model model;

    public MyController() throws InstantiationException, IllegalAccessException {
        this.model = (Model) model.getClass().newInstance();
    }

    public MyController(Model model) {
        this.model = model;
    }

    /**
     * метод поиска блюда по имени
     *
     * @param name - имя блюда
     * @throws Exception - если блюда нет
     * @return - объект блюда
     */
    @Override
    public Dish findDish(String name) throws Exception {
        ArrayList<Dish> dishList = (ArrayList<Dish>) model.getClass().getDeclaredMethod(GET_ARRAY_METHOD).invoke(null);
        for (Dish d : dishList) {
            if (d.getName().equals(name)) {
                return d;
            }
        }
        throw new Exception("Еды нет в списке");
    }

    /**
     * метод поиска блюда по ид
     *
     * @param id - ид блюда
     * @throws Exception - если еды с таким ид нет в списке
     * @return - объект блюда
     */
    @Override
    public Object findDish(int id) throws Exception {
        ArrayList<Dish> dishList = (ArrayList<Dish>) model.getClass().getDeclaredMethod(GET_ARRAY_METHOD).invoke(null);
        for (Dish d : dishList) {
            if (d.getId() == id) {
                return d;
            }
        }

        throw new Exception("Еда не найдена");
    }

    /**
     * @param category - нужная категория
     * @return - список блюд соответствующей категории
     */
    @Override
    public ArrayList<Dish> findDishListByCategory(MyController.DishCategory category) {
        ArrayList<Dish> dishList = new ArrayList<>();
        try {
            dishList = (ArrayList<Dish>) model.getClass().getDeclaredMethod(GET_ARRAY_METHOD).invoke(null);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Dish> result = new ArrayList<>();
        for (Dish d : dishList) {
            if (d.getCategory().equals(category)) {
                result.add(d);
            }
        }
        return result;
    }

    /**
     * метод добавления одного объекта еды в коллекцию модели
     *
     * @param dish - объект еды
     * @throws NoSuchMethodException - уже завистт от модели
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Override
    public void addDish(Dish dish) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<Dish> menu = (ArrayList<Dish>) model.getClass().getDeclaredMethod(GET_ARRAY_METHOD).invoke(null);
        ArrayList<Dish> result = new ArrayList<>();
        result.add(dish);
        menu.addAll(result);
        model.getClass().getDeclaredMethod(ADD_DISH_LIST, ArrayList.class).invoke(menu);
    }

    /**
     * добавляет сразу список объектов еды
     *
     * @param list - сам объект списка
     * @throws NoSuchMethodException - зависит от модели
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    @Override
    public void addDishList(ArrayList<Dish> list) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ArrayList<Dish> menu = (ArrayList<Dish>) model.getClass().getDeclaredMethod(GET_ARRAY_METHOD).invoke(null);
        menu.addAll(list);
        model.getClass().getDeclaredMethod(ADD_DISH_LIST, ArrayList.class).invoke(menu);
    }

    @Override
    public void removeDish(String name) {
        ArrayList<Dish> dishList = new ArrayList<>();
        try {
            dishList = (ArrayList<Dish>) model.getClass().getDeclaredMethod(GET_ARRAY_METHOD).invoke(null);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Dish d : dishList) {
            if (d.getName().equals(name)) {
                dishList.remove(d);
            }
        }
        try {
            model.getClass().getDeclaredMethod(ADD_DISH_LIST, ArrayList.class).invoke(dishList);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeDish(Dish dish) {
        ArrayList<Dish> dishList = new ArrayList<>();
        try {
            dishList = (ArrayList<Dish>) model.getClass().getDeclaredMethod(GET_ARRAY_METHOD).invoke(null);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        dishList.remove(dish);
        try {
            model.getClass().getDeclaredMethod(ADD_DISH_LIST, ArrayList.class).invoke(dishList);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeDish(int id) {
        ArrayList<Dish> dishList = new ArrayList<>();
        try {
            dishList = (ArrayList<Dish>) model.getClass().getDeclaredMethod(GET_ARRAY_METHOD).invoke(null);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Dish d : dishList) {
            if (d.getId() == id) {
                dishList.remove(d);
            }
        }
        try {
            model.getClass().getDeclaredMethod(ADD_DISH_LIST, ArrayList.class).invoke(dishList);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeDish(MyController.DishCategory category) {
        ArrayList<Dish> dishList = new ArrayList<>();
        try {
            dishList = (ArrayList<Dish>) model.getClass().getDeclaredMethod(GET_ARRAY_METHOD).invoke(null);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Dish d : dishList) {
            if (d.getCategory().equals(category)) {
                dishList.remove(d);
            }
        }
        try {
            model.getClass().getDeclaredMethod(ADD_DISH_LIST, ArrayList.class).invoke(dishList);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * метод удаления списка еды из модели
     * @param list - список для удаления
     * @throws Exception - если во время удаления произошла ошибка
     */
    @Override
    public void removeDish(ArrayList<Dish> list) throws Exception {
        ArrayList<Dish> dishList = new ArrayList<>();
        try {
            dishList = (ArrayList<Dish>) model.getClass().getDeclaredMethod(GET_ARRAY_METHOD).invoke(null);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!dishList.removeAll(list)){
            throw new Exception("Удаление не удалось");
        }
        try {
            model.getClass().getDeclaredMethod(ADD_DISH_LIST, ArrayList.class).invoke(dishList);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(MyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
