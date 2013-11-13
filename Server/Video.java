import java.awt.*;
import java.io.IOException;
import java.net.URL;

import javax.media.*;
import javax.swing.*;



//requires JMF library.

@SuppressWarnings("serial")
public class Video extends JPanel
{
	
	private Player audioPlayer = null;
	
	public Video(URL url) throws NoPlayerException, CannotRealizeException, IOException
	{
		setLayout(new BorderLayout());
		Start(url);
	}	
	
	public void Start(URL url) throws NoPlayerException, CannotRealizeException, IOException
	{
		URL mediaURL = url;
		audioPlayer = Manager.createRealizedPlayer(mediaURL);
		
		Component vid = audioPlayer.getVisualComponent();
		add(vid, BorderLayout.CENTER);
	}
}
