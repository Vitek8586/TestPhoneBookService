/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import phonebookclasses.PhoneBookItem;

/** 
 *
 * @author dns1
 */
public class MultiSource implements Source{
    
    List<Source> sources = new ArrayList<Source>();
    
    public MultiSource(){
        try {
            File f  =new File("settings.ini");
            if(!f.exists()) f = new File("c:\\settings.ini");
            
            FileReader fr;
            fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
        
            String s;
        while((s = br.readLine())!=null){
            if(!s.toLowerCase().matches("rem .*")){
                String[] str = s.split("::");
                str[0] = str[0].trim();
                str[1] = str[1].trim();
                
                switch(str[0].toLowerCase()){
                    case ("mysql"):sources.add(new MYSQLSource(str[1])); break;
                    case ("json"):sources.add(new JSONSource(str[1])); break;
                    case ("xml"):sources.add(new XMLSource(str[1])); break;
                    default:break;
                }
            }
        }
        fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MultiSource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MultiSource.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }

    @Override
    public PhoneBookItem[] get(PhoneBookItem item) {
        Set<PhoneBookItem> _items = new HashSet<PhoneBookItem>();
        
        for(Source source:sources){
            _items.addAll(Arrays.asList(source.get(item)));
        }
        
        PhoneBookItem[] a = new PhoneBookItem[_items.size()]; 
        a = _items.toArray(a);
        return a;
    }

    @Override
    public int insert(PhoneBookItem item) {
        for(Source source:sources){
            source.insert(item);
        }
        return 0;
    }

    @Override
    public int update(PhoneBookItem item) {
        for(Source source:sources){
            source.update(item);
        }
        return 0;
    }

    @Override
    public int delete(PhoneBookItem item) {
        for(Source source:sources){
            source.delete(item);
        }
        return 0;
    }
    
}
