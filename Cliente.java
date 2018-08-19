package ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Eric Alejandro López Ayala
 * @group 4cm3
 * @subject Development of Distributed Systems
 */
public class Cliente {
    protected static final String LOCALHOST = "127.0.0.1";
    protected static final int PUERTO = 8000;
    protected static Socket cliente;
    
    public static void main(String[] args) {
        String query = JOptionPane.showInputDialog(
                null,
                "Query SQL",
                JOptionPane.QUESTION_MESSAGE
        );
        System.out.println(query);
        
        try{
            cliente = new Socket(LOCALHOST, PUERTO);
            PrintStream salida = new PrintStream(cliente.getOutputStream());
            salida.println(query);
            salida.flush();
            
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            String tuple = "";
            
            while(!tuple.equals("-1")){
                tuple = entrada.readLine();
                System.out.println(tuple);
            }
            
            entrada.close();
            salida.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
              cliente.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
