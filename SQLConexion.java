package ejercicio1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.*;

/**
 *
 * @author Eric Alejandro López Ayala
 * @group 4cm3
 * @subject Development of Distributed Systems
 */
public class SQLConexion extends Thread{
   protected Socket socketCliente;
   protected BufferedReader entrada;
   protected PrintStream salida;
   protected String consulta;
   
   
    public SQLConexion(Socket socketCliente){
       this.socketCliente = socketCliente;
       try{
           this.entrada = new BufferedReader(new InputStreamReader(this.socketCliente.getInputStream()));
           this.salida =  new PrintStream(this.socketCliente.getOutputStream());
           start();
           salida.flush();
       }
       catch(IOException e){
           System.err.println(e);
           try{
               this.socketCliente.close();
           }
           catch(IOException e2){
               e2.printStackTrace();
           }
       }
   }
    
    @Override
    public void run(){
        try{
            this.consulta = entrada.readLine();
            System.out.println("Consulta a ejecutar: " + consulta + ";");
            ejecutaSQL();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            try{
                socketCliente.close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    
    public void ejecutaSQL(){
        Connection conn;
        Statement st;
        ResultSet rs;
        ResultSetMetaData rsmd;
        boolean existenmasfilas;
        String driver = "com.mysql.jdbc.Driver";
        String usuario = "root", password = "root", registro;
        int numColumnas, i;
        
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection("jdbc:mysql://localhost/mysql", usuario, password);
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            existenmasfilas = rs.next();
            if(!existenmasfilas){
                salida.println("No hay mas filas");
                return;
            }
            rsmd = rs.getMetaData();
            numColumnas = rsmd.getColumnCount();
            System.out.println("Hay: " + numColumnas  + " columnas en el resultado");
            
            while(existenmasfilas){
                registro = "";
                for( i=1; i <= numColumnas; i++){
                    registro = registro.concat(rs.getString(i) + "  ");
                }
                salida.println(registro);
                System.out.println(registro);
                existenmasfilas = rs.next();
            }
            salida.println("-1");
            
            rs.close();
            st.close();
            conn.close();
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
     }
}
