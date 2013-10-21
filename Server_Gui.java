/**
 * Creates the basic layout of the server gui. Works in blue jay
 * will not work on the pi yet.
 * Need to have it working on the pi before we can work on it connecting to
 * the client pi's.
 *
 * Has a lot of code that isn't needed. And comments need to be added.
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

public class GUI implements ActionListener
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

public /*static*/ void main(String[] args)
{
    int y = 0;
    z = 0;
    Setup(y);
}
public void Setup(int x)
{
    JFrame frame = new JFrame("Server");
    contentPane = frame.getContentPane();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
    
    
    JPanel Clock = new JPanel();
    Clock.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    DigitalClock myClock = new DigitalClock();
    Clock.add(myClock);
    contentPane.add(Clock);
    
    JPanel ConnectButton = new JPanel();
    ConnectButton.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    Button = new JButton("Connect A Pi");
    ConnectButton.add(Button);
    Button.addActionListener(this);
    contentPane.add(ConnectButton);
    
    JPanel warnings = new JPanel();
    warnings.setBorder(BorderFactory.createLineBorder(Color.lightGray));
    warnings.setLayout(new GridLayout(2,2));
    
    JTextField pi_1 = new JTextField();
    warnings.add(pi_1);
    pi_1.setBackground(Color.white);
    JTextField pi_2 = new JTextField();
    warnings.add(pi_2);
    pi_2.setBackground(Color.white);
    JTextField pi_3 = new JTextField();
    warnings.add(pi_3); 
    pi_3.setBackground(Color.white);
    JTextField pi_4 = new JTextField();
    warnings.add(pi_4);
    pi_4.setBackground(Color.white);
    if(x >= 1)
    {
        pi_1.setBackground(Color.green);
        //pi_1.setText("safe");
    }
    if(x >= 2)
    {
        pi_2.setBackground(Color.green);
    }
    if(x >= 3)
    {
        pi_3.setBackground(Color.green);
    }
    if(x >= 4)
    {
        pi_4.setBackground(Color.green);
    }
    
    
    contentPane.add(warnings);
    
    
    

    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setResizable(false);
    frame.setVisible(true);
    
}
/*
 * setup the individual pi's and their output values.
 */
public void SetupPi_1()
{
    JFrame Piframe_1 = new JFrame("Server");
    contentPanePi_1 = Piframe_1.getContentPane();
    contentPanePi_1.setLayout(new BoxLayout(contentPanePi_1, BoxLayout.Y_AXIS));
    Piframe_1.setBounds(250,0, 1740, 2080);
    
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
    
    if(o instanceof JButton)
    {
        System.out.println("button pressed. Ready for client connection.");
        //place connection software here.
        z++;
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
        Setup(z);
        if(z ==1)
        {
            SetupPi_1();
        }
        //Pi_videos.main();
    }
    if(o instanceof JToggleButton)
    {
        System.out.println("Toggle button pressed. Playing sound recorded from that pi.");
    }
}
@SuppressWarnings("serial")
static class DigitalClock extends JPanel {

    String stringTime;
    int hour, minute, second;

    String correctionHour = "";
    String correctionMinute = "";
    String correctionSecond = "";

    public void setStringTime(String xyz) {
        this.stringTime = xyz;
    }

    public int findMinimumBetweenTwoNumbers(int a, int b) {
        return (a <= b) ? a : b;
    }

    DigitalClock() {

        Timer t1 = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                repaint();
            }
        });
        t1.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Calendar now = Calendar.getInstance();
        hour = now.get(Calendar.HOUR_OF_DAY);
        minute = now.get(Calendar.MINUTE);
        second = now.get(Calendar.SECOND);

        if (hour < 10) {
            this.correctionHour = "0";
        }
        if (hour >= 10) {
            this.correctionHour = "";
        }

        if (minute < 10) {
            this.correctionMinute = "0";
        }
        if (minute >= 10) {
            this.correctionMinute = "";
        }

        if (second < 10) {
            this.correctionSecond = "0";
        }
        if (second >= 10) {
            this.correctionSecond = "";
        }
        setStringTime(correctionHour + hour + ":" + correctionMinute+ minute + ":" + correctionSecond + second);
        g.setColor(Color.BLACK);
        int length = findMinimumBetweenTwoNumbers(this.getWidth(),this.getHeight());
        Font myFont = new Font("SansSerif", Font.PLAIN, length / 5);
        g.setFont(myFont);
        g.drawString(stringTime, (int) length/6, length/2);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
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
