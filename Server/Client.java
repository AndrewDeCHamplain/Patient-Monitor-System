import java.net.*;
import java.io.*;
import java.util.Random;



public class Client {
        
    private static final int bt = 37;
    private static final int hr = 70;
    private PrintWriter out;
    private BufferedReader in;
    private double CurrentBt;
    private int CurrentHr;
    private String IPaddress;
    private Socket client;
    //"134.117.58.12"
        
    public static void main(String args[]) throws UnknownHostException, IOException
    {
        //String IP = args[0];   
     	Client c = new Client("");
        c.run();
    }
    public Client(String IP) throws UnknownHostException, IOException
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
            
            while(client.isConnected())
            {
                fromServer = in.readLine();
                if(fromServer.equals("disconnect")) {
                    System.out.println(fromServer);
                    break;
                }
                else if (fromServer.equals("start")) {
                    System.out.println(fromServer);
                    sendTemp();
                    sendHeartRate();
                    checkWarning();
                    System.out.println("sent");
                }
                else if(fromServer.equals("warning1")) {
                        System.out.println(fromServer);
                        bodyTempSpike();
                        checkWarning();
                        System.out.println("sent body temp spike");
                }
                else if(fromServer.equals("warning2")) {
                        System.out.println(fromServer);
                        heartRateSpike();
                        checkWarning();
                        System.out.println("sent heart rate spike");
                }
                else if(fromServer.equals("warning3")) {
                        System.out.println(fromServer);
                        heartRateSpike();
                        bodyTempSpike();
                        checkWarning();
                        System.out.println("sent both spikes");
                }
            }
            out.println("disconnect");
            System.out.println("Client was disconnected");
         
            client.close();
        }
        private double temperature()
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
        
        private void sendTemp() throws IOException
        {    
                out.println("temp " + temperature());
               // wait(in.readLine().equals("go"));
        }
        
        private int heartRate()
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
        
        private void sendHeartRate() throws IOException 
        {
            out.println("HR " + heartRate());
        }
        
        private void checkWarning()
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
                    out.println("warning hr");
                    System.out.println("Heart rate warning alert sent to server");
                }
                else if (tempwarn & !hrwarn) {
                    out.println("warning temp");
                    System.out.println("Temperature warning alert sent to server");
                }
                else if (tempwarn & hrwarn) {
                    out.println("warning both");
                    System.out.println("Dual warning alert sent to server");
                }
            }
            else {
                    out.println("warning clear");
            }
        }
        
        private void heartRateSpike()
        {
                CurrentHr = CurrentHr + 50;
        }
        
        private void bodyTempSpike()
        {
                CurrentBt = CurrentBt + 2;
        }
}
