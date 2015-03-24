/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.compare;

import java.util.Comparator;

/**
 * класс компаратор объектов блюд
 * по имени
 * класс обеспечивает сортировку в алфавитном порядке
 * @author lyan
 */
public class NameComparator implements Comparator {

    @Override
    public int compare(Object t, Object t1) {
        String d1 = (String) t, d2 = (String) t1;
        
        return d1.compareTo(d2);
    }

}
