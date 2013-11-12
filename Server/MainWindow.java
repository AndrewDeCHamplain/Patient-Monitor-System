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


private Client_Pi CC_1;
private Client_Pi CC_2;
private Client_Pi CC_3;
private Client_Pi CC_4;

private int q = 0;
private int x = 0;    //setup number of connected clients
private Color unopened = Color.white;
private Color connected = Color.green;
private Color warn = Color.red;
private BitSet warning;

	public MainWindow()
	{
		//setup the bits of warning. 0000
		warning = new BitSet(3);
		//warning.set(2, 4);    //testing the warnings
		
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
	    if(x >= 1)
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
	    if(x >= 2)
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
	    if(x >= 3)
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
	    if(x >= 4)
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
				x += 1;
				setupMain();
				System.out.println("button pressed.");
				if(x == 1)
				{
					//MainWindow test1 = new MainWindow();
					//SetupPi_1();
					SetupPi(1);
				}else if(x == 2)
				{
					SetupPi(2);
					//MainWindow test2 = new MainWindow();
					//SetupPi_2();		
				}else if(x == 3)
				{
					//warning.set(0, 2); 
					//ReplacePi(1);    //testing reseting client 1		
				}else
				{
					q = 1;
					warning.set(0, 4); 
					setupMain();
				}
			}else if(b.equals(Cpi_1))  //if first client button
			{
				System.out.println("pressed warning button 1.");
				if(warning.get(0))   // and if warning is on
				{
					System.out.println("reseting");
					//reset warning
					warning.clear(0);
					//reset main window
					setupMain();
				}
			}else if(b.equals(Cpi_2))  //if second client button
			{
				System.out.println("pressed warning button 2.");
				if(warning.get(1))   // and if warning is on
				{
					System.out.println("reseting");
					//reset warning
					warning.clear(1);
					//reset main window
					setupMain();
				}
			}else if(b.equals(Cpi_3))  //if third client button
			{
				System.out.println("pressed warning button 3.");
				if(warning.get(2))   // and if warning is on
				{
					System.out.println("reseting");
					//reset warning
					warning.clear(2);
					//reset main window
					setupMain();
				}
			}else if(b.equals(Cpi_4))  //if fourth client button
			{
				System.out.println("pressed warning button 4.");
				if(warning.get(3))   // and if warning is on
				{
					System.out.println("reseting");
					//reset warning
					warning.clear(3);
					//reset main window
					setupMain();
				}
			}
			
		}
	}
}
