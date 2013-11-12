import java.awt.*;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow implements ActionListener{
	
private JFrame Piframe_1;
private Container contentPanePi_1;
private JFrame Piframe_2;
private Container contentPanePi_2;
private Container contentPane;
private JButton Connect;
private JMenuItem quitItem;
private JMenuItem quitItem1;
private JMenuItem quitItem2;
private JToggleButton Pi_soundButton_1;
private JToggleButton Pi_soundButton_2;
private int t1 = 0;
private int t2 = 0;
private int x = 0;    //setup number of connected clients
private Color unopened = Color.white;
private Color connected = Color.green;
private Color warn = Color.red;
private int warning = 0;

	public MainWindow()
	{
		
		//MainWindow test = new MainWindow();
		//test.SetupMainWindow();
	//}
	
	//public void SetupMainWindow()
	//{
		
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
	    
	    JTextField pi_1 = new JTextField();
	    warnings.add(pi_1);
	    pi_1.setBackground(unopened);
	    JTextField pi_2 = new JTextField();
	    warnings.add(pi_2);
	    pi_2.setBackground(unopened);
	    JTextField pi_3 = new JTextField();
	    warnings.add(pi_3); 
	    pi_3.setBackground(unopened);
	    JTextField pi_4 = new JTextField();
	    warnings.add(pi_4);
	    pi_4.setBackground(unopened);
	    if(x >= 1)
	    {
	    	if(warning == 0)
	    	{
	        	pi_1.setBackground(connected);
	        	//pi_1.setText("safe");
	    	}else if(warnings == 1)
	    	{
	    		Pi_1.setBackground(warning);
	    		pi_1.setText("warning!");
	    	}
	    }
	    if(x >= 2)
	    {
	        pi_2.setBackground(connected);
	    }
	    if(x >= 3)
	    {
	        pi_3.setBackground(connected);
	    }
	    if(x >= 4)
	    {
	        pi_4.setBackground(connected);
	    }
	    //add the warnings to the pane
	    contentPane.add(warnings);	
	}
	
	
	public void SetupPi_1()
	{
		Piframe_1 = new JFrame("Client 1");
	    contentPanePi_1 = Piframe_1.getContentPane();
	    contentPanePi_1.setLayout(new BoxLayout(contentPanePi_1, BoxLayout.Y_AXIS));
	    Piframe_1.setBounds(250,0, 720, 1080);
	    
	    JMenuBar menubar1 = new JMenuBar();
		Piframe_1.setJMenuBar(menubar1);
	    
	    JMenu fileMenu1 = new JMenu("Options"); //create menu
		menubar1.add(fileMenu1);   //add menu
		//add quit to the menu
		quitItem1 = new JMenuItem("Quit");
		fileMenu1.add(quitItem1);
		
		//setup quit
		quitItem1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
	    
	    JPanel Pi_vid_1 = new JPanel();
	    Pi_vid_1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_vid_1.setSize(720, 480);
	    contentPanePi_1.add(Pi_vid_1);
	    Pi_vid_1.setBackground(Color.black);
	    
	    JPanel Pi_sound_1 = new JPanel();
	    Pi_sound_1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_soundButton_1 = new JToggleButton("Play Sound");
	    Pi_sound_1.add(Pi_soundButton_1);
	    contentPanePi_1.add(Pi_sound_1);
	    Pi_soundButton_1.addActionListener(this);
	    
	    JPanel Pi_sensors_1 = new JPanel();
	    Pi_sensors_1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_sensors_1.setLayout(new BoxLayout(Pi_sensors_1, BoxLayout.X_AXIS));
	    
	    //Temp_count Temp = new Temp_count();
	    //Pi_sensors_1.add(Temp);
	    
	    contentPanePi_1.add(Pi_sensors_1);
	    
	    Piframe_1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    Piframe_1.pack();
	    Piframe_1.setResizable(false);
	    Piframe_1.setVisible(true);
	}
	
	public void SetupPi_2()
	{
		Piframe_2 = new JFrame("Client 2");
	    contentPanePi_2 = Piframe_2.getContentPane();
	    contentPanePi_2.setLayout(new BoxLayout(contentPanePi_2, BoxLayout.Y_AXIS));
	    Piframe_2.setBounds(250,0, 720, 1080);
	    
	    JMenuBar menubar2 = new JMenuBar();
		Piframe_2.setJMenuBar(menubar2);
	    
	    JMenu fileMenu2 = new JMenu("Options"); //create menu
		menubar2.add(fileMenu2);   //add menu
		//add quit to the menu
		quitItem2 = new JMenuItem("Quit");
		fileMenu2.add(quitItem2);
		
		//setup quit
		quitItem2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				
			}
		});
	    
	    JPanel Pi_vid_2 = new JPanel();
	    Pi_vid_2.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_vid_2.setSize(720, 480);
	    contentPanePi_2.add(Pi_vid_2);
	    Pi_vid_2.setBackground(Color.black);
	    
	    JPanel Pi_sound_2 = new JPanel();
	    Pi_sound_2.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_soundButton_2 = new JToggleButton("Play Sound");
	    Pi_sound_2.add(Pi_soundButton_2);
	    contentPanePi_2.add(Pi_sound_2);
	    Pi_soundButton_2.addActionListener(this);
	    
	    JPanel Pi_sensors_2 = new JPanel();
	    Pi_sensors_2.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_sensors_2.setLayout(new BoxLayout(Pi_sensors_2, BoxLayout.X_AXIS));
	    
	    //Temp_count Temp = new Temp_count();
	    //Pi_sensors_1.add(Temp);
	    
	    contentPanePi_2.add(Pi_sensors_2);
	    
	    Piframe_2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    Piframe_2.pack();
	    Piframe_2.setResizable(false);
	    Piframe_2.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o instanceof JButton)
		{
			//JButton b = (JButton)o; // if we want to use the button
			//rest of connect stuff here
			x += 1;
			setupMain();
			System.out.println("button pressed.");
			if(x == 1)
			{
				//MainWindow test1 = new MainWindow();
				SetupPi_1();
				/*
				try {
					Setup.Connect();
				} catch (IOException e1) {
					System.out.println("Failed to connect.");
					e1.printStackTrace();
				}
				*/
			}else if(x == 2)
			{
				//MainWindow test2 = new MainWindow();
				SetupPi_2();
				
			}else if(x == 3)
			{
				
			}else
			{
				
			}
			
		}else if(o instanceof JToggleButton)
		{
			JToggleButton sound = (JToggleButton)o;
			if(sound.equals(Pi_soundButton_1))
			{
				if(t1 == 0)
				{
					System.out.println("Client 1 Sound Toggled On");
					t1 = 1;
				}else 
				{
					System.out.println("Client 1 Sound Toggled Off");
					t1 = 0;
				}
				
			}else if(sound.equals(Pi_soundButton_2))
			{
				if(t2 == 0)
				{
					System.out.println("Client 2 Sound Toggled On");
					t2 = 1;
				}else 
				{
					System.out.println("Client 2 Sound Toggled Off");
					t2 = 0;
				}
			}
			//code to play music when we get that ready
			
		}
		
	}
}
