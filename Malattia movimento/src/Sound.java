

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound 
{
	public Clip clip;
	
	public Sound(String path)
	{
		try 
		{
			AudioInputStream ais= AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
			AudioFormat baseFormat=ais.getFormat();
			AudioFormat decodeFormat= new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,baseFormat.getSampleRate(),16,baseFormat.getChannels(),baseFormat.getChannels()*2,baseFormat.getSampleRate(),false);
				
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat,ais);
			try 
			{
				clip = AudioSystem.getClip();
				clip.open(dais);
			} 
			catch (LineUnavailableException e) {e.printStackTrace();}
		} 
		catch (UnsupportedAudioFileException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
	}
	
	public void Play()
	{
		if(clip==null) return;
		Stop();
		clip.setFramePosition(0);
		clip.start();
		
	}
	public void taglia(int start,int stop)
	{
	clip.setLoopPoints(start,stop);
	clip.loop((Clip.LOOP_CONTINUOUSLY));
	}
	public void Stop()
	{
		if(clip.isRunning()) clip.stop();
	}
	public void infinity()
	{
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void Close()
	{
		Stop();
		clip.close();
	}

}