import java.net.*;
import java.io.*;
import java.util.Random;



public class Client {
        
    private final int bt = 37;
    private final int hr = 70;
    private PrintWriter out;
    private BufferedReader in;
    private double CurrentBt;
    private int CurrentHr;
    private String IPaddress;
    private Socket client;
    //"134.117.58.12"
        
    public static void main(String args[]) throws UnknownHostException, IOException
    {  
             Client c = new Client();
        c.run();
        c.close();
    }
    public Client() throws UnknownHostException, IOException
    {
        IPaddress = "localhost";
        CurrentBt = bt;
        CurrentHr = hr;
        client = new Socket(IPaddress, 8081);
        out = new PrintWriter(client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    }
        
    public void run() throws IOException
    {
            String fromServer;

            while(!(fromServer = in.readLine()).equals("disconnect"))
            {
                temperature();                                                         //Refresh temperature value
                heartRate();                                                        //Refresh Heart Rate value
               // out.println("ready");
/*
                for(int w = 0; w < 1000; w++)
    			{
    				//wait for shit
    			}
*/                
                if(fromServer.equals("disconnect")) {
                	System.out.println(fromServer);                
                	break;                                                                //Stop running
                }
                else if (fromServer.equals("start")) {
                	System.out.println(fromServer);
                	out.println(statusUpdate());
                }
                else if(fromServer.equals("warning1")) {
                	System.out.println(fromServer);
                	bodyTempSpike();
                	out.println(statusUpdate());
                	System.out.println("sent body temp spike");
                }
                else if(fromServer.equals("warning2")) {
                	System.out.println(fromServer);
                	heartRateSpike();
                	out.println(statusUpdate());
                	System.out.println("sent heart rate spike");
                }
                else if(fromServer.equals("warning3")) {
                	System.out.println(fromServer);
                	heartRateSpike();
                	bodyTempSpike();
                	out.println(statusUpdate());
                	System.out.println("sent both spikes");
                }
                else 
                {
                	out.println("unexpected");
                    System.out.println("Unexpected message from server: " + fromServer);
                }
            }
    	}

    	private void temperature()
    	{
    		Random rand = new Random();
    		int m = rand.nextInt(50);
    		double d = m/100.0;
    		if(bt > CurrentBt) {
                CurrentBt = CurrentBt + d;
    		}
    		else {
                CurrentBt = CurrentBt - d;
    		}
    	}
    
        private void heartRate()
        {
            Random rand = new Random();
            int rateChange = rand.nextInt(4);
            int sign = rand.nextInt(2);
            if (hr - 15 > CurrentHr) {
                CurrentHr = CurrentHr + rateChange;
            }
            else if (hr + 15 < CurrentHr){
                CurrentHr = CurrentHr - rateChange;
            }
            else {
                if(sign == 0){
                    CurrentHr = CurrentHr + rateChange;
                }
                else {
                    CurrentHr = CurrentHr - rateChange;
                }
            }
        }
      
        private String checkWarning()
        {
                
            boolean warning = false;
            String warningList = "";
            if (CurrentBt < bt - 1 || CurrentBt > bt + 1) {
                warningList = warningList + "temp ";
                //warning = true;
            }else 
            {
            	warningList = warningList + "clear ";
            }
            if (CurrentHr < hr - 20 || CurrentHr > hr + 30) {
                warningList = warningList + "hr ";
                //warning = true;
            }else
            {
            	warningList = warningList + "clear ";
            }
            
            
            //if(warning) {
            return "warning " + warningList;
            //}
            //else {
               // return "warning clear";
            //}
        }
        
        private String statusUpdate()
        {
                return (getCurrentHr() + getCurrentBt() + checkWarning());
        }
        
        private void heartRateSpike()
        {
                CurrentHr = CurrentHr + 50;
        }
        
        private void bodyTempSpike()
        {
                CurrentBt = CurrentBt + 2;
        }
        
        public String getCurrentHr()
        {
                return "hr " + CurrentHr + " ";
        }
        
        public String getCurrentBt()
        {
                return "temp " + String.format("%.2g%n", CurrentBt) + " ";
        }
        
        public void close() throws IOException
        {
                 out.println("disconnect");
             System.out.println("Client was disconnected");
             in.close();
             out.close();
             client.close();
        }
        
        
}
