import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * The ClientThread class is actually run server-side and it interfaces directly with clientpi.py
 * A new ClientThread is created for every Pi that connects to the server. It sets up the connection
 * with the Pi and receives messages containing patient status. It then parses the messages and sends
 * information to the Client_Pi GUI class to be displayed on screen.
 */
public class ClientThread extends Thread{
        
    @SuppressWarnings("unused")
    private Thread thread;
    private int Pinum;
    private Client_Pi who;
    private String fromClient;
    private MainWindow gui;
    DatagramSocket sendSocket, receiveSocket;
    DatagramPacket sendPacket, receivePacket;
    private String IP;
    private int port;
    private int cameraAttached;
    
	/**
	 * Constructor for ClientThread. Initializes the variables for the GUI and sets up a UDP socket.
	 */
    public ClientThread(Thread clientthread, int i, Client_Pi who, MainWindow gui, String IP, int port, int c) throws IOException
    {
    	cameraAttached = c;
    	this.port = port;
    	this.IP = IP;
        this.gui = gui;
        this.who = who;
        this.thread = clientthread;
        Pinum = i;
        who.setThread(this);
        
        receiveSocket = new DatagramSocket(port);
        sendSocket = new DatagramSocket();
        
    }
    /**
     * run() is necessary because threads in Java require a run() function.
     * It basically just attempts to call the receive() function, So it makes the formatting a little cleaner.
     */
    public void run()
    {
        try {
        receive();
        } catch (IOException | InterruptedException e) {
                e.printStackTrace();
        }                
    }
    
    /**
     * After a connection has been opened with a client, ClientThread will loop in the receive() function
     * until it receives a disconnect message from the client.
     * The message it continuously receives from the client is 8 words long and formatted as such:
     * "temp <temperature value> hr <heart rate value> warning <value1> <value2> <value3>"
     * where the warning values are either "clear", "hr", "temp" or "panic". "clear" means no warning is set.
     */
    public void receive() throws IOException, InterruptedException
    {
    	System.out.println("setup connection");
        byte[] buf = new byte[1024];
        receivePacket = new DatagramPacket(buf, 1024);
        
    	String text = "start";
    	sendPacket = new DatagramPacket(text.getBytes(), text.length(), InetAddress.getByName(IP), port);
        //setup delimiter
        who.setURL(IP);
        who.setPort(port, cameraAttached);
                        
        String delims = " ";
        receiveSocket.send(sendPacket);
        	
        Thread.sleep(10);
        receiveSocket.receive(receivePacket);
        fromClient = new String(buf);
        who.startVideo(cameraAttached);
        while(!(fromClient).equals("disconnect"))
        {
        	System.out.println(fromClient);
        	String[] tokens = fromClient.split(delims);
        	String HeartRate = "", Temperature = "";

            if(fromClient.equals("starting"))
            {
            	//if starting is received, then loop around
            	//and receive another packet
            }else
            {
            	for (int i = 0; i < 5;i += 2) 
            	{
            		String statusType = tokens[i];
            		if (statusType.equals("temp")) 
            		{
            			//if it is a temperature, set the new temperature value
            			Temperature = tokens[i + 1];
            			who.setTempText(Temperature);
            		}else if (statusType.equals("hr"))
            		{
            			HeartRate = tokens[i + 1];
            			who.setHRText(HeartRate);
            		}else if (statusType.equals("warning")) 
            		{
            			for(int t = 1; t <= 3;t++)
            			{
            				String warningType = tokens[i + t];
            				if (warningType.equals("clear")) 
            				{
            					//clear the warnings
            					if(t == 1)
            					{
            						//do nothing, temp clear received
            					}else if(t == 2)
            					{
            						//do nothing, hr clear received
            					}else if(t == 3)
            					{
            						who.clearPanicWarn();
            						//clear panic received
            					}
            				}else
            				{
                    	
            					if (tokens[i+t].equals("temp")) 
            					{
            						who.setTempWarn();
            			
            					}else if (tokens[i+t].equals("hr")) 
            					{
            						who.setHRWarn();
            					
            					}else if (tokens[i+t].equals("panic"))
            					{
                            	  	//add panic button logic
                         			who.setPanicWarn();
            					}else
            					{
            						System.out.println("Unexpected Warning Type: " + tokens[i + t]);
            					}
            				}
            			}
            		}else
            		{
            			System.out.println("Unexpected Status Type: " + statusType);
                      break;
            		}
            	}
                
        		who.hold();
        		receiveSocket.receive(receivePacket);
                fromClient =  new String(buf);
            }
    		}
        	disconnect();
        }
        
        /**
         * When the client sends a disconnect message, this method is run and the thread is closed
         */
        public void disconnect() throws IOException
        {
        	receiveSocket.close();
        	sendSocket.close();
            Thread.currentThread().interrupt();
            gui.DisconnectPi(Pinum);     
        }
        
        /**
         * This method works the same as disconnect() except that the GUI is not updated
         */ 
        public void stopConnection() throws IOException
        {
        	receiveSocket.close();
        	sendSocket.close();
            Thread.currentThread().interrupt();
        }
}
