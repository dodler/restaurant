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

/**
 * интерфейс модели
 * модели данных могут быть разными
 * например есть xml модель, модель с сериализацией
 * @author lyan
 */
public interface IModel {

    ICategory getRootCategory();

    void save(String name) throws IOException;

    void load(String name) throws Exception;

    void treeBypass(TreeCommand command, ICategory rootCategory);

    boolean checkUnique(ICategory rootCategory, ICategory searchCategory);

    boolean checkUnique(ICategory rootCategory, Dish searchDish);

}