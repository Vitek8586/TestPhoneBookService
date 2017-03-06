/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import phonebookclasses.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
 
/**
 *
 * @author dns1
 */
public class MYSQLSource implements Source {
    
    final String DB_URL;// = "jdbc:mysql://localhost:3306/PhoneBook?user=root&password=root";
    Connection conn;
    Statement stmt;
    
    public MYSQLSource(String db_url){
        DB_URL = db_url; 
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL);
            stmt  = conn.createStatement();
        
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLSource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MYSQLSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    @Override
    public PhoneBookItem[] get(PhoneBookItem i) {
        List<PhoneBookItem> _items = new ArrayList<PhoneBookItem>();
        try {
            String _id = (i.getId()==0)?"itemID":""+i.getId();
            String sql = "select *"
                    + " from item "
                    + "where itemID =  " +_id
                    +"   and fullname like '"+i.getFullname()+"%'"
                    +"   and name     like '"+i.getName()    +"%'"
                    +"   and surname  like '"+i.getSurname() +"%'";
            
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
            
                int itemId = Integer.parseInt(rs.getString("ItemId"));
                String fullname = rs.getString("fullname")
                      ,name     = rs.getString("name")
                      ,surname  = rs.getString("Surname");
                _items.add(new PhoneBookItem(itemId,fullname,name,surname)); 
            
            }
            
            for(PhoneBookItem _item:_items){
                List<String> phones = new ArrayList<String>();
                sql = "select * from phone where itemId = " + _item.getId();
                rs = stmt.executeQuery(sql);
                while(rs.next()){
                    phones.add(rs.getString("phone"));
                }
                rs.close();
                _item.setPhones(phones);
            }
            
            rs.close();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLSource.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       PhoneBookItem[] a = new PhoneBookItem[_items.size()]; 
       a = _items.toArray(a);
       return a;        
    }

    @Override
    public int insert(PhoneBookItem item) {
        
        String sql = "insert into item(fullname,name,surname) select '"+item.getFullname()+"','"+item.getName()+"','"+item.getSurname()+"'";
        try {
            stmt.execute(sql);
            sql = "select LAST_INSERT_ID() as ID";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int id = rs.getInt("ID");
            if(id!=0){
                for(String s:item.getPhones()){
                    sql = "insert into phone(itemId,phone) select "+id+",'"+s+"'";
                    stmt.execute(sql);
                }
            }
            
            rs.close();
         
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLSource.class.getName()).log(Level.SEVERE, null, ex);
            
        }
            
       return 0;
    }

    @Override
    public int update(PhoneBookItem item) {
      
        try {
            String sql = "select count(1) as cnt from item where itemId = "+item.getId();
            
            if(id_exists(item)){
                sql = " update item "
                        + "set"
                        + "      fullname = '"+item.getFullname()+"'"
                        + "     ,name     = '"+item.getName()+"'"
                        + "     ,surname  = '"+item.getSurname()+"'"
                        + " where itemId = "+item.getId() ;
                stmt.execute(sql);
                
                sql = " delete from phone where itemId = "+item.getId();
                stmt.execute(sql);
                
                
                for(String phone:item.getPhones()){
                    sql = "insert into phone(itemId,Phone) select "+item.getId()+",'"+phone+"'";
                    stmt.execute(sql);
                }
                
                return 0;
            }else{
                return 1;
            }
            
            
           
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        
    }

    @Override
    public int delete(PhoneBookItem item) {
        
        if(id_exists(item)){
            try {
                String sql =   "SET SQL_SAFE_UPDATES = 0 ";
                stmt.execute(sql);
                sql = "delete from item  where itemId = "+item.getId();
                stmt.execute(sql);
                sql = " delete from phone where itemId = "+item.getId();
                stmt.execute(sql);
                        
                
                
                
            } catch (SQLException ex) {
                Logger.getLogger(MYSQLSource.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return 0;
        }else{
            return 1;
        }
        
            
        
    }
    
    
    public boolean id_exists(PhoneBookItem item){
        try {
            String sql = "select count(1) as cnt from item where itemId = "+item.getId();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int cnt = rs.getInt("cnt");
            rs.close();
            
            return cnt>0;
            
        } catch (SQLException ex) {
            Logger.getLogger(MYSQLSource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
    
}
