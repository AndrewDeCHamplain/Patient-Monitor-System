import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;


public class ClientThread extends Thread{
        
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int Pinum;
    private Client_Pi who;
    private String fromClient;
    private MainWindow gui;
    DatagramSocket sendSocket, receiveSocket;
    DatagramPacket sendPacket, receivePacket;
    private int t;
    private String IP;
    private String temp;
    private String hr;
    private String warn;
    private int port;

    public ClientThread(Socket clientsocket, int i, Client_Pi who, MainWindow gui, String IP, int port) throws IOException
    {
    	this.port = port;
    	this.IP = IP;
        this.gui = gui;
        this.who = who;
        this.socket = clientsocket;
        Pinum = i;
        who.setThread(this);
        
        receiveSocket = new DatagramSocket(port);
        sendSocket = new DatagramSocket();
        
        t = 0;
        temp = "";
        hr = "";
        warn = "";
        //in = new BufferedReader(new InputStreamReader(receiveSocket.getInputStream()));
        //out = new PrintWriter(socket.getOutputStream(), true);
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

        byte[] buf = new byte[1024];
        receivePacket = new DatagramPacket(buf, 1024);
        
    	String text = "start";
    	sendPacket = new DatagramPacket(text.getBytes(), text.length(), InetAddress.getByName(IP), port);
        //setup delimiter
        who.setURL(IP);
        //System.out.println(socket.getRemoteSocketAddress().toString());
                        
        String delims = " ";
        //sendPacket.setData("start".getBytes());
        receiveSocket.send(sendPacket);
        
        	
        receiveSocket.receive(receivePacket);
        fromClient = new String(buf);
        while(!(fromClient).equals("disconnect"))
        {
        	System.out.println(fromClient);
        	String[] tokens = fromClient.split(delims);
        	//System.out.println(tokens[0]);
        	String HeartRate = "", Temperature = "";
        	
        	for (int i = 0; i < 5;i += 2) 
        	{
        		String statusType = tokens[i];
        		System.out.println(tokens[i]);
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
                	for(int t = 1; t < 3;t++)
                	{
                		String warningType = tokens[i + t];
                		if (warningType.equals("clear")) 
                		{
                			//clear the warnings
                			if(t == 1)
                			{
                				//do nothing, temp clear received
                				//who.clearTempWarn();
                			}else if(t == 2)
                			{
                				//do nothing, hr clear received
                				//who.clearHRWarn();
                			}else if(t == 3)
                			{
                				//do nothing, panic clear received
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
                /*
                 * Get another status string array here to reiterate loop
                 */
                //fromClient = in.readLine();
                //out.println("received");
                }
        		who.hold();
        		receiveSocket.receive(receivePacket);
                fromClient =  new String(buf);
        		//byte[] temp = new byte[1];
        		
        		//sendPacket = new DatagramPacket(temp, 1, InetAddress.getByName("10.0.0.21"), 8081);
        		//sendSocket.send(sendPacket);
                //out.println("start");        
    		       
    		}
        	disconnect();
        }
        
        //disconnects the client and closes the frame
        public void disconnect() throws IOException
        {
                out.println("disconnect");
                in.close();
                out.close();
                socket.close();
                gui.DisconnectPi(Pinum);
                
                
        }
        
        //sends the disconnect command
        public void stopConnection() throws IOException
        {
                out.println("disconnect");
                in.close();
                out.close();
                socket.close();
        }
        

}
