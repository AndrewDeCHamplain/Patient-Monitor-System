import java.awt.Canvas;
import java.awt.Color;

import java.awt.Dimension;

//import javax.swing.JFrame;
import javax.swing.JPanel;


//import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.version.LibVlcVersion;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

@SuppressWarnings("serial")
public class Video extends JPanel
{
	private Canvas canvas;
	private MediaPlayerFactory mediaPlayerFactory;
	private CanvasVideoSurface videoSurface;
	private EmbeddedMediaPlayer mediaPlayer;
	
	public Video(String url)
	{
		//setupLibVLC();
        NativeLibrary.addSearchPath(
            RuntimeUtil.getLibVlcLibraryName(), "C:\\Users\\Jeff\\Documents\\work\\third year\\semester 1\\Sysc 3010\\Final project stuff\\Server_2\\bin\\win32-amd64"
        );
        Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
       
		canvas = new Canvas();
		canvas.setSize(new Dimension(1280, 720));
		mediaPlayerFactory = new MediaPlayerFactory();
		videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		
		mediaPlayer.setVideoSurface(videoSurface);
		canvas.setVisible(true);
		//Startmedia();
	}

	public void Startmedia()
	{
		mediaPlayer.playMedia("C://Downtown.mp4");
		//mediaPlayer.playMedia("http://hubblesource.stsci.edu/sources/video/clips/details/images/hst_1.mpg");
	}
	
	public void pause()
	{
		//mediaPlayer.getMediaPlayer().stop();
	}
	
	public void closeVideo()
	{
		mediaPlayer.release();
	}
	
}
