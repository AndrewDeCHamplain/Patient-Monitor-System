import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.BitSet;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;


public class Setup
{        
        private static MainWindow gui;
    //private static ServerSocket server;
    private static Socket client;
    private static Socket client_1;
    private static Socket client_2;
    private static Socket client_3;
    private static Socket client_4;
    private static XmlParser parser;
    private static String IP;
    private static int port;

 
        
    public static void main(String[] args) throws IOException
    {
            NativeLibrary.addSearchPath(
            RuntimeUtil.getLibVlcLibraryName(), "./vlc-2.1.1"
        );
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
            
        @SuppressWarnings("unused")
                Setup hub = new Setup();
    }
        
    public Setup() throws IOException
    {
              gui = new MainWindow();
              parser = new XmlParser();
              IP = "10.0.0.21";
              port = 8081;
    }
        
    public static void Connect(int i, Client_Pi who) throws IOException
    {
               //client = server.accept();
              if(i == 2) {
                      client_2 = client;
               }
               else if(i == 3) {
                        client_3 = client;
               }
               else if(i == 4) {
                client_4 = client;
        }
        else {
            client_1 = client;
        }
               new ClientThread(client, i, who, gui, IP, port).start();
        }
        
    public static void disconnect(int i) throws IOException
    {
               if(i == 2) {
                       client_2.close();
               }
               else if(i == 3) {
                       client_3.close();
               }
               else if(i == 4) {
                       client_4.close();
               }
               else {
                   client_1.close();
               }
    }
    
    public static String parse(String IP)
    {
    	return parser.getPatientInfo(IP);
    }

}
