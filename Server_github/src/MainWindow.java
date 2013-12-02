import java.awt.*;
import java.io.IOException;
import java.util.*;

//import javax.media.CannotRealizeException;
//import javax.media.NoPlayerException;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements ActionListener{
	

	private Container contentPane;
	private JButton Connect;
	private JMenuItem quitItem;
	private JButton Cpi_1;
	private JButton Cpi_2;
	private JButton Cpi_3;
	private JButton Cpi_4;


	private Client_Pi CC_1 = null;
	private Client_Pi CC_2 = null;
	private Client_Pi CC_3 = null;
	private Client_Pi CC_4 = null;
	private String URL_1 = null;
	private String URL_2 = null;
	private String URL_3 = null;
	private String URL_4 = null;

	private int q = 0;
	private BitSet x;    //setup number of connected clients
	private Color unopened = Color.white;
	private Color connected = Color.green;
	private Color warn = Color.red;
	private BitSet warning;

		public MainWindow()
		{
			URL_1 = "http://hubblesource.stsci.edu/sources/video/clips/details/images/hst_1.mpg";
			URL_3 = "http://10.0.0.24:8080";
			URL_4 = "http://10.0.0.24:8081";
			//setup the bits of warning. 0000
			warning = new BitSet(3);
			//warning.set(2, 4);    //testing the warnings
			//setup connected pies. 0000
			x = new BitSet(3);
			
			//setup the frame
			JFrame frame = new JFrame("Server");
			contentPane = frame.getContentPane();
			contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
			//setup the menu bar
			JMenuBar menubar = new JMenuBar();
			frame.setJMenuBar(menubar);
			
			JMenu fileMenu = new JMenu("Options"); //create menu
			menubar.add(fileMenu);   //add menu
			//add quit to the menu
			quitItem = new JMenuItem("Quit");
			fileMenu.add(quitItem);
			
			//setup quit
			quitItem.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
					
				}
			});
			setupMain();  //sets up the main part of the gui
			
			//finalize frame
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			frame.pack();
			frame.setResizable(false);
			frame.setPreferredSize(new Dimension(900, 600));
			frame.setVisible(true);		
		}
		
		public void setupMain()
		{
			//used to reset the board once a pi is connected
			contentPane.removeAll();
			contentPane.revalidate();
			contentPane.repaint();
			//setup the clock
			JPanel Clock = new JPanel();
			Clock.setBorder(BorderFactory.createLineBorder(Color.lightGray));
			DigitalClock myClock = new DigitalClock();
			Clock.add(myClock);
			contentPane.add(Clock);
			//add and setup the button
			JPanel ConnectButton = new JPanel();
		    ConnectButton.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		    Connect = new JButton("Connect A Pi");
		    ConnectButton.add(Connect);
		    Connect.addActionListener(this);
		    contentPane.add(ConnectButton);
		    //add the warning boxes
		    JPanel warnings = new JPanel();
		    warnings.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		    warnings.setLayout(new GridLayout(2,2));
		    
		    //setup the client's connection buttons
		    Cpi_1 = new JButton("Off");
		    warnings.add(Cpi_1);
		    Cpi_1.addActionListener(this);
		    Cpi_1.setBackground(unopened);
		    Cpi_2 = new JButton("Off");
		    warnings.add(Cpi_2);
		    Cpi_2.addActionListener(this);
		    Cpi_2.setBackground(unopened);
		    Cpi_3 = new JButton("Off");
		    warnings.add(Cpi_3); 
		    Cpi_3.addActionListener(this);
		    Cpi_3.setBackground(unopened);
		    Cpi_4 = new JButton("Off");
		    warnings.add(Cpi_4);
		    Cpi_4.addActionListener(this);
		    Cpi_4.setBackground(unopened);
		    //if a client is connected, change the background and label based on it's state
		    //warnings turn it red with "warning" on the label.
		    //connections turn it green with "safe" on the label.
		    if(x.get(0))
		    {
		    	if(!warning.get(0))
		    	{
		    		Cpi_1.setBackground(connected);
		    		Cpi_1.setText("Safe");
		    	}else {
		    		Cpi_1.setBackground(warn);
		    		Cpi_1.setText("warning!");
		    	}
		    }
		    if(x.get(1))
		    {
		    	if(!warning.get(1))
		    	{
		    		Cpi_2.setBackground(connected);
		    		Cpi_2.setText("Safe");
		    	}else {
		    		Cpi_2.setBackground(warn);
		    		Cpi_2.setText("warning!");
		    	}
		        
		    }
		    if(x.get(2))
		    {
		    	if(!warning.get(2))
		    	{
		    		Cpi_3.setBackground(connected);
		    		Cpi_3.setText("Safe");
		    	}else {
		    		Cpi_3.setBackground(warn);
		    		Cpi_3.setText("warning!");
		    	}
		    }
		    if(x.get(3))
		    {
		    	if(!warning.get(3))
		    	{
		    		Cpi_4.setBackground(connected);
		    		Cpi_4.setText("Safe");
		    	}else {
		    		Cpi_4.setBackground(warn);
		    		Cpi_4.setText("warning!");
		    	}
		    }
		    //add the warnings to the pane
		    contentPane.add(warnings);
		    //if 4 clients are connected, then disable the connect button.
		    if(q == 4)
		    {
		    	Connect.setEnabled(false);
		    }
		}
		
		//set the designated client's warning status 
		//should eventually focus on that pi's video
		public void Set_Warnings(BitSet W) throws/* NoPlayerException, CannotRealizeException, */IOException 
		{
			warning.and(W);
			setupMain();
			if(!W.get(0))
			{
				ReplacePi(1);
			}else if(!W.get(1))
			{
				ReplacePi(2);
			}else if(!W.get(2))
			{
				ReplacePi(3);
			}else if(!W.get(3))
			{
				ReplacePi(4);
			}
			
		}
		
		//create a new client_pi up to a max of 4.
		public void SetupPi(int z) throws /*NoPlayerException, CannotRealizeException,*/ IOException
		{
			String l = "" + z;
			System.out.println(l);
			if(z == 1)
			{
				CC_1 = new Client_Pi(l, URL_1);
				Setup.Connect(z, CC_1);
			}else if(z == 2)
			{
				CC_2 = new Client_Pi(l, URL_2);
				Setup.Connect(z, CC_2);
			}else if(z == 3)
			{
				CC_3 = new Client_Pi(l, URL_3);
				Setup.Connect(z, CC_3);
			}else if(z == 4)
			{
				CC_4 = new Client_Pi(l, URL_4);
				Setup.Connect(z, CC_4);
			}
			
		}
		
		//disconnect the designated pi. 
		public void DisconnectPi(int z) throws IOException
		{
			//clear client's frame, set client to null and reset main window
			if(z == 1)
			{
				CC_1.ClearFrame();
				CC_1 = null;
				x.set(0, false);
				setupMain();
			}else if(z == 2)
			{
				CC_2.ClearFrame();
				CC_2 = null;
				x.set(1, false);
				setupMain();
			}else if(z == 3)
			{
				CC_3.ClearFrame();
				CC_3 = null;
				x.set(2, false);
				setupMain();
			}else if(z == 4)
			{
				CC_4.ClearFrame();
				CC_4 = null;
				x.set(3, false);
				setupMain();
			}
		}
		
		//clears the frame of the client
		//used to clear or set the warning response
		public void ReplacePi(int h) throws IOException
		{
			String s = "" + h;
			System.out.println(s);
			if(h == 1)
			{
				CC_1.ClearFrame();
				CC_1.setWindow(s);
			}else if(h == 2)
			{
				CC_2.ClearFrame();
				CC_2.setWindow(s);
			}else if(h == 3)
			{
				CC_3.ClearFrame();
				CC_3.setWindow(s);
			}else if(h == 4)
			{
				CC_4.ClearFrame();
				CC_4.setWindow(s);
			}
		}
		
		//when a button is pressed
		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();
			
			//if it is a jbutton (ie everything that can be pressed)
			if(o instanceof JButton)
			{
				JButton b = (JButton)o; 
				//if it is the connect button
				if(b.equals(Connect))
				{	
					//connect a new client
					System.out.println(x.nextClearBit(3));
					x.set(x.nextClearBit(0));
					System.out.println("button pressed.");
					if(x.get(0) && CC_1 == null)
					{
						try {
							SetupPi(1);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						q++;
						setupMain();
						
					}else if(x.get(1) && CC_2 == null)
					{
						try {
							SetupPi(2);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						q++;
						setupMain();	
					}else if(x.get(2) && CC_3 == null)
					{
						//connection for client 3
						try {
							SetupPi(3);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						q++;
						setupMain();
						//warning.set(0, 2); 
						//ReplacePi(1);    //testing reseting client 1		
					}else if(x.get(3) && CC_4 == null)
					{
						//connection for client 4
						try {
							SetupPi(4);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						q++;
						setupMain();
						//warning.set(0, 4); 
						//setupMain();
					}
				}else if(b.equals(Cpi_1))  //if first client button
				{
					q--;
					System.out.println("pressed warning button 1.");
					if(warning.get(0))   // and if warning is on
					{
						System.out.println("resetting");
						//reset warning
						warning.clear(0);
						//reset main window
						setupMain();
					}else if(!warning.get(0) && x.get(0))
					{
						System.out.println("Disconnecting pi #1");
						try {
							DisconnectPi(1);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}else if(b.equals(Cpi_2))  //if second client button
				{
					q--;
					System.out.println("pressed warning button 2.");
					if(warning.get(1))   // and if warning is on
					{
						System.out.println("resetting");
						//reset warning
						warning.clear(1);
						//reset main window
						setupMain();
					}else if(!warning.get(1) && x.get(1))
					{
						//if warning is not on, then disconnect
						System.out.println("Disconnecting pi #2");
						try {
							DisconnectPi(2);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}else if(b.equals(Cpi_3))  //if third client button
				{
					System.out.println("pressed warning button 3.");
					if(warning.get(2))   // and if warning is on
					{
						System.out.println("resetting");
						//reset warning
						warning.clear(2);
						//reset main window
						setupMain();
					}else if(!warning.get(2) && x.get(2))
					{
						q--;
						//if warning is not on, then disconnect
						System.out.println("Disconnecting pi #3");
						try {
							DisconnectPi(3);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}else if(b.equals(Cpi_4))  //if fourth client button
				{

					System.out.println("pressed warning button 4.");
					if(warning.get(3))   // and if warning is on
					{
						System.out.println("resetting");
						//reset warning
						warning.clear(3);
						//reset main window
						setupMain();
					}else if(!warning.get(3) && x.get(3))
					{
						q--;
						//if warning is not on, then disconnect
						System.out.println("Disconnecting pi #4");
						try {
							DisconnectPi(4);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					
				}
				
			}
		}
	}
