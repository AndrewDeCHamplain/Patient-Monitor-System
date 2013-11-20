import java.net.*;
import java.io.*;
import java.util.Random;



public class Client {
	
		private static int bt = 37;
		private static double CurrentBt = 37;
		private static PrintWriter out;
        
        static String IPaddress;
        //"134.117.58.12"
        
        public static void main(String args[]) throws UnknownHostException, IOException
        {
        		IPaddress = args[0];
                Socket client = new Socket(IPaddress, 8081);
                out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
                
                @SuppressWarnings("unused")
				String fromServer, toServer;
                
                while(client.isConnected())
                {
                        System.out.println(fromServer = in.readLine());
                        toServer = userIn.readLine();
                        if (toServer != null) {
                                System.out.println("Client: " + toServer);
                                out.println(toServer);
                        }
                        if (toServer.equals("disconnect")) {
                                break;
                        }
                        if(toServer.equals("temp"))
                        {
                        	sendTemp(50);
                        }
                }
                out.println("&discon&");
                client.close();
                System.out.println("Client was disconnected");
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
        
        public static double temperature()
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
        
        public static void sendTemp(int n)
        {
        	for(int i = n;i > 0; i--)
        	{
        		out.println("" + temperature());
        	}
        	
        }

}
