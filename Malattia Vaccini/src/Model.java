
public class Model 
{
	private int lato;
	private double dado;
	private static int contatore;
	private int time;
	private double p_c;
	private double p_m;
	private double p_g;
	private double p_v;
	private int mondo[][];
	private int mondo2[][];

	public Model(int lato, double p_c, double p_m, double p_g, double p_v,int time) 
	{
		this.lato = lato;
		this.p_c = p_c;
		this.p_m = p_m;
		this.p_g = p_g;
		this.p_v = p_v;
		this.time=time;
		mondo=new int[lato][lato];
		mondo2=new int[lato][lato];
		azzera(mondo);
		mondo2=CopiaMatrice(mondo2,mondo,lato);
	}

	public void azzera(int mat[][])
	{
		for(int i=0;i<lato;i++)
		{
			for(int j=0;j<lato;j++)
			{
				mat[i][j]=0;
			}
		}
		mat[lato/2][lato/2]=1;
		int numero_vaccinati=(int) ((p_v/100)*lato*lato);
		for(int i=0;i<numero_vaccinati;i++)
		{
			int x=(int)(Math.random()*lato);
			int y=(int)(Math.random()*lato);	
			while(mat[x][y]==4||mat[x][y]==1)
			{
				x=(int)(Math.random()*lato);
				y=(int)(Math.random()*lato);
			}
			mat[x][y]=4;
		}
		
	}
	
	public void pandemia()
	{
		for(int i=0;i<lato;i++)
		{
			for(int j=0;j<lato;j++)
			{
				switch(mondo2[i][j])
				{
					case 4:
						mondo[i][j]=4;
						break;
					case 3:
						mondo[i][j]=3;
						break;
					case 2:
						mondo[i][j]=2;
						break;
					case 1:
						dado=(double)(Math.random()*100);
						if(dado<p_g)
							mondo[i][j]=2;
						else if(dado>=p_g&&dado<p_g+p_m)
							mondo[i][j]=3;
						else
							mondo[i][j]=1;
						break;
					case 0:
						int kont=0;
						if(i>0&&j>0)
						if(mondo2[i-1][j-1]==1&&mondo2[i][j]==0)//alto a sinistra
						{
							kont++;
						}
						if(j>0) if(mondo2[i][j-1]==1&&mondo2[i][j]==0)//alto centrale
						{
							kont++;
						}
						if(j>0&&i<lato-1) if(mondo2[i+1][j-1]==1&&mondo2[i][j]==0)//alto destra
						{
							kont++;
						}
						if(i>0) if(mondo2[i-1][j]==1&&mondo2[i][j]==0)//centro sinistra
						{
							kont++;
						}
						if(i<lato-1) if(mondo2[i+1][j]==1&&mondo2[i][j]==0)//centro destra
						{
							kont++;
						}
						if(i>0&&j<lato-1) if(mondo2[i-1][j+1]==1&&mondo2[i][j]==0)//basso sinistra
						{
							kont++;
						}
						if(j<lato-1) if(mondo2[i][j+1]==1&&mondo2[i][j]==0)//basso centrale
						{
							kont++;
						}
						if(i<lato-1&&j<lato-1) if(mondo2[i+1][j+1]==1&&mondo2[i][j]==0)//basso destra
							kont++;
						
						dado=(double)(Math.random()*100);
						if(dado<(1-Math.pow((1-p_c/100),kont))*100)
							mondo[i][j]=1;
						else
							mondo[i][j]=0;
						
						break;
					default:
						break;
				}
			}
		}
	}
	
	public boolean controllo_fine(int mat[][],int lato)
	{
		for(int i=0;i<lato;i++)
		{
			for(int j=0;j<lato;j++)
			{
				if(mat[i][j]==1)
					return false;
			}
		}
		return true;
	}
	public void diffusione()
	{
		MainClass.lava_vetri=true;
		if(!controllo_fine(mondo,lato))
		{
			do
			{	
				pandemia();
				mondo2=CopiaMatrice(mondo2,mondo,lato);
				try {Thread.sleep(time);} catch (InterruptedException e) {e.printStackTrace();}
				contatore++;
			}
		while(!controllo_fine(mondo,lato));
		}
	}
	
	public void diffusione2()
	{
		MainClass.lava_vetri=true;
		int K_iniz=contatore;
		if(!controllo_fine(mondo,lato))
		{
			do 
			{	
				pandemia();
				mondo2=CopiaMatrice(mondo2,mondo,lato);
				try {Thread.sleep(time);} catch (InterruptedException e) {e.printStackTrace();}
				contatore++;
			}
			while(contatore<(MainClass.steps+K_iniz)&&!controllo_fine(mondo,lato));
		}
	}
	
	public int[][] CopiaMatrice(int[][] mat, int[][] mat2, int dim) 
	{
		for(int i=0; i<dim; i++) 
		{
			for(int j=0; j<dim; j++) 
			{
				mat[i][j]=mat2[i][j];
			}
		}
		return mat;
	}
	
	//percentuali
	public double conta_morti(int mat[][],int lato)
	{
		double k=0;
		for(int i=0;i<lato;i++)
		{
			for(int j=0;j<lato;j++)
			{
				if(mat[i][j]==3)
					k++;
			}
		}
		return k;
	}
	public double conta_guariti(int mat[][],int lato)
	{
		double k=0;
		for(int i=0;i<lato;i++)
		{
			for(int j=0;j<lato;j++)
			{
				if(mat[i][j]==2)
					k++;
			}
		}
		return k;
	}
	public double conta_normali(int mat[][],int lato)
	{
		double k=0;
		for(int i=0;i<lato;i++)
		{
			for(int j=0;j<lato;j++)
			{
				if(mat[i][j]==0)
					k++;
			}
		}
		return k;
	}
	
	public double perc_morti(){return (100*conta_morti(mondo,lato))/(lato*lato);}
	public double perc_guariti(){return (100*conta_guariti(mondo,lato))/(lato*lato);}
	public double perc_normali(){return (100*conta_normali(mondo,lato))/(lato*lato);}
	
	//getters
	public int getContatore(){return contatore;}
	public int getLato() {return lato;}
	public int[][] getMondo() {return mondo;}
	public int[][] getMondo2() {return mondo2;}
	public void setContatore(int i) {contatore=i;}
}

