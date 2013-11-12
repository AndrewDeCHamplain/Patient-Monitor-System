import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;


@SuppressWarnings("serial")
public class Client_Pi extends JFrame implements ActionListener{

	private JFrame Piframe_1;
	private Container contentPanePi;
	private JMenuItem quitItem1;
	public JToggleButton Pi_soundButton;
	private int t = 0;
	private String Z;
	
	public Client_Pi(String z)
	{
		Z = z;
		setWindow(z);
	}
	public void setWindow(String Z)
	{
		Piframe_1 = new JFrame("Client " + Z);
	    contentPanePi = Piframe_1.getContentPane();
	    contentPanePi.setLayout(new BoxLayout(contentPanePi, BoxLayout.Y_AXIS));
	    Piframe_1.setBounds(250,0, 720, 1080);
	    
	    JMenuBar menubar1 = new JMenuBar();
		Piframe_1.setJMenuBar(menubar1);
	    
	    JMenu fileMenu1 = new JMenu("Options"); //create menu
		menubar1.add(fileMenu1);   //add menu
		//add quit to the menu
		quitItem1 = new JMenuItem("Disconnect");
		fileMenu1.add(quitItem1);
		
		//setup quit
		quitItem1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ClearFrame();
				
			}
		});
	    
	    JPanel Pi_vid = new JPanel();
	    Pi_vid.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_vid.setSize(720, 480);
	    contentPanePi.add(Pi_vid);
	    Pi_vid.setBackground(Color.black);
	    
	    JPanel Pi_sound = new JPanel();
	    Pi_sound.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_soundButton = new JToggleButton("Play Sound");
	    Pi_sound.add(Pi_soundButton);
	    contentPanePi.add(Pi_sound);
	    Pi_soundButton.addActionListener(this);
	    
	    JPanel Pi_sensors = new JPanel();
	    Pi_sensors.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	    Pi_sensors.setLayout(new BoxLayout(Pi_sensors, BoxLayout.X_AXIS));
	    
	    //Temp_count Temp = new Temp_count();
	    //Pi_sensors_1.add(Temp);
	    
	    contentPanePi.add(Pi_sensors);
	    
	    Piframe_1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    Piframe_1.pack();
	    Piframe_1.setResizable(false);
	    Piframe_1.setVisible(true);
	}
	
	public void ClearFrame()
	{
		contentPanePi.removeAll();
		//contentPanePi.revalidate();
		//contentPanePi.repaint();
	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o instanceof JToggleButton)
		{
			if(t == 0)
			{
				t++;
				System.out.println("Client #" + Z + " Video toggled on.");
			}else
			{
				t = 0;
				System.out.println("Client #" + Z + " Video toggled off.");
			}
			//play sound
		}
	}
}
