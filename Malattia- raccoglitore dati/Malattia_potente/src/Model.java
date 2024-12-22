
public class Model 
{
	private int lato;
	private int dado;
	private int contatore=0;
	private double p_c;
	private double p_m;
	private double p_g;
	private int mondo[][];
	private int mondo2[][];

	public Model(int lato, double p_c, double p_m, double p_g) 
	{
		this.lato = lato;
		this.p_c = p_c;
		this.p_m = p_m;
		this.p_g = p_g;
		mondo=new int[lato][lato];
		mondo2=new int[lato][lato];
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
	}
	
	public void pandemia()
	{
		for(int i=0;i<lato;i++)
		{
			for(int j=0;j<lato;j++)
			{
				switch(mondo2[i][j])
				{
					case 3:
						mondo[i][j]=3;
						break;
					case 2:
						mondo[i][j]=2;
						break;
					case 1:
						dado=(int)(Math.random()*100);
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
						
						dado=(int)(Math.random()*100);
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
	
	public void diffusione()
	{
		azzera(mondo);
		azzera(mondo2);
		do
		{	
			pandemia();
			mondo2=CopiaMatrice(mondo2,mondo,lato);
			contatore++;
		}
		while(!controllo_fine(mondo,lato));
		
	}
	public int getContatore(){return contatore;}

	public int getLato() {return lato;}
	
	

	public void setContatore(int contatore) {
		this.contatore = contatore;
	}

	public int[][] getMondo() {return mondo;}

	public int[][] getMondo2() {return mondo2;}

	public void stampa(int mat[][])
	{
		for(int i=0;i<lato;i++)
		{
			for(int j=0;j<lato;j++)
			{
				
				System.out.print(mat[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println("-------------------------------------------------------------");
		
	}
	
public double perc_morti()	
	{
		return (100*conta_morti(mondo,lato))/(lato*lato);
	}
public double perc_guariti()	
{
	return (100*conta_guariti(mondo,lato))/(lato*lato);
}
public double perc_normali()	
{
	return (100*conta_normali(mondo,lato))/(lato*lato);
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
}

