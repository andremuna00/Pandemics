import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
//aria //bici
public class MainClass extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private static Graphics g;
	public static int totale_giri;
	public static int kont=0;
	public static int time=0;
	public static int people=50000;
	public static int r_mov=400;
	public static int r_cont=20;
	public static boolean quarantine=false;
	public static boolean from_the_beginning=false;
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs==null)
		{
			createBufferStrategy(3);
			return;
		}
		g= bs.getDrawGraphics();
		g.setColor(Color.black);
		g.drawRect(0, 0, 400, 70);
		g.setColor(Color.red);
		g.fillRect(0, 0, (kont*400)/totale_giri, 70);
		g.dispose();
		bs.show();
	}
	
	private synchronized void start()
	{	
		thread=new Thread(this,"Thread");
		thread.start();
	}
	
	public void run()
	{
		while(true)
		render();
	}
	
	public static void main(String[] args) throws IOException 
	{
		
		double p_c=10;
		double p_m=0.02;
		double p_g=25;	
		int n_v=1;
		double[] p_v= new double [n_v];
		
		for(int i=0;i<n_v;i++)
		{
			p_v[i]=Double.parseDouble(JOptionPane.showInputDialog("Please inputd vaccino "+(i+1)));	
		}
		int lato=501;
		int numero_giri=10000;
		totale_giri =numero_giri*n_v;
		MainClass main=new MainClass();
		
		JFrame frame = new JFrame("MAT");
		frame.setSize(400, 70);
		frame.add(main);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		main.start();
		
		for(int m=0;m<n_v;m++)
		{
				int k=0;
				Model mondo = new Model(lato,p_c,p_m,p_g,p_v[m],time,people,r_cont,r_mov,quarantine,from_the_beginning);
				
				String nome_normali=Double.toString(p_v[m])+"_normali.txt";
				FileWriter fileout_normali = new FileWriter(nome_normali);
				BufferedWriter filebuf_normali = new BufferedWriter(fileout_normali);
				PrintWriter printout_normali = new PrintWriter(filebuf_normali);

				String nome_guariti=Double.toString(p_v[m])+"_guariti.txt";
				FileWriter fileout_guariti = new FileWriter(nome_guariti);
				BufferedWriter filebuf_guariti = new BufferedWriter(fileout_guariti);
				PrintWriter printout_guariti = new PrintWriter(filebuf_guariti);

				String nome_morti=Double.toString(p_v[m])+"_morti.txt";
				FileWriter fileout_morti = new FileWriter(nome_morti);
				BufferedWriter filebuf_morti = new BufferedWriter(fileout_morti);
				PrintWriter printout_morti = new PrintWriter(filebuf_morti);

				String nome_contatore=Double.toString(p_v[m])+"_contatore.txt";
				FileWriter fileout_contatore = new FileWriter(nome_contatore);
				BufferedWriter filebuf_contatore = new BufferedWriter(fileout_contatore);
				PrintWriter printout_contatore = new PrintWriter(filebuf_contatore);
				
				
				do
				{
					mondo.diffusione();
					
					printout_normali.println(mondo.perc_normali());
					printout_guariti.println(mondo.perc_guariti());
					printout_morti.println(mondo.perc_morti());
					printout_contatore.println(mondo.getContatore());
		
					mondo.setContatore(0);
					k++;
					kont++;
				}
				while(k<numero_giri);
				printout_normali.close();
				printout_guariti.close();
				printout_morti.close();
				printout_contatore.close();
			}
		int finito=Integer.parseInt(JOptionPane.showInputDialog("Please finito "));	
	}
}

