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

        out.println("Connected to server");
        out.println("Pi #: " + Pinum);
    }
        
    public void run()
    {
        try {
        receive();
        } catch (IOException e) {
        	e.printStackTrace();
        }                
    }
        
        //a loop to keep receiving
    public void receive() throws IOException
    {
        //setup delimiter
        		
        String delims = " &";
        fromClient = in.readLine();
        out.println("received");
        //check if it equals the disconnect command
        while(!(fromClient).equals("disconnect"))
        {
        	
          	while (!in.readLine().equals("ready")) {
           		//loop until client has values
           	}
           	out.println("start");
           	while ((fromClient = in.readLine()).equals("ready")) {
           		//When the message from the client no longer reads "ready" it has sent usable values
           	}
            String[] tokens = fromClient.split(delims);
            String HeartRate = "", Temperature = "";
        
            for (int i = 0; i < 6;i = i + 2) {
            	String statusType = tokens[i];
                if (statusType.equals("temp")) {
                	//if it is a temperature, set the new temperature value
                    Temperature = tokens[i + 1];
                    who.setTempText(Temperature);
                }
                else if (statusType.equals("hr")) {
                	HeartRate = tokens[i + 1];
                    who.setHRText(HeartRate);
                }
               	else if (statusType.equals("warning")) {
              		String warningType = tokens[i + 1];
              		if (warningType.equals("clear")) {
              			/*Clear the warning field in GUI
              			 * 
              			 */
              		}
                    else {
                    	int a = 1;
                       	while (i + a < tokens.length) {
                       		if (tokens[i+a].equals("temp")) {
                       			/*
                       			 * Set the temperature warning field
                   				 * You will need a separate field in the GUI
              					 */
                       		}
                       		else if (tokens[i+a].equals("hr")) {
                       			/*
                       			 * Set the Heart Rate warning field
                       			 * You will need a separate field in the GUI
                    			 * This code is written to allow for modularity,
                       			 * so display multiple warnings if applicable
                       			 * Another warning type should only require one more else if loop in this code
                       			 */
                       		}
                       		else {
              					System.out.println("Unexpected Warning Type: " + tokens[i + a]);
              				}
                       		a++;
               			}
              		}
              	}
                else {
              		System.out.println("Unexpected Status Type: " + statusType);
              	}
                /*
                 * Get another status string array here to reiterate loop
                 */
                fromClient = in.readLine();
                out.println("received");
            }
                        /*
                        if(tokens[0].equals("temp"))
                        {
                                //if it is a temp, set the new temp
                                who.setTempText(tokens[1]);
                                out.println("go");
                                //get next string
                                fromClient = in.readLine();
                                tokens = fromClient.split(delims);
                        }
                        if(tokens[0].equals("hr"))
                        {
                                //if it is the heart rate, set the new heart rate
                                who.setHRText(tokens[1]);
                                out.println("go");
                                //get the next string
                                fromClient = in.readLine();
                                tokens = fromClient.split(delims);
                        }
                        if(tokens[0].equals("warning"))
                        {
                                if(tokens[1].equals("hr"))
                                {
                                        who.setHRText("warning!");
                                        out.println("go");
                                        //MainWindow.Set_Warnings(who.Set(1));
                                        //get the next string
                                        fromClient = in.readLine();
                                        tokens = fromClient.split(delims);
                                }
                                else if(tokens[1].equals("temp"))
                                {
                                        who.setTempText("warning!");
                                        //get the next string
                                        fromClient = in.readLine();
                                        tokens = fromClient.split(delims);
                                }
                                else if(tokens[1].equals("both"))
                                {
                                        who.setHRText("warning!");
                                        who.setTempText("warning!");
                                        //get the next string
                                        fromClient = in.readLine();
                                        tokens = fromClient.split(delims);
                                }
                                else if(tokens[1].equals("clear"))
                                {
                                	//get the next string
                                	fromClient = in.readLine();
                                	tokens = fromClient.split(delims);
                                }
                                
                                o++;
                                out.println("start"); 
                        }
                        */
                        
                        
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
