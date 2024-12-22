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
	private int numPers;
	private int r_cont;
	private int r_mov;
	private boolean quarantine;
	private boolean from_the_beginning;
	private boolean Int_art;
	
	public static int time_quar=5;
	private int PosIniz;
	private int escape_radius=30;
	
	public Model(int lato, double p_c, double p_m, double p_g,double p_v,int time, int numPers,int r_cont,int r_mov,boolean quarantine,boolean from_the_beginning, boolean Int_art ) 
	{
		this.numPers=numPers;
		this.lato = lato;
		this.p_c = p_c;
		this.p_m = p_m;
		this.p_g = p_g;
		this.p_v=p_v;
		this.time=time;
		this.r_cont=r_cont;
		this.r_mov=r_mov;
		this.quarantine=quarantine;
		this.from_the_beginning=from_the_beginning;
		this.Int_art=Int_art;
		mondo=new int[lato][lato];
		mondo2=new int[lato][lato];
		azzera(mondo);
		mondo2=CopiaMatrice(mondo2,mondo,lato);
	}

	public void azzera(int mat[][])
	{
		if(!quarantine)
		{
			for(int i=0;i<lato;i++)
			{
				for(int j=0;j<lato;j++)
				{
					mat[i][j]=-1;
				}
			}
			int x1,y1;
			x1=(int)(Math.random()*lato);
			y1=(int)(Math.random()*lato);
			mat[x1][y1]=1;
			for (int i = 0; i < numPers-1; i++) 	
			{
				int x,y;
				do 
				{
					x=(int)(Math.random()*lato);
					y=(int)(Math.random()*lato);
				}
				while(mat[x][y]!=-1);
			
				mat[x][y]=0;
			}
			int numero_vaccinati=(int) ((p_v/100)*numPers);
			for(int i=0;i<numero_vaccinati;i++)
			{
				int x=(int)(Math.random()*lato);
				int y=(int)(Math.random()*lato);	
				while(mat[x][y]!=0)
				{
					x=(int)(Math.random()*lato);
					y=(int)(Math.random()*lato);
				}
				mat[x][y]=4;
			}
			PosIniz=0;
		}
		
		if(quarantine&&from_the_beginning)
		{
			for(int i=0;i<lato;i++)
			{
				for(int j=0;j<lato;j++)
				{
					mat[i][j]=-1;
				}
			}
			for(int i=lato/2-lato/4-1;i<=lato/2+lato/4-1;i++)
			{
				mat[lato/2-lato/4-1][i]=-3;
				mat[i][lato/2-lato/4-1]=-3;
				mat[lato/2+lato/4-1][i]=-3;
				mat[i][lato/2+lato/4-1]=-3;
			}
			int x1,y1;
			do
			{
			x1=(int)((Math.random()*lato/2)+lato/4);
			y1=(int)((Math.random()*lato/2)+lato/4);
			}
			while(mat[x1][y1]!=-1);
			mat[x1][y1]=1;
			
			for (int i = 0; i < numPers-1; i++) 	
			{
				int x,y;
				do 
				{
					x=(int)(Math.random()*lato);
					y=(int)(Math.random()*lato);
				}
				while(mat[x][y]!=-1);
			
				mat[x][y]=0;
			}
			int numero_vaccinati=(int) ((p_v/100)*numPers);
			for(int i=0;i<numero_vaccinati;i++)
			{
				int x=(int)(Math.random()*lato);
				int y=(int)(Math.random()*lato);	
				while(mat[x][y]!=0)
				{
					x=(int)(Math.random()*lato);
					y=(int)(Math.random()*lato);
				}
				mat[x][y]=4;
			}
			
			PosIniz=0;
		}
		if(quarantine&&!from_the_beginning)
		{
			for(int i=0;i<lato;i++)
			{
				for(int j=0;j<lato;j++)
				{
					mat[i][j]=-1;
				}
			}
			int x1,y1;
			x1=(int)((Math.random()*lato/2)+lato/4);
			y1=(int)((Math.random()*lato/2)+lato/4);
			mat[x1][y1]=1;
			for (int i = 0; i < numPers-1; i++) 	
			{
				int x,y;
				do 
				{
					x=(int)(Math.random()*lato);
					y=(int)(Math.random()*lato);
				}
				while(mat[x][y]!=-1);
			
				mat[x][y]=0;
			}
			int numero_vaccinati=(int) ((p_v/100)*numPers);
			for(int i=0;i<numero_vaccinati;i++)
			{
				int x=(int)(Math.random()*lato);
				int y=(int)(Math.random()*lato);	
				while(mat[x][y]!=0)
				{
					x=(int)(Math.random()*lato);
					y=(int)(Math.random()*lato);
				}
				mat[x][y]=4;
			}
			PosIniz=0;
		}
	}
	
	int y_check_infetti(int r_cont,int i,int j,int mondo2[][])
	{
		int kont_alto=0;
		int kont_basso=0;
		
		
		for(int var=r_cont;var>0;var--)
		{
			for(int var2=r_cont;var2>-r_cont;var2--)
			{
				if(i-var>=0&&j-var2>=0&&i-var<lato&&j-var2<lato)
				if(mondo2[i-var][j-var2]==1)
				{
					kont_alto++;	
				}
			}
		}
		for(int var=0;var>-r_cont;var--)
		{
			for(int var2=r_cont;var2>-r_cont;var2--)
			{
				if(i-var>=0&&j-var2>=0&&i-var<lato&&j-var2<lato)
				if(mondo2[i-var][j-var2]==1)
				{
					kont_basso++;
				}
			}
		}
		
		if(kont_alto>kont_basso)
			return 1;
		else if(kont_alto<kont_basso)
			return -1;
		else
			{
			double n_1;
			long  n_2;
			do
			{
				n_1=(-1+(2*1*Math.random()));
				n_2 = Math.round(n_1);
				
			}
			while(n_2==0);
			return (int) n_2;
			
			}
	}
	
	
	int x_check_infetti(int r_cont,int i,int j,int mondo2[][])
	{
		int kont_destra=0;
		int kont_sinistra=0;
		for(int var=r_cont;var>-r_cont;var--)
		{
			for(int var2=r_cont;var2>0;var2--)
			{
				
				if(i-var>=0&&j-var2>=0&&i-var<lato&&j-var2<lato)
					if(mondo2[i-var][j-var2]==1)//alto a sinistra
					{
						kont_sinistra++;
					}
			}
		}
		for(int var=r_cont;var>-r_cont;var--)
		{
			for(int var2=0;var2>-r_cont;var2--)
			{
				if(i-var>=0&&j-var2>=0&&i-var<lato&&j-var2<lato)
					if(mondo2[i-var][j-var2]==1)//alto destra
					{
						kont_destra++;
					}
			}
		}
		
		
		if(kont_sinistra>kont_destra)
			return 1;
		else if(kont_sinistra<kont_destra)
			return -1;
		else
		{
			double n_1;
			long  n_2;
			do
			{
				n_1=(-1+(2*1*Math.random()));
				n_2 = Math.round(n_1);
				
			}
			while(n_2==0);
			return (int) n_2;
		}
	}
	
	public void pandemia(int r_mov,int r_cont)
	{
		int x=0,y=0;
		if(quarantine&&!from_the_beginning&&this.getContatore()>=time_quar)
		{
			for(int i=lato/2-lato/4-1;i<=lato/2+lato/4-1;i++)
			{
				
				mondo[lato/2-lato/4-1][i]=-3;
				if(mondo2[lato/2-lato/4-1][i]==0)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=0;
				}
				if(mondo2[lato/2-lato/4-1][i]==1)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=1;
				}
				if(mondo2[lato/2-lato/4-1][i]==2)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=2;
				}
				if(mondo2[lato/2-lato/4-1][i]==3)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=3;
				}
				if(mondo2[lato/2-lato/4-1][i]==4)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=4;
				}
				
						
				
				
				mondo[i][lato/2-lato/4-1]=-3;
				if(mondo2[i][lato/2-lato/4-1]==0)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=0;
				}
				if(mondo2[i][lato/2-lato/4-1]==1)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=1;
				}
				if(mondo2[i][lato/2-lato/4-1]==2)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=2;
				}
				if(mondo2[i][lato/2-lato/4-1]==3)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=3;
				}
				if(mondo2[i][lato/2-lato/4-1]==4)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=4;
				}
				
				
				
				
				mondo[lato/2+lato/4-1][i]=-3;
				if(mondo2[lato/2+lato/4-1][i]==0)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=0;
				}
				if(mondo2[lato/2+lato/4-1][i]==1)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=1;
				}
				if(mondo2[lato/2+lato/4-1][i]==2)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=2;
				}
				if(mondo2[lato/2+lato/4-1][i]==3)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=3;
				}
				if(mondo2[lato/2+lato/4-1][i]==4)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=4;
				}
				
				
				
				
				
				mondo[i][lato/2+lato/4-1]=-3;
				if(mondo2[i][lato/2+lato/4-1]==0)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=0;
				}
				if(mondo2[i][lato/2+lato/4-1]==1)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=1;
				}
				if(mondo2[i][lato/2+lato/4-1]==2)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=2;
				}
				if(mondo2[i][lato/2+lato/4-1]==3)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=3;
				}
				if(mondo2[i][lato/2+lato/4-1]==4)
				{
					int x_move,y_move;
					do 
					{
						x_move=(int)(Math.random()*lato);
						y_move=(int)(Math.random()*lato);
					}
					while(mondo[x_move][y_move]!=-1);
				
					mondo[x_move][y_move]=4;
				}
				
				mondo2=CopiaMatrice(mondo2,mondo,lato);
				
			}
		}
		for(int i=PosIniz;i<lato;i++)
		{
			for(int j=PosIniz;j<lato;j++)
			{
				switch(mondo2[i][j])
				{
					case 4:
						if(!quarantine||r_mov==1||(quarantine&&!from_the_beginning&&this.getContatore()<time_quar))
						{
						do {
							do
							{
								double x_1=(-r_mov+(2*r_mov*Math.random()));
								long x_2 = Math.round(x_1);
								x=(int) x_2;
								double y_1=(-r_mov+(2*r_mov*Math.random()));
								long y_2 = Math.round(y_1);
								y=(int) y_2;
								
							}
							while(y+i>=lato||y+i<0||j+x>=lato||j+x<0);
							
						}
						while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
						mondo[i][j]=-1;
						mondo[i+y][j+x]=4;
						}
						else if((quarantine&&from_the_beginning)||(quarantine&&!from_the_beginning&&this.getContatore()>=time_quar))
						{
							if(i<lato/2-lato/4||i>lato/2+lato/4||j<lato/2-lato/4||j>lato/2+lato/4)
							{
								do {
									do
									{
										double x_1=(-r_mov+(2*r_mov*Math.random()));
										long x_2 = Math.round(x_1);
										x=(int) x_2;
										double y_1=(-r_mov+(2*r_mov*Math.random()));
										long y_2 = Math.round(y_1);
										y=(int) y_2;
										
									}
									while(y+i>=lato||y+i<0||j+x>=lato||j+x<0||(j+x<lato/2+lato/4+1&&j+x>lato/2-lato/4-1&&y+i<lato/2+lato/4+1&&y+i>lato/2-lato/4-1));
									
								}
								while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
								mondo[i][j]=-1;
								mondo[i+y][j+x]=4;
							}
							else
							{
								do {
									do
									{
										double x_1=(-r_mov+(2*r_mov*Math.random()));
										long x_2 = Math.round(x_1);
										x=(int) x_2;
										double y_1=(-r_mov+(2*r_mov*Math.random()));
										long y_2 = Math.round(y_1);
										y=(int) y_2;
									}
									while(y+i>=lato||y+i<0||j+x>=lato||j+x<0||(j+x>lato/2+lato/4-1||j+x<lato/2-lato/4+1||y+i>lato/2+lato/4-1||y+i<lato/2-lato/4+1));
									
								}
								while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
								mondo[i][j]=-1;
								mondo[i+y][j+x]=4;
							}
						}
						break;
					case 3://morto
						mondo[i][j]=3;
						break;
					case 2://si muove
						if(!quarantine||r_mov==1||(quarantine&&!from_the_beginning&&this.getContatore()<time_quar))
						{
						do {
							do
							{
								double x_1=(-r_mov+(2*r_mov*Math.random()));
								long x_2 = Math.round(x_1);
								x=(int) x_2;
								double y_1=(-r_mov+(2*r_mov*Math.random()));
								long y_2 = Math.round(y_1);
								y=(int) y_2;
								
							}
							while(y+i>=lato||y+i<0||j+x>=lato||j+x<0);
							
						}
						while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
						mondo[i][j]=-1;
						mondo[i+y][j+x]=2;
						}
						else if((quarantine&&from_the_beginning)||(quarantine&&!from_the_beginning&&this.getContatore()>=time_quar))
						{
							if(i<lato/2-lato/4||i>lato/2+lato/4||j<lato/2-lato/4||j>lato/2+lato/4)
							{
								do {
									do
									{
										double x_1=(-r_mov+(2*r_mov*Math.random()));
										long x_2 = Math.round(x_1);
										x=(int) x_2;
										double y_1=(-r_mov+(2*r_mov*Math.random()));
										long y_2 = Math.round(y_1);
										y=(int) y_2;
									}
									while(y+i>=lato||y+i<0||j+x>=lato||j+x<0||(j+x<lato/2+lato/4+1&&j+x>lato/2-lato/4-1&&y+i<lato/2+lato/4+1&&y+i>lato/2-lato/4-1));
									
								}
								while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
								mondo[i][j]=-1;
								mondo[i+y][j+x]=2;
							}
							else 
							{
								do {
									do
									{
										double x_1=(-r_mov+(2*r_mov*Math.random()));
										long x_2 = Math.round(x_1);
										x=(int) x_2;
										double y_1=(-r_mov+(2*r_mov*Math.random()));
										long y_2 = Math.round(y_1);
										y=(int) y_2;
										
									}
									while(y+i>=lato||y+i<0||j+x>=lato||j+x<0||(j+x>lato/2+lato/4-1||j+x<lato/2-lato/4+1||y+i>lato/2+lato/4-1||y+i<lato/2-lato/4+1));
									
								}
								while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
								mondo[i][j]=-1;
								mondo[i+y][j+x]=2;
							}
						}
						break;
					case 1://si muove
						
						if(!quarantine||r_mov==1||(quarantine&&!from_the_beginning&&this.getContatore()<time_quar))
						{
						do {
							do
							{

								double x_1=(-r_mov+(2*r_mov*Math.random()));
								long x_2 = Math.round(x_1);
								x=(int) x_2;
								double y_1=(-r_mov+(2*r_mov*Math.random()));
								long y_2 = Math.round(y_1);
								y=(int) y_2;
								
							}
							while(y+i>=lato||y+i<0||j+x>=lato||j+x<0);
							
						}
						while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
						mondo[i][j]=-1;
						mondo[i+y][j+x]=1;
						}
						else if((quarantine&&from_the_beginning)||(quarantine&&!from_the_beginning&&this.getContatore()>=time_quar))
						{
							if(i<lato/2-lato/4||i>lato/2+lato/4||j<lato/2-lato/4||j>lato/2+lato/4)
							{
								do {
									do
									{
										double x_1=(-r_mov+(2*r_mov*Math.random()));
										long x_2 = Math.round(x_1);
										x=(int) x_2;
										double y_1=(-r_mov+(2*r_mov*Math.random()));
										long y_2 = Math.round(y_1);
										y=(int) y_2;
										
									}
									while(y+i>=lato||y+i<0||j+x>=lato||j+x<0||(j+x<lato/2+lato/4+1&&j+x>lato/2-lato/4-1&&y+i<lato/2+lato/4+1&&y+i>lato/2-lato/4-1));
									
								}
								while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
								mondo[i][j]=-1;
								mondo[i+y][j+x]=1;
							}
							else
							{
								do {
									do
									{
										double x_1=(-r_mov+(2*r_mov*Math.random()));
										long x_2 = Math.round(x_1);
										x=(int) x_2;
										double y_1=(-r_mov+(2*r_mov*Math.random()));
										long y_2 = Math.round(y_1);
										y=(int) y_2;
									}
									while(y+i>=lato||y+i<0||j+x>=lato||j+x<0||(j+x>lato/2+lato/4-1||j+x<lato/2-lato/4+1||y+i>lato/2+lato/4-1||y+i<lato/2-lato/4+1));
									
								}
								while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
								mondo[i][j]=-1;
								mondo[i+y][j+x]=1;
							}
						}
						dado=(double)(Math.random()*100);
						if(dado<p_g)
							mondo[i+y][j+x]=2;
						else if(dado>=p_g&&dado<p_g+p_m)
							mondo[i+y][j+x]=3;
						else
							mondo[i+y][j+x]=1;
						
						
						break;
					case 0://si muove
						int kont=0;
						int kont_ext=0;
						for(int var=r_cont;var>=-r_cont;var--)
						{
							for(int var2=r_cont;var2>=-r_cont;var2--)
							{
								if(!quarantine)
								{
									if(i-var>=0&&j-var2>=0&&i-var<lato&&j-var2<lato&&(var!=0||var2!=0))
										if(mondo2[i-var][j-var2]==1)//alto a sinistra
										{
											kont++;
										}
								}
								if(quarantine)
								{
									if(i-var<lato/2-lato/4||i-var>lato/2+lato/4||j-var2<lato/2-lato/4||j-var2>lato/2+lato/4)
									{
										if(i-var>=0&&j-var2>=0&&i-var<lato&&j-var2<lato&&(var!=0||var2!=0))
											if(mondo2[i-var][j-var2]==1)//alto a sinistra
											{
												kont_ext++;
											}
									}
									else
									{
										if(i-var>=0&&j-var2>=0&&i-var<lato&&j-var2<lato&&(var!=0||var2!=0))
											if(mondo2[i-var][j-var2]==1)//alto a sinistra
											{
												kont++;
											}
									}
								}
							/*if(i>=var&&j>=var)
								if(mondo2[i-var][j-var]==1&&mondo2[i][j]==0)//alto a sinistra
								{
									kont++;
								}
							if(j>=var) 
								if(mondo2[i][j-var]==1&&mondo2[i][j]==0)//alto centrale
								{
									kont++;
								}
							if(j>=var&&i<lato-var) 
								if(mondo2[i+var][j-var]==1&&mondo2[i][j]==0)//alto destra
								{
									kont++;
								}
							if(i>=var) 
								if(mondo2[i-var][j]==1&&mondo2[i][j]==0)//centro sinistra
								{
									kont++;
								}
							if(i<lato-var) 
								if(mondo2[i+var][j]==1&&mondo2[i][j]==0)//centro destra
								{
									kont++;
								}
							if(i>=var&&j<lato-var) 
								if(mondo2[i-var][j+var]==1&&mondo2[i][j]==0)//basso sinistra
								{
									kont++;
								}
							if(j<lato-var) 
								if(mondo2[i][j+var]==1&&mondo2[i][j]==0)//basso centrale
								{
									kont++;
								}
						if(i<lato-var&&j<lato-var) 
							if(mondo2[i+var][j+var]==1&&mondo2[i][j]==0)//basso destra
							kont++;*/
							}
						}
						
						if(!quarantine||r_mov==1||(quarantine&&!from_the_beginning&&this.getContatore()<time_quar))
						{
						do {
							do
							{
								if(!Int_art)
								{
								double x_1=(-r_mov+(2*r_mov*Math.random()));
								long x_2 = Math.round(x_1);
								x=(int) x_2;
								double y_1=(-r_mov+(2*r_mov*Math.random()));
								long y_2 = Math.round(y_1);
								y=(int) y_2;
								}
								else
								{
									double x_1=(r_mov*Math.random());
									long x_2 = Math.round(x_1);
									x=(int) x_2;
									x*=x_check_infetti(escape_radius,i,j,mondo2);
									
									double y_1=(r_mov*Math.random());
									long y_2 = Math.round(y_1);
									y=(int) y_2;
									y*=y_check_infetti(escape_radius,i,j,mondo2);
								}
								
							}
							while(y+i>=lato||y+i<0||j+x>=lato||j+x<0);
							
						}
						while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
						mondo[i][j]=-1;
						mondo[i+y][j+x]=0;
						}
						else if((quarantine&&from_the_beginning)||(quarantine&&!from_the_beginning&&this.getContatore()>=time_quar))
						{
							if(i<lato/2-lato/4||i>lato/2+lato/4||j<lato/2-lato/4||j>lato/2+lato/4)
							{
								do {
									do
									{
										if(!Int_art)
										{
										double x_1=(-r_mov+(2*r_mov*Math.random()));
										long x_2 = Math.round(x_1);
										x=(int) x_2;
										double y_1=(-r_mov+(2*r_mov*Math.random()));
										long y_2 = Math.round(y_1);
										y=(int) y_2;
										}
										else
										{
											double x_1=(r_mov*Math.random());
											long x_2 = Math.round(x_1);
											x=(int) x_2;
											x*=x_check_infetti(escape_radius,i,j,mondo2);
											
											double y_1=(r_mov*Math.random());
											long y_2 = Math.round(y_1);
											y=(int) y_2;
											y*=y_check_infetti(escape_radius,i,j,mondo2);
										}
										
									}
									while(y+i>=lato||y+i<0||j+x>=lato||j+x<0||(j+x<lato/2+lato/4+1&&j+x>lato/2-lato/4-1&&y+i<lato/2+lato/4+1&&y+i>lato/2-lato/4-1));	
								}
								while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
								mondo[i][j]=-1;
								mondo[i+y][j+x]=0;
							}
							else
							{
								do {

									do
									{
										if(!Int_art)
										{
										double x_1=(-r_mov+(2*r_mov*Math.random()));
										long x_2 = Math.round(x_1);
										x=(int) x_2;
										double y_1=(-r_mov+(2*r_mov*Math.random()));
										long y_2 = Math.round(y_1);
										y=(int) y_2;
										}
										else
										{
											double x_1=(r_mov*Math.random());
											long x_2 = Math.round(x_1);
											x=(int) x_2;
											x*=x_check_infetti(escape_radius,i,j,mondo2);
											
											double y_1=(r_mov*Math.random());
											long y_2 = Math.round(y_1);
											y=(int) y_2;
											y*=y_check_infetti(escape_radius,i,j,mondo2);
										}
										
									}
									while(y+i>=lato||y+i<0||j+x>=lato||j+x<0||(j+x>lato/2+lato/4-1||j+x<lato/2-lato/4+1||y+i>lato/2+lato/4-1||y+i<lato/2-lato/4+1));
									
								}
								while(mondo[i+y][j+x]!=-1&&(x!=0||y!=0));
			
								mondo[i][j]=-1;
								mondo[i+y][j+x]=0;
							}
						}
						
						if(quarantine)
						{
							if(i+y<lato/2-lato/4+1||i+y>lato/2+lato/4-1||j+x<lato/2-lato/4+1||j+x>lato/2+lato/4-1)
							{
								if(kont_ext!=0)
								{
									dado=(double)(Math.random()*100);
									if(dado<(1-Math.pow((1-p_c/100),kont_ext))*100)
											mondo[i+y][j+x]=1;
									else
										mondo[i+y][j+x]=0;
								}
								else
									mondo[i+y][j+x]=0;
							}
							else
							{
									dado=(double)(Math.random()*100);
										if(dado<(1-Math.pow((1-p_c/100),kont))*100)
												mondo[i+y][j+x]=1;
										else
												mondo[i+y][j+x]=0;
							}
						}
						else 
						{
							dado=(double)(Math.random()*100);
							if(dado<(1-Math.pow((1-p_c/100),kont))*100)
									mondo[i+y][j+x]=1;
							else
									mondo[i+y][j+x]=0;
						}
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
				pandemia(r_mov,r_cont);
				mondo2=CopiaMatrice(mondo2,mondo,lato);
				try {Thread.sleep(time);} catch (InterruptedException e) {e.printStackTrace();}
				contatore++;
			}
		while(!controllo_fine(mondo,lato));
		}
	}
	
	public void diffusione2() // quando non è until end
	{
		MainClass.lava_vetri=true;
		int K_iniz=contatore;
		if(!controllo_fine(mondo,lato))
		{
			do 
			{	
				pandemia(r_mov,r_cont);
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
	
	public double perc_morti(){return (100*conta_morti(mondo,lato))/(numPers);}
	public double perc_guariti(){return (100*conta_guariti(mondo,lato))/(numPers);}
	public double perc_normali(){return (100*conta_normali(mondo,lato))/(numPers);}
	
	//getters
	public int getContatore(){return contatore;}
	public int getLato() {return lato;}
	public int[][] getMondo() {return mondo;}
	public int[][] getMondo2() {return mondo2;}
	public void setContatore(int i) {contatore=i;}
}

