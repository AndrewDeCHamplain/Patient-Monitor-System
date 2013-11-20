import java.awt.BorderLayout;

import javax.swing.JPanel;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

@SuppressWarnings("serial")
public class Video extends JPanel
{
	private EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
	
	public Video(String url)
	{
		setLayout(new BorderLayout());
		mediaPlayerComponent.getMediaPlayer().prepareMedia("http://hubblesource.stsci.edu/sources/video/clips/details/images/hst_1.mpg");
		add(mediaPlayerComponent, BorderLayout.CENTER);
	}
	
	public void play()
	{
		mediaPlayerComponent.getMediaPlayer().start();
	}
	
	public void pause()
	{
		mediaPlayerComponent.getMediaPlayer().stop();
	}
	
	/*
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
	*/
	
	public void closeVideo()
	{
		mediaPlayerComponent.release();
	}
}
