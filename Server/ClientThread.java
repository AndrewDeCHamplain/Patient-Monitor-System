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

	public ClientThread(Socket clientsocket, int i, Client_Pi who) throws IOException
	{
		this.who = who;
		this.socket = clientsocket;
		Pinum = i;

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);

        out.println("Connected to server");
        out.println("Pi #: " + Pinum);
	}
	
	public void run()
	{
		try {
			fromClient = in.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if(!fromClient.equals("disconnect"))
			{
				System.out.println(fromClient);
				who.setText(fromClient);
				out.println("received " + fromClient + " from server");
				try {
					recieve();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//out.flush();
			}else {
				try {
					disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		//}
		
	}
	
	public void recieve() throws IOException
	{
		while(!(fromClient = in.readLine()).equals("&discon&"))
		{
			if(fromClient.equals("temp"))
			{
				while(!(fromClient.equals("&Done&")))
				{
					who.setText(fromClient);
				}
			}
		}	
		disconnect();
	}
	
	
	public Client_Pi WhoIsIt()
	{
		return who;
	}
	
	public void disconnect() throws IOException
	{
		in.close();
		out.close();
		socket.close();
		who.ClearFrame();
		
		
	}
	

}
