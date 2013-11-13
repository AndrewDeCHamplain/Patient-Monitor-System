import java.awt.*;
import java.util.*;

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

private int q = 0;
private BitSet x;    //setup number of connected clients
private Color unopened = Color.white;
private Color connected = Color.green;
private Color warn = Color.red;
private BitSet warning;

	public MainWindow()
	{
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
	
	public Client_Pi ReturnClient(int p)
	{
		
		if(p == 2)
		{
			return CC_2;
		}else if(p == 3)
		{
			return CC_3;
		}else if(p == 4)
		{
			return CC_4;
		}else
		{
			return CC_1;
		}
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
	    if(q == 1)
	    {
	    	Connect.setEnabled(false);
	    }
	}
	
	public void Set_Warnings(BitSet W)
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
	
	
	public void SetupPi(int z)
	{
		String l = "" + z;
		System.out.println(l);
		if(z == 1)
		{
			CC_1 = new Client_Pi(l);
		}else if(z == 2)
		{
			CC_2 = new Client_Pi(l);
		}else if(z == 3)
		{
			CC_3 = new Client_Pi(l);
		}else if(z == 4)
		{
			CC_4 = new Client_Pi(l);
		}
		
	}
	
	public void DisconnectPi(int z)
	{
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
	
	public void ReplacePi(int h)
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
	
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o instanceof JButton)
		{
			JButton b = (JButton)o; // if we want to use more than one button
			//rest of connect stuff here
			if(b.equals(Connect))
			{
				System.out.println(x.nextClearBit(3));
				x.set(x.nextClearBit(0));
				setupMain();
				System.out.println("button pressed.");
				if(x.get(0) && CC_1 == null)
				{
					//MainWindow test1 = new MainWindow();
					//SetupPi_1();
					SetupPi(1);
				}else if(x.get(1) && CC_2 == null)
				{
					SetupPi(2);
					//MainWindow test2 = new MainWindow();
					//SetupPi_2();		
				}else if(x.get(2) && CC_3 == null)
				{
					//warning.set(0, 2); 
					//ReplacePi(1);    //testing reseting client 1		
				}else if(x.get(3) && CC_4 == null)
				{
					q = 1;
					//warning.set(0, 4); 
					setupMain();
				}
			}else if(b.equals(Cpi_1))  //if first client button
			{
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
					DisconnectPi(1);
				}
			}else if(b.equals(Cpi_2))  //if second client button
			{
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
					System.out.println("Disconnecting pi #2");
					DisconnectPi(2);
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
					System.out.println("Disconnecting pi #3");
					DisconnectPi(3);
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
					System.out.println("Disconnecting pi #4");
					DisconnectPi(4);
				}
				
			}
			
		}
	}
}
