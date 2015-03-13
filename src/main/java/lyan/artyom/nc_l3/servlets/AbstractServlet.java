/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lyan.artyom.nc_l3.servlets;

import javax.servlet.http.HttpServlet;
import lyan.artyom.nc_l3.servlets.pageloader.PageLoader;
import model.db.DBModel;

/**
 * пока не нужен пусть останется
 * если понадобится введу
 * @author lyan
 */
public abstract class AbstractServlet extends HttpServlet{
    protected DBModel model;
    protected PageLoader pl;
}
