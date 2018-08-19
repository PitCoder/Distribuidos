package ejercicio1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Eric Alejandro López Ayala
 * @group 4cm3
 * @subject Development of Distributed Systems
 */
public class Servidor extends Thread{
    public static final int PUERTO = 8000;
    ServerSocket Servidor;
    
    public Servidor(){
        try{
            Servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor operativo....");
            start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run(){
        try{
           for(;;){
               Socket socketCliente =  Servidor.accept();
               SQLConexion miConexion = new SQLConexion(socketCliente);
           }
        }
        catch(IOException e){
            e.printStackTrace();
        }   
    }
    
    public static void main(String[] args){
        new Servidor();
    }
}
