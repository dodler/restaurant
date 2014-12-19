/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.PrintStream;
import java.util.ArrayList;
import model.Dish;
import model.ICategory;

/**
 *
 * @author dodler
 */
public class ConsoleViewImpl implements IConsoleView {

    PrintStream out;

    public ConsoleViewImpl() {
        out = System.out;
    }

    public ConsoleViewImpl(PrintStream out) {
        this.out = out;
    }

    private String _show(ICategory cat) {
        String result = "Категория: " + cat.getName();
        result += " содержит " + cat.getDishList().size() + " блюд. ";
        return result;
    }
    @Override
    public void show(ICategory cat) {
        out.println(this._show(cat));
    }

    @Override
    public void showWithDishes(ICategory cat) {
        show(cat);
        showDish(cat.getDishList());
    }

    private String _show(ArrayList<ICategory> catList, String prefix) {
        String result="";
        for (ICategory cat : catList) {
            result += prefix + _show(cat)+"\n";
        }
        return result;
    }
    @Override
    public void show(ArrayList<ICategory> catList) {
         out.println(this._show(catList,""));
    }

    
    private String _showDish(ArrayList<Dish> dishList, String prefix) {
        StringBuilder sb = new StringBuilder();
        for (Dish d : dishList) {
            sb.append(prefix);
            sb.append("Блюдо ");
            sb.append(d.getName());
            sb.append(" стоит ");
            sb.append(d.getPrice());
            sb.append("\n");
        }
        return sb.toString();
    }
    
    @Override
    public void showDish(ArrayList<Dish> dishList) {
        out.println(this._showDish(dishList,""));
    }

    @Override
    public void show(String source) {
        out.println(source);
    }

    private String _show(Dish d){
        StringBuilder sb = new StringBuilder();
        sb.append("**Блюдо ");
        sb.append(d.getName());
        sb.append(" стоит ");
        sb.append(d.getPrice());
        sb.append("\n");
        return  sb.toString();
    }
    
    @Override
    public void show(Dish d) {
        out.println(this._show(d));
    }

    public String _showTreeCategoryWithDishes(ICategory cat, String prefix){
        String result = "";
        ArrayList<ICategory> CategoryList = cat.getSubCategoryList();
        if(CategoryList != null){
            for (ICategory currentCategory : CategoryList) {
                result += prefix+_show(currentCategory)+"\n";
                result += _showTreeCategoryWithDishes(currentCategory, prefix+"_");
                result += _showDish(currentCategory.getDishList(), prefix+"_");
            }
        }
        else{
            result += this._showDish(cat.getDishList(), prefix+"_");
        }
        return result;
    }
    
    public void showTreeCategoryWithDishes(ICategory cat){
        out.println(this._showTreeCategoryWithDishes(cat,""));
    }
    
    public String _showTreeCategory(ICategory cat, String prefix){
        String result = "";
        ArrayList<ICategory> CategoryList = cat.getSubCategoryList();
        if(CategoryList != null){
            for (ICategory currentCategory : CategoryList) {
                result += prefix+_show(currentCategory)+"\n";
                result += _showTreeCategory(currentCategory, prefix+"_");
            }
        }
        return result;
    }
    
    public void showTreeCategory(ICategory cat){
        out.println(this._showTreeCategory(cat,""));
    }
}
