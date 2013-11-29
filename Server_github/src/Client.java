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
           
        while(client.isConnected()) {
        	
        	temperature(); 							//Refresh temperature value
        	heartRate();							//Refresh Heart Rate value
            fromServer = in.readLine();				//Check for message from server
            if(fromServer.equals("disconnect")) {
                System.out.println(fromServer);		
                break;								//Stop running
            }
            else if (fromServer.equals("start")) {
                System.out.println(fromServer);
                statusUpdate();
                System.out.println("sent");
            }
            else if(fromServer.equals("warning1")) {
                System.out.println(fromServer);
                bodyTempSpike();
                statusUpdate();
                System.out.println("sent body temp spike");
            }
            else if(fromServer.equals("warning2")) {
                System.out.println(fromServer);
                heartRateSpike();
                statusUpdate();
                System.out.println("sent heart rate spike");
            }
            else if(fromServer.equals("warning3")) {
                System.out.println(fromServer);
                heartRateSpike();
                bodyTempSpike();
                statusUpdate();
                System.out.println("sent both spikes");
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
            boolean hrwarn = false , tempwarn = false;
            if (CurrentBt < bt - 1 || CurrentBt > bt + 1) {
                tempwarn = true;
            }
            if (CurrentHr < hr - 20 || CurrentHr > hr + 30) {
                hrwarn = true;
            }
            
            if(hrwarn || tempwarn) {
                if (hrwarn & !tempwarn) {
                    return "warning hr";
                }
                else if (tempwarn & !hrwarn) {
                    return "warning temp";
                }
                else {
                    return "warning both";
                }
            } 
            else {
                return "warning clear";
            }
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
             client.close();
        }
        
}
