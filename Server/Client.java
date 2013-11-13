import java.net.*;
import java.io.*;
import java.util.Random;



public class Client {
	
		private static int bt = 37;
		private double CurrentBt = 37;
        
        static String IPaddress = "134.117.58.37";
        
        public static void main(String args[]) throws UnknownHostException, IOException
        {
                Socket client = new Socket(IPaddress, 8081);
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
                
                @SuppressWarnings("unused")
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
        
        public double temperature()
        {
        	Random rand = new Random();
        	int m = rand.nextInt(50);
    		double d = m/100.0;
        	if(bt > CurrentBt)
        	{
        		return CurrentBt = CurrentBt + d;
        	}else
        	{
        		return CurrentBt = CurrentBt - d;
        	}
        }

}
