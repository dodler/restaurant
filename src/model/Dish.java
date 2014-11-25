package model;

import java.util.Calendar;
import java.util.Objects;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
* убрать поле категории
 */
/**
 * объекта одной еды контейнер
 *
 * @author dodler
 */
public class Dish {

    private int id; // уникальный ид
    private String name; // имя. желательно тоже уникальное
    private double price; // стоимость еды

    /**
     * пустой конструктор делать не буду не дело пустую еду есть
     */
    public Dish() {
        throw new UnsupportedOperationException("Пустого блюда не существует. Попробуйте придумать, тогда можете убрать исключение");
    }

    /**
     * конструктор, который создает готовую еду со всеми параметрами
     * ид выставляет автоматически - хешкод времени
     * @param name - имя еды, желательно тоже уникальное
     */
    public Dish(final String name) {
        this.id = Objects.hashCode(Calendar.getInstance());
        this.name = name;
    }

    /**
     * конструктор, который создает готовую еду со всеми параметрами
     *
     * @param id - уникальный ид, задает ид еды
     * @param name - имя еды, желательно тоже уникальное
     * @param price - стоимость еды
     */
    public Dish(final int id,
            final String name,
            final int price
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    /**
     * возвращает ид
     *
     * @return - ид
     */
    public int getId() {
        return this.id;
    }

    /**
     * возвращает имя
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * возвращает цену
     * @throws Exception - если цену сразу не задали
     * @return
     */
    public double getPrice()  throws Exception{
        if (price <= 0) {
            throw new Exception("Цена не задана, приносим извинения");
        } else {
            return this.price;
        }
    }
    
    /**
     * метод задания исключения
     * @param price - цена блюда
     * @throws Exception - если цена задана
     */
    public void setPrice(double price) throws Exception{
        if (price <= 0){
            this.price = price;
        }else{
            throw new Exception("Цена уже задана.");
        }
    }

}
