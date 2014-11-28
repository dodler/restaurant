/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


* категории древовидные
* сделать реализацию дерева
 */
package model;

public interface IModel {

    Category getRootCategory();

    void saveToFile(String name);

    void loadFromFile(String name);

}