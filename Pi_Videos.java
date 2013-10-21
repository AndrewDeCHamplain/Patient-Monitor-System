/**
 * Creates the gui for the recieved video and sound from the connected pi.
 *
 * Has a lot of useless code that will be shaved off as we rework the code.
 * 
 * @author (Jeff Chapman) 
 * @version (1.0.0)
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
//import javax.media.Player;
//import javax.media.Manager;
//import javax.media.CannotRealizeException;
//import javax.media.NoPlayerException;

import javax.swing.*;

public class Pi_videos extends GUI implements ActionListener
{
private JFrame frame;
private Container contentPane;
private JFrame Piframe_1;
private Container contentPanePi_1;
private JToggleButton Pi_soundButton_1;
private JButton Button;
private JTextField pi_1;
private JTextField pi_2;
private JTextField pi_3;
private JTextField pi_4;
private int z;

public /*static*/ void main(/*String[] args*/)
{
    int y = 0;
    z = 0;
    SetupPi_1();
}
/*
 * setup the individual pi's and their output values.
 */
public void SetupPi_1()
{
    JFrame Piframe_1 = new JFrame("Server");
    contentPanePi_1 = Piframe_1.getContentPane();
    contentPanePi_1.setLayout(new BoxLayout(contentPanePi_1, BoxLayout.Y_AXIS));
    Piframe_1.setBounds(250,0, 720, 1080);
    
    JPanel Pi_vid_1 = new JPanel();
    Pi_vid_1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    Pi_vid_1.setSize(720, 480);
    contentPanePi_1.add(Pi_vid_1);
    Pi_vid_1.setBackground(Color.black);
    
    JPanel Pi_sound_1 = new JPanel();
    Pi_sound_1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    JToggleButton Pi_soundButton_1 = new JToggleButton("Play Sound");
    Pi_sound_1.add(Pi_soundButton_1);
    contentPanePi_1.add(Pi_sound_1);
    
    JPanel Pi_sensors_1 = new JPanel();
    Pi_sensors_1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    Pi_sensors_1.setLayout(new BoxLayout(Pi_sensors_1, BoxLayout.X_AXIS));
    
    Temp_count Temp = new Temp_count();
    Pi_sensors_1.add(Temp);
    
    contentPanePi_1.add(Pi_sensors_1);
    
    
    
    
    
     
    
    Piframe_1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    Piframe_1.pack();
    Piframe_1.setResizable(false);
    Piframe_1.setVisible(true);
}

/*
 * listen to the connect buttons
 */
public void actionPerformed(ActionEvent e)
{
    Object o = e.getSource();
    if(o instanceof JToggleButton)
    {
        System.out.println("Toggle button pressed. Playing sound recorded from that pi.");
    }
}
/*public class mediaPlayer extends JFrame
{
    public mediaPlayer()
    {
        setLayout(new BorderLayout());

        //file you want to play
        URL mediaURL = //Whatever
        //create the media player with the media url
        Player mediaPlayer = Manager.createRealizedPlayer(mediaURL);
        //get components for video and playback controls
        Component video = mediaPlayer.getVisualComponent();
        Component controls = mediaPlayer.getControlPanelComponent();
        add(video,BorderLayout.CENTER);
        add(controls,BorderLayout.SOUTH);
    }
}*/


@SuppressWarnings("serial")
static class Temp_count extends JPanel
{
    String stringTemp;
    int temp = 27;
    String single_temp = "";

    Temp_count()
    {
        Timer t_temp = new Timer(2000, new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {

                repaint();
            }
        });
    }
    
    public void setStringTemp(String xyz) {
        this.stringTemp = xyz;
    }
    
    public int findMinimumBetweenTwoNumbers(int a, int b) {
        return (a <= b) ? a : b;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        
        if (temp < 10) {
            single_temp = "0";
        }
        if (temp >= 10) {
            single_temp = "";
        }

        setStringTemp(single_temp + temp + " C");
        g.setColor(Color.BLACK);
        int length = findMinimumBetweenTwoNumbers(this.getWidth(),this.getHeight());
        Font myFont = new Font("SansSerif", Font.PLAIN, length / 5);
        g.setFont(myFont);
        g.drawString(stringTemp, (int) length/6, length/2);

    }
}
}
