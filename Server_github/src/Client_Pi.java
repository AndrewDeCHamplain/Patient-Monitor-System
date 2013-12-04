import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.Socket;
import java.util.BitSet;





import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;


import javax.swing.WindowConstants;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;


@SuppressWarnings("serial")
public class Client_Pi extends JFrame {

		private String URL = null;
        private JFrame Piframe;
        private Container contentPanePi;
        public JToggleButton Pi_soundButton;
        private JTextField temp;
        private JTextField hr;
        private JTextField panic;
        private JTextArea Label;
        private int t = 0;
        private String Z;
        private Socket socket;
        private int n;
        private BitSet warn;
        private BitSet warning;
        private ClientThread thread;
        private EmbeddedMediaPlayerComponent mediaPlayerComponent;
        private String tempText;
        private String hrText;
        private MainWindow Main;
        
        public Client_Pi(String z, BitSet warning, MainWindow main) 
        {
        		this.Main = main;
        		this.warning = warning;
                n = Integer.parseInt(z);
                Z = z;
                CreateWindow(z);
                warn = new BitSet(3);
        }
        
        public void CreateWindow(String Z)
        {
        	Piframe = new JFrame("Client " + Z);
        	setWindow(Z);
        	tempText = "Connected";
        	hrText = "Connected";
        }
        public void setWindow(String Z)
        {
            mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
            Piframe.setContentPane(mediaPlayerComponent);
            setLocationOnScreen();
            Piframe.setMinimumSize(new Dimension(650, 540));            
                    
            contentPanePi = Piframe.getContentPane();
            contentPanePi.setLayout(new BoxLayout(contentPanePi, BoxLayout.Y_AXIS));
            
            JPanel videoPanel = new JPanel();
            videoPanel.setPreferredSize(new Dimension(640, 480));
            contentPanePi.add(videoPanel);

            
            JPanel bottom = new JPanel();
            bottom.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));
            bottom.setLayout(new GridLayout(1, 2));
            bottom.setMaximumSize(new Dimension(640, 100));

            JPanel values = new JPanel();
            values.setBorder(BorderFactory.createLineBorder(Color.lightGray));
            values.setLayout(new BoxLayout(values, BoxLayout.Y_AXIS));
            values.setLayout(new GridLayout(3, 2));
            
            JTextField tempLabel = new JTextField();
            tempLabel.setText("Temperature");
            values.add(tempLabel);
            tempLabel.setEditable(false);
            
             temp = new JTextField();
             values.add(temp);
             temp.setText(tempText);
             temp.setHorizontalAlignment(JTextField.CENTER);
             temp.setEditable(false);
             
             JTextField hrLabel = new JTextField();
             hrLabel.setText("Heart Rate");
             values.add(hrLabel);
             hrLabel.setEditable(false);
             
             hr = new JTextField();
             values.add(hr);
             hr.setText(hrText);
             hr.setHorizontalAlignment(JTextField.CENTER);
             hr.setEditable(false);
             
             JTextField panicLabel = new JTextField();
             panicLabel.setText("Panic Button");
             values.add(panicLabel);
             panicLabel.setEditable(false);
             
              panic = new JTextField();
              values.add(panic);
              panic.setText("safe");
              panic.setHorizontalAlignment(JTextField.CENTER);
              panic.setEditable(false);
                        
             bottom.add(values);
             Label = new JTextArea();
             //Label.append(" hello!\n");
             //Label.append(" hello!\n");
             //Label.append(" hello!");
             Label.setEditable(false);
             bottom.add(Label);
             
            contentPanePi.add(bottom);

            Piframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
             
            Piframe.pack();
            Piframe.setVisible(true);
            videoPanel.setVisible(false);
            
            mediaPlayerComponent.getMediaPlayer().playMedia("http://10.0.0.21:8080/?action=stream");
            //mediaPlayerComponent.getMediaPlayer().playMedia("http:/" + URL  );
            //mediaPlayerComponent.getMediaPlayer().playMedia("http://" + URL + "/?action=stream");
            //mediaPlayerComponent.getMediaPlayer().playMedia("http://hubblesource.stsci.edu/sources/video/clips/details/images/hst_1.mpg");
            //mediaPlayerComponent.getMediaPlayer().playMedia("M:\\Crystallize-LindseyStirling.mp4");
            
        }
        
        public void CloseFrame() throws IOException
        {
            thread.stopConnection();
            mediaPlayerComponent.release();
            Piframe.setVisible(false);
            Piframe.dispose();
            Setup.disconnect(n);
        }
        
        public void ClearFrame()
        {
        	mediaPlayerComponent.release();
        	Piframe.setVisible(false);
        	contentPanePi.removeAll();
			contentPanePi.revalidate();
			contentPanePi.repaint();
			setWindow(Z);
        }
        
/*
            //sets the warning bits
            //if q = 0. then clear the warnings
            // if q = 1, then set the warning for this client
        public BitSet Set(int q)
        {
        		warn = new BitSet(3);
                if(q == 1)
                {
                       warning.set(n, true);
                       return warn;
                       
                }else
                {
                	warning.set(n, false);
                	return warning;
                }
        }
        */
        
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
        
        public void setURL(String URL)
        {
        	this.URL = URL;
            //String IP = URL.substring(1, 10);
            //Label.append(IP);
        	Label.append(Setup.parse(URL));
        }
        
        public void setLabel(String lab)
        {
        	Label.append(lab);
        }
        
        public void setTempText(String in)
        {
        	in += "   ";
        	tempText = in.substring(0, 5);
        	temp.setText(tempText);
        }
        
        public void setHRText(String in)
        {
        	hrText = in;
        	hr.setText(hrText);
        }
        
        public void setTempWarn()
        {
        	temp.setBackground(new Color(209, 68, 68));
        	Main.Set_Warnings(n-1, 1);
        }
        
        public void setHRWarn()
        {
        	hr.setBackground(new Color(209, 68, 68));
        	Main.Set_Warnings(n-1, 1);
        }
        
        public void setPanicWarn()
        {
        	panic.setBackground(new Color(209, 68, 68));
        	panic.setText("PANIC!");
        	Main.Set_Warnings(n-1, 1);
        }
        
        public void clearTempWarn()
        {
        	temp.setBackground(Color.white);
        }
        
        public void clearHRWarn()
        {
        	hr.setBackground(Color.white);
        }
        
        public void clearPanic()
        {
        	panic.setBackground(Color.white);
        	panic.setText("safe");
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
        
        public void hold() throws InterruptedException
        {
        	Thread.sleep(1000);
        }
}
