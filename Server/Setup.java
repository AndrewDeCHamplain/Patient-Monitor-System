import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Setup
{        
	private static MainWindow gui;
        private static ServerSocket server;
        private static Socket client;
        private static Socket client_1;
        private static Socket client_2;
        private static Socket client_3;
        private static Socket client_4;

 
        
        public static void main(String[] args) throws IOException
        {
                @SuppressWarnings("unused")
				Setup hub = new Setup();
        }
        
        public Setup() throws IOException
        {
        	gui = new MainWindow();
        	server = new ServerSocket(8081);
        }
        
        public static void Connect(int i, Client_Pi who) throws IOException
        {

        	client = server.accept();
        	if(i == 2)
        	{
        		client_2 = client;
        	}else if(i == 3)
        	{
        		client_3 = client;
        	}else if(i == 4)
        	{
        		client_4 = client;
        	}else
        	{
            	client_1 = client;
        	}
        	new ClientThread(client, i, who).start();
        	/*
            ConnectedToWho(i).setSocket(server.accept());
            in = new BufferedReader(new InputStreamReader(ConnectedToWho(i).GetSocket().getInputStream()));
            out = new PrintWriter(ConnectedToWho(i).GetSocket().getOutputStream(), true);
            out.println("Connected to server");
                    */
        }
        
        public static void disconnect(int i) throws IOException
        {
        	if(i == 2)
        	{
        		client_2.close();
        	}else if(i == 3)
        	{
        		client_3.close();
        	}else if(i == 4)
        	{
        		client_4.close();
        	}else
        	{
            	client_1.close();
        	}
        }
        
}
