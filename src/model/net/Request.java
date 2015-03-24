/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.net;

import java.io.Serializable;
import model.IModel;

/**
 * класс описыващий запрос от клиена к серверу инкапсулирует в себе модель,
 * модель задается единственный раз
 *
 * @author lyan
 */
public class Request implements Serializable {

    public final static int GET = 0, POST = 1, EXIT = 2; // методы запросов
    // 0 - хочет получить модель, 1 - содержит модель

    private final IModel model;

    private int type;

    /**
     * конструктор позволяет задать модель в запрос и нужный тип запроса нужно
     * указать Request.GET если хотите получить в ответном запросе модель и
     * Request.POST если хотите отправить изменения на сервер
     *
     * @param model модель данных реализует интерфейс IModel, если хотите
     * получить модель укажите вместе модели null или любое другое значение
     * @param type тип запроса, int, значения GET или POST
     */
    public Request(IModel model, int type) {
        this.model = model;
        this.type = type;
    }

    /**
     * модель данных с инкапсулированным меню ресторана
     * @return модель, реализующая интерфейс IModel
     */
    public IModel getModel() {
        return this.model;
    }
    
    /**
     * метод возвращает тип запроса
     * значения GET или POST
     * @return 
     */
    public int getType(){
        return this.type;
    }

}
