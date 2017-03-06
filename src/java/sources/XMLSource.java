/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import phonebookclasses.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
/** 
 *
 * @author dns1
 */
public class XMLSource extends AbstractFileSource {

    public XMLSource(String source_path) {
        super(source_path);
    }

    @Override
    
    void readFromFile() {
        try {
            XMLInputFactory input = XMLInputFactory.newInstance();
            FileInputStream fis = new FileInputStream(SOURCE_PATH);
            XMLStreamReader reader = input.createXMLStreamReader(SOURCE_PATH,fis );
            String fullname="",name="",surname="";
            int id = 0;
            List<String> phones = new ArrayList<String>();
            
            while(reader.hasNext()){
                reader.next();
                
                if(reader.isStartElement()){
                    String startElement =  reader.getLocalName();
                    if(startElement.equals("ITEM")){
                        fullname="";name="";surname="";
                        id = 0;
                        phones = new ArrayList<String>();
                    }else{
                        reader.next();
                        if(startElement.equals("FULLNAME")){
                            fullname = reader.getText();
                        }else if(startElement.equals("NAME")){
                            name = reader.getText();
                        }else if(startElement.equals("SURNAME")){
                            surname = reader.getText();
                        }else if(startElement.equals("ID")){
                            id = Integer.parseInt(reader.getText());
                        }else if(startElement.equals("PHONE")){
                            phones.add(reader.getText());
                        }   
                    
                    } 
                    
                    
                }else if(reader.isEndElement()&&reader.getLocalName().equals("ITEM")){
                    items.add(new PhoneBookItem(id,fullname,name,surname,phones));
                }
                
            }
            reader.close();
            fis.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("No source file :(");
        } catch (XMLStreamException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            Logger.getLogger(XMLSource.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }

    @Override
    void saveToFile() {
        
        try {
            XMLOutputFactory output = XMLOutputFactory.newInstance();
            FileOutputStream fw=new FileOutputStream(SOURCE_PATH);
            XMLStreamWriter writer  = output.createXMLStreamWriter(fw, "UTF-8");
            writer.writeStartDocument("1.0");
            writer.writeStartElement("PHONEBOOK");
            
            for(PhoneBookItem item:items){
                writer.writeStartElement("ITEM");
                
                    writer.writeStartElement("ID");
                    writer.writeCharacters(""+item.getId());
                    writer.writeEndElement();
                    
                    writer.writeStartElement("FULLNAME");
                    writer.writeCharacters(item.getFullname());
                    writer.writeEndElement();
                    
                    writer.writeStartElement("NAME");
                    writer.writeCharacters(item.getName());
                    writer.writeEndElement();
                    
                    writer.writeStartElement("SURNAME");
                    writer.writeCharacters(item.getSurname());
                    writer.writeEndElement();
                    
                    //writer.writeStartElement("PHONES");
                        for(String phone:item.getPhones()){
                            writer.writeStartElement("PHONE");
                            writer.writeCharacters(phone);
                            writer.writeEndElement();
                        }
                    //writer.writeEndElement();
                
                
                writer.writeEndElement();
            }
            
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
            fw.close();
            System.out.println("Closed");
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(XMLSource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(XMLSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
