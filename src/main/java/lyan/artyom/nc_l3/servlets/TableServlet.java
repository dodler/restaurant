/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lyan.artyom.nc_l3.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lyan.artyom.nc_l3.servlets.pageloader.PageLoader;
import model.db.DBModel;
import model.db.MySqlModel;

/**
 *
 * @author lyan
 */
public class TableServlet extends HttpServlet {

    private static DBModel model;
    
    private PageLoader pl; // генератор страниц. пока в разрабоке, есть какая то реализация но кривая
    private int state; // текущее состояние сервлета

    private final int STATE_POST = 1; // обработать запрос POST 
    private final int STATE_GET = 2; // обработать запрос GET
    
    {
        pl = new PageLoader();
        model = new MySqlModel();
    }
    
    public DBModel getModel(){
        return model;
    }
    
    private String[] searchFields = new String[]{};

    /**
     * метод в котором формируется ответ get запроса к сервлету выводится
     * страница меню ресторана, на которой отобратся все доступные для заказа
     * блюда
     *
     * @param response - запрос кторый будет отправлен на сервер
     * @throws SQLException - ошибк пдоключения к базе данных
     * @throws Exception - ошибка считывания данных из базы данных
     */
    private void processGet(HttpServletResponse response) throws SQLException, Exception {
        model.connect();
        model.load();
        pl.createPage();
        pl.addLink("index.html", "mainPage", "На главную");
        pl.addPageHeader(1, "МЕНЮ РЕСТОРАНА");
        pl.startForm("addDish", "AddServlet", "GET");
        pl.addSubmitButton("Добавить новое блюдо");
        pl.stopForm();
        pl.startForm("searchForm", "SearchServlet", "GET");
        pl.addTextForm("searchForm","dishSearch", "GET", "Цена от:pUp", "Цена до:pDown", "Поиск по имени блюда:dName", "Поиск по категории:cName");
        pl.addSubmitButton("Искать");
        pl.stopForm();
        pl.addTable("model", model.getCategories());
        pl.addStyle("input{border-radius:5px; }]");
        pl.addStyle("div{margin-top:0px}");
        pl.addStyle("div#tools{width:19%; margin-right:0px;float:right}");
        pl.addStyle("div#data{width:79%; height:100%}");
        pl.moveElmToDiv(true, "addDish", "tools");
        pl.moveElmToDiv(true, "model", "data");
        pl.moveElmToDiv(false, "searchForm", "tools");
        // TODO пофиксить баг
        // если добавлять элементы в один див, но в порядке, отличном от порядка создания
        // то они не добавятся в див
        pl.finishPage();
        ArrayList<String> page = pl.getResultPage();
        try (PrintWriter out = response.getWriter()) {
            for (String s : page) {
                out.println(s);
            }
            out.close();
        }
        model.reset();
    }
    
    /**
     * метод обработки post запросов к сервлету
     */
    private void processPost(){
        
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * метод http запросов к сервлету
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        switch (state) {
            case STATE_GET: {
                processGet(response);
            }
            break;
            case STATE_POST: {
                processPost();
            }
            break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            state=STATE_GET;
            processRequest(request, response);

        } catch (Exception ex) {
            Logger.getLogger(TableServlet.class.getName()).log(Level.SEVERE, null, ex);
            this.getServletContext().getRequestDispatcher("error.html");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * логика работы
     * флаг переставляется в состояние STATE_POST
     * метод processRequest смотрит состояние флага и 
     * делегирует обрабоку в нужный метод
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            state = STATE_POST;
            processRequest(request, response);

        } catch (Exception ex) {
            Logger.getLogger(TableServlet.class.getName()).log(Level.SEVERE, null, ex);
            this.getServletContext().getRequestDispatcher("error.html");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
