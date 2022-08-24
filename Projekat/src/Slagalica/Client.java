package Slagalica;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client extends Thread{

	Socket soketZaKomunikaciju = null;
	BufferedReader serverInput = null;
	PrintStream serverOutput = null;
	Asocijacije asocijacije;
	int poeniPrviIgrac=0,poeniDrugiIgrac=0;
	
	
	public Client() {
		asocijacije=new Asocijacije(this);
		try {
			soketZaKomunikaciju = new Socket("localhost", 9001);
			
			serverInput=new BufferedReader(new InputStreamReader(soketZaKomunikaciju.getInputStream()));
			serverOutput=new PrintStream(soketZaKomunikaciju.getOutputStream());
			
			}catch (UnknownHostException e) {
				System.out.println("Nepoznat HOST!");
			}catch (IOException e) {
				System.out.println("Server je pao!");
			}
	}

	//new Thread(new Client()).start();

	@Override
	public void run() {
		String input;
		System.out.println("Radi nit klijentska");
		try {
			
				
				do {
					System.out.println("čeka na input u clientu");
					input=serverInput.readLine();
					System.out.println("dobio input klijent: "+input);
					switch(input) {
				
					case "započniTajmer":asocijacije.zapocniTajmer();
										break;
					case "omogućiEkran":asocijacije.omogucitiSvaOstalaPolja();
										asocijacije.KoJeNaPotezu.setText("Vi ste na potezu");
										asocijacije.KoJeNaPotezu.setLocation(230, 80);
										break;
					case "zabraniEkran":asocijacije.onemogucitiSvaOstalaPolja();
										//dal mi ovo treba???
										//asocijacije.ugasiDalje();
										asocijacije.KoJeNaPotezu.setText("Protivnik na potezu");
										asocijacije.KoJeNaPotezu.setLocation(222, 80);
									    break;
					case "A1Kliknut":asocijacije.a1update();
									break;
					case "A2Kliknut":asocijacije.a2update();
									break;
					case "A3Kliknut":asocijacije.a3update();
									break;
					case "A4Kliknut":asocijacije.a4update();
									break;
					case "AKonacnoUpdate":asocijacije.aKonacnoUpdate();
									break;
					case "B1Kliknut":asocijacije.b1update();
									break;
					case "B2Kliknut":asocijacije.b2update();
									break;
					case "B3Kliknut":asocijacije.b3update();
									break;
					case "B4Kliknut":asocijacije.b4update();
									break;
					case "BKonacnoUpdate":asocijacije.bKonacnoUpdate();
									break;
					case "C1Kliknut":asocijacije.c1update();
									break;
					case "C2Kliknut":asocijacije.c2update();
									break;
					case "C3Kliknut":asocijacije.c3update();
									break;
					case "C4Kliknut":asocijacije.c4update();
									break;
					case "CKonacnoUpdate":asocijacije.cKonacnoUpdate();
									break;
					case "D1Kliknut":asocijacije.d1update();
									break;
					case "D2Kliknut":asocijacije.d2update();
									break;
					case "D3Kliknut":asocijacije.d3update();
									break;
					case "D4Kliknut":asocijacije.d4update();
									break;
					case "DKonacnoUpdate":asocijacije.dKonacnoUpdate();
									break;
					case "KonacnoUpdate":asocijacije.KonacnoUpdate();
									break;
					case "ZavršenaPartija":asocijacije.zavrsenapartija();
									break;
					case "updatePoeniPrvog":
									input=serverInput.readLine();
									asocijacije.brojPoenaPrvi.setText(""+input);
									poeniPrviIgrac=poeniPrviIgrac+Integer.parseInt(input);
									System.out.println(poeniPrviIgrac);
									break;
					case "updatePoeniDrugog":
									input=serverInput.readLine();
									asocijacije.brojPoeniDrugi.setText(""+input);
									poeniDrugiIgrac=poeniDrugiIgrac+Integer.parseInt(input);
									System.out.println(poeniDrugiIgrac);
									break;
					case "Izlaz":break;
					}	
					
				}while(!input.equals("Izlaz"));
				
				soketZaKomunikaciju.close();
				System.out.println("Zatvoren je soket za komunikaciju, klijent se ugasio!");
		
			} catch (IOException e) {
				System.out.println("Klijent izašao!");
				e.printStackTrace();
			}
		}
		
	public void posaljiServeru(String string) {
		serverOutput.println(string);
		System.out.println("Poslao je serveru:" +string);
	}
	
}



