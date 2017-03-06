/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import phonebookclasses.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;




 
/**
 *
 * @author dns1
 */
public class JSONSource extends AbstractFileSource{

    public JSONSource(String source_path) {
        super(source_path);
    }
   
    private JSONArray toJSONArray(){
        JSONArray jsonarray = new JSONArray();
            for(PhoneBookItem item:items){
                jsonarray.add(toJSONObject(item));
            }
        
        return jsonarray;
    }
    
    private JSONObject toJSONObject(PhoneBookItem item){
        JSONObject obj = new JSONObject();
        obj.put("id", item.getId());
        obj.put("fullname", item.getFullname());
        obj.put("name", item.getName());
        obj.put("surname",item.getSurname());
        
        JSONArray aphones = new JSONArray();
        for(String phone:item.getPhones())
            aphones.add(phone);
        
        obj.put("phones", aphones);
        
        return obj;
    }
    
    
    @Override
    void saveToFile(){
        try {
            FileWriter fw = new FileWriter(new File(SOURCE_PATH));
            //fw.write( toJSONArray().toJSONString());
            toJSONArray().writeJSONString(fw);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(JSONSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    @Override
    void readFromFile(){
        items = new HashSet<PhoneBookItem>();
        try {
            StringBuilder sb = new StringBuilder();
            
            FileReader fr = new FileReader(new File(SOURCE_PATH));
            
            JSONParser parser = new JSONParser();
            JSONArray jsonarray = (JSONArray) parser.parse(fr);
            
            fr.close();
            
            for(Object o:jsonarray.toArray()){
                JSONObject obj = (JSONObject )o;
                JSONArray aphones =(JSONArray) obj.get("phones");
                
                List<String> phones = new ArrayList<String>();
                
                for(Object phone:aphones)phones.add((String)phone);
                
                int id = Integer.parseInt(obj.get("id").toString());
                
                String   ful =(String)obj.get("fullname")
                        ,nam =(String)obj.get("name")
                        ,sur =(String)obj.get("surname");
                
                
                items.add(new PhoneBookItem(id,ful,nam,sur,phones));
                
            }
        
        } catch (FileNotFoundException ex) {
            System.out.println("No source file");
        } catch (IOException ex) {
            Logger.getLogger(JSONSource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(JSONSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    
   
   
    
}
