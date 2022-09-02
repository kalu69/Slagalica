package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import mojbrojClasses.MyNumbers;
import quizClasses.Questions;

public class ClientHandler extends Thread {
	private BufferedReader clientInput = null;
	private PrintStream clientOutput = null;
	private Socket socketCommunication = null;
	private WaitMonitor waiterPair;
	boolean zavrsenKviz=false;
	private String username;
	private boolean isQuit = false;	//promenljiva koja odredjuje da li klijent zavrsava igru
	private ClientHandler pair;	//promenljiva koja referencira ClientHandler instancu koja predstavlja igraca koji je par ovom igracu (ovoj instanci)
	private boolean isPaired = false;	//promenljiva koja odredjuje da li je klijentu dodeljen njegov par (protivnik)
	private String code; //promenljiva koja predstavlja generisan kod sobe kojoj kojoj je igrac pristupio (ukoliko je preko koda)
	
	//ATRIBUTI ZA IGRU MOJ BROJ
	private MyNumbers myNumbers;	//random brojevi koji se dodeljuju igracu u igri
	private boolean isMojBrojPlayed = false;
	private boolean isMojBrojPlayedOfPair = false;
	private int mojBrojFinishedNumber;	//razlika izmedju dobijenog i trazenog broja ovog igraca u igri Moj Broj
	private int mojBrojFinishedNumberOfPair;
	//ATRIBUTI ZA IGRU KVIZ (KO ZNA ZNA)
	private Questions[] nizPitanja;		//random pitanja koja se dodeljuju igracu u igri
	private boolean isQuestionAnswered = false;
	private boolean isQuestionAnsweredOfPair = false;
	private String isCorrectAnswer;
	private String isCorrectAnswerOfPair;
	int c;
	int a=0;
	
	
	ClientHandler(Socket socketCommunication,int c) {
		this.socketCommunication = socketCommunication;
		this.c=c;
	}
	
	ClientHandler(String username){
		this.username = username;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ClientHandler)) {
			return false;
		}
		ClientHandler client = (ClientHandler) obj;
		if(this.username.equals(client.username)) {
			return true;
		} else return false;
	}
	
	
	public void setIsQuit(boolean hm) {
		isQuit = hm;
	}
	
	
	private Questions[] getRandomQuestions() {
		Questions[] pitanja = new Questions[5];
		LinkedList<Questions> questions = (LinkedList<Questions>) Server.questionsList.clone();
		for (int i = 0; i < pitanja.length; i++) {
			pitanja[i] = questions.get(new Random().nextInt(questions.size()));
			questions.remove(pitanja[i]);
		}
		return pitanja;
	}
	
	
	//ova metoda se ne koristi, ova ispod
	private void createNewWaiterPair() {

		Server.waitersPair.remove(waiterPair);
		waiterPair = new WaitMonitor(2);
		pair.waiterPair = waiterPair;
		Server.waitersPair.add(waiterPair);
	}
	
	
	
	private void setUsername() throws IOException {
		String input;
		do {
			input = clientInput.readLine();	//cita se String koji je klijent uneo kao svoj username
			if(Server.onlineUsers.contains(new ClientHandler(input))) {
				clientOutput.println("Uneti username je vec koriscen. Pokusaj opet!");
				continue;
			} else break;
		} while (true);
		username = input;
		Server.onlineUsers.add(this);	//dodaje se listi aktivnih usera trenutni klijent
		clientOutput.println("Dobrodosli " + username + "!");
	}
	
	
	
	private void pairRandom() throws InterruptedException {
		synchronized(waiterPair) {
			for (ClientHandler client : Server.onlineUsers) {
				if(client != this && client.isPaired == false && waiterPair == client.waiterPair) {
					//kada se nadju dve razlicite instance koje nisu povezane i imaju isti WaitMonitor objekat, povezuju se
					client.pair = this;
					this.pair = client;
					pair.isPaired = true;
					this.isPaired = true;
					//dodeljuju se suparnicima isti brojevi i ista pitanja za igre
					myNumbers = new MyNumbers();
					pair.myNumbers = this.myNumbers;
					nizPitanja = getRandomQuestions();
					pair.nizPitanja = this.nizPitanja;
					waiterPair.notify();	//obavestava se instanca koja ceka da bude povezana, da je povezana
					/*
					 *
					 */
					break;
				}
			}
		}
		if(!isPaired) {
			ExitThread exit = new ExitThread(waiterPair, this, socketCommunication);
			exit.start();	//pokrece se nit koja osluskuje da li je klijent napustio igru
			synchronized(waiterPair) {
				waiterPair.wait();	//ova nit ce stajati dok ne primi obavestenje da je instanca povezana ili da je klijent napustio igru
			}
			if(isQuit == false) {
				exit.interrupt();	//ukoliko je klijent povezan i nije napustio igru, exit nit se zatvara
			} else return;
		}
		clientOutput.println(pair.username);	//klijentu se salje username njegovog para, protivnika
	}
	
	
	
	private void pairByCode() throws IOException, InterruptedException {	
		for (ClientHandler client : Server.onlineUsers) {
			if(client != this && client.isPaired == false && code.equals(client.code)) {
				//instanci se dodeljuje WaitMonitor objekat koji ima instanca koja ima i isti kod, kako bi se niti sinhronizovale
				this.waiterPair = client.waiterPair;
				waiterPair.addConnection();
				Server.waitersPair.add(waiterPair);
				synchronized(waiterPair) {
					client.pair = this;
					this.pair = client;
					pair.isPaired = true;
					this.isPaired = true;
					//dodeljuju se suparnicima isti brojevi i ista pitanja za igre
					myNumbers = new MyNumbers();
					pair.myNumbers = this.myNumbers;
					nizPitanja = getRandomQuestions();
					pair.nizPitanja = this.nizPitanja;
					waiterPair.notify();	//obavestava se instanca koja ceka da bude povezana, da je povezana
					code = null; client.code = null;	//kod kojim su klijenti povezani im vise nije potreban
					break;
				}
			}
		}
		clientOutput.println(pair.username);	//klijentu se salje username njegovog para, protivnika
	}
	private void pairing() throws IOException, InterruptedException {
		String key = clientInput.readLine();	//cita se od klijenta uneta opcija za povezivanje sa drugim igracem 
		switch (key) {
		//OPCIJA NASUMICNOG POVEZIVANJA
		case "R": {
			for (WaitMonitor waiter : Server.waitersPair) {
				if(waiter.getConnectionCounter() < 2) {
					waiterPair = waiter;
					//ovoj instanci ClientHandler klase dodeljuje se WaitMonitor objekat koji nije zauzelo
					//vise od jedne instance ClientHandler klase kako bi se te dve instance sinhronizovale putem tog objekta
					waiterPair.addConnection();
					break;
				}
			}
			pairRandom();	//pokrece se metoda koja vrsi "nasumicno povezivanje"
			break;
		}
		//OPCIJA OTVARANJA SOBE I GENERISANJA KODA
		case "G": {
			synchronized(Server.codesList) {
				code = Server.generateCode();	//generise se random kod
				Server.codesList.add(code);	//izgenerisani kod se dodaje u listu gde se cuvaju svi trenutno aktivni kodovi
			}
			clientOutput.println(code);	//klijentu se salje izgenerisani kod
			waiterPair = new WaitMonitor(1);	//instanci se dodeljuje nov WaitMonitor objekat
			//preko kog ce se sinhronizovati instanca ClientHandler klase koja hendluje klijenta koji je pristupio sobi putem istog koda
			while(!isPaired) {
				ExitThread exit = new ExitThread(waiterPair, this, socketCommunication);
				exit.start();	//pokrece se nit koja osluskuje da li je klijent napustio igru
				synchronized(waiterPair) {
					waiterPair.wait();	//ova nit ce stajati dok ne primi obavestenje da je instanca povezana ili da je klijent napustio igru
				}
				if(isQuit == false) {
					exit.interrupt();	//ukoliko je klijent povezan i nije napustio igru, exit nit se zatvara
				}
				else return;
			}
			clientOutput.println(pair.username);	//klijentu se salje username njegovog para, protivnika
			break;
		}
		//OPCIJA PRISTUPANJA SOBI PUTEM KODA
		case "P": {
			while(true) {
				String input = clientInput.readLine();	//cita se kod koji je uneo klijent
				synchronized(Server.codesList) {
					if(!Server.codesList.contains(input)) {
						clientOutput.println("Uneli ste nepostojeci kod.");
						continue;
					} else {
						code = input;
						Server.codesList.remove(code);	//kada se klijentima dodeli isti kod po kom ce se spojiti taj kod se brise iz baze kako se ne bi niko vise njim spojio
						break;
					}
				}
			}
			pairByCode();	//pokrece se metoda kojom se vrsi povezivanje putem koda
			break;
		}
		default:
			setIsQuit(true);
			return;
		}
	}
	private void writeMyNumbersJson() {
		JSONObject objectJson = new JSONObject();
		JSONArray arrayJson = new JSONArray();
		for (int i = 0; i < myNumbers.getBrojeviLength(); i++) {
			arrayJson.add(myNumbers.getBroj(i));
		}
		objectJson.put("brojevi", arrayJson);
		objectJson.put("srednjiBroj", myNumbers.getSrednjiBroj());
		objectJson.put("veciBroj", myNumbers.getVeciBroj());
		objectJson.put("wantedNumber", myNumbers.getWantedNumber());
		clientOutput.println(objectJson.toJSONString());
	}
	private void startMojBroj() throws IOException, InterruptedException {
		writeMyNumbersJson();	//klijentu se salju random izgenerisani brojevi za igru
		String message = clientInput.readLine();	//cita se poruka od klijenta u kojoj bi trebalo da se nalazi rezultat igre moj broj, tj. razlika izmedju trazenog i dobijenog broja
		if(message.equals("EXIT")) {
			setIsQuit(true);
			pair.setIsQuit(true);
			if(pair.isMojBrojPlayed) {
				synchronized(waiterPair) {
					waiterPair.notify();
				}
			}
			return;
		}
		if(isQuit) {
			clientOutput.println("Protivnik je napustio igru.");	//klijent se obavestava da je protivnik napustio igru
			return;
		}
		mojBrojFinishedNumber = Integer.parseInt(message);
		isMojBrojPlayed = true;
		if(pair.isMojBrojPlayed) {
			synchronized(waiterPair) {
				pair.mojBrojFinishedNumberOfPair = mojBrojFinishedNumber;
				pair.isMojBrojPlayedOfPair = isMojBrojPlayed;
				mojBrojFinishedNumberOfPair = pair.mojBrojFinishedNumber;
				isMojBrojPlayedOfPair = pair.isMojBrojPlayed;
				waiterPair.notify();
				//instanca ce se naci u ovom slucaju ukoliko je njena odgovarajuca instanca Client klase druga zavrsila igru
				//zato obavestava svog para (tj. povezanu instancu ClientHandler klase, tj. pair (atribut ove klase)) da je i on zavrsio
			}
		} else {
			while(!isMojBrojPlayedOfPair) {
				synchronized(waiterPair) {
					waiterPair.wait();	//ova nit ce stajati dok ne primi obavestenje da je protivnik isto zavrsio igru
				}
				if(isQuit) {
					clientOutput.println("Protivnik je napustio igru.");
					return;
				}
			}
		}
		//ClientHandler salje svom Clientu poruku o tome koji je ishod igre u zavisnosti od dobijenih rezultata igraca
		if(mojBrojFinishedNumber < mojBrojFinishedNumberOfPair) {
			clientOutput.println("Pobedili ste!");
		} else if(mojBrojFinishedNumber > mojBrojFinishedNumberOfPair) {
			clientOutput.println("Izgubili ste!");
		} else if(mojBrojFinishedNumber == Integer.MAX_VALUE && mojBrojFinishedNumberOfPair == Integer.MAX_VALUE) {
			clientOutput.println("Oba igraca bez bodova!");
		} else {
			clientOutput.println("Nereseno!");
		} 
	}
	private JSONArray initializeAnswersJSON(Questions pitanje) {
		JSONArray answers = new JSONArray();
		for (int i = 0; i < 4; i++) {
			answers.add(pitanje.getAnswer(i));
		}
		return answers;
	}
	private void writePitanja(Questions[] nizPitanja) {
		JSONObject quiz = new JSONObject();
		JSONArray questions = new JSONArray();
		for(int questionIndex = 0; questionIndex < 5; questionIndex++) {
			JSONObject question = new JSONObject();
			JSONArray answers = initializeAnswersJSON(nizPitanja[questionIndex]);
			question.put("answers", answers);
			question.put("question", nizPitanja[questionIndex].getQuestion());
			question.put("correctAnswer", nizPitanja[questionIndex].getCorrectAnswer());
			questions.add(question);
		}
		quiz.put("questions", questions);
		clientOutput.println(quiz.toJSONString());
	}
	private void startQuiz() throws IOException, InterruptedException {
		writePitanja(nizPitanja);	//klijentu se salju random izgenerisana pitanja za igru
		
		String input;
		int i = 0;
		do {
			input = clientInput.readLine();	//cita se od klijenta poruka da li je odgovorio tacno ili mozda napustio igru
			if(input.equals("EXIT")) {
				setIsQuit(true);
				pair.setIsQuit(true);
				synchronized(waiterPair) {
					waiterPair.notify();	//obavestava se par da je klijent napustio igru
				}
				return;
			}
			if(isQuit) {
				clientOutput.println("Protivnik je napustio igru.");	//obavestava se klijent da je par napustio igru
				return;
			}
			isCorrectAnswer = input;
			isQuestionAnswered = true;
			if(pair.isQuestionAnswered) {
				synchronized(waiterPair) {
					pair.isCorrectAnswerOfPair = this.isCorrectAnswer;
					pair.isQuestionAnsweredOfPair = true;
					this.isCorrectAnswerOfPair = pair.isCorrectAnswer;
					waiterPair.notify();	//obavestava se protivnik da je klijent odigrao potez
				}
			} else {
				while(!isQuestionAnsweredOfPair) {
					synchronized(waiterPair) {
						waiterPair.wait();	//ova nit ce stajati dok ne primi obavestenje da je i protivnik odigrao potez ili mozda napustio igru
					}
					if(isQuit) {
						clientOutput.println("Protivnik je napustio igru.");
						return;
					}
				}
			}
			String key = isCorrectAnswer + "-" + isCorrectAnswerOfPair;
			clientOutput.println(key);	//klijentu se salje ishod poteza u zavisnosti od odgovora igraca
			isQuestionAnswered = false; isCorrectAnswer = null; isQuestionAnsweredOfPair = false; isCorrectAnswerOfPair = null;
			i++;
		} while(i < 5);	//ponavlja se 5 puta jer igra sadrzi 5 pitanja, dakle 5 poteza
		
		zavrsenKviz=true;
		if (pair.zavrsenKviz == false) {
			synchronized (waiterPair) {
				waiterPair.wait();
			}
		}
		if (pair.zavrsenKviz == true) {
			synchronized (waiterPair) {
				waiterPair.notify();
			}
		}
		
	}
	private void finish() {
		isQuit = true;
		if(waiterPair != null) { 
			waiterPair.removeConnection();
		}
		//CUDNO, nekako iako izbrises iz waitersPair liste on ima drugog za dodeljivanje, super ali kako?
		if(Server.waitersPair.contains(waiterPair) && waiterPair.getConnectionCounter() == 0) {
			Server.waitersPair.remove(waiterPair);
		}
		waiterPair = null;
		if(username != null) {
			Server.onlineUsers.remove(this);
		}
	}
	
	private void startAsocijacije() {
		
		if(c%2!=0) {	
			clientOutput.println("zabraniEkran");
		}	
		//svi treba da zapocnu tajmer
		clientOutput.println("započniTajmer");
		
		int suma = 0;
		String input;
		try {
			do {
				// System.out.println("ceka na input server");
				input = clientInput.readLine();
				// System.out.println("dobio input"+input);
				switch (input) {
				case "završenTurn":
					clientOutput.println("zabraniEkran");
					pair.clientOutput.println("omogućiEkran");	
					a++;
					break;
				case "A1Kliknut":
						pair.clientOutput.println("A1Kliknut");
						break;
				case "A2Kliknut":
						pair.clientOutput.println("A2Kliknut");
						break;
				case "A3Kliknut":
						pair.clientOutput.println("A3Kliknut");
						break;
				case "A4Kliknut":
						pair.clientOutput.println("A4Kliknut");
						break;
				case "B1Kliknut":
						pair.clientOutput.println("B1Kliknut");
						break;
				case "B2Kliknut":
						pair.clientOutput.println("B2Kliknut");
						break;
				case "B3Kliknut":
						pair.clientOutput.println("B3Kliknut");
						break;
				case "B4Kliknut":
						pair.clientOutput.println("B4Kliknut");
						break;
				case "C1Kliknut":
						pair.clientOutput.println("C1Kliknut");
						break;
				case "C2Kliknut":
						pair.clientOutput.println("C2Kliknut");
						break;
				case "C3Kliknut":
						pair.clientOutput.println("C3Kliknut");
						break;
				case "C4Kliknut":
						pair.clientOutput.println("C4Kliknut");
						break;
				case "D1Kliknut":
						pair.clientOutput.println("D1Kliknut");
						break;
				case "D2Kliknut":
						pair.clientOutput.println("D2Kliknut");
						break;
				case "D3Kliknut":
						pair.clientOutput.println("D3Kliknut");
						break;
				case "D4Kliknut":
						pair.clientOutput.println("D4Kliknut");
						break;
				case "AKonacnoUpdate":
						pair.clientOutput.println("AKonacnoUpdate");
						break;
				case "BKonacnoUpdate":
						pair.clientOutput.println("BKonacnoUpdate");
						break;
				case "CKonacnoUpdate":
						pair.clientOutput.println("CKonacnoUpdate");
						break;
				case "DKonacnoUpdate":
						pair.clientOutput.println("DKonacnoUpdate");
						break;
				case "KonacnoUpdate":
						pair.clientOutput.println("KonacnoUpdate");
						break;
				case "ZavršenaPartija":
						clientOutput.println("ZavršenaPartija");
						pair.clientOutput.println("ZavršenaPartija");
						break;
				case "updatePoeni":
					suma = suma + a;
					suma=suma+pair.a;
					
					if (suma % 2 == 0) {
						// znaci da je prvi igrac na potezu
						// i da je on zvao update poeni
						// znaci da bi trebali da se updatuju samo poeni prvog kod svih
						input = clientInput.readLine();
						clientOutput.println("updatePoeniPrvog");
						clientOutput.println(input);
						pair.clientOutput.println("updatePoeniPrvog");
						pair.clientOutput.println(input);
						
					}
					if (suma % 2 == 1) {
						// znaci da je drugi igrac na potezu
						// i da je on zvao update poeni
						// znaci da bi trebali da se updatuju samo poeni drugog kod svih
						input = clientInput.readLine();
						clientOutput.println("updatePoeniDrugog");
						clientOutput.println(input);
						pair.clientOutput.println("updatePoeniDrugog");
						pair.clientOutput.println(input);
					}
					break;
				case "Izlaz":
					clientOutput.println("Izlaz");
					break;
				}

			} while (!input.equals("Izlaz"));

			//Server.onlineUsers.remove(this);
			System.out.println("ClientHandler se ugasio, izbacio klijenta iz liste");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	@Override
	public void run() {
		try {
			clientInput = new BufferedReader(new InputStreamReader(socketCommunication.getInputStream()));
			clientOutput = new PrintStream(socketCommunication.getOutputStream());
			
			setUsername();
			pairing();
			if(!isQuit) {
				startMojBroj();
			}
			if(!isQuit) {
				startQuiz();
			}
			if(!isQuit) {
				startAsocijacije();
			}
			
			finish();
			socketCommunication.close();
			System.out.println("Konekcija zatvorena.");
		} catch (IOException e) {
			finish();
			System.out.println("Konekcija zatvorena.");
		} catch (InterruptedException e) {
			finish();
			e.printStackTrace();
		}
	}

	
}
