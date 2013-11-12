import java.net.*;
import java.io.*;


public class Client {
        
        static String IPaddress = "134.117.58.29";
        
        public static void main(String args[]) throws UnknownHostException, IOException
        {
                Socket client = new Socket(IPaddress, 8081);
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
                
                String fromServer, toServer;
                
                while(true)
                {
                	System.out.println(fromServer = in.readLine());
                	toServer = userIn.readLine();
                	if (toServer != null) {
                		System.out.println("Client: " + toServer);
                		out.println(toServer);
                	}
                	if (toServer.equals("quit")) {
                		break;
                	}
                }
                /*
                if(client.isConnected())
                {
                        out.println("Hi");
                }else
                {
                        System.out.println("No connection. unable to send.");
                }
                */
               client.close();
        }

}
