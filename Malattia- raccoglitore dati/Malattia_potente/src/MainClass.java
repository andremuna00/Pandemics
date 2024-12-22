import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class MainClass
{
	public static void main(String[] args) throws IOException 
	{
		/*t ng=0;
		ng=Integer.parseInt(JOptionPane.showInputDialog("Please input n di % guarigione "));	
		*/
		int nm=0;
		nm=Integer.parseInt(JOptionPane.showInputDialog("Please input n di % morte "));	
		
		double p_c=0;//(45-95, 10 in 10)
		double[] p_m=new double[nm];//()
		double[] p_g=new double[7];//()
		
		
		p_g[0]=2;
		p_g[1]=4;
		p_g[2]=7;
		p_g[3]=13;
		p_g[4]=22;
		p_g[5]=39;
		p_g[6]=70;
		
		p_c=Double.parseDouble(JOptionPane.showInputDialog("Please inputd contagio "));	
		
		for(int i=0;i<nm;i++){
			p_m[i]=Double.parseDouble(JOptionPane.showInputDialog("Please inputd morte "+(i+1)));			
	    }
		
		
		/*
		for(int i=0;i<ng;i++){
		p_g[i]=Double.parseDouble(JOptionPane.showInputDialog("Please inputd guarigplz "+(i+1)));	
		}*/
		
		
		int lato=501;
		int numero_giri=1024;
		
		
		for(int m=0;m<nm;m++){
		for(int i=0;i<7;i++){
		int k=0;
		Model mondo = new Model(lato,p_c,p_m[m],p_g[i]);
		String nome=Double.toString(p_c)+"-"+Double.toString(p_g[i])+"-"+Double.toString(p_m[m])+".txt";
	  	FileWriter fileout = new FileWriter(nome);
        BufferedWriter filebuf = new BufferedWriter(fileout);
        PrintWriter printout = new PrintWriter(filebuf);
        printout.println("%normali;%guariti;%morti;contatore");
		do
		{
			mondo.diffusione();
	        printout.println(mondo.perc_normali()+";"+mondo.perc_guariti()+";"+mondo.perc_morti()+";"+mondo.getContatore());
	        mondo.setContatore(0);
	        k++;
		}
	    while(k<numero_giri);
		printout.close();
		}
		}
		int finito=Integer.parseInt(JOptionPane.showInputDialog("Please finito "));	
	}
}

