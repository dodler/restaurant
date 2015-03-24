package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dish implements Serializable{
    private String name;
    private double price;
    private int id;


/* КОНСТРУКТОРЫ */
    // Конструктор, создающий блюдо с указанным названием и ценой.
    public Dish(String name, double cost){
        this.name = name;
        this.price = cost;
        this.id = Calendar.getInstance().hashCode();// TODO переписать с автоинкрементов ид в модели
    }

    // Конструктор, создающий блюдо с указанным названием и стандартной ценой 100 руб.
    public Dish(String name){
        this(name, 0);
    }
    
    public Dish(String name, double cost, int id){
        this(name, cost);
        this.id = id;
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
    public double getPrice(){
        return this.price;
    }

    // Метод изменения цены блюда.
    public void setPrice(double price){
        try {
            checkCost(price);
        } catch (IncorrectCostException ex) {
            Logger.getLogger(Dish.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.price = price;
    }

    // Метод получения ID блюда.
    public int getId(){
        return this.id;
    }

/* ПРИВАТНЫЕ МЕТОДЫ */
    // Метод проверки правильности цены блюда.
    @Deprecated
    private void checkCost(double cost) throws IncorrectCostException{
           if (cost < 0) throw new IncorrectCostException();
    }
    
    /**
     * проверка равенства двух объектов Dish
     * @param o - объект блюда
     * @return 
     */
    @Override
    public boolean equals(Object o){
        if (!o.getClass().equals(this.getClass())) return false;
        
        Dish d = (Dish)o;
        return d.id == this.id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.price) ^ (Double.doubleToLongBits(this.price) >>> 32));
        hash = 53 * hash + this.id;
        return hash;
    }
    
    /**
     * 
     * @return
     * @throws CloneNotSupportedException 
     */
    @Override
    public Dish clone() throws CloneNotSupportedException{
       Dish d = (Dish) super.clone();
       d.id=this.id;
       d.name=this.name;
       d.price=this.price;
       return d;
    }

}
