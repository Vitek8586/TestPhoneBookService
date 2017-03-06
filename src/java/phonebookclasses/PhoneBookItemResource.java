/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonebookclasses;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import sources.*;


/**
 *
 * @author dns1
 */
@Path("phonebookitem")
public class PhoneBookItemResource {
     
    //Source source = new MYSQLSource("jdbc:mysql://localhost:3306/PhoneBook?user=root&password=root");
    //Source source = new JSONSource("c:\\!exp\\source.txt");
    //Source  source  = new XMLSource("c:\\!exp\\source.xml");
    Source source = new MultiSource();
        
 
    public PhoneBookItemResource(){
    }
    
    
    
    /*@GET
    @Produces("text/xml")
    public PhoneBookItem getPhoneBookItem(){
        System.out.println("---"+this.getClass().getCanonicalName()+" вызван метод .getPhoneBookItem() ");
        
        
        return _item;
    }*/
    
    @GET
    @Produces("text/xml")
    public PhoneBookItem[] getPhoneBookItem (@QueryParam("id") int id
                                            ,@QueryParam("name")      String name
                                            ,@QueryParam("fullname")  String fullname
                                            ,@QueryParam("surname")   String surname
                                            ){
       System.out.println("---"+this.getClass().getCanonicalName()+" вызван метод .getPhoneBookItem() ");
       return source.get(new PhoneBookItem(id,fullname,name,surname));
    }
    
    
    
   
    @POST
    @Consumes("text/xml")
    public void updatePhoneBookItem(PhoneBookItem item){
        System.out.println("---"+this.getClass().getCanonicalName()+" вызван метод .updatePhoneBookItem()");
        source.update(item);
    }
    
    
    @PUT
    @Consumes("text/xml")
    public void createPhoneBookItem(PhoneBookItem item){
        System.out.println("---"+this.getClass().getCanonicalName()+" вызван метод .createPhoneBookItem()");
        source.insert(item);
    }
    
   
    @DELETE
    @Consumes("text/xml")
    public void deletePhoneBookItem(@QueryParam("id") int id){
        System.out.println("---"+this.getClass().getCanonicalName()+" вызван метод .deletePhoneBookItem()");
        source.delete(new PhoneBookItem(id));
        
    }
    
}
