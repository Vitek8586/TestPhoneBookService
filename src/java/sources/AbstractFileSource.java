/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.util.ArrayList;
import phonebookclasses.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author dns1
 */
public abstract class AbstractFileSource implements Source {
     
    final String SOURCE_PATH; 
    
    protected Set<PhoneBookItem> items = new HashSet<PhoneBookItem>();
    
    
    public AbstractFileSource(String source_path){
        SOURCE_PATH = source_path;
        readFromFile();
    }
    
    @Override
    public PhoneBookItem[] get(PhoneBookItem i){
        List<PhoneBookItem> _items = new ArrayList<PhoneBookItem>();
        for(PhoneBookItem _i:items){
            if(
                  ((i.getId() == 0)|(i.getId()==_i.getId()))
                &&(_i.getFullname().toUpperCase().matches(i.getFullname().toUpperCase()+".*"))
                &&(_i.getName().toUpperCase().matches(i.getName().toUpperCase()+".*"))   
                &&(_i.getSurname().toUpperCase().matches(i.getSurname().toUpperCase()+".*"))   
              ) 
               _items.add(_i);
               
                
                
        }
        
       PhoneBookItem[] a = new PhoneBookItem[_items.size()]; 
       a = _items.toArray(a);
       return a; 
      
    }
    
    
   @Override
    public int insert(PhoneBookItem item) {
        int id=0;
        for(PhoneBookItem i:items){
            if(i.getId()>id)id = i.getId();
        }
        id++;
        item.setId(id);
        items.add(item);
        saveToFile();
        return 0;
    }

    @Override
    public int update(PhoneBookItem item) {
        
        if(items.remove(item)){
            items.add(item);
            saveToFile();
            return 0;
        }else{
            return 1;
        }
        
        
    }

    @Override
    public int delete(PhoneBookItem item) {
       if(items.remove(item)){
            saveToFile();
            return 0;
       }else{
           return 1;
       }
       
    }
    
     public String toString(){
        StringBuilder sb = new StringBuilder();
        for(PhoneBookItem item:items){
            sb.append("\n"+item.toString());
        }
        return sb.toString();
    }
     
    abstract void readFromFile(); 
    abstract void saveToFile();
    
    
    
    
}
