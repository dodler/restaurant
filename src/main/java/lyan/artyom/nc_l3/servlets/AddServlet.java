/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lyan.artyom.nc_l3.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lyan.artyom.nc_l3.servlets.pageloader.PageLoader;
import lyan.artyom.nc_l3.servlets.pageloader.containers.HtmlTextForm;
import model.CategoryImpl;
import model.Dish;
import model.ICategory;
import model.db.MySqlModel;

/**
 *
 * @author lyan
 */
public class AddServlet extends HttpServlet {

    PageLoader pl;
    MySqlModel msm;

    {
        pl = new PageLoader();
        msm = new MySqlModel();
    }

    /**
     * создание формы добавления нового объекта
     *
     * @param pw куда вывести страницу
     * @throws SQLException ошибка подкючения к бд
     */
    private void createAddForm(PrintWriter pw) throws SQLException {
        msm.connect();
        msm.load();
        ArrayList<ICategory> cats = msm.getCategories();

        pl.createPage();
        pl.startForm("addElm", "AddServlet", "GET");
        HtmlTextForm htf = new HtmlTextForm();
        htf.addName("name").addText("Имя").addType("text").addValue("");
        htf.addName("price").addText("Стоимость").addType("text").addValue("");
        pl.addTextForm(htf);
        htf.clean(); // имя и цена

        // выпадающий список для списка категорий
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("method", "GET");
        params.put("size", "1");
        params.put("name", "category");

        for (ICategory cat : cats) {
            htf.addName("cId").addText(cat.getName()).addType("text").addValue(String.valueOf(cat.getId()));
        }
        pl.addSelectForm(params, htf);

        htf.clean();
        params.clear();
        params.put("method", "GET");
        params.put("size", "1");
        params.put("name", "type");
        htf.addName("cat").addText("Категория").addType("text").addValue("2");
        htf.addName("dish").addText("Блюдо").addType("text").addValue("1");
        pl.addSelectForm(params, htf);
        pl.addSubmitButton("Добавить");
        pl.stopForm();
        pl.finishPage();
        pl.write(pw);
    }

    /**
     * создание новго блюда в базе данных ид генерируется автоматически проверка
     * на целостность встроена в запрос
     *
     * @param name имя нового блюда
     * @param price цена на новое блюдо
     * @param pCat категория, которй принадлежит новое блюдо
     */
    private void acceptNewDish(String name, double price, int pCat) throws SQLException, Exception {
        msm.connect();
        msm.add(pCat, new Dish(name, price));
    }

    /**
     * метод добавления новой категории ид генерируется автоматически -
     * инкремент максимального ид в таблицe
     *
     * @param name имя новой категории
     */
    private void acceptNewCategory(String name) throws SQLException {
        msm.connect();
        msm.add(new CategoryImpl(name));
    }

    private void showResult(PrintWriter pw) {
        pl.createPage();
        pl.addDivElm("success", "<p>Успешно создано</p>");
        pl.finishPage();
        pl.write(pw);
    }
    
    private void showError(PrintWriter pw, Exception e){
        pl.createPage();
        pl.addDivElm("error", "<p>Произошла ошибка:"+e.getMessage()+"</p>");
        pl.finishPage();
        pl.write(pw);
    }

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

        String name = request.getParameter("name"),
                price = request.getParameter("price"),
                type = request.getParameter("type"),
                category = request.getParameter("category");
        PrintWriter out = null;
        try {
            out  = response.getWriter();
            if (name == null || name.equals("")) { // просто отрисовать форму без добавления
                createAddForm(out);
            } else {
                switch (type) {
                    case "1":
                        acceptNewDish(name, Double.parseDouble(price), Integer.parseInt(category));
                        break;
                    case "2":
                        acceptNewCategory(name);
                        break;
                    default:
                        request.getServletContext().getContext("ErrorServlet?message=\"Неверный запрос\"");
                }
                showResult(out);
            }
        } catch (SQLException ex) {
            showError(out, ex);
            Logger.getLogger(AddServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            showError(out, ex);
            Logger.getLogger(AddServlet.class.getName()).log(Level.SEVERE, null, ex);
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
