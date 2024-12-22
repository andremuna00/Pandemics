import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainClass extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private static Graphics g;
	public static int totale_giri;
	public static int kont=0;
	
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
	
	private synchronized void stop()
	{
		try{thread.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public void run()
	{
		while(true)
		render();
	}
	
	public static void main(String[] args) throws IOException 
	{
		
		int nm=0;
		nm=Integer.parseInt(JOptionPane.showInputDialog("Please input n di % morte "));	
		double p_c=0;
		double[] p_m=new double[nm];
		double[] p_g=new double[7];
		p_g[0]=2;
		p_g[1]=4;
		p_g[2]=7;
		p_g[3]=13;
		p_g[4]=22;
		p_g[5]=39;
		p_g[6]=70;
		
		p_c=Double.parseDouble(JOptionPane.showInputDialog("Please inputd contagio "));	
		
		for(int i=0;i<nm;i++)
		{
			p_m[i]=Double.parseDouble(JOptionPane.showInputDialog("Please inputd morte "+(i+1)));			
	    }

		int lato=11;
		int numero_giri=100;
		totale_giri =nm*7*numero_giri;
		MainClass main=new MainClass();
		
		JFrame frame = new JFrame("MAT");
		frame.setSize(400, 70);
		frame.add(main);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		main.start();
		
		for(int m=0;m<nm;m++)
		{
			for(int i=0;i<7;i++)
			{
				int k=0;
				Model mondo = new Model(lato,p_c,p_m[m],p_g[i]);
				
				String nome_normali=Double.toString(p_c)+"_"+Double.toString(p_g[i])+"_"+Double.toString(p_m[m])+"normali.txt";
				FileWriter fileout_normali = new FileWriter(nome_normali);
				BufferedWriter filebuf_normali = new BufferedWriter(fileout_normali);
				PrintWriter printout_normali = new PrintWriter(filebuf_normali);

				String nome_guariti=Double.toString(p_c)+"_"+Double.toString(p_g[i])+"_"+Double.toString(p_m[m])+"guariti.txt";
				FileWriter fileout_guariti = new FileWriter(nome_guariti);
				BufferedWriter filebuf_guariti = new BufferedWriter(fileout_guariti);
				PrintWriter printout_guariti = new PrintWriter(filebuf_guariti);

				String nome_morti=Double.toString(p_c)+"_"+Double.toString(p_g[i])+"_"+Double.toString(p_m[m])+"morti.txt";
				FileWriter fileout_morti = new FileWriter(nome_morti);
				BufferedWriter filebuf_morti = new BufferedWriter(fileout_morti);
				PrintWriter printout_morti = new PrintWriter(filebuf_morti);

				String nome_contatore=Double.toString(p_c)+"_"+Double.toString(p_g[i])+"_"+Double.toString(p_m[m])+"contatore.txt";
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
		}
		int finito=Integer.parseInt(JOptionPane.showInputDialog("Please finito "));	
	}
}

