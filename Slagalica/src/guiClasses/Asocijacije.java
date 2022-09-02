package guiClasses;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import main.Client;

@SuppressWarnings("serial")
public class Asocijacije extends JFrame {
	
	private String username;
	private String usernameProtivnik;
	private PrintStream serverOutput;
	int poeniProtivnik;
	
	
	int poeni;
	Client client;
	Timer t=new Timer();
	TimerTask task;
	public JLabel KoJeNaPotezu=new JLabel("Vi ste na potezu");
	JLabel tajmerLabel=new JLabel();
	ImageIcon alarmIkona=new ImageIcon("alarmMali.png");
	JLabel alarmLabel=new JLabel();
	
	JButton Dalje=new JButton("Dalje");
	
	JButton A1=new JButton("A1");
	JButton A2=new JButton("A2");
	JButton A3=new JButton("A3");
	JButton A4=new JButton("A4");
	JTextField AKonacno=new JTextField("A");
	
	JButton B1=new JButton("B1");
	JButton B2=new JButton("B2");
	JButton B3=new JButton("B3");
	JButton B4=new JButton("B4");
	JTextField BKonacno=new JTextField("B");
	
	JButton C1=new JButton("C1");
	JButton C2=new JButton("C2");
	JButton C3=new JButton("C3");
	JButton C4=new JButton("C4");
	JTextField CKonacno=new JTextField("C");
	
	JButton D1=new JButton("D1");
	JButton D2=new JButton("D2");
	JButton D3=new JButton("D3");
	JButton D4=new JButton("D4");
	JTextField DKonacno=new JTextField("D");
	
	JTextField KonacnoResenje=new JTextField("Konačno rešenje");
	JLabel poeniPrviIgrac=new JLabel();
	public JLabel brojPoenaPrvi=new JLabel(""+poeni);
	
	JLabel poeniDrugiIgrac=new JLabel();
	public JLabel brojPoeniDrugi=new JLabel(""+poeniProtivnik);
	
	
	boolean A1Kliknut=false;
	boolean A2Kliknut=false;
	boolean A3Kliknut=false;
	boolean A4Kliknut=false;
	
	boolean B1Kliknut=false;
	boolean B2Kliknut=false;
	boolean B3Kliknut=false;
	boolean B4Kliknut=false;
	
	boolean C1Kliknut=false;
	boolean C2Kliknut=false;
	boolean C3Kliknut=false;
	boolean C4Kliknut=false;
	
	boolean D1Kliknut=false;
	boolean D2Kliknut=false;
	boolean D3Kliknut=false;
	boolean D4Kliknut=false;
	
	boolean APogodjen=false;
	boolean BPogodjen=false;
	boolean CPogodjen=false;
	boolean DPogodjen=false;
	
	
	
	public Asocijacije(Client client) {
			super("Asocijacije");
			this.client=client;
			this.initFrame();
		}
	
	public Asocijacije(String username,String usernameOfPair, int scores,int scoresOfPair,PrintStream serverOutput) {
		super("Asocijacije");
		this.username=username;
		this.usernameProtivnik=usernameOfPair;
		this.poeni=scores;
		this.poeniProtivnik=scoresOfPair;
		this.serverOutput=serverOutput;
		this.initFrame();
	}
	
	private void initFrame() {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.setSize(600,450);
		this.setLayout(null);
		KoJeNaPotezu.setSize(170, 30);
		KoJeNaPotezu.setLocation(230, 80);
		KoJeNaPotezu.setFont(new Font("Comic Sans MS", Font.BOLD,17));
		Dalje.setBounds(265, 130, 60, 30);
		Dalje.setMargin(new Insets(3,5,3,5));
		Dalje.setFont(new Font("Arial", Font.BOLD,15));
		tajmerLabel.setBounds(292, 40, 70, 40);
		tajmerLabel.setVerticalAlignment(JLabel.CENTER);
		alarmLabel.setIcon(alarmIkona);
		alarmLabel.setBounds(283, 10, 50, 50);
		
		poeniPrviIgrac.setText(username);
		poeniDrugiIgrac.setText(usernameProtivnik);
		
		tajmerLabel.setFont(new Font("Comic Sans MS", Font.BOLD,17));
		A1.setBounds(20, 10, 110,30);
		A2.setBounds(40, 45, 110,30);
		A3.setBounds(60, 80, 110, 30);
		A4.setBounds(80, 115, 110, 30);
		AKonacno.setBounds(100, 150, 120, 30);
		AKonacno.setFont(new Font("Arial", Font.BOLD,20));
		AKonacno.setBackground(new Color(0x8AC9C6));
		AKonacno.setHorizontalAlignment(JTextField.CENTER);
		
		
		KonacnoResenje.setBounds(150, 185, 300, 30);
		KonacnoResenje.setFont(new Font("Arial", Font.BOLD,20));
		KonacnoResenje.setBackground(new Color(0x8AC9C6));
		KonacnoResenje.setHorizontalAlignment(JTextField.CENTER);
		
		CKonacno.setBounds(100, 220, 120, 30);
		CKonacno.setFont(new Font("Arial", Font.BOLD,20));
		CKonacno.setBackground(new Color(0x8AC9C6));
		CKonacno.setHorizontalAlignment(JTextField.CENTER);
		
		C4.setBounds(80, 255, 110,30);
		C3.setBounds(60, 290, 110,30);
		C2.setBounds(40, 325, 110, 30);
		C1.setBounds(20, 360, 110, 30);
		
		B1.setBounds(460, 10, 110,30);
		B2.setBounds(440, 45, 110,30);
		B3.setBounds(420, 80, 110, 30);
		B4.setBounds(400, 115, 110, 30);
		
		BKonacno.setBounds(370, 150, 120, 30);
		BKonacno.setFont(new Font("Arial", Font.BOLD,20));
		BKonacno.setBackground(new Color(0x8AC9C6));
		BKonacno.setHorizontalAlignment(JTextField.CENTER);
		
		DKonacno.setBounds(370, 220, 120, 30);
		DKonacno.setFont(new Font("Arial", Font.BOLD,20));
		DKonacno.setBackground(new Color(0x8AC9C6));
		DKonacno.setHorizontalAlignment(JTextField.CENTER);
		
		D1.setBounds(400, 255, 110, 30);
		D2.setBounds(420, 290, 110, 30);
		D3.setBounds(440, 325, 110, 30);
		D4.setBounds(460, 360, 110, 30);
		
		poeniPrviIgrac.setBounds(200,30,50,20);
		brojPoenaPrvi.setBounds(200,50,50,20);
		brojPoenaPrvi.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		brojPoenaPrvi.setBackground(new Color(0x004EFF));
		brojPoenaPrvi.setHorizontalAlignment(JLabel.CENTER);
		brojPoenaPrvi.setOpaque(true);
		
		poeniDrugiIgrac.setBounds(350,30,50,20);
		brojPoeniDrugi.setBounds(350,50,50,20);
		brojPoeniDrugi.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		brojPoeniDrugi.setBackground(new Color(0xFF7276));
		brojPoeniDrugi.setHorizontalAlignment(JLabel.CENTER);
		brojPoeniDrugi.setOpaque(true);
		
		AKonacno.setEditable(false);
		BKonacno.setEditable(false);
		CKonacno.setEditable(false);
		DKonacno.setEditable(false);
		KonacnoResenje.setEditable(false);
		
		A1.setFocusable(false);
		A2.setFocusable(false);
		A3.setFocusable(false);
		A4.setFocusable(false);
		
		B1.setFocusable(false);
		B2.setFocusable(false);
		B3.setFocusable(false);
		B4.setFocusable(false);
		
		C1.setFocusable(false);
		C2.setFocusable(false);
		C3.setFocusable(false);
		C4.setFocusable(false);
		
		D1.setFocusable(false);
		D2.setFocusable(false);
		D3.setFocusable(false);
		D4.setFocusable(false);
		
		
		AKonacno.setFocusTraversalPolicyProvider(false);
		
		this.getContentPane().add(A1);		
		this.getContentPane().add(A2);		
		this.getContentPane().add(A3);
		this.getContentPane().add(A4);	
		this.getContentPane().add(C1);		
		this.getContentPane().add(C2);		
		this.getContentPane().add(C3);
		this.getContentPane().add(C4);
		this.getContentPane().add(B1);		
		this.getContentPane().add(B2);		
		this.getContentPane().add(B3);
		this.getContentPane().add(B4);
		this.getContentPane().add(D1);		
		this.getContentPane().add(D2);		
		this.getContentPane().add(D3);
		this.getContentPane().add(D4);
		this.getContentPane().add(AKonacno);
		this.getContentPane().add(KonacnoResenje);
		this.getContentPane().add(CKonacno);
		this.getContentPane().add(BKonacno);
		this.getContentPane().add(DKonacno);
		this.getContentPane().add(poeniPrviIgrac);
		this.getContentPane().add(brojPoenaPrvi);
		this.getContentPane().add(poeniDrugiIgrac);
		this.getContentPane().add(brojPoeniDrugi);
		this.getContentPane().add(KoJeNaPotezu);
		this.getContentPane().add(Dalje);
		this.getContentPane().add(tajmerLabel);
		this.getContentPane().add(alarmLabel);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		task=new TimerTask() {
			@Override
			public void run() {

				for(int i=60;i>0;i--) {
					tajmerLabel.setText(""+i);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				tajmerLabel.setText("");
				serverOutput.println("ZavršenaPartija");	
				serverOutput.println("KonacnoUpdate");	
				t.cancel();
			}	
		};
		
		Dalje.setEnabled(false);
		
		Dalje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ako klikne dalje to je kao da je omašio konacno pod a
				Dalje.setEnabled(false);
				serverOutput.println("završenTurn");
			}
		});
		
		A1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==A1) {
					a1update();
					if(AKonacno.isEditable()==false) {
						AKonacno.setEditable(true);
					}
					if(BPogodjen || CPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("A1Kliknut");
					//ova linija koda me jebe
					//kad se otvori a1 npr treba da ostanu fokusirana
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		A2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==A2) {
					a2update();
					if(AKonacno.isEditable()==false) {
						AKonacno.setEditable(true);
					}if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(BPogodjen || CPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("A2Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		A3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==A3) {
					a3update();
					if(AKonacno.isEditable()==false) {
						AKonacno.setEditable(true);
					}
					if(BPogodjen || CPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("A3Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		A4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==A4) {
					a4update();
					if(AKonacno.isEditable()==false) {
						AKonacno.setEditable(true);
					}
					if(BPogodjen || CPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("A4Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		AKonacno.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(AKonacno.isEditable())
					AKonacno.setText("");
			}
		});
		
		AKonacno.addActionListener(new ActionListener() {
			String resenjeA;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==AKonacno) {
					resenjeA=AKonacno.getText();
					resenjeA=resenjeA.trim().toLowerCase();
					if(resenjeA.equals("dinar")) {
						aKonacnoUpdate();
						if(KonacnoResenje.isEditable()==false) {
							KonacnoResenje.setEditable(true);
						}
						if(A1Kliknut==false) {
							a1update();
							serverOutput.println("A1Kliknut");
						}
						if(A2Kliknut==false) {
							a2update();
							serverOutput.println("A2Kliknut");
						}
						if(A3Kliknut==false) {
							a3update();
							serverOutput.println("A3Kliknut");
						}
						if(A4Kliknut==false) {
							a4update();
							serverOutput.println("A4Kliknut");
						}
						poeni+=10;
						updatePoeni();
						AKonacno.setEditable(false);
						serverOutput.println("AKonacnoUpdate");
					}else {
						AKonacno.setText("A");
						//isteko je turn
						serverOutput.println("završenTurn");
						ugasiDalje();	
					}
				}
			}
		});
		
		B1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==B1) {
					b1update();
					if(BKonacno.isEditable()==false) {
						BKonacno.setEditable(true);
					}
					if(APogodjen || CPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("B1Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		B2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==B2) {
					b2update();
					if(BKonacno.isEditable()==false) {
						BKonacno.setEditable(true);
					}
					if(APogodjen || CPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("B2Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		
		B3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==B3) {
					b3update();
					if(BKonacno.isEditable()==false) {
						BKonacno.setEditable(true);
					}
					if(APogodjen || CPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("B3Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		B4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==B4) {
					b4update();
					if(BKonacno.isEditable()==false) {
						BKonacno.setEditable(true);
					}
					if(APogodjen || CPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("B4Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		BKonacno.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(BKonacno.isEditable())
					BKonacno.setText("");
			}
		});
		
		BKonacno.addActionListener(new ActionListener() {
			String resenjeB;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==BKonacno) {
					resenjeB=BKonacno.getText();
					resenjeB=resenjeB.trim().toLowerCase();
					if(resenjeB.equals("rastanak")) {
						bKonacnoUpdate();
						if(KonacnoResenje.isEditable()==false) {
							KonacnoResenje.setEditable(true);
						}
						if(B1Kliknut==false) {
							b1update();
							serverOutput.println("B1Kliknut");
						}
						if(B2Kliknut==false) {
							b2update();
							serverOutput.println("B2Kliknut");
						}
						if(B3Kliknut==false) {
							b3update();
							serverOutput.println("B3Kliknut");
						}
						if(B4Kliknut==false) {
							b4update();
							serverOutput.println("B4Kliknut");
						}
						poeni+=10;
						updatePoeni();
						BKonacno.setEditable(false);
						serverOutput.println("BKonacnoUpdate");
					}else {
						BKonacno.setText("B");
						serverOutput.println("završenTurn");
						ugasiDalje();
					}
				}
			}
		});
		
		
		C1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==C1) {
					c1update();
					if(CKonacno.isEditable()==false) {
						CKonacno.setEditable(true);
					}
					if(APogodjen || BPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("C1Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		C2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==C2) {
					c2update();
					if(CKonacno.isEditable()==false) {
						CKonacno.setEditable(true);
					}
					if(APogodjen || BPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("C2Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		
		C3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==C3) {
					c3update();
					if(CKonacno.isEditable()==false) {
						CKonacno.setEditable(true);
					}
					if(APogodjen || BPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("C3Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		C4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==C4) {
					c4update();
					if(CKonacno.isEditable()==false) {
						CKonacno.setEditable(true);
					}
					if(APogodjen || BPogodjen || DPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(D1Kliknut || D2Kliknut || D3Kliknut || D4Kliknut) {
						DKonacno.setEditable(true);
					}
					serverOutput.println("C4Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		CKonacno.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(CKonacno.isEditable())
					CKonacno.setText("");
			}
		});
		
		CKonacno.addActionListener(new ActionListener() {
			String resenjeC;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==CKonacno) {
					resenjeC=CKonacno.getText();
					resenjeC=resenjeC.trim().toLowerCase();
					if(resenjeC.equals("buna")) {
						cKonacnoUpdate();
						if(KonacnoResenje.isEditable()==false) {
							KonacnoResenje.setEditable(true);
						}

						if(C1Kliknut==false) {
							c1update();
							serverOutput.println("C1Kliknut");
						}
						if(C2Kliknut==false) {
							c2update();
							serverOutput.println("C2Kliknut");
						}
						if(C3Kliknut==false) {
							c3update();
							serverOutput.println("C3Kliknut");
						}
						if(C4Kliknut==false) {
							c4update();
							serverOutput.println("C4Kliknut");
						}
						poeni+=10;
						updatePoeni();
						CKonacno.setEditable(false);
						serverOutput.println("CKonacnoUpdate");
					}else {
						CKonacno.setText("C");
						serverOutput.println("završenTurn");
						ugasiDalje();
					}		
				}
			}
		});
		
		
		D1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==D1) {
					d1update();
					if(DKonacno.isEditable()==false) {
						DKonacno.setEditable(true);
					}
					if(APogodjen || BPogodjen || CPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					serverOutput.println("D1Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		D2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==D2) {
					d2update();
					if(DKonacno.isEditable()==false) {
						DKonacno.setEditable(true);
					}
					if(APogodjen || BPogodjen || CPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEnabled(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEnabled(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEnabled(true);
					}
					serverOutput.println("D2Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		
		D3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==D3) {
					d3update();
					if(DKonacno.isEditable()==false) {
						DKonacno.setEditable(true);
					}
					if(APogodjen || BPogodjen || CPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					serverOutput.println("D3Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		D4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==D4) {
					d4update();
					if(DKonacno.isEditable()==false) {
						DKonacno.setEditable(true);
					}
					if(APogodjen || BPogodjen || CPogodjen) {
						KonacnoResenje.setEditable(true);
					}
					if(A1Kliknut || A2Kliknut || A3Kliknut || A4Kliknut) {
						AKonacno.setEditable(true);
					}
					if(B1Kliknut || B2Kliknut || B3Kliknut || B4Kliknut) {
						BKonacno.setEditable(true);
					}
					if(C1Kliknut || C2Kliknut || C3Kliknut || C4Kliknut) {
						CKonacno.setEditable(true);
					}
					serverOutput.println("D3Kliknut");
					onemogucitiSvaOstalaPolja();
					Dalje.setEnabled(true);
				}
			}
		});
		
		DKonacno.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(DKonacno.isEditable())
					DKonacno.setText("");
			}
		});
		
		DKonacno.addActionListener(new ActionListener() {
			String resenjeD;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==DKonacno) {
					resenjeD=DKonacno.getText();
					resenjeD=resenjeD.trim().toLowerCase();
					if(resenjeD.equals("dom")) {
						dKonacnoUpdate();
						if(KonacnoResenje.isEditable()==false) {
							KonacnoResenje.setEditable(true);
						}
						if(D1Kliknut==false) {
							d1update();
							serverOutput.println("D1Kliknut");
						}
						if(D2Kliknut==false) {
							d2update();
							serverOutput.println("D2Kliknut");
						}
						if(D3Kliknut==false) {
							d3update();
							serverOutput.println("D3Kliknut");
						}
						if(D4Kliknut==false) {
							d4update();
							serverOutput.println("D4Kliknut");
						}
						poeni+=10;
						updatePoeni();
						DKonacno.setEditable(false);
						serverOutput.println("DKonacnoUpdate");
					}else {
						DKonacno.setText("D");
						serverOutput.println("završenTurn");
						ugasiDalje();
					}		
				}
			}
		});
		
		KonacnoResenje.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if(KonacnoResenje.isEditable())
					KonacnoResenje.setText("");
			}
		});
		
		// OTKRITI SVA PREOSTALA POLJA
		// DODATI POENE
		KonacnoResenje.addActionListener(new ActionListener() {
			String konacnoResenje;
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==KonacnoResenje) {
					konacnoResenje=KonacnoResenje.getText();
					konacnoResenje=konacnoResenje.trim().toLowerCase();
					if(konacnoResenje.equals("djak") || konacnoResenje.equals("đak")) {
						KonacnoResenje.setText("Đak");
						KonacnoResenje.setEditable(false);
						KonacnoResenje.setBackground(new Color(0x008AD8));
						poeni+=10;
						if(APogodjen==false) {
							a1update();
							a2update();
							a3update();
							a4update();
							aKonacnoUpdate();
							poeni+=10;
						}
						if(BPogodjen==false) {
							b1update();
							b2update();
							b3update();
							b4update();
							bKonacnoUpdate();
							poeni+=10;
						}
						if(CPogodjen==false) {
							c1update();
							c2update();
							c3update();
							c4update();
							cKonacnoUpdate();
							poeni+=10;
						}
						if(DPogodjen==false) {
							d1update();
							d2update();
							d3update();
							d4update();
							dKonacnoUpdate();
							poeni+=10;
						}
						updatePoeni();
						serverOutput.println("KonacnoUpdate");
						serverOutput.println("ZavršenaPartija");
					}
					else {
						KonacnoResenje.setText("Konačno Rešenje");
						serverOutput.println("završenTurn");
						ugasiDalje();
					}
				}
			}
		});
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				serverOutput.println("Izlaz");
				System.exit(0);
			}
		});
		
		
	}
	
	public void a1update() {
		if(A1.isEnabled()==false) {
			A1.setEnabled(true);
		}
		A1.setText("Novac");
		A1.setBackground(new Color(0x008AD8));
		A1.setEnabled(false);
		A1Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void a2update() {
		if(A2.isEnabled()==false) {
			A2.setEnabled(true);
		}
		A2.setText("Srbija");
		A2.setBackground(new Color(0x008AD8));
		A2.setEnabled(false);
		A2Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void a3update() {
		if(A3.isEnabled()==false) {
			A3.setEnabled(true);
		}
		A3.setText("Alžir");
		A3.setBackground(new Color(0x008AD8));
		A3.setEnabled(false);
		A3Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void a4update() {
		if(A4.isEnabled()==false) {
			A4.setEnabled(true);
		}
		A4.setText("Kuvajt");
		A4.setBackground(new Color(0x008AD8));
		A4.setEnabled(false);
		A4Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void b1update() {
		if(B1.isEnabled()==false) {
			B1.setEnabled(true);
		}
		B1.setText("Ljubavni");
		B1.setBackground(new Color(0x008AD8));
		B1.setEnabled(false);
		B1Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void b2update() {
		if(B2.isEnabled()==false) {
			B2.setEnabled(true);
		}
		B2.setText("Raskid");
		B2.setBackground(new Color(0x008AD8));
		B2.setEnabled(false);
		B2Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void b3update() {
		if(B3.isEnabled()==false) {
			B3.setEnabled(true);
		}
		B3.setText("Hajdučki");
		B3.setBackground(new Color(0x008AD8));
		B3.setEnabled(false);
		B3Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void b4update() {
		if(B4.isEnabled()==false) {
			B4.setEnabled(true);
		}
		B4.setText("Oproštaj");
		B4.setBackground(new Color(0x008AD8));
		B4.setEnabled(false);
		B4Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void c1update() {
		if(C1.isEnabled()==false) {
			C1.setEnabled(true);
		}
		C1.setText("Reka");
		C1.setBackground(new Color(0x008AD8));
		C1.setEnabled(false);
		C1Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void c2update() {
		if(C2.isEnabled()==false) {
			C2.setEnabled(true);
		}
		C2.setText("Seljak");
		C2.setBackground(new Color(0x008AD8));
		C2.setEnabled(false);
		C2Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void c3update() {
		if(C3.isEnabled()==false) {
			C3.setEnabled(true);
		}
		C3.setText("Hadži prodan");
		C3.setBackground(new Color(0x008AD8));
		C3.setEnabled(false);
		C3Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void c4update() {
		if(C4.isEnabled()==false) {
			C4.setEnabled(true);
		}
		C4.setText("Timok");
		C4.setBackground(new Color(0x008AD8));
		C4.setEnabled(false);
		C4Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}

	public void d1update() {
		if(D1.isEnabled()==false) {
			D1.setEnabled(true);
		}
		D1.setText("Zdravlje");
		D1.setBackground(new Color(0x008AD8));
		D1.setEnabled(false);
		D1Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	

	public void d2update() {
		if(D2.isEnabled()==false) {
			D2.setEnabled(true);
		}
		D2.setText("Sindikat");
		D2.setBackground(new Color(0x008AD8));
		D2.setEnabled(false);
		D2Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}

	public void d3update() {
		if(D3.isEnabled()==false) {
			D3.setEnabled(true);
		}
		D3.setText("Skupština");
		D3.setBackground(new Color(0x008AD8));
		D3.setEnabled(false);
		D3Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}

	public void d4update() {
		if(D4.isEnabled()==false) {
			D4.setEnabled(true);
		}
		D4.setText("Omladina");
		D4.setBackground(new Color(0x008AD8));
		D4.setEnabled(false);
		D4Kliknut=true;
		UIManager.put("Button.disabledText", Color.BLACK);
	}
	
	public void aKonacnoUpdate() {
		AKonacno.setText("Dinar");
		//AKonacno.setEditable(false);
		AKonacno.setBackground(new Color(0x008AD8));
		APogodjen=true;
	}
	
	public void bKonacnoUpdate() {
		BKonacno.setText("Rastanak");
		BKonacno.setEditable(false);
		BKonacno.setBackground(new Color(0x008AD8));
		BPogodjen=true;
	}
	
	public void cKonacnoUpdate() {
		CKonacno.setText("Buna");
		CKonacno.setEditable(false);
		CKonacno.setBackground(new Color(0x008AD8));
		CPogodjen=true;
	}
	
	public void dKonacnoUpdate() {
		DKonacno.setText("Dom");
		DKonacno.setEditable(false);
		DKonacno.setBackground(new Color(0x008AD8));
		DPogodjen=true;
	}
	
	public void KonacnoUpdate() {
		//koancno update mora da updatuje sva ostala polja
		if(APogodjen==false) {
			if(A1Kliknut==false)
				a1update();
			if(A2Kliknut==false)
				a2update();
			if(A3Kliknut==false)
				a3update();
			if(A4Kliknut==false)
				a4update();
			aKonacnoUpdate();
		}
		if(BPogodjen==false) {
			if(B1Kliknut==false)
				b1update();
			if(B2Kliknut==false)
				b2update();
			if(B3Kliknut==false)
				b3update();
			if(B4Kliknut==false)
				b4update();
			bKonacnoUpdate();
		}
		if(CPogodjen==false) {
			if(C1Kliknut==false)
				c1update();
			if(C2Kliknut==false)
				c2update();
			if(C3Kliknut==false)
				c3update();
			if(C4Kliknut==false)
				c4update();
			cKonacnoUpdate();
		}
		if(DPogodjen==false) {
			if(D1Kliknut==false)
				d1update();
			if(D2Kliknut==false)
				d2update();
			if(D3Kliknut==false)
				d3update();
			if(D4Kliknut==false)
				d4update();
			dKonacnoUpdate();
		}

		KonacnoResenje.setText("Đak");
		KonacnoResenje.setEditable(false);
		KonacnoResenje.setBackground(new Color(0x008AD8));
	}
	
	
	
	public void onemogucitiSvaOstalaPolja() {
		//ZNACI AKO JE ENABLED I NIJE KLIKNUT ONDA GA UGASI
		//AKO JE ENABLED I KLIKNUT JE TO JE NEMOGUCE
		//AKO JE DISABLED ONDA JE KLIKNUT ODMA
		if(A1.isEnabled() && A1Kliknut==false) {
			A1.setEnabled(false);
		}
		if(A2.isEnabled() && A2Kliknut==false) {
			A2.setEnabled(false);
		}
		if(A3.isEnabled() && A3Kliknut==false) {
			A3.setEnabled(false);
		}
		if(A4.isEnabled() && A4Kliknut==false) {
			A4.setEnabled(false);
		}
		if(B1.isEnabled() && B1Kliknut==false) {
			B1.setEnabled(false);
		}
		if(B2.isEnabled() && B2Kliknut==false) {
			B2.setEnabled(false);
		}
		if(B3.isEnabled() && B3Kliknut==false) {
			B3.setEnabled(false);
		}
		if(B4.isEnabled() && B4Kliknut==false) {
			B4.setEnabled(false);
		}
		if(C1.isEnabled() && C1Kliknut==false) {
			C1.setEnabled(false);
		}
		if(C2.isEnabled() && C2Kliknut==false) {
			C2.setEnabled(false);
		}
		if(C3.isEnabled() && C3Kliknut==false) {
			C3.setEnabled(false);
		}
		if(C4.isEnabled() && C4Kliknut==false) {
			C4.setEnabled(false);
		}
		if(D1.isEnabled() && D1Kliknut==false) {
			D1.setEnabled(false);
		}
		if(D2.isEnabled() && D2Kliknut==false) {
			D2.setEnabled(false);
		}
		if(D3.isEnabled() && D3Kliknut==false) {
			D3.setEnabled(false);
		}
		if(D4.isEnabled() && D4Kliknut==false) {
			D4.setEnabled(false);
		}
		
	}
	
	public void upaliDalje() {
		if(Dalje.isEnabled()==false)
			Dalje.setEnabled(true);
	}
	
	public void ugasiDalje() {
		if(Dalje.isEnabled()==true)
			Dalje.setEnabled(false);
	}
	
	public void omogucitiSvaOstalaPolja() {
		if(A1.isEnabled()==false && A1Kliknut==false) {
			A1.setEnabled(true);
		}
		if(A2.isEnabled()==false && A2Kliknut==false) {
			A2.setEnabled(true);
		}
		if(A3.isEnabled()==false && A3Kliknut==false) {
			A3.setEnabled(true);
		}
		if(A4.isEnabled()==false && A4Kliknut==false) {
			A4.setEnabled(true);
		}
		if(B1.isEnabled()==false && B1Kliknut==false) {
			B1.setEnabled(true);
		}
		if(B2.isEnabled()==false && B2Kliknut==false) {
			B2.setEnabled(true);
		}
		if(B3.isEnabled()==false && B3Kliknut==false) {
			B3.setEnabled(true);
		}
		if(B4.isEnabled()==false && B4Kliknut==false) {
			B4.setEnabled(true);
		}
		if(C1.isEnabled()==false && C1Kliknut==false) {
			C1.setEnabled(true);
		}
		if(C2.isEnabled()==false && C2Kliknut==false) {
			C2.setEnabled(true);
		}
		if(C3.isEnabled()==false && C3Kliknut==false) {
			C3.setEnabled(true);
		}
		if(C4.isEnabled()==false && C4Kliknut==false) {
			C4.setEnabled(true);
		}
		if(D1.isEnabled()==false && D1Kliknut==false) {
			D1.setEnabled(true);
		}
		if(D2.isEnabled()==false && D2Kliknut==false) {
			D2.setEnabled(true);
		}
		if(D3.isEnabled()==false && D3Kliknut==false) {
			D3.setEnabled(true);
		}
		if(D4.isEnabled()==false && D4Kliknut==false) {
			D4.setEnabled(true);
		}
		if(A1Kliknut && A2Kliknut && A3Kliknut && A4Kliknut && 
				B1Kliknut && B2Kliknut && B3Kliknut && B4Kliknut && 
				C1Kliknut && C2Kliknut && C3Kliknut && C4Kliknut && 
				D1Kliknut && D2Kliknut && D3Kliknut && D4Kliknut) {
			Dalje.setEnabled(true);
			AKonacno.setEditable(true);
			BKonacno.setEditable(true);
			CKonacno.setEditable(true);
			DKonacno.setEditable(true);
			KonacnoResenje.setEditable(true);
		}
	}
	
	public void zavrsenapartija() {
		//OVDE TREBAJU DA SE OTKRIJU SVA MOGUĆA POLJA AKO VEĆ NISU OTKRIVENA
		//mislim da ne treba da onemogucim sva polja jer ce biti onemogucena po defaultu kad neko zavrsi
		//treba samo da stavim kraj partije
		if(AKonacno.isEditable()==true) {
			AKonacno.setEditable(false);
		}
		if(BKonacno.isEditable()==true) {
			BKonacno.setEditable(false);
		}
		if(BKonacno.isEditable()==true) {
			BKonacno.setEditable(false);
		}
		if(BKonacno.isEditable()==true) {
			BKonacno.setEditable(false);
		}
		Dalje.setEnabled(false);
		KoJeNaPotezu.setText("KRAJ PARTIJE!");
		KoJeNaPotezu.setLocation(240, 80);
		t.cancel();
	 }
	
	 public void updatePoeni() {
		 serverOutput.println("updatePoeni");
		 serverOutput.println(""+poeni);
	 }

	public void zapocniTajmer() {
		t.schedule(task, 1000);
	}
	
	public void editableKolone() {
		AKonacno.setEditable(false);
		BKonacno.setEditable(false);
		CKonacno.setEditable(false);
		DKonacno.setEditable(false);
		KonacnoResenje.setEditable(false);
	}
	
	
}
