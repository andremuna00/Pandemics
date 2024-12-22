
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
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
	private JTextField tfRowsColumns;
	private JTextField tfNumSteps;
	private int lato;
	public static int steps=0;
	private double p_c;
	private double p_m;
	private double p_g;
	private int time=0;
	private static MainClass main;
	public static boolean lava_vetri=false;
	
	
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
			g2.setColor(Color.black);
			g2.fillRect(0, 0, 700, 700);
			lava_vetri=false;
		}
			for(int i=0;i<modello.getLato();i++)
			{
				for(int j=0;j<modello.getLato();j++)
				{
					if(modello.getMondo()[i][j]==0)
						g.setColor(Color.white);
					if(modello.getMondo()[i][j]==1)
						g.setColor(Color.yellow);
					if(modello.getMondo()[i][j]==2)
						g.setColor(Color.green);
					if(modello.getMondo()[i][j]==3)
						g.setColor(Color.red);
					g.setFont(new Font("Century Gothic",Font.BOLD,600/modello.getLato()));
					g.drawString("X", j*(600/modello.getLato())+30, i*(600/modello.getLato())+30);
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
	
	private synchronized void stop()
	{
		try{thread.join();} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public void run()
	{
		while(true)
		render();
	}
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainClass window = new MainClass();
					window.setLocation(800, 0);
					window.frmPandemic.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		main = new MainClass();
		JFrame frame = new JFrame("MAT");
		frame.setSize(700, 700);
		frame.setLocation(10, 10);
		frame.add(main);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(Color.black);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	public MainClass() 
	{
		initialize();
	}

	private void initialize()
	{
		frmPandemic = new JFrame();
		frmPandemic.setTitle("Pandemic");
		frmPandemic.setBounds(100, 100, 500, 718);
		frmPandemic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPandemic.getContentPane().setLayout(null);
		
		JPanel panelInput = new JPanel();
		panelInput.setBounds(10, 11, 460, 240);
		frmPandemic.getContentPane().add(panelInput);
		panelInput.setLayout(null);
		
		JLabel lblDataInput = new JLabel("DATA INPUT");
		lblDataInput.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblDataInput.setBounds(10, 8, 150, 30);
		panelInput.add(lblDataInput);
		
		JLabel lblMatrix = new JLabel("MATRIX");
		lblMatrix.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMatrix.setBounds(30, 49, 150, 20);
		panelInput.add(lblMatrix);
		
		JLabel lblRowsColumns = new JLabel("Number of rows/columns");
		lblRowsColumns.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRowsColumns.setBounds(60, 80, 170, 20);
		panelInput.add(lblRowsColumns);
		
		tfRowsColumns = new JTextField();
		tfRowsColumns.setBounds(290, 82, 86, 20);
		panelInput.add(tfRowsColumns);
		tfRowsColumns.setColumns(10);
		
		JLabel lblProbabilities = new JLabel("PROBABILITIES");
		lblProbabilities.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblProbabilities.setBounds(30, 114, 150, 20);
		panelInput.add(lblProbabilities);
		
		JLabel lblProbInf = new JLabel("Probability of infection");
		lblProbInf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProbInf.setBounds(60, 145, 150, 17);
		panelInput.add(lblProbInf);
		
		tfNumInf = new JTextField();
		tfNumInf.setBounds(290, 145, 86, 20);
		panelInput.add(tfNumInf);
		tfNumInf.setColumns(10);
		
		JLabel lblProbRec = new JLabel("Probability of recovery");
		lblProbRec.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProbRec.setBounds(60, 175, 150, 20);
		panelInput.add(lblProbRec);
		
		tfNumRec = new JTextField();
		tfNumRec.setBounds(290, 176, 86, 20);
		panelInput.add(tfNumRec);
		tfNumRec.setColumns(10);
		
		JLabel lblProbDea = new JLabel("Probability of death");
		lblProbDea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblProbDea.setBounds(60, 206, 150, 20);
		panelInput.add(lblProbDea);
		
		tfNumDea = new JTextField();
		tfNumDea.setBounds(290, 208, 86, 20);
		panelInput.add(tfNumDea);
		tfNumDea.setColumns(10);
		
		JLabel lblOptions = new JLabel("");
		lblOptions.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBounds(10, 262, 460, 182);
		frmPandemic.getContentPane().add(panelButtons);
		panelButtons.setLayout(null);
		
		JPanel panelInfo = new JPanel();
		panelInfo.setBounds(10, 455, 464, 210);
		frmPandemic.getContentPane().add(panelInfo);
		panelInfo.setLayout(null);
		
		JLabel lblNumStab = new JLabel("Number of steps until stabilization");
		lblNumStab.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumStab.setBounds(20, 81, 230, 20);
		panelInfo.add(lblNumStab);
		
		JLabel lblNumStabOUT = new JLabel("");
		lblNumStabOUT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumStabOUT.setBounds(320, 81, 50, 20);
		panelInfo.add(lblNumStabOUT);
		
		JLabel lblPerDea = new JLabel("Percentage of dead population");
		lblPerDea.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPerDea.setBounds(20, 117, 200, 20);
		panelInfo.add(lblPerDea);
		
		JLabel lblPerDeaOUT = new JLabel("");
		lblPerDeaOUT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPerDeaOUT.setBounds(320, 117, 50, 20);
		panelInfo.add(lblPerDeaOUT);
		
		JLabel lblPerSym1 = new JLabel("%");
		lblPerSym1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPerSym1.setBounds(380, 117, 20, 20);
		panelInfo.add(lblPerSym1);
		
		
		JLabel lblPerRec = new JLabel("Percentage of recovered population");
		lblPerRec.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPerRec.setBounds(20, 148, 250, 20);
		panelInfo.add(lblPerRec);
		
		JLabel lblPerRecOUT = new JLabel("");
		lblPerRecOUT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPerRecOUT.setBounds(320, 148, 50, 20);
		panelInfo.add(lblPerRecOUT);
		
		
		JLabel lblPerSym2 = new JLabel("%");
		lblPerSym2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPerSym2.setBounds(380, 148, 20, 20);
		panelInfo.add(lblPerSym2);
		
		JLabel lblPerNotInf = new JLabel("Percentage of not infected population");
		lblPerNotInf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPerNotInf.setBounds(20, 179, 250, 20);
		panelInfo.add(lblPerNotInf);
		
		JLabel lblPerNotInfOUT = new JLabel("");
		lblPerNotInfOUT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPerNotInfOUT.setBounds(320, 179, 50, 20);
		panelInfo.add(lblPerNotInfOUT);
		
		
		JButton btnRun = new JButton("RUN UNTIL END");
		btnRun.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRun.setBounds(10, 62, 250, 40);
		panelButtons.add(btnRun);
		btnRun.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				modello.diffusione();
				lblNumStabOUT.setText(""+modello.getContatore());
				lblPerDeaOUT.setText(""+modello.perc_morti());
				lblPerNotInfOUT.setText(""+modello.perc_normali());
				lblPerRecOUT.setText(""+modello.perc_guariti());
			}
		});
		
		JButton btnClean = new JButton("START/CLEAN");
		btnClean.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClean.setBounds(10, 11, 440, 40);
		panelButtons.add(btnClean);
		btnClean.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				MainClass.lava_vetri=true;
				lato=Integer.parseInt(tfRowsColumns.getText());
				p_c=Double.parseDouble(tfNumInf.getText());
				p_m=Double.parseDouble(tfNumDea.getText());
				p_g=Double.parseDouble(tfNumRec.getText());
				main.start();
				modello=new Model(lato, p_c, p_m, p_g,time);
				modello.setContatore(0);MainClass.lava_vetri=true;
				}});
		
		JButton btn1FW = new JButton("1 >");
		btn1FW.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btn1FW.setBounds(280, 62, 80, 40);
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
		btn10FW.setBounds(370, 62, 80, 40);
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
		
		JLabel lblInfo = new JLabel("INFO");
		lblInfo.setBounds(10, 11, 126, 25);
		lblInfo.setFont(new Font("Tahoma", Font.BOLD, 20));
		panelInfo.add(lblInfo);
		
		JLabel lblStab = new JLabel("Does the matrix stabilize before the end of the steps?");
		lblStab.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStab.setBounds(20, 50, 350, 20);
		panelInfo.add(lblStab);
		
		JLabel lblPerSym3 = new JLabel("%");
		
		lblPerSym3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPerSym3.setBounds(380, 179, 20, 20);
		panelInfo.add(lblPerSym3);
		
	}
}

