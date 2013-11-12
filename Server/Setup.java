
import java.net.*;
import java.io.*;


public class Setup extends MainWindow{
	
	static ServerSocket server;
	public static SocketAddress connected;
	
	public static void main(String[] args)
	{
		Setup test = new Setup();
		try {
			test.setup();
		} catch (IOException e) {
			System.out.println("did not setup.");
			e.printStackTrace();
		}
		
	}
	
	public void setup() throws IOException
	{
		server = new ServerSocket(8081);
		server.setReuseAddress(true);
		
		//Connect();
		
	}
	
	public static void Connect() throws IOException
	{
		Socket clientSocket = server.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		connected = clientSocket.getLocalSocketAddress();

		while(in.readLine().equals("quit") == false)
		{
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
			//processInput(in.readLine());
			System.out.println(in.readLine());
			out.println("Why hello there.");
		}
		
	}
	
}
