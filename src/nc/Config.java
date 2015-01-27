/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lyan
 */
public class Config {

    private BufferedReader reader;
    private Map<String, String> conf;

    public Config(String source) throws IOException {
        reader = new BufferedReader(new FileReader(source));
        conf = new HashMap<>();
        String in;
        String[] elms; // строчки разделенные символов = в конфиге
        while ((in = reader.readLine()) != null) {
            elms = in.split("=");
            conf.put(elms[0], elms[1]);
        }
    }

    /**
     * метод доступа к параметрам конфига
     *
     * @param param - строчный параметр, которй требуется найти
     * @return - значение параметра
     */
    public String get(String param) throws Exception {
        if (conf.containsKey(param)) {
            return conf.get(param);
        } else {
            throw new Exception();
        }
    }
}
