package socketserv;

import java.net.*;
import java.io.*;
import java.util.Date;
import thread.UsingHT;

public class MultiServerThread extends Thread {
    private Socket socket = null;
    Socket clienteX = null;
    UsingHT HT;   
    int port;
    int puertoReal =0; /* por si se debe conectar desde otro puerto */
    boolean verificado = false; /* para esperar respuesta válida del serv */
   
   public MultiServerThread(Socket socket, int puerto) {
      super("MultiServerThread");
      this.socket = socket;
      this.port = puerto;
      ServerMultiClient.NoClients++;
      System.out.println("MultiThread running & listenning, port: "+puerto);
      HT = new UsingHT();
   }

    public void run() {

        try 
        {

          PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
          BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
          String lineIn;          
          
          
            while((lineIn = entrada.readLine()) != null)
            {
               System.out.println("Received: "+lineIn+"  -port: "+port);
               String resp = HT.requestService(lineIn, port);                            
               
                if(lineIn.equals("FIN"))
                {
                    ServerMultiClient.NoClients--;
                    break;
                }
                else if(lineIn.equals("CUANTOS"))
                {
                    escritor.println("NC: "+ServerMultiClient.NoClients);
                    escritor.flush();
                } 
               
                try
                {
                    puertoReal = Integer.parseInt(resp);
                    
                    if(puertoReal != 0)
                    {
                        /* significa que se debe hacer conexión desde otro puerto */
                        try{



                            /* se hace nueva verificación con el puerto correcto */



                            clienteX = new Socket (socket.getInetAddress(),puertoReal);     


                            PrintWriter escritorX = new PrintWriter(clienteX.getOutputStream(), true);
                            BufferedReader entradaX = new BufferedReader(new InputStreamReader(clienteX.getInputStream()));  

                            /* se hace la petición escribriendo en ese puerto */
                            escritorX.println(lineIn);

                            /* se recupera la respuesta en ese puerto */
                            String newResp = entradaX.readLine();

                            /* se imprimen las respuestas */
                            //System.out.println(newResp);                        

                            escritor.println("SERV 2 ->"+newResp);
                            escritor.flush();



                            /* se cierran conexiones alternas */
                            clienteX.close();
                            escritorX.close();
                            entradaX.close();

                        }catch (Exception e)
                        {
                          System.out.println ("Fallo : "+ e.toString());
                          System.exit (0);
                        }
                    }
                    else{
                        escritor.println("No se encontró el servicio en ningún puerto");
                    }
                        

                }
                catch(Exception e){

                    /* no es el puerto, es una respuesta del sv*/
                    escritor.println(""+resp);
                    escritor.flush();
                    System.out.println(""+resp);
                    verificado = true;
                }
                
                
          }            
          try{
             entrada.close();
             escritor.close();
             socket.close();

          }catch(Exception e)
          {
             System.out.println ("Error : " + e.toString());
             socket.close();
             System.exit (0);
          }

        }catch (IOException e) 
        {
          System.out.println("Error---");
          e.printStackTrace();
        }
    }
}
