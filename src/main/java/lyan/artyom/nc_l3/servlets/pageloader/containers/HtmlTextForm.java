/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lyan.artyom.nc_l3.servlets.pageloader.containers;

import java.util.LinkedList;
// TODO написать комменты
/**
 *
 * @author lyan
 */
public class HtmlTextForm {

    protected final LinkedList<String> values, names, texts, types;

    {
        values = new LinkedList<>();
        names = new LinkedList<>();
        texts = new LinkedList<>();
        types = new LinkedList<>();
    }
    
    public void clean(){
        names.clear();
        values.clear();
        texts.clear();
        types.clear();
    }
    
    public HtmlTextForm addName(String name){
        names.add(name);
        return this;
    }
    
    public HtmlTextForm addValue(String value){
        values.add(value);
        return this;
    }
    
    public HtmlTextForm addText(String text){
        texts.add(text);
        return this;
    }
    
    public HtmlTextForm addType(String type){
        types.add(type);
        return this;
    }
    
    public LinkedList<String> getValues(){
        return values;
    }
    
    public LinkedList<String> getNames(){
        return names;
    }
    
    public LinkedList<String> getTypes(){
        return types;
    }
    
    public LinkedList<String> getTexts(){
        return texts;
    }
}
