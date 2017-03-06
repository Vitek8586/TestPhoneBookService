/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebookclasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author dns1
 */
@XmlRootElement
public class PhoneBookItem implements Serializable{
    private int id;
    private String fullname,name,surname;
    private List<String> phones = new ArrayList<String>();
     
    public PhoneBookItem(){
        saveFio(0, "Testov", "Test", "Testovich");
    }
    public PhoneBookItem(int id){
        saveFio(id, "", "", "");
    }
    
    public PhoneBookItem(int    id
                        ,String fullname
                        ,String name
                        ,String surname
                        ,List<String> phones){
        
        saveFio(id, fullname, name, surname);
        this.phones   = phones;
    }
    
    public PhoneBookItem(int    id
                        ,String fullname
                        ,String name
                        ,String surname
                        ,String...phones){
        saveFio(id, fullname, name, surname);
        this.phones   = Arrays.asList(phones);
    }
    
    
    public String getFullname(){
        return fullname;
    }
    
    public void setFullname(String fullName){
        this.fullname = fullName;
    }
    
    
    
  
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    
    public String getSurname(){
        return surname;
    }
    
    public void setSurname(String surname){
        this.surname = surname;
    }
            
    public List<String> getPhones(){
        return phones;
    }
    
    public void setPhones(List<String> phones){
        this.phones = phones;
    }
    
    
    private void saveFio(int    id
                 ,String fullname
                 ,String name
                 ,String surname){
        this.id       = id;
        this.fullname = fullname==null?"":fullname;
        this.name     = name==null?"":name;
        this.surname  = surname==null?"":surname;
    
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    @Override
    public boolean equals(Object o){
        return (((PhoneBookItem)o).id == this.id);
    }
    
    public int hashCode(){
        return id;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(id+" "+fullname+" "+name+" "+surname);
        for(String phone:phones)
                sb.append("  \n "+phone);
        return sb.toString();
    }
    
    
    
    
}
