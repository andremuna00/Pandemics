

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite 
{
	public BufferedImage image;
	
	public Sprite(String path)
	{
		try 
		{
			image=ImageIO.read(getClass().getResource(path));
			
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public BufferedImage getBufferedImage()
	{
		return image;
	}
}
