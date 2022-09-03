package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.SwingUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import guiClasses.*;
import mojbrojClasses.MyNumbers;
import quizClasses.Questions;

public class Client {
	private Socket socketCommunication;
	private BufferedReader serverInput;
	public PrintStream serverOutput;
	//private static BufferedReader console;
	
	private WaitMonitor waiter;
	private boolean isQuit = false;
	
	private Username usernameGUI;
	private Pairing pairingGUI;
	private MojBroj mojbrojGUI;
	private Quiz quizGUI;
	private Asocijacije asocijacijeGUI;
	
	private String username;
	private String usernameOfPair;
	private int scores = 0;
	private int scoresOfPair = 0;
	
	public Client() {
		try {
			socketCommunication = new Socket("localhost", 9001);
			serverInput = new BufferedReader(new InputStreamReader(socketCommunication.getInputStream()));
			serverOutput = new PrintStream(socketCommunication.getOutputStream());
			//console = new BufferedReader(new InputStreamReader(System.in));
			//ovaj waiter nema veze sa onom listom waitera
			//ovaj nam služi za komunikaciju između guia i klijenta
		}catch (IOException e) {
			e.printStackTrace();
		}
		waiter = new WaitMonitor();
	}
	
	private void setUsername() throws IOException, InterruptedException {
		String input;
		//poslao je usernameGUIu waiter, sad klijent i username imaju referenu na istog waitera
		usernameGUI = new Username(waiter);
		while(true) {
			//ceka dok ne dobije notify od usernameGUI u 86-toj liniji koda Username klase
			synchronized(waiter) {
				waiter.wait();		//ova nit ce stajati dok klijent ne unese username u adekvatnom obliku
			}
			//kad dobije nofity izlazi iz synchronized boka, usernameGUI se dispose
			serverOutput.println(usernameGUI.getUsername());
			//salje se serveru username da bi proverio da li je vec neki klijent ulogovan sa tim usernameom
			//u 86toj liniji ClientHandler se prima odgovor
			input = serverInput.readLine();	//cita povratnu informaciju od servera
			if(input == null) return;
			//prenosi se i na GUI dobijena poruka(Dobrodošli...ili Uneti user je vec koriscen...)
			usernameGUI.setMessage(input);	
			if(input.equals("Uneti username je vec koriscen. Pokusaj opet!")) {
				continue;
			} else break;
		}
		username = usernameGUI.getUsername();	//ukoliko je prosao sve provere, username se dodeljuje klijentu
	}
	private void pair() throws IOException, InterruptedException {
		pairingGUI = new Pairing(waiter, username, serverOutput);
		synchronized(waiter) {
			waiter.wait();	//ova nit ce stajati dok klijent ne odabere opciju za povezivanje sa drugim igracem
		}
		char key = pairingGUI.getMessage();	//uzima se uneta opcija
		serverOutput.println(key);	//salje se serveru uneta opcija za povezivanje sa drugim igracem
		switch (key) {
		//OPCIJA NASUMICNOG POVEZIVANJA
		case 'R': {
			usernameOfPair = serverInput.readLine();	//cita se od servera username protivnika koji je ovom igracu dodeljen
			pairingGUI.setPairLabel(usernameOfPair);
			break;
		}
		//OPCIJA OTVARANJA SOBE I GENERISANJA KODA
		case 'G': {
			String code = serverInput.readLine();	//od servera se dobija generisan kod
			pairingGUI.setCode(code);
			usernameOfPair = serverInput.readLine();	//cita se od servera username protivnika koji je ovom igracu dodeljen
			pairingGUI.setPairLabel(usernameOfPair);
			break;
		}
		//OPCIJA PRISTUPANJA SOBI PUTEM KODA
		case 'P': {
			String code;
			while(true) {
				synchronized(waiter) {
					waiter.wait();	//ova nit ce stajati dok klijent ne unese kod u adekvatnom obliku
				}
				code = pairingGUI.getCode();
				serverOutput.println(code);	//serveru se salje kod da bi se pronasao igrac koji je izgenerisao taj kod
				String input = serverInput.readLine();	//cita se od servera povratna informacija
				if(input.equals("Uneli ste nepostojeci kod.")) {
					pairingGUI.repeatCode(input);
				} else {
					usernameOfPair = input;	//cita se od servera username protivnika koji je ovom igracu dodeljen
					pairingGUI.setPairLabel(usernameOfPair);
					break;
				}
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + key);
		}
	}
	private JSONObject parseJSON(String input) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject object = null;
		object = (JSONObject) parser.parse(input);
		return object;
	}
	private MyNumbers getMyNumbersFromJSON(JSONObject object) {
		JSONArray array = (JSONArray) object.get("brojevi");
		int[] brojevi = new int[4];
		for (int i = 0; i < 4; i++) {
			brojevi[i] = (int) ((long)(array.get(i)));
		}
		int srednjiBroj = (int) ((long)object.get("srednjiBroj"));
		int veciBroj = (int) ((long)object.get("veciBroj"));
		int wantedNumber = (int) ((long)object.get("wantedNumber"));
		return new MyNumbers(brojevi, srednjiBroj, veciBroj, wantedNumber);
	}
	private void startMojBroj() throws IOException, InterruptedException, ParseException {
		String input = serverInput.readLine();	//cita se od servera objekat koji predstavlja brojeve koji su random dodeljeni igracu
		JSONObject object = parseJSON(input);
		MyNumbers myNumbers = getMyNumbersFromJSON(object);	//izvrsena je transformacija objekta iz json formata u java
		mojbrojGUI = new MojBroj(myNumbers, waiter, username, usernameOfPair, scores, scoresOfPair, serverOutput);	//otvara se gui
		synchronized(waiter) {
			waiter.wait();	//ova nit ce stajati dok igrac ne zavrsi igru
		}
		serverOutput.println(Integer.toString(mojbrojGUI.getFinishedNumber()));	//serveru se salje rezultat igraca, tj razlika izmedju trazenog broja i dobijenog
		input = serverInput.readLine();	//cita se povratna informacija od servera o tome kako je i protivnik odigrao u odnosu na igraca
		mojbrojGUI.setMessageLabel(input);	//na osnovu serverove poruke dodeljuju se bodovi igracima
		if(input.equals("Protivnik je napustio igru.")) {
			isQuit = true;
			return;
		}
		scores = mojbrojGUI.getScores();	//cuvaju se bodovi igraca sa kraja igre kako bi se preneli u sledecu igru
		scoresOfPair = mojbrojGUI.getScoresOfPair();	//cuvaju se bodovi protivnika sa kraja igre kako bi se preneli u sledecu igru
	}
	private Questions[] getQuestionsFromJSON(JSONObject object) {
		JSONArray questions = (JSONArray) object.get("questions");
		Questions[] pitanjaNiz = new Questions[5];
		for(int i = 0; i < 5; i++) {
			JSONObject question = (JSONObject) questions.get(i);
			String pitanje = (String) question.get("question");
			String tacanOdgovor = (String) question.get("correctAnswer");
			JSONArray answers = (JSONArray) question.get("answers");
			String[] odgovori = new String[4];
			for(int j = 0; j < 4; j++) {
				odgovori[j] = (String) answers.get(j);
			}
			pitanjaNiz[i] = new Questions(odgovori, pitanje, tacanOdgovor);
		}
		return pitanjaNiz;
	}
	private void startQuiz() throws IOException, InterruptedException, ParseException {
		String input = serverInput.readLine();	//cita se od servera objekat koji predstavlja pitanja koja su random dodeljena igracu
		JSONObject object = parseJSON(input);
		Questions[] pitanjaNiz = getQuestionsFromJSON(object);	//izvrsena je transformacija objekta iz json formata u java
		quizGUI = new Quiz(pitanjaNiz, waiter, username, usernameOfPair, scores, scoresOfPair, serverOutput);	//otvara se gui
		int i = 0;
		do {
			synchronized(waiter) {
				waiter.wait();	//ova nit ce stajati dok igraca ne odigra potez (ne da odgovor na pitanje ili preskoci)
			}
			serverOutput.println(quizGUI.getIsCorrect());	//serveru se salje da li je igrac odgovorio tacno na pitanje ili ne, ili ga je preskocio
			input = serverInput.readLine();	//cita se povratna informacija od servera o tome kako je i protivnik odigrao u odnosu na igraca
			quizGUI.setMessage(input);	//na osnovu serverove poruke dodeljuju se bodovi igracima
			if(input.equals("Protivnik je napustio igru.")) {
				isQuit = true;
				return;
			}
			i++;
		} while (i < 5);	//ponavlja se 5 puta jer igra sadrzi 5 pitanja, dakle 5 poteza
		scores = quizGUI.getScores();	//cuvaju se bodovi igraca sa kraja igre kako bi se preneli u sledecu igru
		scoresOfPair = quizGUI.getScoresOfPair();	//cuvaju se bodovi protivnika sa kraja igre kako bi se preneli u sledecu igru
	}
	
	private void startAsocijacije() {
		//prvo pravim gui, njemu saljem username, usernamePair, scores, scoresOfPair, serverOutput
		
		String input;
		try {
			input=serverInput.readLine();
			JSONObject asocijacija=parseJSON(input);
			asocijacijeGUI=new Asocijacije(username, usernameOfPair, scores, scoresOfPair, serverOutput,
					(String)asocijacija.get("A1"), (String) asocijacija.get("A2"),(String)  asocijacija.get("A3"),(String) asocijacija.get("A4"),(String) asocijacija.get("B1"),(String) asocijacija.get("B2"),
					(String)asocijacija.get("B3"),(String) asocijacija.get("B4"),(String) asocijacija.get("C1"),(String)asocijacija.get("C2"),(String)asocijacija.get("C3"),(String) asocijacija.get("C4"),
					(String)asocijacija.get("D1"),(String) asocijacija.get("D2"), (String)asocijacija.get("D3"),(String) asocijacija.get("D4"),(String) asocijacija.get("A"), (String)asocijacija.get("B"),(String) asocijacija.get("C"),
					(String)asocijacija.get("D"),(String) asocijacija.get("Konacno"));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//sad valjda treba klijentska logika igre
		try {
			do {
				System.out.println("čeka na input u clientu");
				input=serverInput.readLine();
				System.out.println("dobio input klijent: "+input);
				switch(input) {
			
				case "započniTajmer":asocijacijeGUI.zapocniTajmer();
									break;
				case "omogućiEkran":asocijacijeGUI.omogucitiSvaOstalaPolja();
									asocijacijeGUI.KoJeNaPotezu.setText("Vi ste na potezu");
									asocijacijeGUI.KoJeNaPotezu.setLocation(230, 80);
									break;
				case "zabraniEkran":asocijacijeGUI.onemogucitiSvaOstalaPolja();
									asocijacijeGUI.editableKolone();
									//dal mi ovo treba???
									//asocijacije.ugasiDalje();
									asocijacijeGUI.KoJeNaPotezu.setText("Protivnik na potezu");
									asocijacijeGUI.KoJeNaPotezu.setLocation(222, 80);
								    break;
				case "A1Kliknut":asocijacijeGUI.a1update();
								break;
				case "A2Kliknut":asocijacijeGUI.a2update();
								break;
				case "A3Kliknut":asocijacijeGUI.a3update();
								break;
				case "A4Kliknut":asocijacijeGUI.a4update();
								break;
				case "AKonacnoUpdate":asocijacijeGUI.aKonacnoUpdate();
								break;
				case "B1Kliknut":asocijacijeGUI.b1update();
								break;
				case "B2Kliknut":asocijacijeGUI.b2update();
								break;
				case "B3Kliknut":asocijacijeGUI.b3update();
								break;
				case "B4Kliknut":asocijacijeGUI.b4update();
								break;
				case "BKonacnoUpdate":asocijacijeGUI.bKonacnoUpdate();
								break;
				case "C1Kliknut":asocijacijeGUI.c1update();
								break;
				case "C2Kliknut":asocijacijeGUI.c2update();
								break;
				case "C3Kliknut":asocijacijeGUI.c3update();
								break;
				case "C4Kliknut":asocijacijeGUI.c4update();
								break;
				case "CKonacnoUpdate":asocijacijeGUI.cKonacnoUpdate();
								break;
				case "D1Kliknut":asocijacijeGUI.d1update();
								break;
				case "D2Kliknut":asocijacijeGUI.d2update();
								break;
				case "D3Kliknut":asocijacijeGUI.d3update();
								break;
				case "D4Kliknut":asocijacijeGUI.d4update();
								break;
				case "DKonacnoUpdate":asocijacijeGUI.dKonacnoUpdate();
								break;
				case "KonacnoUpdate":asocijacijeGUI.KonacnoUpdate();
								break;
				case "ZavršenaPartija":asocijacijeGUI.zavrsenapartija();
								break;
				/*case "updatePoeniPrvog":
								scores=scores+asocijacijeGUI.getPoeni();
								asocijacijeGUI.poeniLabel.setText(""+scores);
								System.out.println(scores);
								break;
				case "updatePoeniDrugog":
								input=serverInput.readLine();
								scoresOfPair=scoresOfPair+Integer.parseInt(input);
								asocijacijeGUI.brojPoeniDrugi.setText(""+input);
								System.out.println(scoresOfPair);
								break;*/
				case "updateSvoje":
								scores=asocijacijeGUI.getPoeni();
								asocijacijeGUI.poeniLabel.setText(""+scores);
								break;
				case "updateProtivnik":
								input=serverInput.readLine();
								scoresOfPair=Integer.parseInt(input);
								asocijacijeGUI.poeniProtivnikLabel.setText(""+scoresOfPair);
								break;
				case "Izlaz":break;
				}	
				
			}while(!input.equals("Izlaz"));
			
			System.out.println("Zatvoren je soket za komunikaciju, klijent se ugasio!");
			return;
	
		} catch (IOException e) {
			System.out.println("Klijent izašao!");
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void posaljiServeru(String string) {
		serverOutput.println(string);
		System.out.println("Poslao je serveru:" +string);
	}
	
	public static void main(String[] args) throws ParseException {
			Client klijent=new Client();
			try {
				klijent.setUsername();	//klijent unosi username
				klijent.pair();
				klijent.startMojBroj();	//zapocinje se igra Moj Broj
				if(!klijent.isQuit) {
					klijent.startQuiz();	//zapocinje se igra Kviz (Ko zna zna)
				}
				
				if(!klijent.isQuit) {
					klijent.startAsocijacije();	
				}
				
				klijent.socketCommunication.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	//klijentu se dodeljuje drugi klijent, par
		
	}
	
}
