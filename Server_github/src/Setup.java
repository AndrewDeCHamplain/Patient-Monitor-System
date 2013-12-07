import java.io.*;
import java.util.ArrayList;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;


public class Setup
{        
    private static MainWindow gui;
    private static Thread client;
    private static Thread client_1;
    private static Thread client_2;
    private static Thread client_3;
    private static Thread client_4;
    private static XmlParser parser;
    private static ArrayList<String> IP;
    private static ArrayList<Integer> port;
    private static ArrayList<Integer> camera;

 
        
    public static void main(String[] args) throws IOException
    {
    	
    	//used to import the library needed to use vlcj
        NativeLibrary.addSearchPath(
        RuntimeUtil.getLibVlcLibraryName(), "./vlc-2.1.1");
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
        
        @SuppressWarnings("unused")
        Setup hub = new Setup();
    }
        
    //sets up the server side of the program
    public Setup() throws IOException
    {
    	//very inefficient way to setup the clients
    	//could eventually use xml to pull the client's info from
    	camera = new ArrayList<Integer>();
    	port = new ArrayList<Integer>();
    	IP = new ArrayList<String>();
        gui = new MainWindow();
        parser = new XmlParser();
        IP.add("10.0.0.22");
        IP.add("10.0.0.21");
        IP.add("10.0.0.24");
        IP.add("10.0.0.23");
        port.add(8082);
        port.add(8081);
        port.add(8084);
        port.add(8083);
        camera.add(1);
        camera.add(1);
        camera.add(0);
        camera.add(0);      
    }
      
    //connects a new thread that will talk to the clients
    public static void Connect(int i, Client_Pi who) throws IOException
    {
        new ClientThread(client, i, who, gui, IP.get(i-1), port.get(i-1), camera.get(i-1)).start();
    }
   
    //parses the xml and returns a string that consists of the 
    //patient's info.
    public static String parse(String IP)
    {
    	return parser.getPatientInfo(IP);
    }
    
    //sets a reference to each thread
    //could use an array list to make it expandable
    public void setThread(int i, Thread thread)
    {
    	if(i == 2) {
            client_2 = thread;
    	}
    	else if(i == 3) {
            client_3 = thread;
    	}
    	else if(i == 4) {
	    client_4 = thread;
    	}
    	else {
	    client_1 = thread;
    	}
    }

}
