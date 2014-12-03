/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.


* категории древовидные
* сделать реализацию дерева
 */
package model;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

public interface IModel{

    ICategory getRootCategory();

    void saveToFile(String name) throws IOException;

    void loadFromFile(String name)  throws ParserConfigurationException, SAXException, IOException ;

}