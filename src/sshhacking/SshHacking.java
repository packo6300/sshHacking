/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sshhacking;

import service.SSHConnector;
import com.jcraft.jsch.JSchException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import visual.Principal;

/**
 *
 * @author sistemas10
 */
public class SshHacking {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try{  
           
            Properties p = new Properties();
            p.load(new FileInputStream("config.ini"));
            String usuario=p.getProperty("user");
            String password=p.getProperty("pass");
            String host =p.getProperty("host");
            Integer port =Integer.parseInt(p.getProperty("port"));
            Boolean visual= Boolean.parseBoolean(p.getProperty("visual"));
            if(visual){
                Principal principal = new Principal();
                principal.setVisible(true);
                principal.setLocationRelativeTo(null);
            }
            else{
                SSHConnector ssh= new SSHConnector();
                ssh.connect(usuario,password,host, port);            
                String cadena;
                FileReader f;
                FileWriter log;            
                f = new FileReader("comands.txt");
                log= new FileWriter("log.txt");
                PrintWriter pw;
                pw = new PrintWriter(log);
                BufferedReader b = new BufferedReader(f);
                    while((cadena = b.readLine())!=null) {
                        String result = ssh.executeCommand(cadena);
                        pw.println(result);
                        System.out.print(result);
                    }
                JOptionPane.showMessageDialog(null, "Operación realizada correctamente");
                log.close();
                ssh.disconnect();
                System.exit(0);
            }
        }
        catch(JSchException d){
            System.out.println(d);
            JOptionPane.showMessageDialog(null, "error");
            System.exit(0);
        } catch (IOException ex) {
            Logger.getLogger(SshHacking.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SshHacking.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
