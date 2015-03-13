/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lyan.artyom.nc_l3.servlets.pageloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import lyan.artyom.nc_l3.servlets.pageloader.containers.HtmlTextForm;
import model.Dish;
import model.ICategory;

/**
 * загрузчик шаблонов страниц для сервлетов в этом проекте загружать пока можно
 * из файла
 *
 * @author lyan
 */
public class PageLoader {

    private BufferedReader br;

    {
        br = null;
        sb = new StringBuilder();
        result = new ArrayList<>();
    }

    /**
     * метод добавления div элемента на страницу элемент идентифицируется по
     * имени в итоге будет добавлено
     * <div name=...>
     * ваше содержимое
     * </div>
     *
     * @param name имя элемента
     * @param elm сам элемент в виде массива строк
     */
    public void addDivElm(String name, ArrayList<String> elm) {
        result.add("<div id=\"" + name + "\">");
        result.addAll(elm);
        result.add("</div>");
    }

    /**
     * тоже что и addDivElm с ArrayList но на вход подается только одна строка
     *
     * @param name имя элемента
     * @param elm строка описание элемнета
     */
    public void addDivElm(String name, String elm) {
        result.add("<div id=\"" + name + "\">");
        result.add(elm);
        result.add("</div>");
    }

    /**
     * добавление произвольного элемента на страницу, описанного в строке не
     * использовать для добавления div!! иначе, могу возникнуть проблемы с
     * вставкой нового див
     *
     * @param elm элемент, который нужно добавить
     * @throws java.lang.Exception если метод использован для добавления div
     */
    public void addElm(String elm) throws Exception {
        if (elm.contains("div")) {
            throw new Exception("Запрещено использовать метод для добавления div");
        }
        result.add(elm);
    }

    /**
     * начинает редактирование страницы
     */
    public void createPage() {
        if (result != null) {
            result.clear();
        } else {
            result = new ArrayList<>();
        }

        result.add("<!DOCTYPE html>");
        result.add("<html>");
        result.add("<head>");
        result.add("<title>Лабораторная работа 3</title>");
        result.add("<meta charset=\"UTF-8\">");
        result.add("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        result.add("</head>");
        result.add("<body>");

    }

    /**
     * заканчивает редактирование страницы
     */
    public void finishPage() {
        result.add("</body>");
        result.add("</html>");

    }

    /**
     * метод добавляет стиль странице стиль - css тег <style type="text/css">
     * весь css должен быть содержаться в одной строке
     *
     * @param text - описание стиля, которое нужно встроить в страницу
     */
    public void addStyle(String text) {
        int insPos = result.indexOf("<head>");
        result.add(insPos + 1, "<style type=\"text/css\">");
        result.add(insPos + 2, text);
        result.add(insPos + 3, "</style>");
    }

    /**
     * метод добавляет заголовок к страницу тег типа <h_> с текстом text
     *
     * @param type - номер заголовка в теге <h_>
     * @param text - текст, который будет размещен
     */
    public void addPageHeader(int type, String text) {
        result.add("<h" + Integer.toString(type) + ">");
        result.add(text);
        result.add("</h1>");
    }

    StringBuilder sb;

    /**
     * метод добавляет таблицу на страницу содержимое - категории и блюда из
     * списка
     *
     * @param name имя таблицы
     * @param categories - список блюд и категорий, которые надо добавить
     */
    public void addTable(String name, ArrayList<ICategory> categories) {
        result.add("<table name=\"" + name + "\" bgcolor=\"FFF5EE\" border=\"0.1\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 100%;\">");
        result.add("<tbody>");
        Dish d;
        for (ICategory c : categories) {
            sb.append("<tr align=\"center\">");
            sb.append("<td><a href=\"/NC_L3/EditServlet?id=" + c.getId() + "\" target=_blank>");
            sb.append(c.getName() + "(id:" + String.valueOf(c.getId()) + ")");
            sb.append("</a></td>");
            sb.append("</tr>");
            result.add(sb.toString());
            sb.delete(0, sb.length());
            for (int i = 0; i < c.getDishs().size(); i++) {
                d = c.getDishs().get(i);
                sb.append("<tr>");
                sb.append("<td><a href=\"/NC_L3/EditServlet?id=" + d.getID() + "\" target=_blank>"); // добавили ссылку на редактирование элемента
                sb.append(d.getName());
                sb.append("</a></td>");
                sb.append("<td>");
                sb.append(d.getCost());
                sb.append("</td>");
                sb.append("</tr>");
                result.add(sb.toString());
                sb.delete(0, sb.length());
            }

        }
        result.add("</tbody>");
        result.add("</table>");
    }

    /**
     * метод добавляет форму поиска на страницу форма содержит несколько полей,
     * которые указаны в fields
     *
     * @param type тип поля ввода
     * @param name имя формы поиска
     * @param method метод - POST или GET
     * @param fieldsName - поля поиска, через двоиточие разделенные подпись и
     * поля
     */
    public void addTextForm(String type, String name, String method, String... fieldsName) {
        for (String s : fieldsName) {
            result.add("<p>" + s.substring(0, s.indexOf(":")) + "<input type=\"" + type + "\" name=" + s.substring(s.indexOf(":") + 1, s.length()) + " ></p>");
        }
    }

    /**
     * перегруженный метод но с контейнером на входе удобнее, придется
     * конфигурировать до вызова
     *
     * @param htf конфигурация текстовой формы
     */
    public void addTextForm(HtmlTextForm htf) {
        LinkedList<String> names = htf.getNames(), types = htf.getTypes(), texts = htf.getTexts(), values = htf.getValues();
        sb = new StringBuilder();
        int pos = 0;
        for (String s : names) {
            sb.append("<p>");
            sb.append(texts.get(pos));
            sb.append("<input type=\"");
            sb.append(types.get(pos));
            sb.append("\" name=\"");
            sb.append(s);
            sb.append("\" value=\"");
            sb.append(values.get(pos));
            sb.append("\"> </p>");
            result.add(sb.toString());
            sb.delete(0, sb.length());
            pos++;
        }
    }

    /**
     * сдвигает элемент в слой div идентификация элемента по имени и дива тое по
     * имени TODO отладить до конца пока есть баги
     *
     * @param elm имя элемента
     * @param divName имя div куда надо поместить
     * @param create нужно ли создать div если он отсутствует. если да, то див
     * получит имя divName
     * @throws java.lang.Exception ошибка входного параметра - отсутствует
     * элемент с указанным именем или указанный див
     */
    public void moveElmToDiv(boolean create, String elm, String divName) throws Exception {
        boolean divCreated = false;
        int divPos = result.indexOf("<div id=\"" + divName + "\">"), elmPos = -1;// позиция нужного див, позиция нужного элемента
        if (divPos == -1) {
            if (create) {
                divCreated = true;
                result.add("<div id=\"" + divName + "\">");
                divPos = result.size() - 1;
            } else {
                throw new Exception("Указанный слой не найден");
            }
        }
        int i;
        // все теперь отлажено
        if ((elmPos = findElmPos(elm)) == -1) { // ищем позицию нужного элемента
            throw new Exception("Указанный элемент не найден");
        } else {
            // если нашли то 
            i = findElmPos(elmPos + 1, tag);// позиция закрывающего тега
        }
        String cpy;
        for (int j = 0; j < i - elmPos + 1; j++) {
            cpy = String.copyValueOf(result.get(elmPos + j).toCharArray());
            result.add(divPos + j + 1, cpy); // перемещение строк
        }
        if (divCreated) {
            result.add("</div>");
        }
        for (int j = 0; j < i - elmPos + 1; j++) {
            result.remove(elmPos); // удаление старых строк
        }

    }
    String tag; // 'элемент устанавливает после успешного поиска в методе findElmPos

    private int findElmPos(int i, String name) {
        String s;
        for (int j = i; j < result.size(); j++) {
            s = result.get(j);
            if (s.contains(name)) {
                tag = s.substring(1, s.indexOf(">")); // запомнили тег
                return result.indexOf(s); // позиция элемента с данным 
            }
        }
        return -1;
    }

    /**
     * метод добавления в форму выпадающего списка
     * также монжо конфигурировать через HtmlTextForm как addTextForm
     * надо указать при этом размер
     * @param selectParam параметры селекта, такие как метод запроса, имя и т.д.
     * @param optionParam параметры опции, установленное значение (value) и тд.
     */
    public void addSelectForm(LinkedHashMap<String, String> selectParam, HtmlTextForm optionParam){
        StringBuilder temp = new StringBuilder();
        Set<Entry<String, String>> s = selectParam.entrySet();
        Iterator<Entry<String,String>> i = s.iterator();
        Entry<String, String> e;
        
        while(i.hasNext()){
            e=i.next();
            temp.append(e.getKey());
            temp.append("=\"");
            temp.append(e.getValue());
            temp.append("\" "); // добавляем все параметры для select 
        }
        
        String cur; // текущая строка для итерации
        int pos; // текущая позиция итерации
        result.add("<select " + temp.toString() + ">"); // зашили параметры в тег селект
        for (Iterator<String> it = optionParam.getValues().iterator(); it.hasNext();) {
            cur = it.next();
            pos= optionParam.getValues().indexOf(cur);
            
            result.add("<option name=\"" + optionParam.getNames().get(pos) + "\" value=\"" + cur + "\" type=\"" + optionParam.getTypes().get(pos) + "\">"+optionParam.getTexts().get(pos)+"</option>");
        }
        result.add("</select>");
    }
    
    /**
     * метод поиска элемента по имени возвращает позицию тега с указанным именем
     * в массиве result если нашел -1 если нет
     *
     * @return позиция элемента
     */
    private int findElmPos(String name) {
        tag = "";
        for (String s : result) {
            if (s.contains(name)) {
                tag = s.substring(1, s.indexOf(" ")); // запомнили тег
                return result.indexOf(s); // позиция элемента с данным именем
            }
        }
        return -1;
    }

    /**
     * метод возвращает все что пользователь создал на странице в виде массива
     * строк, которые можно вывести через PrintWriter например аналогичного
     * эффекта можно добиться вызва метод write
     *
     * @return
     */
    public ArrayList<String> getResultPage() {
        ArrayList<String> res = new ArrayList<>();
        res.addAll(result);
        result.clear();
        return res;
    }

    /**
     * метод выводит готовую страницу в обхект PrintWriter например вывести
     * страницу на сторону клиента чрез сервлет
     *
     * @param pw - writer, куда будет выведена страница
     */
    public void write(PrintWriter pw) {
        for (String s : result) {
            pw.println(s);
        }
        pw.flush();
        result.clear();
    }

    private ArrayList<String> result;

    private void loadFromFile(String file) throws FileNotFoundException, IOException {
        br = new BufferedReader(new FileReader(new File(file)));
        String inp;
        while ((inp = br.readLine()) != null) { // считывание файла
            result.add(inp);
        }
    }

    /**
     * считывает из файла! метод возвращает набор строк - файл разметки (html
     * или jsp или еще что) чтобы сервлет мог его легко выводить в веб
     *
     * @param file - путь до файла html или jsp
     * @return файл разметки в виде строк
     * @throws java.io.FileNotFoundException - точнее файл отсутствует
     * @throws java.io.IOException - ошибка чтения файла
     */
    public ArrayList<String> getPage(String file) throws FileNotFoundException, IOException {
        loadFromFile(file);
        return result;
    }

    /**
     * метод добавления кнопки например:
     * <form action="BaseServlet" method="POST">
     * форма с типом POST, которая будет работать с сервлетом BaseServlet перед
     * добавением кнопки нужно сделать форму ну или без нее
     *
     * @param buttonLabel - надпись на кнопке
     */
    public void addSubmitButton(String buttonLabel) {
        result.add("<input type=\"submit\" value=\"" + buttonLabel + "\">");
        buttons.add(buttonLabel);
    }

    /**
     * метод начинает редактирование формы создает ее метод stopForm завершает
     * редактирование формы
     *
     * @param name имя формы
     * @param action действие, которые производит форма - ссылка на сервлет или
     * страницу
     * @param method метод http
     */
    public void startForm(String name, String action, String method) {
        result.add("<form name=\"" + name + "\" action=\"" + action + "\" method=\"" + method + "\" accept-charset=\"UTF-8\">");
    }

    public void stopForm() {
        result.add("</form>");
    }

    /**
     * добвляет на странциу div кнопку она будет представлять собой простую
     * ссылку но в диве и если надо с картинкой пока не реализован, а исключение
     * для нереализованного метода я забыл
     *
     * @param destination куда отправит кнопка или действие?
     * @param name для задания стиля кнопки
     * @param label надпись на кнопке
     */
    public void addLink(String destination, String name, String label) {
        result.add("<a href=\"" + destination + "\" name=\"" + name + "\"" + ">" + label + "</a>");
    }

    private ArrayList<String> buttons;

    public PageLoader() {
        buttons = new ArrayList<>();
    }
}
