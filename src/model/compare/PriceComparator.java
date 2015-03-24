/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.compare;

import java.util.Comparator;

/**
 *
 * @author lyan
 */
public class PriceComparator implements Comparator {

    @Override
    public int compare(Object t, Object t1) {
        String s = (String) t, s1 = (String) t1;
        if (s.equals("")) {
            s = "0";
        }
        if (s1.equals("")) {
            s1 = "0";
        }
        double d = Double.parseDouble(s), d1 = Double.parseDouble(s1);

        return (int) Math.round(d - d1);
    }

}
