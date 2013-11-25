import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import java.util.BitSet;

//import javax.media.CannotRealizeException;
//import javax.media.NoPlayerException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
//import javax.swing.WindowConstants;


//import javax.media.*;


@SuppressWarnings("serial")
public class Client_Pi extends JFrame implements ActionListener{

	@SuppressWarnings("unused")
	private String URL = null;
	private JFrame Piframe_1;
	private Container contentPanePi;
	public JToggleButton Pi_soundButton;
	private JTextField chat;
	private int t = 0;
	private String Z;
	@SuppressWarnings("unused")
	private Video Pi_vid;
	private Socket socket;
	private int n;
	private BitSet warn;
	
	public Client_Pi(String z, String ip) //throws NoPlayerException, CannotRealizeException, IOException
	{
		n = Integer.parseInt(z);
		URL = ip;
		Z = z;
		setWindow(z);
		warn = new BitSet(3);
	}
	public void setWindow(String Z) //throws NoPlayerException, CannotRealizeException, IOException
	{
		Piframe_1 = new JFrame("Client " + Z);
	    contentPanePi = Piframe_1.getContentPane();
	    contentPanePi.setLayout(new BoxLayout(contentPanePi, BoxLayout.Y_AXIS));
	    Piframe_1.setBounds(250,0, 720, 1080);
	    
	    /*
		Pi_vid = new Video(URL);
	    Pi_vid.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_vid.setSize(720, 480);
	    contentPanePi.add(Pi_vid);
	    Pi_vid.setBackground(Color.black);
	    */
	    
	    JPanel Pi_sound = new JPanel();
	    Pi_sound.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_soundButton = new JToggleButton("Play Sound");
	    Pi_sound.add(Pi_soundButton);
	    contentPanePi.add(Pi_sound);
	    Pi_soundButton.addActionListener(this);
	    
	    JPanel Pi_sensors = new JPanel();
	    Pi_sensors.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_sensors.setLayout(new BoxLayout(Pi_sensors, BoxLayout.X_AXIS));
	    
	     chat = new JTextField();
	     Pi_sensors.add(chat);
	     chat.setText("Connected");
	     chat.setHorizontalAlignment(JTextField.CENTER);

	    
	    //Temp_count Temp = new Temp_count();
	    //Pi_sensors_1.add(Temp);
	    
	    contentPanePi.add(Pi_sensors);
	    
	    //Piframe_1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    Piframe_1.pack();
	    Piframe_1.setResizable(false);
	    Piframe_1.setVisible(true);
	}
	
	public void ClearFrame() throws IOException
	{
		Piframe_1.setVisible(false);
		Piframe_1.dispose();
		Setup.disconnect(n);
		//MainWindow.DisconnectPi(n); // can't call non-static
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o instanceof JToggleButton)
		{
			if(t == 0)
			{
				t++;
				System.out.println("Client #" + Z + " Video toggled on.");
				//Pi_vid.play();
				
			}else
			{
				t = 0;
				System.out.println("Client #" + Z + " Video toggled off.");
				//Pi_vid.pause();
				
			}
			//play sound
		}
	}
	public BitSet Set(int q)
	{
		if(q == 1)
		{
			if(n == 0)
			{
				warn.set(0, true);
				return warn;
			}
			else if(n == 1)
			{
				warn.set(1, true);
				return warn;
			}else if(n == 2)
			{
				warn.set(2, true);
				return warn;
			}else if(n == 3)
			{
				warn.set(3, true);
				return warn;
			}
		}
		warn = new BitSet(3);
		return warn;
	}
	
	public Client_Pi Who()
	{
		return this;
	}
	
	public void setSocket(Socket sock)
	{
		socket = sock;
	}
	
	public Socket GetSocket()
	{
		return socket;
	}
	
	public void setText(String in)
	{

	     chat.setText(in);
	}
}
