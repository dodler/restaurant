/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.acceptors;

import java.awt.Component;
import java.util.HashMap;
import ui.MarkupLoader;

/**
 *
 * @author lyan
 */
public abstract class IUiAcceptor {
    protected Component t;
    public abstract boolean isApplicable(String type);
    public abstract Component acceptUI(MarkupLoaderPropertiesContainer mlpc, Component parent, HashMap<String, Component> components, MarkupLoader ml) throws Exception;
}
