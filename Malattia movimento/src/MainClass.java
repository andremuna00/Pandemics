
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.*;


public class MainClass extends Canvas implements Runnable

{
	private static final long serialVersionUID = 1L;
	private static Model modello;
	private Thread thread;
	private static Graphics g;
	private static Graphics g2;
	
	private JFrame frmPandemic;
	private JTextField tfNumInf;
	private JTextField tfNumRec;
	private JTextField tfNumDea;
	private JTextField tfNumVac;
	private JTextField tfRowsColumns;
	private JTextField tfNumPers;
	private JTextField tfNumSteps;
	
	private int lato;
	private double p_c;
	private double p_m;
	private double p_g;
	private double p_v;
	private int NumPers;
	private int time=50;
	private int r_cont=1;
	private int r_mov=100;
	public static int steps=0;
	private boolean quarantine=false;
	private boolean from_the_beginning=true;
	private boolean Int_art=false;
	
	private static MainClass main;
	public static boolean lava_vetri=false;
	public static Color sfondo = new Color(0,0,0);
	public static Sound canzone;
	
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs==null)
		{
			createBufferStrategy(3);
			return;
		}
		g= bs.getDrawGraphics();
		g2= bs.getDrawGraphics();
		
		if(lava_vetri)
		{
			g2.clearRect(0, 0, 700, 700);
			g2.setColor(sfondo);
			g2.fillRect(0, 0, 700, 700);
			lava_vetri=false;
		}
			for(int i=0;i<modello.getLato();i++)
			{
				for(int j=0;j<modello.getLato();j++)
				{
					if(modello.getMondo()[i][j]==-3)
						g.setColor(Color.gray);
					if(modello.getMondo()[i][j]==-2)
						g.setColor(Color.pink);
					if(modello.getMondo()[i][j]==-1)
						g.setColor(sfondo);
					if(modello.getMondo()[i][j]==0)
						g.setColor(Color.white);
					if(modello.getMondo()[i][j]==1)
						g.setColor(Color.yellow);
					if(modello.getMondo()[i][j]==2)
						g.setColor(Color.green);
					if(modello.getMondo()[i][j]==3)
						g.setColor(Color.red);
					if(modello.getMondo()[i][j]==4)
						g.setColor(Color.blue);

					g.setFont(new Font("Century Gothic",Font.BOLD,600/modello.getLato()));
					g.drawString("X", (int) (j*(600/modello.getLato())+30), (int)(i*(600/modello.getLato())+30));
				}
			}
		g.dispose();
		g2.dispose();
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
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					MainClass window = new MainClass();
					window.frmPandemic.setVisible(true);
				} 
				catch (Exception e) {e.printStackTrace();}
			}
		});
		
		main = new MainClass();
		JFrame frame = new JFrame("MAT");
		frame.setBounds(800,900,700, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(main);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(sfondo);
		frame.setVisible(true);
		
	}
	
	public MainClass() 
	{
		initialize();
	}

	private void initialize()
	{
		
		frmPandemic = new JFrame();
		frmPandemic.setResizable(false);
		frmPandemic.setTitle("Pandemic");
		frmPandemic.setBounds(0, 0, 500, 740);
		frmPandemic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPandemic.getContentPane().setLayout(null);
		
		JLabel lblcopryright = new JLabel("© Andrea Munarin");
		lblcopryright.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblcopryright.setBounds(350, 665, 600, 20);
		frmPandemic.getContentPane().add(lblcopryright);
		
		//PANNELLO INPUT
		JPanel panelInput = new JPanel();
		panelInput.setBounds(10, 11, 480, 350);
		frmPandemic.getContentPane().add(panelInput);
		panelInput.setLayout(null);
		
		JLabel lblDataInput = new JLabel("DATA INPUT");
		lblDataInput.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDataInput.setBounds(10, 0, 150, 30);
		panelInput.add(lblDataInput);
		//parte matrice
		JLabel lblMatrix = new JLabel("MATRIX");
		lblMatrix.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMatrix.setBounds(30, 41, 150, 20);
		panelInput.add(lblMatrix);
		
		JLabel lblRowsColumns = new JLabel("Number of rows/columns");
		lblRowsColumns.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRowsColumns.setBounds(60, 72, 170, 20);
		panelInput.add(lblRowsColumns);
		
		tfRowsColumns = new JTextField();
		tfRowsColumns.setBounds(290, 74, 130, 20);
		panelInput.add(tfRowsColumns);
		tfRowsColumns.setColumns(10);
		
		JLabel lblPeople = new JLabel("People");
		lblPeople.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPeople.setBounds(60, 102, 170, 20);
		panelInput.add(lblPeople);
		
		tfNumPers = new JTextField();
		tfNumPers.setBounds(290, 104, 130, 20);
		panelInput.add(tfNumPers);
		tfNumPers.setColumns(10);
		
		//parte percentuali
		JLabel lblProbabilities = new JLabel("PROBABILITIES");
		lblProbabilities.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProbabilities.setBounds(30, 136, 150, 20);
		panelInput.add(lblProbabilities);
		
		JCheckBox cbQuar = new JCheckBox("Quarantine");
		cbQuar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbQuar.setBounds(290, 136, 150, 20);
		cbQuar.setSelected(false);
		panelInput.add(cbQuar);
		
		JLabel lblProbInf = new JLabel("Probability of infection ");
		lblProbInf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProbInf.setBounds(60, 167, 150, 17);
		panelInput.add(lblProbInf);
		
		tfNumInf = new JTextField();
		tfNumInf.setBounds(290, 167, 130, 20);
		panelInput.add(tfNumInf);
		tfNumInf.setColumns(10);
		
		JLabel lblProbRec = new JLabel("Probability of healing [h]");
		lblProbRec.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProbRec.setBounds(60, 197, 150, 20);
		panelInput.add(lblProbRec);
		
		tfNumRec = new JTextField();
		tfNumRec.setBounds(290, 198, 130, 20);
		panelInput.add(tfNumRec);
		tfNumRec.setColumns(10);
		
		JLabel lblProbDea = new JLabel("Probability of death [d]");
		lblProbDea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProbDea.setBounds(60, 228, 150, 20);
		panelInput.add(lblProbDea);
		
		tfNumDea = new JTextField();
		tfNumDea.setBounds(290, 230, 130, 20);
		panelInput.add(tfNumDea);
		tfNumDea.setColumns(10);
		
		JLabel lblProbVac = new JLabel("Percentage of vaccination [v]");
		lblProbVac.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProbVac.setBounds(60, 259, 180, 20);
		panelInput.add(lblProbVac);
		
		tfNumVac = new JTextField();
		tfNumVac.setBounds(290, 261, 130, 20);
		panelInput.add(tfNumVac);
		tfNumVac.setColumns(10);
		
		JLabel lblRadCon = new JLabel("Trassmission routes");
		lblRadCon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRadCon.setBounds(60, 291, 170, 20);
		panelInput.add(lblRadCon);
		
		String[] trasmission = {"Direct conctact","Droplets","Air","Vector","Custom"};
		JComboBox<Object> cbRadCon = new JComboBox<Object>(trasmission);
		cbRadCon.setBounds(290, 293, 130, 20);
		cbRadCon.setSelectedIndex(0);
		cbRadCon.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==cbRadCon)
				{
					JComboBox<?> cb =(JComboBox<?>)e.getSource();
					String msg = (String)cb.getSelectedItem();
						switch(msg)
						{
							case  "Direct conctact":
								r_cont=1;
								break;
							case  "Droplets":
								r_cont=5;
								break;
							case  "Air":
								r_cont=10;
								break;
							case  "Vector":
								r_cont=20;
								break;
							case "Custom":
								r_cont=Integer.parseInt(JOptionPane.showInputDialog("Insert the range of contagion"));
							default:
								break;
						}
				}
			}
		});
		panelInput.add(cbRadCon);
		
		JLabel lblRadMov = new JLabel("Limit of movement");
		lblRadMov.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRadMov.setBounds(60, 321, 170, 20);
		panelInput.add(lblRadMov);
		
		String[] vehicles = {"Static","Single cell","Feet","Bike","Car","Custom"};
		JComboBox<Object> cbRadMov = new JComboBox<Object>(vehicles);
		cbRadMov.setBounds(290, 323, 130, 20);
		cbRadMov.setSelectedIndex(2);
		cbRadMov.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(e.getSource()==cbRadMov)
				{
					JComboBox<?> cb =(JComboBox<?>)e.getSource();
					String msg = (String)cb.getSelectedItem();
						switch(msg)
						{
							case  "Static":
								r_mov=0;
								break;
							case  "Single cell":
								r_mov=1;
								break;
							case  "Feet":
								r_mov=100;
								break;
							case  "Bike":
								r_mov=200;
								break;
							case  "Car":
								r_mov=400;
								break;
							case  "Custom":
								r_mov=Integer.parseInt(JOptionPane.showInputDialog("Insert the range of movement"));	
								break;
						}
				}
			}
		});
		panelInput.add(cbRadMov);
		
		JLabel lblOptions = new JLabel("");
		lblOptions.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		
		//PANNELLO INFO
				JPanel panelInfo = new JPanel();
				panelInfo.setBounds(10, 505, 464, 210);
				frmPandemic.getContentPane().add(panelInfo);
				panelInfo.setLayout(null);
				
				JLabel lblInfo = new JLabel("INFO");
				lblInfo.setBounds(10, 11, 126, 25);
				lblInfo.setFont(new Font("Tahoma", Font.BOLD, 20));
				panelInfo.add(lblInfo);
				
				JLabel lblNumStab = new JLabel("Elapsed days [N]");
				lblNumStab.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblNumStab.setBounds(20, 46, 230, 20);
				panelInfo.add(lblNumStab);
				
				JLabel lblNumStabOUT = new JLabel("");
				lblNumStabOUT.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblNumStabOUT.setBounds(320, 46, 100, 20);
				panelInfo.add(lblNumStabOUT);
				
				JLabel lblPerDea = new JLabel("Percentage of dead population [%D]");
				lblPerDea.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblPerDea.setBounds(20, 77, 300, 20);
				panelInfo.add(lblPerDea);
				
				JLabel lblPerDeaOUT = new JLabel("");
				lblPerDeaOUT.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblPerDeaOUT.setBounds(320, 77, 100, 20);
				panelInfo.add(lblPerDeaOUT);
				
				JLabel lblPerSym1 = new JLabel("%");
				lblPerSym1.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblPerSym1.setBounds(430, 77, 20, 20);
				panelInfo.add(lblPerSym1);
				
				
				JLabel lblPerRec = new JLabel("Percentage of healed population [%H]");
				lblPerRec.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblPerRec.setBounds(20, 108, 300, 20);
				panelInfo.add(lblPerRec);
				
				JLabel lblPerRecOUT = new JLabel("");
				lblPerRecOUT.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblPerRecOUT.setBounds(320, 108, 100, 20);
				panelInfo.add(lblPerRecOUT);
				
				
				JLabel lblPerSym2 = new JLabel("%");
				lblPerSym2.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblPerSym2.setBounds(430, 108, 20, 20);
				panelInfo.add(lblPerSym2);
				
				JLabel lblPerNotInf = new JLabel("Percentage of not infected population [%nI]");
				lblPerNotInf.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblPerNotInf.setBounds(20, 139, 300, 20);
				panelInfo.add(lblPerNotInf);
				
				JLabel lblPerNotInfOUT = new JLabel("");
				lblPerNotInfOUT.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblPerNotInfOUT.setBounds(320, 139, 100, 20);
				panelInfo.add(lblPerNotInfOUT);
				
				JLabel lblPerSym3 = new JLabel("%");
				lblPerSym3.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblPerSym3.setBounds(430, 139, 20, 20);
				panelInfo.add(lblPerSym3);
		
		//PANNELLO BOTTONI
		JPanel panelButtons = new JPanel();
		panelButtons.setBounds(10, 332, 460, 180);
		frmPandemic.getContentPane().add(panelButtons);
		panelButtons.setLayout(null);
		
		JButton btnRun = new JButton("RUN UNTIL END");
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRun.setBounds(10, 82, 250, 40);
		panelButtons.add(btnRun);
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/*if(modello.getContatore()==0)
				{
					if(Int_art==false)
						canzone=new Sound("/Pirati_dei_Caraibi_-_colonna_sonora_soundtrack_.wav");
					if(Int_art==true)
						canzone=new Sound("/Run.wav");
					//canzone.Play();
					//canzone.infinity();
				}*/
				modello.diffusione();
				//canzone.Stop();
				lblNumStabOUT.setText(""+modello.getContatore());
				lblPerDeaOUT.setText(""+modello.perc_morti());
				lblPerNotInfOUT.setText(""+modello.perc_normali());
				lblPerRecOUT.setText(""+modello.perc_guariti());
			}
		});
		
		JButton btnClean = new JButton("START/CLEAN");
		btnClean.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClean.setBounds(10, 31, 440, 40);
		panelButtons.add(btnClean);
		btnClean.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MainClass.lava_vetri=true;
				lato=Integer.parseInt(tfRowsColumns.getText());
				p_c=Double.parseDouble(tfNumInf.getText());
				p_m=Double.parseDouble(tfNumDea.getText());
				p_g=Double.parseDouble(tfNumRec.getText());
				p_v=Double.parseDouble(tfNumVac.getText());
				NumPers=Integer.parseInt(tfNumPers.getText());
				if (cbQuar.isSelected()) 
				{
					quarantine=true;
				}
				else
					quarantine=false;
				main.start();
				modello=new Model(lato, p_c, p_m, p_g,p_v,time,NumPers,r_cont,r_mov,quarantine,from_the_beginning,Int_art);
				modello.setContatore(0);MainClass.lava_vetri=true;
				}});
		
		JButton btn1FW = new JButton("1 >");
		btn1FW.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn1FW.setBounds(280, 82, 80, 40);
		panelButtons.add(btn1FW);
		btn1FW.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){ 
				steps=1; 
				modello.diffusione2();
				lblNumStabOUT.setText(""+modello.getContatore());
				lblPerDeaOUT.setText(""+modello.perc_morti());
				lblPerNotInfOUT.setText(""+modello.perc_normali());
				lblPerRecOUT.setText(""+modello.perc_guariti());
			}});
		
		JButton btn10FW = new JButton("10 >>");
		btn10FW.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn10FW.setBounds(370, 82, 80, 40);
		panelButtons.add(btn10FW);
		btn10FW.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0){
				steps=10; 
				modello.diffusione2();
				lblNumStabOUT.setText(""+modello.getContatore());
				lblPerDeaOUT.setText(""+modello.perc_morti());
				lblPerNotInfOUT.setText(""+modello.perc_normali());
				lblPerRecOUT.setText(""+modello.perc_guariti());
			}});
		
		JLabel lblRunFor = new JLabel("Run for");
		lblRunFor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRunFor.setBounds(10, 140, 64, 20);
		panelButtons.add(lblRunFor);
		
		tfNumSteps = new JTextField();
		tfNumSteps.setBounds(64, 142, 86, 20);
		panelButtons.add(tfNumSteps);
		tfNumSteps.setColumns(10);
		
		JLabel lblSteps = new JLabel("steps");
		lblSteps.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSteps.setBounds(160, 140, 46, 20);
		panelButtons.add(lblSteps);
		
		JButton btnGo = new JButton("GO");
		btnGo.setBounds(216, 132, 80, 40);
		panelButtons.add(btnGo);	
		
		JCheckBox chckbxFaster = new JCheckBox("Faster");
		chckbxFaster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chckbxFaster.isSelected()==true) {
					time=5;
				}else {
					time=100;
				}
			}
		});
		chckbxFaster.setBounds(331, 141, 97, 23);
		panelButtons.add(chckbxFaster);
		
		
		
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0){ 
				steps=Integer.parseInt(tfNumSteps.getText());
				modello.diffusione2();
				lblNumStabOUT.setText(""+modello.getContatore());
				lblPerDeaOUT.setText(""+modello.perc_morti());
				lblPerNotInfOUT.setText(""+modello.perc_normali());
				lblPerRecOUT.setText(""+modello.perc_guariti());
			}});
		
		//VALORI DI DEFAULT
		tfRowsColumns.setText("99");
		tfNumPers.setText("2000");
		tfNumInf.setText("10");
		tfNumRec.setText("25");
		tfNumDea.setText("0.1");
		tfNumVac.setText("0");
		
		JButton btnFill = new JButton("Fill");
		btnFill.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tfNumPers.setText(""+Integer.parseInt(tfRowsColumns.getText())*Integer.parseInt(tfRowsColumns.getText()));
				cbRadMov.setSelectedIndex(0);
			}
		});
		btnFill.setBounds(121, 103, 59, 23);
		panelInput.add(btnFill);
		
		JLabel label = new JLabel("(90-300)");
		label.setBounds(221, 77, 56, 14);
		panelInput.add(label);
		
	
		
		JMenuBar menuBar = new JMenuBar();
		frmPandemic.setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmChiudi = new JMenuItem("Chiudi");
		mntmChiudi.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		mnMenu.add(mntmChiudi);
		
		JMenu mnMalattia = new JMenu("Disease");
		mnMalattia.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
		mnMenu.add(mnMalattia);
		

		ButtonGroup groupC = new ButtonGroup();
		JMenuItem Ebola = new JMenuItem("Ebola");
		groupC.add(Ebola);
		Ebola.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				
				tfNumInf.setText("0.8");
				tfNumRec.setText("2");
				tfNumDea.setText("2.28");
				
			}
			
		});
		mnMalattia.add(Ebola);
		
		JMenuItem measles = new JMenuItem("Measles");
		groupC.add(measles);
		measles.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				tfNumInf.setText("9.4");
				tfNumRec.setText("25");
				tfNumDea.setText("0.02");
			}
			
		});
		mnMalattia.add(measles);
		
		JMenuItem sars = new JMenuItem("SARS");
		groupC.add(sars);
		sars.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{

				tfNumInf.setText("1");
				tfNumRec.setText("4");
				tfNumDea.setText("0.36");

			}
			
		});
		mnMalattia.add(sars);
		

		JMenuItem pertussis = new JMenuItem("Pertussis");
		groupC.add(pertussis);
		pertussis.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{

				tfNumInf.setText("2.3");
				tfNumRec.setText("7");
				tfNumDea.setText("0.004");	
			}
			
		});
		mnMalattia.add(pertussis);
		
		JMenuItem Diphtheria = new JMenuItem("Diphtheria");
		groupC.add(Diphtheria);
		Diphtheria.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{

				tfNumInf.setText("6.8");
				tfNumRec.setText("15");
				tfNumDea.setText("1.35");
			}
			
		});
		mnMalattia.add(Diphtheria);
		
		JMenuItem mumps = new JMenuItem("Mumps");
		groupC.add(mumps);
		mumps.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{

				tfNumInf.setText("3.4");
				tfNumRec.setText("37");
				tfNumDea.setText("0.0005");
			}
			
		});
		mnMalattia.add(mumps);
		
		JMenuItem flu = new JMenuItem("Flu");
		groupC.add(flu);
		flu.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{

				tfNumInf.setText("2.9");
				tfNumRec.setText("69");
				tfNumDea.setText("0.004");
			}
			
		});
		mnMalattia.add(flu);
		
		JMenu mnQuarantine = new JMenu("Quarantine");
		mnMenu.add(mnQuarantine);
		
		ButtonGroup group = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("From the beginning");
		group.add(rbMenuItem);
		rbMenuItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				from_the_beginning=true;
			}
			
		});
		rbMenuItem.setSelected(true);
		mnQuarantine.add(rbMenuItem);
		
		JMenu mnAftersomesteps = new JMenu("After some steps");
		mnQuarantine.add(mnAftersomesteps);
		rbMenuItem = new JRadioButtonMenuItem("5 days");
		group.add(rbMenuItem);
		rbMenuItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				from_the_beginning=false;
				Model.time_quar=5;
			}
			
		});
		mnAftersomesteps.add(rbMenuItem);
		rbMenuItem = new JRadioButtonMenuItem("10 days");
		group.add(rbMenuItem);
		rbMenuItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				from_the_beginning=false;
				Model.time_quar=10;
			}
			
		});
		mnAftersomesteps.add(rbMenuItem);
		rbMenuItem = new JRadioButtonMenuItem("15 days");
		group.add(rbMenuItem);
		rbMenuItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				from_the_beginning=false;
				Model.time_quar=15;
			}
			
		});
		mnAftersomesteps.add(rbMenuItem);
		rbMenuItem = new JRadioButtonMenuItem("20 days");
		group.add(rbMenuItem);
		rbMenuItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				from_the_beginning=false;
				Model.time_quar=20;
			}
			
		});
		mnAftersomesteps.add(rbMenuItem);
		
		JMenu mnInt_art = new JMenu("Artificial Intelligence");
		mnMenu.add(mnInt_art);
		
		ButtonGroup group2 = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem2 = new JRadioButtonMenuItem("Disabled");
		group2.add(rbMenuItem2);
		rbMenuItem2.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Int_art=false;
			}
			
		});
		rbMenuItem2.setSelected(true);
		mnInt_art.add(rbMenuItem2);
		
		rbMenuItem2 = new JRadioButtonMenuItem("Enabled");
		group2.add(rbMenuItem2);
		rbMenuItem2.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Int_art=true;
				cbRadMov.setSelectedIndex(1);
				r_mov=1;
				chckbxFaster.setSelected(true);
				time=5;
				
			}
			
		});
		mnInt_art.add(rbMenuItem2);
	}
	}