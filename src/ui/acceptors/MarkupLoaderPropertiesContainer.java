/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

/**
 * класс контейнер для свойств элементов разметки приложения
 *
 * @author lyan
 */
public class MarkupLoaderPropertiesContainer {

    private static MarkupLoaderPropertiesContainer instance = new MarkupLoaderPropertiesContainer();
    
    public static MarkupLoaderPropertiesContainer getInstance(){
        return instance;
    }
    
    public String transparent; // прозрачность кнопки
    public String rows; // названия столбцов таблицы
    public String model;// модель таблицы
    public String iconUrl; // адрес иконки для кнопки
    public String nodeName; // имя узла
    public String fontStyle; // пока не испльзовано
    public String fontSize;
    public String fontName;
    public String focusable;
    public String backgroundColor;
    public String className;
    public String name;
    public String label;
    public String x;
    public String y;
    public String width;
    public String height;
    public String autoEnable;
    public String layout;
    public String mouseListener;
    
    private MarkupLoaderPropertiesContainer(){
        
    }
}
