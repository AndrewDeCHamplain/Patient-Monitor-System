import java.net.*;
import java.io.*;
import java.util.Random;



public class Client {
        
                private static final int bt = 37;
                private static double CurrentBt = 37;
                private static final int hr = 70;
                private static int CurrentHr = hr;
                private static PrintWriter out;
                private static BufferedReader in;
        
        static String IPaddress;
        //"134.117.58.12"
        
        public static void main(String args[]) throws UnknownHostException, IOException
        {
                IPaddress = args[0];
                Socket client = new Socket(IPaddress, 8081);
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
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
                                sendTemp();
                        }
                }
                out.println("&discon&");
                System.out.println("Client was disconnected");
             
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
        
        public static void sendTemp()
        {    
        	out.println("temp " + temperature());
        }
        
        public static int heartRate()
        {
        	Random rand = new Random();
        	int rateChange = rand.nextInt(4);
        	int sign = rand.nextInt(2);
        	if (hr - 15 > CurrentHr) {
        		return CurrentHr = CurrentHr + rateChange;
        	}
        	else if (hr + 15 < CurrentHr){
        		return CurrentHr = CurrentHr - rateChange;
        	}
        	else {
        		if(sign == 0){
        			return CurrentHr = CurrentHr + rateChange;
        		}
        		else {
        			return CurrentHr = CurrentHr - rateChange;
        		}
        	}
        }
        
        public static void sendHeartRate() 
        {
        	out.println("HR " + heartRate());
        }
        
        public static void groinKick()
        {
        	CurrentHr = CurrentHr + 50;
        	sendHeartRate();
        }
        

}
