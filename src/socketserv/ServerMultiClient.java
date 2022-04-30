package socketserv;


import java.net.*;
import java.io.*;
import java.util.Scanner;
import thread.UsingHT;
public class ServerMultiClient{
   static int NoClients=0;
   //UsingHT table;   

    public void NewServer(String[] args) throws IOException{
	ServerSocket socketServidor = null;
	Socket socketCliente = null;
        int puerto = 0;
        Scanner read = new Scanner(System.in);
        
        
        
        if(args.length != 1){
            System.out.println("Dime puerto");
            puerto = read.nextInt();
            socketServidor = new ServerSocket (puerto);
        }
        else{
            try
            {
                Integer piv = new Integer (args[0]); 
                puerto = piv.parseInt(piv.toString());
                socketServidor = new ServerSocket (puerto);
                //table = new UsingHT(puerto);
                
            }catch (Exception e)
            {
                System.out.println ("Error : "+ e.toString());
                System.exit (0);
            }
        }
        
	

	System.out.println ("Server started... (Socket TCP) Port: "+puerto);
	int enproceso=1;
	while(enproceso==1)
        {
            try
            {
                socketCliente = socketServidor.accept();
                MultiServerThread controlThread=new MultiServerThread(socketCliente, puerto);
                controlThread.start(); 
                
            }catch (Exception e)
            {
                System.out.println ("Error : " + e.toString());
                socketServidor.close();
                System.exit (0);
            }
	}
	System.out.println("Finalizando Servidor...");               

   }
    
    
}
