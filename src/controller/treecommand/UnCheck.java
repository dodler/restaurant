/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.treecommand;

import model.ICategory;

/**
 *
 * @author dodler
 */
public class UnCheck extends TreeCommand{
    
    
    ICategory cat;
    
    void setCat(ICategory cat){
        this.cat = cat;
    }
    
    @Override
    public void handle(ICategory category) {
        
    }
    
}
