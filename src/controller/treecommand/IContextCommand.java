/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.treecommand;

import controller.context.TreePassContext;
import model.ICategory;

/**
 *
 * @author dodler
 */
public interface IContextCommand {
    public Object handle(TreePassContext context, ICategory category);
}
