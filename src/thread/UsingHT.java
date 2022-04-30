/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.FileReader;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import thread.Servicio;

/**
 *
 * @author nsierrar
 */
public class UsingHT
{
    int key = 1;
    Hashtable<Integer, Servicio> hS = new Hashtable<Integer, Servicio>();
    Servicio s;
    
    public UsingHT(int puerto){
        removeAll();
        readServicesJSON(""+puerto);
    }
    
    public UsingHT(){
        removeAll();
        readServicesJSON("all");
    }
    
    private void removeAll(){
        hS.clear();
        key = 1;
    }  

  
    
    private void addService(String servicio, String ip, int puerto, int factor){
        s = new Servicio(puerto, ip, servicio, factor);
        hS.put(key, s); key++;
    }
    
    private void showServices(){
        /* Impresión de servicios */
        System.out.println("SE MUESTRAN SERVICIOS");
        for (int i = 1; i < key; i++) {
            System.out.println(hS.get(i).getServicio()+" - "+hS.get(i).getPort());                       
        }
    }
    
    /* recibe el puerto desde el que se solicita y el servicio que le gustaría recibir*/
    public String requestService(String servicio, int puerto){
        System.out.println("Verificando HT para "+servicio+" en puerto "+puerto);
        boolean existe = false;
        int portReal =0;
        String ret = "";
        
        if(hS.size() > 0){
            /* Se verifica si el servicio está en ese puerto */
            int portAux =0;
            String serv = "";
            for (int i = 1; i < key; i++) {
                portAux = hS.get(i).getPort();
                serv = hS.get(i).getServicio();
                
                /* se encuentra el servicio */
                if(serv.equals(servicio)){
                    /* Es el mismo puerto */
                    if(portAux == puerto){
                        existe = true;
                        break;
                    }
                    else{
                        /* puerto no coincide, se guarda el puerto que debe ser */
                        portReal = portAux;
                    }
                }
                                               
            }
            
            if(existe){
                existe = false;
                ret= "Método encontrado y es el puerto: "+puerto;
                
                /* switch con los métodos */
                if(servicio.equals("fecha")){
                    Date fecha = new Date();
                    String fec = ""+fecha.getTime();
                    ret = fec;
                }
                
            }
            else{
                ret = ""+portReal;
                
            }
        }
        
        
        //System.out.println("Respuesta a enviar: -"+ret+"-");
        return ret;
    }   
    
    
    private void readServicesJSON(String puerto){
        JSONParser parser = new JSONParser();
        try 
        {                                   
            Object obj = parser.parse(new FileReader(""+puerto+".json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray services = (JSONArray) jsonObject.get("services");
            Iterator<JSONObject> iterator = services.iterator();
            
            while (iterator.hasNext()) {  
                JSONObject aux = new JSONObject();
                aux = iterator.next();
                addService(aux.get("nameOfService").toString(), aux.get("ip").toString(), Integer.parseInt(aux.get("port").toString()), Integer.parseInt(aux.get("factor").toString()));               
            }
            
            //showServices();
            
            
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    
    
}
