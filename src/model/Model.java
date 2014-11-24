/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


* категории древовидные
* сделать реализацию дерева
 */
package model;

import java.util.Locale.Category;
import utils.MenuCategory;

/**
 *
 * @author dodler
 */
public abstract class Model {
    private int id;
    private String name;
    
    private static final MenuCategory rootCategory = new MenuCategory("123", null);
    
    public static MenuCategory getRootCategory(){
        return rootCategory;
    }
    
    public abstract void addCategory(Category parent);
    public abstract void deleteCategory(Category parent);
    
    public abstract void saveToFile(String name);
    public abstract void loadFromFile();
    
}
