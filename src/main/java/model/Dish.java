package model;

import java.io.Serializable;
import java.util.UUID;

public class Dish implements Serializable {
    private String name;
    private double cost;
    private int ID;
    private int parent = -1;

    /* КОНСТРУКТОРЫ */
    // Конструктор, создающий блюдо с указанным названием и ценой.
    public Dish(String name, double cost) throws Exception {
        if (cost <= 0) throw new Exception("Цена должна быть положительной");
        this.name = name;
        this.cost = cost;
        this.ID = UUID.randomUUID().hashCode();
    }
    
    public Dish (int id, String name){
        this.ID = id;
        this.name = name;
    }

    public Dish(int id, String name, double cost) {
        this.ID = id;
        this.name = name;
        this.cost = cost;
    }

    // Конструктор, создающий блюдо с указанным названием и стандартной ценой 100 руб.
    public Dish(String name) throws Exception {
        this(name, 0);
    }

    public void setParent(int parent) {
        if (parent > 0) {
            this.parent = parent;
        }
    }

    public int getParent() {
        return this.parent;
    }

    /* МЕТОДЫ */
    // Метод получения названия блюда.
    public String getName() {
        return this.name;
    }

    // Метод изменения названия блюда.
    public void setName(String name) {
        this.name = name;
    }

    // Метод получения цены блюда.
    public double getCost() {
        return this.cost;
    }

    // Метод изменения цены блюда.
    public void setCost(double cost) {
        if (cost >= 0)
        this.cost = cost;
    }

    // Метод получения ID блюда.
    public int getID() {
        return this.ID;
    }
}
