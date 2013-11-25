import java.awt.Color;
import java.awt.Container;
//import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;



@SuppressWarnings("serial")
public class Client_Pi extends JFrame implements ActionListener{

	private String URL = null;
	private JFrame Piframe_1;
	private Container contentPanePi;
	public JToggleButton Pi_soundButton;
	private JTextField temp;
	private JTextField hr;
	private int t = 0;
	private String Z;
	private Video Pi_vid;
	private Socket socket;
	private int n;
	
	public Client_Pi(String z, String ip)
	{
		n = Integer.parseInt(z);
		URL = ip;
		Z = z;
		setWindow(z);
	}
	public void setWindow(String Z) 
	{
		//setup frame
		Piframe_1 = new JFrame("Client " + Z);
	    contentPanePi = Piframe_1.getContentPane();
	    contentPanePi.setLayout(new BoxLayout(contentPanePi, BoxLayout.Y_AXIS));
	    Piframe_1.setLocation(250,0);
	    //Piframe_1.setSize(new Dimension(1300, 820));
	    /*
	    //setup video JPanel
		Pi_vid = new Video(URL);
	    Pi_vid.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    //Pi_vid.setSize(720, 480);
	    contentPanePi.add(Pi_vid);
	    Pi_vid.setBackground(Color.black);
	    */
	    //setup the JPanel for the toggle button
	    JPanel Pi_sound = new JPanel();
	    Pi_sound.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_soundButton = new JToggleButton("Play Sound");
	    Pi_sound.add(Pi_soundButton);
	    contentPanePi.add(Pi_sound);
	    Pi_soundButton.addActionListener(this);
	    
	    //setup the JPanel for the displayed temperature and heart rate
	    JPanel Pi_sensors = new JPanel();
	    Pi_sensors.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_sensors.setLayout(new BoxLayout(Pi_sensors, BoxLayout.X_AXIS));
	    
	    //add text field to display received values
	     temp = new JTextField();
	     Pi_sensors.add(temp);
	     temp.setText("Connected");
	     temp.setHorizontalAlignment(JTextField.CENTER);

	    //have to add a second text field for the eventual heart rate values
	     hr = new JTextField();
	     Pi_sensors.add(hr);
	     hr.setText("Connected");
	     hr.setHorizontalAlignment(JTextField.CENTER);
	     
	    //add the JPanel to the frame
	    contentPanePi.add(Pi_sensors);
	    
	    //complete the frame, set visible and lock resizing.
	    Piframe_1.pack();
	    Piframe_1.setResizable(false);
	    Piframe_1.setVisible(true);
	}
	/*
	public void start()
	{
	    //try to start the video that should be in the frame
	    Pi_vid.Startmedia(); // not actually playing a video for some reason
	}
	*/
	
	//disposes of the frame and disconnects the socket associated with this client
	public void ClearFrame() throws IOException
	{
		Piframe_1.setVisible(false);
		Piframe_1.dispose();
		Pi_vid.closeVideo();
		Setup.disconnect(n);
		//MainWindow.DisconnectPi(n); // can't call non-static
	}
	
	
	//response to the toggle button being pressed
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o instanceof JToggleButton)
		{
			if(t == 0)
			{
				t++;
				System.out.println("Client #" + Z + " Video toggled on.");
				//play video / sound
				
			}else
			{
				t = 0;
				System.out.println("Client #" + Z + " Video toggled off.");
				//pause video / sound
				
			}
			//play sound
		}
	}
	
	//returns a reference to this client
	public Client_Pi Who()
	{
		return this;
	}
	
	//sets a reference to the socket that is connected to this client
	public void setSocket(Socket sock)
	{
		socket = sock;
	}
	
	//returns the socket associated with this client
	public Socket GetSocket()
	{
		return socket;
	}
	
	//basic method to set the temp text received from this client
	public void setTempText(String in)
	{
	     temp.setText(in);
	}
	
	//basic method to set the heart rate text received from this client
	public void setHRText(String in)
	{
	     hr.setText(in);
	}
}
