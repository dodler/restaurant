package model;

import java.io.Serializable;
import java.util.UUID;

public class Dish implements Serializable{
    private String name;
    private double cost;
    private UUID ID;

/* КОНСТРУКТОРЫ */
    // Конструктор, создающий блюдо с указанным названием и ценой.
    public Dish(String name, double cost) throws Exception{
        checkCost(cost);
        this.name = name;
        this.cost = cost;
        this.ID = UUID.randomUUID();
    }

    // Конструктор, создающий блюдо с указанным названием и стандартной ценой 100 руб.
    public Dish(String name) throws Exception{
        this(name, 0);
    }

/* МЕТОДЫ */
    // Метод получения названия блюда.
    public String getName(){
        return this.name;
    }

    // Метод изменения названия блюда.
    public void setName(String name){
        this.name = name;
    }

    // Метод получения цены блюда.
    public double getCost(){
        return this.cost;
    }

    // Метод изменения цены блюда.
    public void setCost(double cost) throws Exception{
        checkCost(cost);
        this.cost = cost;
    }

    // Метод получения ID блюда.
    public UUID getID(){
        return this.ID;
    }

/* ПРИВАТНЫЕ МЕТОДЫ */
    // Метод проверки правильности цены блюда.
    private void checkCost(double cost) throws Exception{
           if (cost < 0) throw new Exception();
    }

}
