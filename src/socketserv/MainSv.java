/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketserv;

import java.io.IOException;

/**
 *
 * @author alexs
 */
public class MainSv 
{
    public static void main(String args[]){
        try {
            ServerMultiClient sv = new ServerMultiClient();
            sv.NewServer(args);
        } catch (IOException ex) {
            System.out.println("No fue posible crear server\n"+ex.toString());
        }
    }
}
