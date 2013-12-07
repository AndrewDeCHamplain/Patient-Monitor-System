import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


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

    public ClientThread(Thread clientthread, int i, Client_Pi who, MainWindow gui, String IP, int port, int c) throws IOException
    {
    	cameraAttached = c;
    	//System.out.println(who.number());
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
        
    public void run()
    {
        try {
        receive();
        } catch (IOException | InterruptedException e) {
                e.printStackTrace();
        }                
    }
    
    //a loop to keep receiving
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
            						/*
            						 * Set the temperature warning field
            						 * You will need a separate field in the GUI
            						 */
            					}else if (tokens[i+t].equals("hr")) 
            					{
            						who.setHRWarn();
            						/*
            						 * Set the Heart Rate warning field
            						 * You will need a separate field in the GUI
            						 * This code is written to allow for modularity,
            						 * so display multiple warnings if applicable
            						 * Another warning type should only require one more else if loop in this code
            						 */
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
                /*
                 * Get another status string array here to reiterate loop
                 */
                
                
        		who.hold();
        		receiveSocket.receive(receivePacket);
                fromClient =  new String(buf);
            }
    		}
        	disconnect();
        }
        
        //disconnects the client and closes the frame
        public void disconnect() throws IOException
        {
        	receiveSocket.close();
        	sendSocket.close();
            Thread.currentThread().interrupt();
            gui.DisconnectPi(Pinum);     
        }
        
        //sends the disconnect command
        public UncaughtExceptionHandler stopConnection() throws IOException
        {
        	receiveSocket.close();
        	sendSocket.close();
            Thread.currentThread().interrupt();
			return null;
        }
}
