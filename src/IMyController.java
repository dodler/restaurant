
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dodler
 */
public interface IMyController {

    /**
     * метод добавления одного объекта еды в коллекцию модели
     * @param dish - объект еды
     * @throws NoSuchMethodException - уже завистт от модели
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    void addDish(Dish dish) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    /**
     * добавляет сразу список объектов еды
     * @param list - сам объект списка
     * @throws NoSuchMethodException - зависит от модели
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    void addDishList(ArrayList<Dish> list) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;

    /**
     * метод поиска блюда по имени
     * @param name - имя блюда
     * @throws Exception - если блюда нет
     * @return  - объект блюда
     */
    Dish findDish(String name) throws Exception;

    /**
     * метод поиска блюда по ид
     * @param id - ид блюда
     * @throws Exception - если еды с таким ид нет в списке
     * @return - объект блюда
     */
    Object findDish(int id) throws Exception;

    /**
     * @param category - нужная категория
     * @return - список блюд соответствующей категории
     */
    ArrayList<Dish> findDishListByCategory(MyController.DishCategory category);

    void removeDish(String name);

    void removeDish(Dish dish);

    void removeDish(int id);

    void removeDish(MyController.DishCategory category);

    void removeDish(ArrayList<Dish> list)throws Exception;
    
}
