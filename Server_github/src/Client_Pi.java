import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
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
//import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
//import javax.swing.WindowConstants;


import javax.swing.WindowConstants;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;


//import javax.media.*;


@SuppressWarnings("serial")
public class Client_Pi extends JFrame implements ActionListener{

        @SuppressWarnings("unused")
        private String URL = null;
        private JFrame Piframe;
        private Container contentPanePi;
        public JToggleButton Pi_soundButton;
        private JTextField temp;
        private JTextField hr;
        private int t = 0;
        private String Z;
        private Socket socket;
        private int n;
        private BitSet warn;
        private ClientThread thread;
        private EmbeddedMediaPlayerComponent mediaPlayerComponent;
        
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
            Piframe = new JFrame("Client " + Z);

                    mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
                    Piframe.setContentPane(mediaPlayerComponent);
                    setLocationOnScreen();
                    
            contentPanePi = Piframe.getContentPane();
            contentPanePi.setLayout(new BoxLayout(contentPanePi, BoxLayout.Y_AXIS));
            contentPanePi.setSize(540, 640);
            
            
            JPanel Pi_sound = new JPanel();
            Pi_sound.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            Pi_sound.setLayout(new GridLayout(1, 2));
            Pi_soundButton = new JToggleButton("Pause video");
            Pi_sound.add(Pi_soundButton);
            Pi_sound.setSize(30, 640);
            
            Pi_soundButton.addActionListener(this);
            
            
            JPanel Pi_sensors = new JPanel();
            Pi_sensors.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            Pi_sensors.setLayout(new BoxLayout(Pi_sensors, BoxLayout.X_AXIS));
            Pi_sensors.setSize(30 ,640);
            Pi_sound.add(Pi_sensors);
            
             temp = new JTextField();
             Pi_sensors.add(temp);
             temp.setText("Connected");
             temp.setHorizontalAlignment(JTextField.CENTER);
             temp.setEditable(false);
             
             hr = new JTextField();
             Pi_sensors.add(hr);
             hr.setText("Connected");
             hr.setHorizontalAlignment(JTextField.CENTER);
             hr.setEditable(false);
                        
            contentPanePi.add(Pi_sound);

            //Piframe.setUndecorated(true);
            //getRootPane().setWindowDecorationStyle(JRootPane.NONE);
            Piframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
             
            Piframe.pack();
            //Piframe.setResizable(false);
            Piframe.setVisible(true);
            
            mediaPlayerComponent.getMediaPlayer().playMedia("http://10.0.0.21:8080/?action=stream");
            //mediaPlayerComponent.getMediaPlayer().playMedia("http://hubblesource.stsci.edu/sources/video/clips/details/images/hst_1.mpg");
            //mediaPlayerComponent.getMediaPlayer().playMedia("M:\\Crystallize-LindseyStirling.mp4");
        }
        
        public void ClearFrame() throws IOException
        {
                thread.stopConnection();
                mediaPlayerComponent.release();
            Piframe.setVisible(false);
            Piframe.dispose();
            Setup.disconnect(n);
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

            //sets the warning bits
            //if q = 0. then clear the warnings
            // if q = 1, then set the warning for this client
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
        
        public void setThread(ClientThread thread)
        {
                this.thread = thread;
        }
        
        public Socket GetSocket()
        {
                return socket;
        }
        
        public void setTempText(String in)
        {

             temp.setText(in);
        }
        
        public void setHRText(String in)
        {

             hr.setText(in);
        }
        
        public static int GetScreenWorkingWidth() {
            return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        }

        public static int GetScreenWorkingHeight() {
            return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        }
        
        public void setLocationOnScreen()
        {
                if(n == 1)
                {
                        Piframe.setLocation(250, 0);
                }else if(n == 2)
                {
                        Piframe.setLocation((GetScreenWorkingWidth()/2)+250, 0);
                }else if(n == 3)
                {
                        Piframe.setLocation(250, (GetScreenWorkingHeight()/2));
                }else if(n == 4)
                {
                        Piframe.setLocation((GetScreenWorkingWidth()/2)+250, (GetScreenWorkingHeight()/2));
                }
        }
}
