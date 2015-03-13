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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lyan.artyom.nc_l3.servlets.pageloader.PageLoader;
import model.CategoryImpl;
import model.Dish;
import model.ICategory;
import model.db.MySqlModel;

/**
 *
 * @author lyan
 */
public class SearchServlet extends HttpServlet {

    private MySqlModel msm;
    private PageLoader pl;

    {
        msm = new MySqlModel();
        pl = new PageLoader();
    }

    /**
     * метод находит в бд все элементы в ценовом диапазоне от low до high c
     * именем блюда совпадающим с dName и именем категории совпадающим с cName
     *
     * @param low
     * @param high
     * @return
     */
    private ArrayList<Dish> findElm(double low, double high, String dName, String cName) {
        ArrayList<Dish> result = new ArrayList<>(), fRes = new ArrayList<>();
        ArrayList<ICategory> catRes = new ArrayList<>();
        for (ICategory c : msm.getCategories()) {
            for (Dish d : c.getDishs()) {
                if (d.getCost() <= high && d.getCost() >= low && !result.contains(d)) {
                    // тут баг, значения почему то местами поменяны
                    // поэтому приходится инвертировать отрезок, на котором цены действуют
                    result.add(d);
                }
            }
        }

        if (!dName.equals("") && !dName.equals("*")) {
            Dish d;
            for (Iterator<Dish> i = result.iterator(); i.hasNext();) {
                d = i.next();
                if (!containPattern(d.getName(), dName)) {
                    i.remove();
                }
            }
        }

        return result;
    }

    /**
     * провверка вхождения шаблона в строку
     * в шаблоне могут быть символы
     * звездочка * означает любое количесво символов, в том число пустой символ
     * @param name нужная строка
     * @param pattern нужный шаблон
     * @return подходит строка заданному шаблону
     */
    private boolean containPattern(String name, String pattern) {
        String[] patterns=pattern.split("\\*");
        int pPos = -1, curPos;
        for(String p:patterns){
            curPos = name.indexOf(p);
            if (curPos >= 0 && pPos<curPos){
                pPos=curPos;
            }else{
                return false;
            }
        }
        return true;
    }

    /**
     * метод выводит страницу с результатами поиска в метод нужно передать
     * параметры запроса метод просто создает необходимый PageLoader но не
     * производит печать. для печати нужно отдельно сделать вывод из PageLoader
     *
     * @param lowPrice нижняя граница цены. на странице будут отображены только
     * блюда с ценой выше lowPrice
     * @param highPrice верхняя граница цена.
     * @param dishName шаблон, по которому можно искать по именам блюда. *
     * означает любое количество символолв. метод будет искать любое вхождение
     * @param categoryName шаблон для поиска по именам категорий. пустая строка
     * означает отсутствие ограничений
     */
    private void createResultPage(double lowPrice, double highPrice, String dishName, String categoryName) {
        ArrayList<Dish> elms = findElm(lowPrice, highPrice, dishName, categoryName);
        CategoryImpl c;
        ArrayList<ICategory> cats = new ArrayList<>();
        c = new CategoryImpl("result");
        for (Dish d : elms) {
            c.addDish(d);
        }
        cats.add(c);
        pl.addTable("РЕЗУЛЬТАТЫ", cats);
        pl.addLink("/NC_L3/SearchServlet", "searchStartPage", "Назад");
    }

    /**
     * метод выводит просто форму для поиска действует аналогично
     * printResultPage для вывода html страницы нужно напрямую вызвать печать
     * страницы из PageLoader
     */
    private void createSearchPage() {
        pl.startForm("searchForm", "SearchServlet", "GET");
        pl.addTextForm("searchForm", "dishSearch", "GET", "Цена от:pUp", "Цена до:pDown", "Поиск по имени блюда:dName", "Поиск по категории:cName");
        pl.addSubmitButton("Искать");
        pl.stopForm();
    }

    // надеюсь это код никто никогда не увидит :)
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        try {
            msm.connect(); // подкючение к бд
            msm.load();
        } catch (SQLException ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.getServletContext().getContext("ErrorServlet?message=" + ex.getMessage());
        }

        String pUp = request.getParameter("pUp"),
                pDown = request.getParameter("pDown"),
                dName = request.getParameter("dName"),
                cName = request.getParameter("cName"); // параметры поискового запроса

        pl.createPage(); // начали редактировать страницу
        if (pUp != null && pDown != null && dName != null && cName != null) {
            if (pUp.equals("")) {
                pUp = "-1";
            }
            if (pDown.equals("")) {
                pDown = "100000000"; // чтобы не выбрасывалось исключений пустой строки при парсинге дабл
            }
            createResultPage(Double.parseDouble(pUp), // если пришли данные на поиск
                    Double.parseDouble(pDown), // то создаем соответствующую страницу
                    dName,
                    cName
            );
        } else {
            createSearchPage(); // иначе просто пустая форма поиска
        }
        pl.finishPage(); // закончили

        try (PrintWriter out = response.getWriter()) {
            pl.write(out);
            out.flush();
            msm.reset();
        } catch (Exception ex) {
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.getServletContext().getContext("ErrorServlet?message=" + ex.getMessage());
        }
    }

    private ArrayList<Dish> dishs; // список доступных блюд
    // обновляется при каждом запросе

    /**
     * метод поика по именам категорий
     *
     * @param pattern
     * @param maxLen
     * @return
     */
    private ArrayList<ICategory> findPattern(String pattern, int maxLen) {
        return null;
    }

    /**
     * метод поиска по указанному шаблону
     *
     * @param pattern
     */
    private ArrayList<Dish> findPattern(String pattern) {
        final String[] words = pattern.split("\\*"); // получили слова разделенные друг от друга 
        final String name;
        ArrayList<Dish> result = new ArrayList<>();

        for (Dish d : dishs) {
            int maxI = -1, found = 0, curI = 0; // maxI - самое дальнее вхождение, curI - текущее вхождение
            // found - текущее число совпадений
            for (String word : words) {
                if ((curI = d.getName().indexOf(word)) > maxI) {
                    maxI = curI;
                    found++;
                } else {
                    return null;
                }
            }
            if (found == words.length) {
                result.add(d);
            }
        }
        return result;
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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
