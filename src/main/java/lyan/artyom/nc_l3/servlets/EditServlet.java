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
import lyan.artyom.nc_l3.servlets.pageloader.containers.HtmlTextForm;
import model.Dish;
import model.ICategory;
import model.db.DBModel;
import model.db.MySqlModel;

/**
 *
 * @author lyan
 */
public class EditServlet extends HttpServlet {

    private final PageLoader pl;
    private final DBModel model;
// TODO дописать комментарии

    {
        pl = new PageLoader();
        model = new MySqlModel();
    }

    private void acceptEdit(String id, String price, String name, String category) throws SQLException, Exception {
        model.connect();
        model.load();
        if (Double.parseDouble(price) < 0) {
            throw new Exception("Цена не может быть отрицательной");
        }
        ((MySqlModel) model).update(Integer.parseInt(id), Integer.parseInt(category), name, Double.parseDouble(price));
        model.reset();
    }

    private void createEditPage(int id) throws SQLException, Exception {
        model.connect();
        model.load();
        Dish d = null;
        ICategory c = null;
        ArrayList<ICategory> cats = model.getCategories();
        for (ICategory c1 : cats) {
            for (Dish d1 : c1.getDishs()) {
                if (id == d1.getID()) {
                    c = c1;
                    d = d1;
                    break;
                }
            }
        }
        for (ICategory c1 : cats) {
            if (c1.getId() == id) {
                c = c1;
            }
        }
        if (d == null && c == null) {
            throw new Exception("Элемент не обнаружен");
        }
        pl.createPage();
        pl.startForm("editForm", "EditServlet", "GET");
        HtmlTextForm htf = new HtmlTextForm();
        htf.addName("id").addText("").addType("hidden").addValue(String.valueOf(id));
        if (d != null) {// для редактирования блюда
            htf.addName("price").addText("Цена").addType("text").addValue(String.valueOf(d.getCost()));
            htf.addName("name").addText("Имя").addType("text").addValue(d.getName());
            htf.addName("category").addText("Категория").addType("text").addValue(String.valueOf(c.getId()));
        } else {
            // для редактирования категории
            htf.addName("name").addText("Имя").addType("text").addValue(c.getName());
        }
        pl.addTextForm(htf);
        pl.addSubmitButton("Сохранить");
        pl.stopForm();
        pl.startForm("deleteItem", "DeleteServlet", "GET");
        htf.clean();
        htf.addName("id").addText("").addType("hidden").addValue(String.valueOf(id));
        pl.addTextForm(htf);
        pl.addSubmitButton("Удалить");
        pl.stopForm();
        pl.finishPage();

        model.reset();
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
        response.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id"),
                price = request.getParameter("price"),
                category = request.getParameter("category"),
                name = request.getParameter("name");
        if (id != null && price != null) {
            try {
                acceptEdit(id, price, name, category);
            } catch (Exception ex) {
                Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.getServletContext().getContext("ErrorServlet?message=" + ex.getMessage());
            }
            pl.createPage();
            pl.addDivElm("success", "<p>Поле успешно отредактировано</p>");
            pl.finishPage();
        } else if (id != null && !id.equals("")) {
            try {
                createEditPage(Integer.parseInt(id));
            } catch (Exception ex) {
                Logger.getLogger(EditServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.getServletContext().getContext("ErrorServlet?message=" + ex.getMessage());
            }
        }

        try (PrintWriter out = response.getWriter()) {
            pl.write(out);
            out.flush();
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
