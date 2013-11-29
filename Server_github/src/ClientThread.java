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
    private static String fromClient;
    private int o;
    private MainWindow gui;

        public ClientThread(Socket clientsocket, int i, Client_Pi who, MainWindow gui) throws IOException
        {
                this.gui = gui;
                this.who = who;
                this.socket = clientsocket;
                Pinum = i;
                o = 0;
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
                //out.println("start");
                fromClient = in.readLine();
                //setup delimiter
                String delims = " &";
                String[] tokens = fromClient.split(delims);
                //check if it equals the disconnect command
                while(!(fromClient).equals("disconnect"))
                {
                        if(o == 21)
                        {
                                stopConnection(); 
                        }else if(o == 0)
                        {
                        	out.println("start");
                        	o++;
                        }
                        
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