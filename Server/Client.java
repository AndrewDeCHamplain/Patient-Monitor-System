import java.net.*;
import java.io.*;

public class Client {
	
	static String IPaddress = "172.17.32.130";
	
	public static void main(String args[]) throws UnknownHostException, IOException
	{
		Socket client = new Socket(IPaddress, 8081);
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		
		
		//BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		if(client.isConnected())
		{
			out.println("Hi");
		}else
		{
			System.out.println("No connection. unable to send.");
		}
		
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		System.out.println(in.readLine());
		out.println("quit");
	}

}
