/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.compare;

import java.util.Comparator;

/**
 * компаратор, который сравнивает объекты блюд по ид объект, ид которого больше
 * по знчению, считается больше
 *
 * @author lyan
 */
public class IdComparator implements Comparator {

    @Override
    public int compare(Object t, Object t1) {
        String s = (String) t, s1 = (String) t1;
        if (s.equals("")) {
            s = "0";
        }
        if (s1.equals("")) {
            s1 = "0";
        }
        int d = Integer.parseInt(s), d1 = Integer.parseInt(s1);
        return d - d1;
    }

}
