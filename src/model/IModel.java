/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


* категории древовидные
* сделать реализацию дерева
 */
package model;

import controller.treecommand.TreeCommand;

import java.io.IOException;

public interface IModel {

    Category getRootCategory();

    void saveToFile(String name) throws IOException;

    void loadFromFile(String name) throws IOException;

    void treeBypass(TreeCommand command, ICategory rootCategory);

    boolean checkUnique(Category rootCategory, Category searchCategory);

    boolean checkUnique(Category rootCategory, Dish searchDish);

}