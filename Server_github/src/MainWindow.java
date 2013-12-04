import java.awt.*;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow implements ActionListener{
	
	/*
	 *Create Fields for GUI elements of the server
	 *Includes the master connect button and the buttons corresponding to each Pi
	 */
	private Container contentPane;
	private JButton Connect;
	private JMenuItem quitItem;
	private JButton Cpi_1;
	private JButton Cpi_2;
	private JButton Cpi_3;
	private JButton Cpi_4;

	/*
	 *Create fields for each client window.
	 *For our project there are four, but adding more should be as simple as defining more
	 *This is done so the server window can distinguish the windows of different Pis
	 */
	private Client_Pi CC_1 = null;
	private Client_Pi CC_2 = null;
	private Client_Pi CC_3 = null;
	private Client_Pi CC_4 = null;
	
	/*
	 *Define the constants for the Server window
	 *Includes the colours for different states and bitsets for determining individual client statuses
	 */
	private int q = 0;   					//The number of total client connections
	private BitSet x;    					//4 bits determining which clients are connected
	private static BitSet warning;			//4 bits determining which clients are sending a warning
	private Color unopened = Color.white;	//disconnected client buttons are white
	private Color connected = Color.green;	//Connected client buttons are green
	private Color warn = Color.red;			//Connected clients with warnings are red
	

	/*
	 *Constructor for the main window
	 *Builds the window and initializes bitsets
	 */
	public MainWindow()
	{
											
		warning = new BitSet(3);			//initialize the bits of warning. 0000
		
											
		x = new BitSet(3);					//initialize connected pies. 0000
		
		/*setup the basic GUI elements*/
		JFrame frame = new JFrame("Server");
		contentPane = frame.getContentPane();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		JMenuBar menubar = new JMenuBar();
		frame.setJMenuBar(menubar);
		JMenu fileMenu = new JMenu("Options");
		menubar.add(fileMenu);
		quitItem = new JMenuItem("Quit");
		fileMenu.add(quitItem);
			
		/*setup what happens when quit is selected in the server window*/ 
		quitItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
		
		setupMain();  //sets up the main part of the gui
		
		/*finalize frame*/
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(900, 600));
		frame.setVisible(true);		
	}
	/******************End of MainWindow()***************************/
	/****************************************************************/
	
	/*
	 *Function to refresh all of the elements in the server window
	 *Every time an element of the window changes, this method is called
	 */
	public void setupMain()
	{
		/*reset the board once a pi is connected*/
		contentPane.removeAll();
		contentPane.revalidate();
		contentPane.repaint();
		
		/*setup the clock*/
		JPanel Clock = new JPanel();
		Clock.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		DigitalClock myClock = new DigitalClock();
		Clock.add(myClock);
		contentPane.add(Clock);
		
		/*add and setup the main connect button*/
		JPanel ConnectButton = new JPanel();
	    ConnectButton.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Connect = new JButton("Connect A Pi");
	    ConnectButton.add(Connect);
	    Connect.addActionListener(this);
	    contentPane.add(ConnectButton);
	    
	    /*add the warning boxes*/
	    JPanel warnings = new JPanel();
	    warnings.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    warnings.setLayout(new GridLayout(2,2));
		    
	    /*setup the client's connection buttons*/
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
	    
	    /*if a client is connected, change the background and label based on it's state
	     *warnings turn it red with "warning" on the label.
	     *connections turn it green with "safe" on the label.
	     */
	    if(x.get(0)) {
	    	if(!warning.get(0)){
	    		Cpi_1.setBackground(connected);
	    		Cpi_1.setText("Safe"); 			//Changing the colour to green
	    	}
	    	else {
	    		Cpi_1.setBackground(warn);
	    		Cpi_1.setText("warning!");		//Changing the colour to red
	    	}
	    }
	    if(x.get(1)){
	    	if(!warning.get(1)){
	    		Cpi_2.setBackground(connected);
	    		Cpi_2.setText("Safe");
	    	}
	    	else {
	    		Cpi_2.setBackground(warn);
	    		Cpi_2.setText("warning!");
	    	}
	        
	    }
	    if(x.get(2)){
	    	if(!warning.get(2)){
	    		Cpi_3.setBackground(connected);
	    		Cpi_3.setText("Safe");
	    	}
	    	else {
	    		Cpi_3.setBackground(warn);
	    		Cpi_3.setText("warning!");
	    	}
	    }
	    if(x.get(3)){
	    	if(!warning.get(3)){
	    		Cpi_4.setBackground(connected);
	    		Cpi_4.setText("Safe");
	    	}
	    	else {
	    		Cpi_4.setBackground(warn);
	    		Cpi_4.setText("warning!");
	    	}
	    }
	    
	    /*add the warnings to the pane*/
	    contentPane.add(warnings);
	    
	    /*if 4 clients are connected, then disable the connect button.*/
	    if(q == 4)
	    {
	    	Connect.setEnabled(false);
	    }
	}		
	/*********************End of setupMain()*************************/
	/****************************************************************/
		
	/*
	 *set the designated client's warning status
	 */
	public void Set_Warnings(int j, int u)
	{
		warning.set(j);
		setupMain();
	}
		
	/*
	 *create a new client window up to a max of 4.
	 *@param int z - A number between 1 and 4 corresponding to the Pi
	 */
	public void SetupPi(int z) throws IOException
	{
		String l = "" + z; 	//Create a string from the integer z
		if(z == 1) {
			CC_1 = new Client_Pi(l, this);
			Setup.Connect(z, CC_1);
		}
		else if(z == 2){
				CC_2 = new Client_Pi(l, this);
				Setup.Connect(z, CC_2);
		}
		else if(z == 3)	{
			CC_3 = new Client_Pi(l, this);
			Setup.Connect(z, CC_3);
		}
		else if(z == 4){
			CC_4 = new Client_Pi(l, this);
			Setup.Connect(z, CC_4);
		}
		else {
			System.out.println("Invalid Pi number given for function SetupPi(int z): " + z);
		}
	}
	
	/*
	 *disconnect the designated pi.
	 *@param int z - an integer between 1 and 4 corresponding to a Pi
	 */
	public void DisconnectPi(int z) throws IOException
	{
		/*clear client's frame, set client to null and reset main window*/
		if(z == 1) {
			CC_1.CloseFrame();
			CC_1 = null;
			x.set(0, false);
			setupMain();
		}
		else if(z == 2){
			CC_2.CloseFrame();
			CC_2 = null;
			x.set(1, false);
			setupMain();
		}
		else if(z == 3){
			CC_3.CloseFrame();
			CC_3 = null;
			x.set(2, false);
			setupMain();
		}
		else if(z == 4){
			CC_4.CloseFrame();
			CC_4 = null;
			x.set(3, false);
			setupMain();
		}
		else {
			System.out.println("Invalid Pi number given for function DisconnectPi(int z): " + z);
		}
	}
		
	/*Clears the frame of the client
	 *Used to clear or set the warning response
	 *@param int h - an integer between 1 and 4 corresponding to a Pi
	 */
	public void ReplacePi(int h) throws IOException
	{
		if(h == 1)
		{
			CC_1.ClearFrame();
		}else if(h == 2)
		{
			CC_2.ClearFrame();
		}else if(h == 3)
		{
			CC_3.ClearFrame();
		}else if(h == 4)
		{
			CC_4.ClearFrame();
		}
		else {
			System.out.println("Invalid Pi number given for function ReplacePi(int h): " + h);
		}
	}
		
	/*
	 *When any button is pushed, this method is called
	 *It determines the origin of the button press
	 *and executes instructions based on the origin
	 *@param ActionEvent e - This is the event that triggered this function
	 */
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
				x.set(x.nextClearBit(0));
				if(x.get(0) && CC_1 == null)
				{
					try {
						SetupPi(1);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					q++;
					
				}else if(x.get(1) && CC_2 == null)
				{
					try {
						SetupPi(2);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					q++;	
				}else if(x.get(2) && CC_3 == null)
				{
					//connection for client 3
					try {
						SetupPi(3);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					q++;
				}else if(x.get(3) && CC_4 == null)
				{
					//connection for client 4
					try {
						SetupPi(4);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					q++;
					//warning.set(0, 4); 
					//setupMain();
				}
			}else if(b.equals(Cpi_1))  //if first client button
			{
				if(warning.get(0))   // and if warning is on
				{
					//reset warning
					warning.clear(0);
					CC_1.clearTempWarn();
					CC_1.clearHRWarn();
					CC_1.clearPanic();
					//reset main window
				}else if(!warning.get(0) && x.get(0))
				{
					try {
						DisconnectPi(1);
						q--;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}else if(b.equals(Cpi_2))  //if second client button
			{
				if(warning.get(1))   // and if warning is on
				{
					//reset warning
					warning.clear(1);
					CC_2.clearTempWarn();
					CC_2.clearHRWarn();
					CC_2.clearPanic();
					//reset main window
				}else if(!warning.get(1) && x.get(1))
				{
					//if warning is not on, then disconnect
					try {
						DisconnectPi(2);
						q--;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}else if(b.equals(Cpi_3))  //if third client button
			{
				if(warning.get(2))   // and if warning is on
				{
					//reset warning
					warning.clear(2);
					CC_3.clearTempWarn();
					CC_3.clearHRWarn();
					CC_4.clearPanic();
					//reset main window
				}else if(!warning.get(2) && x.get(2))
				{
					//if warning is not on, then disconnect
					try {
						DisconnectPi(3);
						q--;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}else if(b.equals(Cpi_4))  //if fourth client button
			{
				if(warning.get(3))   // and if warning is on
				{
					//reset warning
					warning.clear(3);
					CC_4.clearTempWarn();
					CC_4.clearHRWarn();
					CC_4.clearPanic();
					//reset main window
				}else if(!warning.get(3) && x.get(3))
				{
					//if warning is not on, then disconnect
					try {
						DisconnectPi(4);
						q--;
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
			setupMain();
		}
	}
}
