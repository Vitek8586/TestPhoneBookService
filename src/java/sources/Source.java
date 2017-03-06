/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import phonebookclasses.*;

import java.util.List;

/**
 *
 * @author dns1
 */
public interface Source {
     
    public PhoneBookItem[] get(PhoneBookItem item);
    public int insert(PhoneBookItem item);    
    public int update(PhoneBookItem item);    
    public int delete(PhoneBookItem item);
    
    
}
