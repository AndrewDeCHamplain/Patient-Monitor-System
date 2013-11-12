import java.net.*;
import java.io.*;


public class Setup
{        
        static ServerSocket server;
        static Socket client;
        static PrintWriter out;
        static BufferedReader in;
        static MainWindow gui;
        static Client patient;
       // public static SocketAddress connected;
        
        public static void main(String[] args)
        {
        	gui = new MainWindow();
        	try {
                Connect();    
                } catch (IOException e) {
                        System.out.println("Lost connection");
                        
                        e.printStackTrace();
                }
        	System.exit(0);
        }
        
        
        public static void Connect() throws IOException
        {
        		server = new ServerSocket(8081);
                client = server.accept();
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out = new PrintWriter(client.getOutputStream(), true);
                out.println("Connected to server");
                String fromClient;

                while(!(fromClient = in.readLine()).equals("quit"))
                {
                        
                        System.out.println(fromClient);
                        out.println("received! from server");
                }
                System.out.println(fromClient);
        }
          
}
