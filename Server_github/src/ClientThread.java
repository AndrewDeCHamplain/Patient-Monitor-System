import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientThread extends Thread{
        
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int Pinum;
    private Client_Pi who;
    private String fromClient;
    private MainWindow gui;

    public ClientThread(Socket clientsocket, int i, Client_Pi who, MainWindow gui) throws IOException
    {
        this.gui = gui;
        this.who = who;
        this.socket = clientsocket;
        Pinum = i;
        who.setThread(this);
        
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
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
        //setup delimiter
        who.setURL(socket.getRemoteSocketAddress().toString());
        System.out.println(socket.getRemoteSocketAddress().toString());
                        
        String delims = " ";
        out.println("start");
        //check if it equals the disconnect command
        while(!(fromClient = in.readLine()).equals("disconnect"))
        {

        	String[] tokens = fromClient.split(delims);
        	//System.out.println(tokens[0]);
        	String HeartRate = "", Temperature = "";
        	
        	for (int i = 0; i < 5;i += 2) {
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
                         			who.setTempWarn();
                         			who.setHRWarn();
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
                out.println("start");        
                        
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
