/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc.server;

/**
 *
 * @author lyan
 */
public abstract class CmdHandler {
    abstract public boolean isApplicable(String cmd);
    abstract public void handle(String[] args);
    abstract public String getDescription();
}
