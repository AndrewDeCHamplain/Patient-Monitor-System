import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;

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
        private String Z;
        private int n;
        private int port;
        private ClientThread thread;
        private EmbeddedMediaPlayerComponent mediaPlayerComponent;
        private String tempText;
        private String hrText;
        private MainWindow Main;
        
        public Client_Pi(String z, MainWindow main) 
        {
        	this.Main = main;
            n = Integer.parseInt(z);
            Z = z;
            CreateWindow(z);
        }
        
        //creates the frame
        public void CreateWindow(String Z)
        {
        	Piframe = new JFrame("Client " + Z);
        	setWindow(Z);
        	tempText = "Connected";
        	hrText = "Connected";
        }
        
        //adds everything to the frame
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
            Label.setEditable(false);
            bottom.add(Label);
             
            contentPanePi.add(bottom);

            Piframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
             
            Piframe.pack();
            Piframe.setVisible(true);
            videoPanel.setVisible(false);
            
            //set the window and wait for the url to be set by
            //the thread
            try {
				hold();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            
            //System.out.println(URL + " " + port);
            //mediaPlayerComponent.getMediaPlayer().playMedia("http://" + URL + ":" + port + "/?action=stream");
            //mediaPlayerComponent.getMediaPlayer().playMedia("http:/" + URL  );
            //mediaPlayerComponent.getMediaPlayer().playMedia("http://" + URL + "/?action=stream");
            //mediaPlayerComponent.getMediaPlayer().playMedia("http://hubblesource.stsci.edu/sources/video/clips/details/images/hst_1.mpg");
            //mediaPlayerComponent.getMediaPlayer().playMedia("M:\\Crystallize-LindseyStirling.mp4");
            
        }
        
        //starts the video depending on wheither the client
        //has a camera or not
        public void startVideo(int c)
        {
        	if(c == 1)
        	{
        		System.out.println(URL + " " + port);
        		mediaPlayerComponent.getMediaPlayer().playMedia("http://" + URL + ":8080/?action=stream");
        	}else
        	{
        		//plays a video from the server
        		mediaPlayerComponent.getMediaPlayer().playMedia("C:\\Crystallize-LindseyStirling.mp4");
        	}
        }
        
        //closes the client window
        public void CloseFrame() throws IOException
        {
            thread.stopConnection();
            mediaPlayerComponent.release();
            Piframe.setVisible(false);
            Piframe.dispose();
            //Setup.disconnect(n);
        }
        
        //clear this frame, repaint and reset the window
        public void ClearFrame()
        {
        	mediaPlayerComponent.release();
        	Piframe.setVisible(false);
        	contentPanePi.removeAll();
			contentPanePi.revalidate();
			contentPanePi.repaint();
			setWindow(Z);
        }
        
        //returns a reference to this client window
        public Client_Pi Who()
        {
                return this;
        }
        
        //set the thread for this client window
        public void setThread(ClientThread thread)
        {
                this.thread = thread;
        }
        
        //set the port for this client window
        public void setPort(int p, int c)
        {
        	System.out.println(p);
        	port = p;
        	//startVideo(c);
        }
        
        //set the url for this client window
        //add the patients data to the label
        public void setURL(String URL)
        {
        	//System.out.println(URL);
        	this.URL = URL;
        	setLabel(Setup.parse(URL));
        }
        
        //set the label text
        public void setLabel(String lab)
        {
        	Label.append(lab);
        }
        
        //set the temperature text
        public void setTempText(String in)
        {
        	in += "   ";
        	tempText = in.substring(0, 5);
        	temp.setText(tempText);
        }
        
        //set heart rate text
        public void setHRText(String in)
        {
        	hrText = in;
        	hr.setText(hrText);
        }
        
        //set the temperature warning
        public void setTempWarn()
        {
        	temp.setBackground(new Color(209, 68, 68));
        	Main.Set_Warnings(n-1, 1);
        }
        
        //set the heart rate warning
        public void setHRWarn()
        {
        	hr.setBackground(new Color(209, 68, 68));
        	Main.Set_Warnings(n-1, 1);
        }
        
        //set the panic warning
        public void setPanicWarn()
        {
        	panic.setBackground(new Color(209, 68, 68));
        	panic.setText("PANIC!");
        	Main.Set_Warnings(n-1, 1);
        }
        
        //clear the panic warning
        public void clearPanicWarn()
        {
        	panic.setBackground(Color.white);
        	panic.setText("safe");
        }
        
        //clear the temperature warning
        public void clearTempWarn()
        {
        	temp.setBackground(Color.white);
        }
        
        //clear the heart rate warning
        public void clearHRWarn()
        {
        	hr.setBackground(Color.white);
        }
        
        //returns the width of the screen
        public static int GetScreenWorkingWidth() {
            return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
        }

        //returns the hight of the screen
        public static int GetScreenWorkingHeight() {
            return java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        }
        
        //sets the location of the client's windows on
        //the screen
        public void setLocationOnScreen()
        {
                if(n == 1)
                {
                	//top left
                    Piframe.setLocation(250, 0);
                }else if(n == 2)
                {
                	//top right
                    Piframe.setLocation((GetScreenWorkingWidth()/2)+250, 0);
                }else if(n == 3)
                {
                	//bottom left
                    Piframe.setLocation(250, (GetScreenWorkingHeight()/2));
                }else if(n == 4)
                {
                	//bottom right
                    Piframe.setLocation((GetScreenWorkingWidth()/2)+250, (GetScreenWorkingHeight()/2));
                }
        }
        
        //hold the thread for a short time
        public void hold() throws InterruptedException
        {
        	Thread.sleep(1000);
        }
        
        //returns the number of this client
        public int number()
        {
        	return n;
        }
}
