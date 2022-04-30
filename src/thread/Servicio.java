/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

/**
 *
 * @author nsierrar
 */

public class Servicio {
    private int port;
    private String ip;
    private String Servicio;
    private int factor;

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }
    
    public Servicio(){
        
    }
    
    public Servicio(int port, String ip, String servicio, int factor){
        setPort(port);
        setIp(ip);
        setServicio(servicio);       
        setFactor(factor);
    }

    int getPort(){
        return(this.port);
    }
    
    void setPort(int newport){
        this.port = newport;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getServicio() {
        return Servicio;
    }

    public void setServicio(String Servicio) {
        this.Servicio = Servicio;
    }
    
    
    
}
